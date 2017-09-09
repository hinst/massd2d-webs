package hinst.massd2d.webs

import java.util.*

fun dateToStringDirect(d: Date): String {
    val calendar = GregorianCalendar(TimeZone.getTimeZone("UTC"))
    calendar.time = d
    return "" + calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH)+1) + "." + calendar.get(Calendar.DAY_OF_MONTH) +
        " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + " h" + d.hours
}

fun dateToUTCDate(d: Date) {
    val calendar = GregorianCalendar(TimeZone.getTimeZone("UTC"))
    calendar.time = d
}