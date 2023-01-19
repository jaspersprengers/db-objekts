package com.dbobjekts.mariadb.testdb
import com.dbobjekts.mariadb.testdb.core.Address
import com.dbobjekts.mariadb.testdb.core.AllTypesNil
import com.dbobjekts.mariadb.testdb.core.Composite
import com.dbobjekts.mariadb.testdb.core.CompositeForeignKey
import com.dbobjekts.mariadb.testdb.core.Country
import com.dbobjekts.mariadb.testdb.core.Department
import com.dbobjekts.mariadb.testdb.core.Employee
import com.dbobjekts.mariadb.testdb.core.EmployeeAddress
import com.dbobjekts.mariadb.testdb.core.EmployeeDepartment
import com.dbobjekts.mariadb.testdb.hr.Certificate
import com.dbobjekts.mariadb.testdb.hr.Hobby
import com.dbobjekts.mariadb.testdb.nation.Continents
import com.dbobjekts.mariadb.testdb.nation.Countries
import com.dbobjekts.mariadb.testdb.nation.CountryLanguages
import com.dbobjekts.mariadb.testdb.nation.CountryStats
import com.dbobjekts.mariadb.testdb.nation.Guests
import com.dbobjekts.mariadb.testdb.nation.Languages
import com.dbobjekts.mariadb.testdb.nation.Regions
import com.dbobjekts.mariadb.testdb.nation.RegionAreas
import com.dbobjekts.mariadb.testdb.nation.Vips

/**  
 * Auto-generated metadata object. Provides a list of unique aliases for each table across all schemas in the catalog. These are used in the SQL that db-Objekts generates.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.
 *
 * You can import the individual values of in this object in your code to use them as convenient shortcuts.
 *
 * ```
 * import com.dbobjekts.mariadb.testdb.Aliases.e
 * [...]
 * transaction.select(e.name)
 *```
 */        
object Aliases : HasAliases {
    override val a = Address
    override val atn = AllTypesNil
    override val c1 = Composite
    override val cfk = CompositeForeignKey
    override val c = Country
    override val d = Department
    override val e = Employee
    override val ea = EmployeeAddress
    override val ed = EmployeeDepartment
    override val c2 = Certificate
    override val h = Hobby
    override val c3 = Continents
    override val c4 = Countries
    override val cl = CountryLanguages
    override val cs = CountryStats
    override val g = Guests
    override val l = Languages
    override val r = Regions
    override val ra = RegionAreas
    override val v = Vips
}

/**  
 * Auto-generated metadata object. Interface for a list of unique aliases for each table across all schemas in the catalog.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.
 *
 * Any class can extends this interface and delegate to the Aliases object. This imports all the shortcuts without the need for separate manual import statements 
 *
 * ```
 * import com.dbobjekts.mariadb.testdb.Aliases
 * import com.dbobjekts.mariadb.testdb.HasAliases
 * [...]
 * class AcmeDbInteractions : HasAliases by Aliases {
 *  transaction.select(e.name, a.id, ea.kind)
 * }
 *```
 */ 
interface HasAliases {
    val a : Address
    val atn : AllTypesNil
    val c1 : Composite
    val cfk : CompositeForeignKey
    val c : Country
    val d : Department
    val e : Employee
    val ea : EmployeeAddress
    val ed : EmployeeDepartment
    val c2 : Certificate
    val h : Hobby
    val c3 : Continents
    val c4 : Countries
    val cl : CountryLanguages
    val cs : CountryStats
    val g : Guests
    val l : Languages
    val r : Regions
    val ra : RegionAreas
    val v : Vips
}
     