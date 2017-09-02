package hinst.massd2d.webs

import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.routing

class App {
    fun run() {
        val server = embeddedServer(Netty, 8080) {
            routing {
                get("/") {
                    call.respondText("Page not found", ContentType.Text.Plain)
                }
            }
        }
        server.start(true)
    }
}