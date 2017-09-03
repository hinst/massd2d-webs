package hinst.massd2d.webs

import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.content.files
import org.jetbrains.ktor.content.static
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.routing
import java.io.FileInputStream
import java.util.*

class App(val webPath: String = "/massd2d", val configFileName: String = "config-desktop.properties") {
    private val config = Properties()
    init {
        config.load(FileInputStream(appMainPath + "/" + configFileName))
    }
    private val db = DB(config.getProperty("database"))

    fun run() {
        db.start()
        val server = embeddedServer(Netty, 9001) {
            routing {
                get(webPath + "/") { respondPage(call,"hello") }
                get(webPath + "/commitHistoryPage") { respondPage(call, "commitHistory") }
                get(webPath + "/commitHistory") { call.respondText(getCommitHistory(), ContentType.Application.Json) }
                static(webPath + "/web-3rd") {
                    files(appMainPath + "/web-3rd")
                }
                static(webPath + "/src-js") {
                    files(appMainPath + "/src-js")
                }
                get("/") {
                    call.respondText("URL outside root", ContentType.Text.Plain)
                }
            }
        }
        server.start(true)
    }

    private fun getPage(filePath: String): String {
        val template = loadFileString(appMainPath + "/src-web/main.html")
        val content = loadFileString(appMainPath + "/src-page/" + filePath)
            .replace("%webPath%", webPath)
        val text = template.replace("%content%", content)
            .replace("%webPath%", webPath)
        return text
    }

    private suspend fun respondPage(call: ApplicationCall, pageName: String) {
        call.respondText(getPage(pageName + ".html"), ContentType.Text.Html)
    }

    val bitBucketPassword
        get() = loadFileString(appMainPath + "/secret/hinst_bbp")

    private fun getCommitHistory(): String {
        val commitHistory = CommitHistory("hinst", bitBucketPassword, "massd2d")
        return commitHistory.text
    }
}
