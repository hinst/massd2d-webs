package hinst.massd2d.webs

import com.mashape.unirest.http.Unirest
import java.util.*

class Main

fun main(args: Array<String>) {
    if (false) {
        println(dateToStringDirect(Date()))
        return
    }
    println("STARTING... appMainPath=" + appMainPath)
    val app = if (args.size > 0) App(configFileName = args[0]) else App()
    app.run()
    Unirest.shutdown()
    println("EXITING...")
}