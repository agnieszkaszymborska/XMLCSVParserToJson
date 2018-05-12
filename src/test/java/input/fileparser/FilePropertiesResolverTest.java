package input.fileparser;

import error.IncorrectFileTypeException;
import org.junit.Test;

import static org.junit.Assert.*;

public class FilePropertiesResolverTest {

    @Test
    public void resolveFileByFileExtension_XmlFileExtension_ShouldReturnXmlFilePropertiesClass() throws IncorrectFileTypeException {
        XMLFileProperties xmlFileProperties = new XMLFileProperties("");
        FilePropertiesResolver filePropertiesResolver = new FilePropertiesResolver(new CSVFileProperties(),
                xmlFileProperties);
        assertEquals(filePropertiesResolver.resolveFileByFileExtension("xml"), xmlFileProperties);
    }

    @Test
    public void resolveFileByFileExtension_CsvFileExtension_ShouldReturnCsvFilePropertiesClass() throws IncorrectFileTypeException {
        CSVFileProperties csvFileProperties = new CSVFileProperties();
        FilePropertiesResolver filePropertiesResolver = new FilePropertiesResolver(csvFileProperties,
                new XMLFileProperties(""));
        assertEquals(filePropertiesResolver.resolveFileByFileExtension("csv"), csvFileProperties);
    }

    @Test(expected = IncorrectFileTypeException.class)
    public void resolveFileByFileExtension_PngFileExtension_ShouldThrowIncorrectFileTypeException() throws IncorrectFileTypeException {
        FilePropertiesResolver filePropertiesResolver = new FilePropertiesResolver(new CSVFileProperties(),
                new XMLFileProperties(""));
        filePropertiesResolver.resolveFileByFileExtension("png");
    }
}