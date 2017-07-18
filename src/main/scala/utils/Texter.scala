package utils

// Functions read in and write .txt files
// from: https://gist.github.com/lossyrob/45695e44601d6a9e9077
object Texter {

  def read(path: String): String = {
    scala.io.Source.fromFile(path, "UTF-8").getLines.mkString
  }
  
  def write(path: String, txt: String): Unit = {
    import java.nio.file.{Paths, Files}
    import java.nio.charset.StandardCharsets
    
    Files.write(Paths.get(path), txt.getBytes(StandardCharsets.UTF_8))
  }  	
}