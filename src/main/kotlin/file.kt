package hinst.massd2d.webs

import sun.misc.IOUtils
import java.io.File
import java.io.InputStream
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Paths

fun loadFileString(filePath: String): String =
    String(Files.readAllBytes(Paths.get(filePath)), charset("UTF-8"))

val jarFilePath = Main().javaClass.protectionDomain.codeSource.location.toURI().path!!
val appMainPath = File(jarFilePath).parentFile.parentFile.parentFile.parentFile.parentFile.path!!

