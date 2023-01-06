package com.dbobjekts.mariadb.testdb
import com.dbobjekts.mariadb.testdb.core.Core
import com.dbobjekts.mariadb.testdb.hr.Hr
import com.dbobjekts.mariadb.testdb.nation.Nation
import com.dbobjekts.mariadb.testdb.core.Address
import com.dbobjekts.mariadb.testdb.core.AllTypesNil
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

object Aliases {
    val a = Address
    val atn = AllTypesNil
    val c = Country
    val d = Department
    val e = Employee
    val ea = EmployeeAddress
    val ed = EmployeeDepartment
    val c1 = Certificate
    val h = Hobby
    val c2 = Continents
    val c3 = Countries
    val cl = CountryLanguages
    val cs = CountryStats
    val g = Guests
    val l = Languages
    val r = Regions
    val ra = RegionAreas
    val v = Vips
}
     