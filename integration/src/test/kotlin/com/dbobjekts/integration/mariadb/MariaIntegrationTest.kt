package com.dbobjekts.integration.mariadb

import com.dbobjekts.api.Catalogs
import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.metadata.column.BooleanColumn
import com.dbobjekts.util.HikariDataSourceFactory
import com.dbobjekts.vendors.Vendors
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import java.nio.file.Paths
import javax.sql.DataSource


//@Testcontainers
class MariaIntegrationTe0st {

    companion object {
        @Container
        @JvmStatic
        val CONTAINER = MariaDBWrapper()
        lateinit var ds: DataSource
        lateinit var tm: TransactionManager

        @JvmStatic
        @BeforeAll
        fun setup() {
            val port = 3306//CONTAINER.firstMappedPort
            ds = HikariDataSourceFactory
                .create(
                    url = "jdbc:mariadb://localhost:$port/test",
                    username = "root",
                    password = "test", driver = "org.mariadb.jdbc.Driver"
                )
            TransactionManager.initialize(ds, Catalogs.EMPTY_MARIADB_CATALOG)
            tm = TransactionManager.singleton()
            /*tm { tr ->
                tr.deleteFrom(EmployeeAddress).where()
                tr.deleteFrom(EmployeeDepartment).where()
                tr.deleteFrom(Department).where()
                tr.deleteFrom(Address).where()
                tr.deleteFrom(Certificate).where()
                tr.deleteFrom(Employee).where()
                tr.deleteFrom(Country).where()
                tr.deleteFrom(Hobby).where()
            }*/
        }
    }

    @Disabled
    @Test
    fun `generate MySQL sources`() {
        val generator = CodeGenerator()
        generator.dataSourceConfigurer()
            .vendor(Vendors.MARIADB)
            .withDataSource(ds)
        generator.mappingConfigurer().overrideTypeForColumnByJDBCType(jdbcType = "TINYINT", columnType = BooleanColumn::class.java)
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.integration.mariadb")
            //.sourceWriter(writer)
            .outputDirectoryForGeneratedSources(Paths.get("./src/generated-sources/kotlin").toAbsolutePath().toString())
        generator.generate()

    }

    //@Disabled
    //@Test
    /*
    fun `five minute tutorial`() {
        tm { tr ->

            // Let's start with some reference data. The mandatoryColumns method helps you to provide all non-nullable fields for the insert.
            tr.insert(Country).mandatoryColumns("nl", "Netherlands").execute()
            tr.insert(Country).mandatoryColumns("be", "Belgium").execute()
            tr.insert(Country).mandatoryColumns("de", "Germany").execute()

            // Insert statement in tables with an auto-generated ID return a positive Long with the newly created row primary key.
            val id = tr.insert(Employee).mandatoryColumns("Bill", 3050.3, LocalDate.of(1980, 5, 7)).execute()
            assertThat(id).isPositive()
            // now we can update Bill's record by id
            tr.update(Employee).salary(4000.0).married(true).where(e.id.eq(id))

            //The 'name' column in the Hobby project is non-null, and so is the corresponding Column implementation
            // However, in an outer join, it can be null and therefore we pick the nullable counterpart of the column value, which return String?
            val optionalHobby: Tuple2<String, String?> = tr.select(e.name, h.name.nullable)
                .from(e.leftJoin(h))
                .where(e.salary.gt(3000.0)).first()
            assertThat(optionalHobby.v1).isEqualTo("Bill")
            assertThat(optionalHobby.v2).isNull()

            val row: Tuple3<String, Double?, Boolean?> = tr.sql(
                "select name, salary, married from core.EMPLOYEE where date_of_birth > ?", LocalDate.of(1980, 1, 1)
            ).withResultTypes().string().doubleNil().booleanNil().first()
            assertThat(row.v1).isEqualTo("Bill")
            assertThat(row.v2).isEqualTo(4000.0)
            assertThat(row.v3).isTrue()

            // We have Pete, Jane and Bob.
            val petesId: Long = tr.insert(Employee).mandatoryColumns("Pete", 5020.34, LocalDate.of(1980, 5, 7)).married(true).execute()
            val janesId: Long = tr.insert(Employee).mandatoryColumns("Jane", 6020.0, LocalDate.of(1978, 5, 7)).married(false).execute()
            tr.insert(Employee).mandatoryColumns("Bob", 3020.34, LocalDate.of(1980, 5, 7)).execute()

            // Jane works in Belgium, Pete works in Germany, and they both live together in the Netherlands.
            val janesWorkAddress: Long = tr.insert(Address).mandatoryColumns("Rue d'Eglise", "be").execute()
            val petesWorkAddress: Long = tr.insert(Address).street("Kirchstrasse").countryId("de").execute()
            val janeAndPetesHomeAddress: Long = tr.insert(Address).mandatoryColumns("Kerkstraat", "nl").execute()

            tr.insert(EmployeeAddress).mandatoryColumns(janesId, janesWorkAddress, "WORK").execute()
            tr.insert(EmployeeAddress).mandatoryColumns(petesId, petesWorkAddress, "WORK").execute()
            tr.insert(EmployeeAddress).mandatoryColumns(petesId, janeAndPetesHomeAddress, "HOME").execute()
            tr.insert(EmployeeAddress).mandatoryColumns(janesId, janeAndPetesHomeAddress, "HOME").execute()

            // This query selects name and salary for all rows in the employee table. Notice we have imported the 'e' alias from the Aliases object. This is a handy shortcut that refers to the exact same Employee object.
            // Consider the different methods to retrieve results
            assertThat(tr.select(e.name, e.salary).asList()).hasSize(4)
            val noResults =
                tr.select(e.name, e.salary).where(e.name.eq("Vlad")).firstOrNull()// Null when no match
            assertThat(noResults).isNull()
            //if you call first() on an empty result, it throws
            assertThatThrownBy { tr.select(e.name, e.salary).where(e.name.eq("Vlad")).first() }

            //Nested conditions in the where-clause are possible:
            val listOfIds = tr.select(e.id).where(e.salary.gt(6000.0).or(e.married.eq(true).and(e.salary).isNotNull())).asList()
            assertThat(listOfIds).hasSize(3)

            //Pete and Jane live on the same address
            // Note that all the table joins are made automatically
            val results: List<Tuple4<String, LocalDate, String, String>> =
                tr.select(e.name, e.dateOfBirth, ea.kind, c.name).where(a.street.eq("Kerkstraat"))
                    .orderDesc(e.name).asList()

            assertThat(results).hasSize(2)
            val (name, dob, addressType, country) = results.get(0)
            assertThat(name).isEqualTo("Pete")
            assertThat(dob).isEqualTo(LocalDate.of(1980, 5, 7))
            assertThat(addressType).isEqualTo("HOME")
            assertThat(country).isEqualTo("Netherlands")

            //Select with range
            //String matching methods startsWith, endsWith and contains produce SQL with LIKE '%xxx' syntax
            val dt = LocalDate.of(1980, 5, 7)
            tr.insert(Employee).mandatoryColumns("Arthur King", 1000.0, dt).execute()
            tr.insert(Employee).mandatoryColumns("Arthur Philip Dent", 1000.0, dt).execute()
            tr.insert(Employee).mandatoryColumns("Philip Jones", 1000.0, dt).execute()
            tr.insert(Employee).mandatoryColumns("Trisha Dent", 1000.0, dt).execute()
            assertThat(
                tr.select(e.name).where(
                    e.name.startsWith("Arthur")
                        .or(e.name.endsWith("Dent").or(e.name.contains("Philip")))
                ).asList()
            ).hasSize(4)

        }
    }
*/


}
