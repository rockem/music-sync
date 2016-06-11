package features.support

class iTunesXmlFileCreator {

    String pathToXml

    iTunesXmlFileCreator(String filePath) {
        this.pathToXml = filePath
    }


    def createFrom(String xml) {
        File file = new File(pathToXml)
        file.getParentFile().mkdirs()
        FileWriter writer = new FileWriter(file);
        writer.write(xml)
        writer.close()
    }

    def delete() {
        new File(pathToXml).delete()
    }
}
