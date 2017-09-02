package hinst.massd2d.webs

import java.nio.file.Files
import java.nio.file.Paths

fun loadFileString(filePath: String): String =
    String(Files.readAllBytes(Paths.get(filePath)), charset("UTF-8"));