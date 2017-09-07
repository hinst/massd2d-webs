package hinst.massd2d.webs

import java.net.URLEncoder
import java.util.*

class CommitHistoryMan(
    val db: DB,
    val userName: String,
    val userPassword: String,
    val repoSlug: String
) {
    companion object {
        const val initialUpdateInterval: Long = 30 * 1000;
        const val updateInterval: Long = 12 * 60 * 60 * 1000;

    }

    fun start() {
        Timer().schedule(UpdaterTask(), initialUpdateInterval, updateInterval)
    }

    fun load(): String {
        val items = CommitHistory.loadFromBitbucket(userName, userPassword, repoSlug)
        val text = CommitHistory.renderJson(items)
        return text
    }

    fun update() {
        val text = load()
        val row = CommitHistoryDB.Row(rowId, Date(), text)
        db.commitHistory.put(row)
    }

    inner class UpdaterTask() : TimerTask() {
        override fun run() {
            this@CommitHistoryMan.update()
        }
    }

    val rowId: String
        get() = URLEncoder.encode(userName, "UTF-8") + "/" + URLEncoder.encode(repoSlug, "UTF-8")

    fun get() {
        db.commitHistory.get(rowId)
    }

}