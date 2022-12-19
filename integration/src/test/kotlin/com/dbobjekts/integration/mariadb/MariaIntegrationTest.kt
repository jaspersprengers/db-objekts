package com.dbobjekts.integration.mariadb

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.api.TransactionManagerBuilder
import com.dbobjekts.api.Tuple2
import com.dbobjekts.api.Tuple4
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.integration.mariadb.Aliases.a
import com.dbobjekts.integration.mariadb.Aliases.c
import com.dbobjekts.integration.mariadb.Aliases.e
import com.dbobjekts.integration.mariadb.Aliases.ea
import com.dbobjekts.integration.mariadb.Aliases.h
import com.dbobjekts.integration.mariadb.core.Address
import com.dbobjekts.integration.mariadb.core.Country
import com.dbobjekts.integration.mariadb.core.Employee
import com.dbobjekts.integration.mariadb.core.EmployeeAddress
import com.dbobjekts.metadata.column.BooleanColumn
import com.dbobjekts.util.HikariDataSourceFactory
import com.dbobjekts.util.TestSourceWriter
import com.dbobjekts.vendors.Vendors
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.nio.file.Paths
import java.time.LocalDate
import javax.sql.DataSource


@Testcontainers
class MariaIntegrationTest {

    companion object {
        @Container
        @JvmStatic
        val CONTAINER = MariaDBWrapper()
        lateinit var ds: DataSource
        lateinit var tm: TransactionManager

        @JvmStatic
        @BeforeAll
        fun setup() {
            val port = CONTAINER.firstMappedPort
            ds = HikariDataSourceFactory
                .create(
                    url = "jdbc:mariadb://localhost:$port/test",
                    username = "root",
                    password = "test", driver = "org.mariadb.jdbc.Driver"
                )
            TransactionManagerBuilder().dataSource(ds).catalog(CoreCatalog).buildForSingleton()
            tm = TransactionManager.singletonInstance()
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

    @Test
    fun `generate MySQL sources`() {
        val writer = TestSourceWriter()
        val generator = CodeGenerator()
        generator.dataSourceConfigurer()
            .vendor(Vendors.MARIADB)
            .withDataSource(ds)
        generator.mappingConfigurer().overrideTypeForColumnByJDBCType(jdbcType = "TINYINT", columnType = BooleanColumn::class.java)
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.integration.mariadb")
            //.sourceWriter(writer)
            .outputDirectoryForGeneratedSources(Paths.get("../core/src/generated-sources/kotlin").toAbsolutePath().toString())
        generator.generate()
        print(writer.toString())

    }

    @Test
    fun `five minute tutorial`() {
        tm { tr ->

            val id = tr.insert(Employee).mandatoryColumns("Bill", 3050.3, LocalDate.of(1980, 5, 7)).execute()
            assertThat(id).isPositive()
            tr.update(Employee).salary(4000.0).married(true).where(e.id.eq(id))

            val optionalHobby: Tuple2<String, String?> = tr.select(e.name, h.name.nullable)
                .from(e.leftJoin(h))
                .where(e.salary.gt(3000.0)).first()
            assertThat(optionalHobby.first).isEqualTo("Bill")
            assertThat(optionalHobby.second).isNull()

            // The mandatoryColumns method helps you to provide all non-nullable fields for the insert.
            tr.insert(Country).mandatoryColumns("nl", "Netherlands").execute()
            tr.insert(Country).mandatoryColumns("be", "Belgium").execute()
            tr.insert(Country).mandatoryColumns("de", "Germany").execute()

            val row = tr.sql(
                "select name, salary, married from core.EMPLOYEE where date_of_birth > ?", LocalDate.of(1980, 1, 1)
            ).withResultTypes().string().doubleNil().booleanNil().first()
            assertThat(row.first).isEqualTo("Bill")
            assertThat(row.second).isEqualTo(4000.0)
            assertThat(row.third).isTrue()

            // We have Pete, Jane and Bob. If an insert resulted in a generated primary key value, it is returned as a Long from the execute() method.
            val petesId: Long = tr.insert(Employee).mandatoryColumns("Pete", 5020.34, LocalDate.of(1980, 5, 7)).married(true).execute()
            val janesId: Long = tr.insert(Employee).mandatoryColumns("Jane", 6020.0, LocalDate.of(1978, 5, 7)).married(false).execute()
            val bobsId: Long = tr.insert(Employee).mandatoryColumns("Bob", 3020.34, LocalDate.of(1980, 5, 7)).execute()

            // Jane works in Belgium, Pete works in Germany, and they both live in the Netherlands.
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
            val asNullable: Tuple2<String, Double>? =
                tr.select(e.name, e.salary).where(e.name.eq("Vlad")).firstOrNull()// Null when no match
            assertThat(asNullable).isNull()
            assertThatThrownBy { tr.select(e.name, e.salary).where(e.name.eq("Vlad")).first() }// throws when no match }

            //Nested conditions in the where-clause are possible:
            val listOfIds = tr.select(e.id).where(e.salary.gt(6000.0).or(e.married.eq(true).and(e.salary).isNotNull())).asList()
            assertThat(listOfIds).hasSize(3)

            //Pete and Jane live on the same address
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


}
