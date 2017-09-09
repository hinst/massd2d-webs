package hinst.massd2d.webs

import com.mashape.unirest.http.Unirest
import java.util.*

class Main

fun main(args: Array<String>) {
    println("STARTING... appMainPath=" + appMainPath)
    TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    val app = if (args.size > 0) App(configFileName = args[0]) else App()
    app.run()
    Unirest.shutdown()
    println("EXITING...")
}