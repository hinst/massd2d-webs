package hinst.massd2d.webs

import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.content.LocalFileContent
import org.jetbrains.ktor.content.files
import org.jetbrains.ktor.content.static
import org.jetbrains.ktor.features.ConditionalHeaders
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respond
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.routing
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.time.Instant
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
    val log = org.slf4j.LoggerFactory.getLogger(this.javaClass)

    fun run() {
        log.debug(webPath)
        db.start()
        commitHistoryMan.start()
        val server = embeddedServer(Netty, 9001) {
            install(ConditionalHeaders)
            routing {
                get(webPath + "/") { respondPage(call, "hello") }
                get(webPath + "/commitHistoryPage") { respondPage(call, "commitHistory") }
                get(webPath + "/downloadPage") { respondPage(call, "download") }
                get(webPath + "/commitHistory") { call.respondText(getCommitHistory(), ContentType.Application.Json) }
                get(webPath + "/accessHistorySum") {
                    val key = call.request.queryParameters["key"]
                    if (key != null)
                        call.respondText(getAccessHistorySum(key).toString(), ContentType.Text.Plain)
                }
                fun setFiles(folder: String) {
                    static(webPath + "/" + folder) {
                        files(appMainPath + "/" + folder)
                    }
                }
                setFiles("web-3rd")
                setFiles("src-js")
                setFiles("src-img")
                get(webPath + "/game-files") {
                    respondWithGameFile(call)
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
        var time = System.nanoTime()
        val row = commitHistoryMan.get()
        time = System.nanoTime() - time
        if (row != null) {
            val outerData = JSONObject(mapOf(
                "history" to JSONArray(row.content),
                    "latestUpdateMoment" to row.moment.toEpochMilli(),
                    "readingTime" to time)
            )
            return outerData.toString()
        }
        return "null"
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

    suspend fun respondWithGameFile(call: ApplicationCall) {
        val name = call.request.queryParameters["name"]
        val files = File(appMainPath + "/game-files").listFiles();
        val file = files.find { it.name == name }
        if (file != null) {
            val fileName = file.name
            call.response.headers.append("Content-Disposition", "attachment; filename=\"$fileName\"")
            call.respond(LocalFileContent(file))
            db.accessHistory.put(AccessHistoryDB.Row(Instant.now(), fileName))
        }
    }

    fun getAccessHistorySum(key: String): Int {
        return db.accessHistory.getSum(key)
    }

}
