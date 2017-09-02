package hinst.massd2d.webs

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun loadFileString(filePath: String): String =
    String(Files.readAllBytes(Paths.get(filePath)), charset("UTF-8"))

val jarFilePath = App().javaClass.protectionDomain.codeSource.location.toURI().path!!
val appMainPath = File(jarFilePath).parentFile.parentFile.parentFile.parentFile.parentFile.path!!