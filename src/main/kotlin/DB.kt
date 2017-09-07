package hinst.massd2d.webs

import java.sql.DriverManager
import com.mysql.jdbc.Driver

class DB(val url: String) {
    init {
    }
    val userName = "massd2d_webs"
    val userPassword = "massd2d_webs"
    val commitHistory = CommitHistoryDB(this)

    fun getConnection() = DriverManager.getConnection(url, userName, userPassword)

    fun start() {
        getConnection().use {
            it.autoCommit = false
            commitHistory.start(it)
            it.commit()
        }
    }
}