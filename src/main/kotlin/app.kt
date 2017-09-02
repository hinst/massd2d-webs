package hinst.massd2d.webs

import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.routing

class App(val webPath: String = "/massd2d") {
    fun run() {
        val server = embeddedServer(Netty, 8080) {
            routing {
                get("/") {
                    call.respondText("URL outside root", ContentType.Text.Plain)
                }
                get(webPath + "/") {
                    call.respondText("URL outside root", ContentType.Text.Plain)
                }
            }
        }
        server.start(true)
    }
}