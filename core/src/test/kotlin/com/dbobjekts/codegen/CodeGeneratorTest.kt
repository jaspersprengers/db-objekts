package com.dbobjekts.codegen

import com.dbobjekts.api.Columns
import com.dbobjekts.codegen.parsers.TableMetaDataRow
import com.dbobjekts.metadata.column.*
import com.dbobjekts.metadata.column.AutoKeyLongColumn
import com.dbobjekts.util.PathUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class CodeGeneratorTest {


    fun setup(): CodeGenerator {
        val generator = CodeGenerator()
        generator.dataSourceConfigurer()
            .vendor("H2")
            .configureDataSource().url("jdbc:h2:mem:test").user("sa")
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.integration.h2")
        return generator
    }

    @Test
    fun `create schemas`() {

        fun validate(dataType: String, autoIncrement: Boolean, expectedClass: Class<*>, sequence: String? = null) {
            val generator = setup()
            val parserFactory = MockDBParserFactory()
            val id = TableMetaDataRow(
                schema = "core",
                table = "EMPLOYEE",
                column = "id",
                dataType = dataType,
                isPrimaryKey = true,
                nullable = false,
                autoIncrement = autoIncrement
            )
            parserFactory.addRow(id)
            generator.parserFactory = parserFactory
            sequence?.let { generator.mappingConfigurer().sequenceForPrimaryKey("core", "employee", "id", it) }
            val helper = CatalogDefinitionHelper(generator.createCatalogDefinition())
            val t = helper.getTable(table = "EMPLOYEE")!!
            val idCol = helper.getColumn(t, "ID")!!
            assertThat(idCol.column).isInstanceOf(expectedClass)

        }

        validate("BIGINT", false, LongColumn::class.java)
        validate("INTEGER", false, IntegerColumn::class.java)

        validate("BIGINT", true, LongColumn::class.java)
        validate("INTEGER", true, AutoKeyIntegerColumn::class.java)

        // auto increment setting supersedes a matching index
        validate("BIGINT", true, AutoKeyLongColumn::class.java, "employee_seq")
        validate("INTEGER", true, AutoKeyIntegerColumn::class.java, "employee_seq")

        validate("BIGINT", false, SequenceKeyLongColumn::class.java, "employee_seq")
        validate("INTEGER", false, SequenceKeyIntegerColumn::class.java, "employee_seq")

    }

    @Test
    fun `edge cases`() {
        // non-nullable nullable column with default value?

        // auto increment setting supersedes a matching index
    }

    /*   @Test
       fun `test UUID`() {
           TransactionManager.newTransaction { tr ->
               val uuid = UUID.randomUUID()
               val id = tr.insert(AllTypes).uuidC(uuid).execute()
               val retrieved = tr.select(AllTypes.uuidC).where(AllTypes.id.eq(id)).first()
               Assertions.assertThat(retrieved.toString()).isEqualTo(uuid.toString())
           }
       }*/

    /*  @Test
      fun `cannot exclude foreign key reference`() {
          val writer = TestSourceWriter()
          val generator = CodeGenerator()

          generator.dataSourceConfigurer()
              .vendor("h2")

          generator.exclusionConfigurer()
              .ignoreColumns("COUNTRY_ID")

          generator.outputConfigurer()
              .basePackageForSources("com.dbobjekts")
          Assertions.assertThatThrownBy { generator.createCatalogDefinition() }
              .hasMessage("Column COUNTRY_ID VARCHAR(10) NOT NULL cannot be marked for exclusion. It is either a primary key or foreign key.")

      }*/

    /*
        private fun setupTables() {
            createManager().newTransaction { transaction ->
                transaction.execute("CREATE SCHEMA if not exists core authorization sa")
                transaction.execute("CREATE SCHEMA if not exists hr authorization sa")
                transaction.execute("CREATE SCHEMA if not exists custom authorization sa")

                transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.EMPLOYEE_SEQ START WITH 10")
                transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.ADDRESS_SEQ START WITH 10")
                transaction.execute("CREATE SEQUENCE IF NOT EXISTS hr.CERTIFICATE_SEQ START WITH 10")
                transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.DEPARTMENT_SEQ START WITH 10")

                transaction.execute("create table IF NOT EXISTS hr.HOBBY(id varchar(10) primary key, name varchar(50) not null)")
                transaction.execute(
                    "create table IF NOT EXISTS core.EMPLOYEE(id BIGINT primary key, name varchar(50) not null, salary double not null, " +
                            "married boolean null, date_of_birth DATE not null, children SMALLINT null, hobby_id varchar(10) null, foreign key(hobby_id) references hr.HOBBY(id))"
                )
                transaction.execute("create table IF NOT EXISTS hr.CERTIFICATE(id BIGINT primary key, name varchar(50) not null, employee_id BIGINT not null, foreign key(employee_id) references core.employee(id))")
                transaction.execute("create table IF NOT EXISTS core.COUNTRY(id varchar(10) primary key, name varchar(50) not null)")

                transaction.execute("create table IF NOT EXISTS core.ADDRESS(id BIGINT primary key, street varchar(50) not null, country_id varchar(10) not null, foreign key(country_id) references core.country(id))")
                transaction.execute("create table if not exists core.EMPLOYEE_ADDRESS(employee_id BIGINT not null, address_id BIGINT not null,kind varchar(10) not null, foreign key(employee_id) references core.employee(id), foreign key(address_id) references core.ADDRESS(id))")
                transaction.execute("create table if not exists core.SHAPE (height DOUBLE, width DOUBLE)")
                transaction.execute("create table IF NOT EXISTS core.DEPARTMENT(id BIGINT primary key, name varchar(50) not null)")
                transaction.execute(
                    "create table if not exists core.EMPLOYEE_DEPARTMENT(employee_id BIGINT not null, department_id BIGINT not null, foreign key(employee_id) references core.employee(id), " +
                            "foreign key(department_id) references core.DEPARTMENT(id))"
                )
                transaction.execute(
                    "create table IF NOT EXISTS custom.ALL_TYPES(" +
                            "ID BIGINT primary key auto_increment," +
                            "TINYINT_C TINYINT, " +
                            "SMALLINT_C SMALLINT, " +
                            "INTEGER_C INTEGER," +
                            "INT_C INT, " +
                            "CHAR_C CHAR," +
                            "BLOB_C BLOB," +
                            "CLOB_C CLOB," +
                            "VARCHAR_C VARCHAR(50)," +
                            "BIGINT_C BIGINT, " +
                            "FLOAT_C FLOAT, " +
                            "DOUBLE_C DOUBLE, " +
                            "TIME_C TIME, " +
                            "DATE_C DATE, " +
                            "TIMESTAMP_C TIMESTAMP, " +
                            "TIMESTAMP_TZ_C TIMESTAMP WITH TIME ZONE," +
                            "BOOLEAN_C BOOLEAN, " +
                            "UUID_C UUID," +
                            "INT_BOOLEAN_C TINYINT)"
                )
                transaction.execute("create table IF NOT EXISTS CUSTOM.TUPLES(c1 INTEGER,c2 INTEGER,c3 INTEGER,c4 INTEGER,c5 INTEGER,c6 INTEGER,c7 INTEGER,c8 INTEGER,c9 INTEGER,c10 INTEGER,c11 INTEGER,c12 INTEGER,c13 INTEGER,c14 INTEGER,c15 INTEGER,c16 INTEGER,c17 INTEGER,c18 INTEGER,c19 INTEGER,c20 INTEGER,c21 INTEGER,c22 INTEGER)")

            }
        }

        private fun createManager(): TransactionManager {
            val ds = HikariDataSourceFactory.create(url = "jdbc:h2:mem:test", username = "sa", password = null, driver = "org.h2.Driver")
            TransactionManager.builder()
                .dataSource(dataSource = ds)
                .catalog(Catalogdefinition)
                .buildForSingleton()
            return TransactionManager.singletonInstance()
        }*/

}
