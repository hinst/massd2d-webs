package hinst.massd2d.webs

import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.content.files
import org.jetbrains.ktor.content.static
import org.jetbrains.ktor.features.ConditionalHeaders
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.routing
import java.io.File
import java.io.FileInputStream
import java.util.*

class App(val configFileName: String = "config-desktop.properties") {
    private val config = Properties()
    init {
        config.load(FileInputStream(appMainPath + "/" + configFileName))
    }
    val webPath = config.getProperty("webPath")
    private val db = DB(config.getProperty("database"))
    private val bitBucketPassword
        get() = loadFileString(appMainPath + "/secret/hinst_bbp")
    private val commitHistoryMan = CommitHistoryMan(db = db, userName = "hinst", userPassword = bitBucketPassword, repoSlug = "massd2d")

    fun run() {
        db.start()
        commitHistoryMan.start()
        val server = embeddedServer(Netty, 9001) {
            install(ConditionalHeaders)
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
                static(webPath + "/src-style") {
                    files(appMainPath + "/src-style")
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
        val text = replacePageVars(template.replace("%content%", content))
        return text
    }

    private suspend fun respondPage(call: ApplicationCall, pageName: String) {
        call.respondText(getPage(pageName + ".html"), ContentType.Text.Html)
    }

    private fun getCommitHistory(): String {
        val items = CommitHistory.loadFromBitbucket("hinst", bitBucketPassword, "massd2d")
        val text = CommitHistory.renderJson(items)
        return text
    }

    private fun replacePageVars(source: String): String {
        var text = source.replace("%webPath%", webPath)
        val templates = File(appMainPath + "/src-template").listFiles();
        templates.forEach { template -> run {
            val key = "%" + template.name + "%"
            if (text.indexOf(key) >= 0)
                text = text.replace(key, loadFileString(template.absolutePath))
        }}
        return text
    }
}
