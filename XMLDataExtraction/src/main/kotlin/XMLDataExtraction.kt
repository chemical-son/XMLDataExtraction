import java.io.File
import java.io.InputStream

class XMLDataExtraction(private var inputFilePath: String) {

    public fun extractData() {
        val id = findValuesInXMLString(inputFilePath, "<sura_no>", "</sura_no>")
        val value = findValuesInXMLString(inputFilePath, "<sura_name_ar>", "</sura_name_ar>")

        val resultAsArrayOfStrings = prepareAsStringFileAndroidFormat(id, value)

        //this will save file in resource folder
        val parentPath = inputFilePath.substring(0, inputFilePath.lastIndexOf('\\'))
        createXMLFile(resultAsArrayOfStrings, "$parentPath\\strings.xml")}

    private fun createXMLFile(list: ArrayList<String>?, fileName: String){
        var fileName = fileName // to enable edit over it.

        if(list == null){
            println("the input list is empty!")
            return
        }
        if(fileName == ""){
            println("the input file name is empty\n we put it as \"xmlFile.xml\"")
            fileName = "xmlFile.xml"
        }

        val file = File(fileName)

        // create a new file
        val isNewFileCreated: Boolean = file.createNewFile()

        if (isNewFileCreated) {
            println("$fileName is created successfully.")
        } else {
            println("$fileName already exists.")
        }

        file.writeText("")//this to delete all data inside this file
        for(element in list){
            file.appendText(element + '\n')
        }

    }

    private fun prepareAsStringFileAndroidFormat(id: ArrayList<String>, value: ArrayList<String>): ArrayList<String>?{
        var resultList: ArrayList<String> = ArrayList()
        if (id.size != value.size){
            println("the two arrays must be from the same size")
            return null
        }

        var tempResult: String
        for(i in id.indices){
            tempResult = "<string name=\"_${id[i]}\">${value[i]}</string>"
            resultList.add(tempResult)
        }
        return resultList
    }

    private fun findValuesInXMLString(filePath: String, startTagName: String, endTagName: String): ArrayList<String>{

        val inputStream: InputStream = File(filePath).inputStream()

        var inputString = inputStream.bufferedReader().use { it.readText() }

        var temp = 0
        var list: ArrayList<String> = ArrayList()

        var startIndex: Int
        var endIndex: Int
        var supResult: String
        val lastIndex = inputString.lastIndexOf(endTagName) // we will stop at this index (point)
        try {
            while(temp < inputString.length){

                //start index of sura no. begin
                startIndex = inputString.indexOf(startTagName, temp, true)
                //end index of sura no. ends
                endIndex = inputString.indexOf(endTagName, temp, true)

                //sura no.
                supResult = inputString.substring(startIndex + startTagName.length, endIndex)

                //break if we reach to last sura_no in the hole input text
                if(supResult !in list)
                    list.add(supResult)

                if(lastIndex == endIndex)
                    break

                temp = endIndex + endTagName.length
            }
        }catch (e: Exception){
            println(e)
        }
        return list
    }

}