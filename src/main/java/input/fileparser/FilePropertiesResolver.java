package input.fileparser;

import error.IncorrectFileTypeException;
import input.filetype.FileType;

public class FilePropertiesResolver {

    private final CSVFileProperties csvFileProperties;
    private final XMLFileProperties xmlFileProperties;

    public FilePropertiesResolver(CSVFileProperties csvFileProperties, XMLFileProperties xmlFileProperties) {
        this.csvFileProperties = csvFileProperties;
        this.xmlFileProperties = xmlFileProperties;
    }

    public FileProperties resolveFileByFileExtension(String fileExtension) throws IncorrectFileTypeException {
        FileType fileType = FileType.getFileFormatByFileExtension(fileExtension.toLowerCase());

        if(fileType == null) {
            throw new IncorrectFileTypeException();
        }

        if(FileType.CSV.equals(fileType)) {
            return csvFileProperties;
        }
        else if (FileType.XML.equals(fileType)) {
            return xmlFileProperties;
        }

        return null;
    }
}
