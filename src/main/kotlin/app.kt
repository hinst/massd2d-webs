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
import java.nio.file.Files
import java.nio.file.Paths

class App(val webPath: String = "/massd2d") {
    fun run() {
        val server = embeddedServer(Netty, 8080) {
            routing {
                get(webPath + "/") { respondPage(call,"hello") }
                get(webPath + "/commitHistory") { }
                static(webPath + "/web-3rd") {
                    files("web-3rd")
                }
                get("/") {
                    call.respondText("URL outside root", ContentType.Text.Plain)
                }
            }
        }
        server.start(true)
    }

    private fun getPage(filePath: String): String {
        val template = loadFileString("src-web/main.html")
        val content = loadFileString("src-page/" + filePath)
        val text = template.replace("%content%", content)
                .replace("%webPath%", webPath)
        return text
    }

    private suspend fun respondPage(call: ApplicationCall, pageName: String) {
        call.respondText(getPage(pageName + ".html"), ContentType.Text.Html)
    }
}