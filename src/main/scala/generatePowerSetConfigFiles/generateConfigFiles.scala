import com.typesafe.config.ConfigFactory
//import utils.Texter
//import java.io.File
//For reading in txt files:
import scala.io.Source
import java.io.{BufferedWriter, File, FileWriter}
//import utils.IOUtils.using

object GenConfigFiles extends App {
  //Get the output directory path from the second argument:
  val outputDir:String = args(1)
  // Extract featureFamilies complete set using regex pattern matching:
  /*val pattern = "featureFamilies\\s*=\\s*\\[(.*)\\]".r
  pattern.findFirstIn(configString) match {
    case Some(featureFamilyString) => println("Found featureFamily string set.  The match was: " + featureFamilyString)
    case None => println("No matches found.")
  }
  */
  // To make things easier, let's just hard code the featureFamilies set for now:
  val featureFamilies = Set[String]("Dependency", "NegationProperty", "Phi", "POS", "Positional", "Tails")
  
  for (featureFamilySubset <- featureFamilies.subsets){
    // Build config file name:
    val outFileSB = new StringBuilder
    outFileSB ++= outputDir
    outFileSB ++= "/"
    println("Writing config file to directory:")
    println(outFileSB.toString)
    outFileSB ++= "application_"
    // build featureFamilies subset string (using StringBuilder):
    val sb = new StringBuilder
    sb ++= """featureFamilies = [""""

    // Can't get empty set string to have closed bracket:
    if (featureFamilySubset.size < 1){
      val sb = new StringBuilder
      sb ++= """featureFamilies = [""]"""
    }
    //featureFamilySubset.foreach(println)
    var i = 1
    for (featureFamily <- featureFamilySubset){
      sb ++= featureFamily
      // make the output file name by adding each included feature family:
      outFileSB ++= featureFamily
      if (i < featureFamilySubset.size){
        sb ++= """", """"
        outFileSB ++= "_"
      } else{
        sb ++= """"]"""
      }
      i += 1
    }
    outFileSB ++= ".conf"
    //println(sb.toString)
    // write featureFamily subset to new application.conf text file:
    // Read in config file as string from first argument: 
    //val configString = Texter.read(args(0))
    //not using read function for now:
    // Read in config file as Iterator[String]:
    val configLines = Source.fromFile(args(0)).getLines().toList
    val pattern = "featureFamilies\\s*=\\s*\\[(.*)\\]".r
    // let's try making the new config file string with StringBuilder:
    val configSB = new StringBuilder
    //new BufferedWriter(new FileWriter(new File(outFileSB.toString), true)) { outputFile =>
      configLines.foreach {
        line => 
          //configSB ++= line
          configSB ++= pattern.replaceFirstIn(line, sb.toString)
          configSB ++= "\n"
          //messy code just to get print out for now:
          //if (line startsWith("  featureFamilies")){
          //  println(pattern.replaceFirstIn(line, sb.toString))
          //}

          //val matchInLine = pattern.findFirstIn(line)
          //matchInLine.foreach {
          //  m =>
          //  println(s"Found match: $m")
          //
          //  println("Changed match to: ")
          //}
        //
        //if (line startsWith("  featureFamilies")){
        //  println(line)
        //  println(line.replace("featureFamilies\\s*=\\s*\\[(.*)\\]", sb.toString))
        //println("Next foreach")
        //}
      }
      println("Opening connection to file: " + outFileSB.toString)
      val file = new File(outFileSB.toString)
      val bw = new BufferedWriter(new FileWriter(file))
      bw.write(configSB.toString)
      bw.close()
      println("File Written.")
    //}
  }
}
