package hinst.massd2d.webs

import com.mashape.unirest.http.Unirest
import org.apache.commons.io.IOUtils

class CommitHistory(val userName: String, val userPassword: String, val repoSlug: String) {
    val data: String
    init {
        val url = "https://api.bitbucket.org/2.0/repositories/" + userName + "/" + repoSlug + "/commits"
        val response = Unirest.get(url).basicAuth(userName, userPassword).asJson()
        data = IOUtils.toString(response.rawBody, "UTF-8")
    }
}