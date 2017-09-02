package hinst.massd2d.webs

import java.sql.DriverManager
import com.mysql.jdbc.Driver

class DB(val url: String) {
    init {
        println(url)
    }
    val userName = "massd2d_webs"
    val userPassword = "massd2d_webs"

    fun getConnection() = DriverManager.getConnection(url, userName, userPassword)

    fun start() {
        getConnection().use {
            it.createStatement().use {
                it.execute("select 0;")
            }
        }
    }
}