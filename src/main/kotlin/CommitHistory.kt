package hinst.massd2d.webs

import com.mashape.unirest.http.Unirest
import org.apache.commons.io.IOUtils
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CommitHistory(val userName: String, val userPassword: String, val repoSlug: String) {
    val items: Array<ChangesetInfo>
    val json: JSONArray
    val text: String
    init {
        val url = "https://api.bitbucket.org/2.0/repositories/" + userName + "/" + repoSlug + "/commits?pagelen=100"
        val response = Unirest.get(url).basicAuth(userName, userPassword).asJson()
        val sourceText = IOUtils.toString(response.rawBody, "UTF-8")
        val sourceObject = response.body
        val commitSourceArray = sourceObject.`object`.getJSONArray("values")
        items = Array(commitSourceArray.count(), { i ->
            ChangesetInfo(
                commitSourceArray.getJSONObject(i).getString("message"),
                OffsetDateTime.parse(commitSourceArray.getJSONObject(i).getString("date")))
        })
        json = JSONArray()
        items.forEach { item -> json.put(mapOf("message" to item.message,
            "moment" to item.date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))))
        }
        text = json.toString()
    }
}