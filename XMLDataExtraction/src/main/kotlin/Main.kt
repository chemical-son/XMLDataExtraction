import java.nio.file.Paths

fun main(){
    //this returns the current path to get file from resources folder
    //if you want to change this path just put your new path in filePath variable as a String
    val path = Paths.get("").toAbsolutePath().toString()

    val filePath = "$path\\src\\main\\resources\\hafs_smart_v8.xml"
    var xmlDataExtraction = XMLDataExtraction(filePath)
    //the result stored in resources file
    xmlDataExtraction.extractData()
}