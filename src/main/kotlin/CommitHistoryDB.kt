package hinst.massd2d.webs

import java.sql.ResultSet
import java.util.*

class CommitHistoryDB(val db: DB) {
    object Statements {
        val createTable = "CREATE TABLE IF NOT EXISTS RepoCommitHistoryCache(id VARCHAR(100) PRIMARY KEY, moment DATETIME, content TEXT);"
    }

    data class Row(val id: String, val moment: Date, val content: String) {
        constructor(row: ResultSet) : this(
            id = row.getString("id"),
            moment = row.getDate("moment"),
            content = row.getString("content"))
    }

    fun get(id: String): Row? {
        var row: Row? = null;
        db.getConnection().use {
            it.prepareStatement(
                "SELECT id, moment, content FROM RepoCommitHistoryCache WHERE id=?;").use {
                it.setString(1, id)
                it.executeQuery().use {
                    while (it.next()) {
                        row = Row(it)
                        break
                    }
                }
            }
        }
        return row
    }
}
