package hinst.massd2d.webs

import java.time.Instant

class AccessHistoryDB(val db: DB) {
    data class Row(val moment: Instant, val key: String)
    val tableName = "AccessHistory"
    val createTableStatement = "CREATE TABLE IF NOT EXISTS $tableName(moment DATETIME, `key` TINYTEXT);"
    val log = org.slf4j.LoggerFactory.getLogger(this.javaClass)

    fun start(connection: java.sql.Connection) {
        connection.createStatement().use {
            it.execute(createTableStatement)
        }
    }

    fun put(row: Row) {
        db.getConnection().use {
            it.autoCommit = false
            val statement = "INSERT INTO $tableName(moment, `key`) VALUES(?, ?)"
            //                                      1       2           1  2
            it.prepareStatement(statement).use {
                it.setTimestamp(1, java.sql.Timestamp(row.moment.toEpochMilli()))
                it.setString(2, row.key)
                it.execute()
            }
            it.commit()
        }
    }

}