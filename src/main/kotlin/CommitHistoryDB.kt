package hinst.massd2d.webs

import java.sql.ResultSet
import java.time.Instant
import java.time.OffsetDateTime
import java.util.*

class CommitHistoryDB(val db: DB) {
    data class Row(val id: String, val moment: Date, val content: String) {
        constructor(row: ResultSet) : this(
            id = row.getString("id"),
            moment = row.getDate("moment"),
            content = row.getString("content"))
    }

    val tableName = "RepoCommitHistoryCache"
    val fields = "id, moment, content"
    val createTableStatement = "CREATE TABLE IF NOT EXISTS $tableName(id VARCHAR(100) PRIMARY KEY, moment DATETIME, content TEXT);"

    fun start(connection: java.sql.Connection) {
        connection.createStatement().use {
            it.execute(createTableStatement)
        }
    }

    fun get(id: String): Row? {
        var row: Row? = null;
        db.getConnection().use {
            it.prepareStatement(
                "SELECT $fields FROM RepoCommitHistoryCache WHERE id=?;").use {
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

    fun put(row: Row) {
        db.getConnection().use {
            it.autoCommit = false
            it.prepareStatement("INSERT INTO $tableName (id) VALUES(?) ON DUPLICATE KEY UPDATE id=id").use{
                it.setString(1, row.id)
                it.execute()
            }
            val updateStatement = "UPDATE $tableName SET moment=?, content=? where id=?"
            //                                                  1          2          3
            it.prepareStatement(updateStatement).use {
                it.setDate(1, java.sql.Date(row.moment.time))
                it.setString(2, row.content)
                it.setString(3, row.id)
                it.execute()
            }
            it.commit()
        }
    }
}
