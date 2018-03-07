package utils

import java.nio.file.{Files, Path, Paths}

object Writer {

  /**
    * Function, that writes clients to a .txt file
    */
  def writeToFile(outcomeResult: List[Client]): Path = {
    val content = outcomeResult.mkString("\n").getBytes
    Files.write(Paths.get("result.txt"), content)

  }
}
