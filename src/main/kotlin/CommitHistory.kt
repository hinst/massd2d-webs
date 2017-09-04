package hinst.massd2d.webs

import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import org.apache.commons.io.IOUtils
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object CommitHistory {
    fun loadFromBitbucket(userName: String, userPassword: String, repoSlug: String): Array<ChangesetInfo> {
        val url = "https://api.bitbucket.org/2.0/repositories/" + userName + "/" + repoSlug + "/commits?pagelen=100"
        val response = Unirest.get(url).basicAuth(userName, userPassword).asJson()
        if (false) {
            val sourceText = IOUtils.toString(response.rawBody, "UTF-8")
        }
        val sourceObject = response.body
        val commitSourceArray = sourceObject.`object`.getJSONArray("values")
        val items = Array(commitSourceArray.count(), { i ->
            ChangesetInfo(
                    commitSourceArray.getJSONObject(i).getString("message"),
                    OffsetDateTime.parse(commitSourceArray.getJSONObject(i).getString("date")))
        })
        return items
    }

    fun renderJson(items: Array<ChangesetInfo>): String {
        val json = JSONArray()
        items.forEach { item -> json.put(mapOf("message" to item.message,
                "moment" to item.date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))))
        }
        val text = json.toString()
        return text
    }

}