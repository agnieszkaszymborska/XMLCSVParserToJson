package input.fileparser;

import error.InvalidFormatOfFileException;
import org.json.simple.JSONObject;
import input.ProductMetadata;
import org.junit.Test;
import output.OutputProduct;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.*;

public class CSVFilePropertiesTest {

    @Test
    public void parseFile_CsvFileWithFourProducts_shouldReturn4Products() throws InvalidFormatOfFileException {
        CSVFileProperties csvFileProperties = new CSVFileProperties();
        BufferedReader reader = new BufferedReader(new StringReader("Key;Identifier;Type;Author;Title\n" +
                "1;Book-1;Book;Robert C. Martin;Clean Code\n" +
                "2;Book-2;Book;Andrew Hunt;The Pragmatic Programmer\n" +
                "3;Film-1;Film;Lilly Wachowski, Lana Wachowski;The Matrix\n" +
                "4;Film-2;Film;George Lucas;Star Wars"));
        List<OutputProduct> products = csvFileProperties.parseFile(reader);
        assertEquals(products.size(), 4);
    }

    @Test
    public void parseFile_CsvFileWithProductWithKeyIdAndAuthor_shouldReturnProductObjectWithTheSameKeyIdAndAuthor() throws InvalidFormatOfFileException {
        CSVFileProperties csvFileProperties = new CSVFileProperties();
        BufferedReader reader = new BufferedReader(new StringReader("Key;Identifier;Type;Author;Title\n" +
                "1;Book-1;Book;Robert C. Martin;Clean Code"));

        JSONObject expectedJsonObjectForAddAttributes = new JSONObject();
        expectedJsonObjectForAddAttributes.put(ProductMetadata.CSV_AUTHOR.getPropertyName(), "Robert C. Martin");
        expectedJsonObjectForAddAttributes.put(ProductMetadata.CSV_TITLE.getPropertyName(), "Clean Code");

        OutputProduct expectedResult = OutputProduct
                .builder()
                .identifier("Book-1")
                .type("Book")
                .key("1")
                .simpleAttributes(expectedJsonObjectForAddAttributes)
                .build();
        List<OutputProduct> products = csvFileProperties.parseFile(reader);
        assertEquals(products.get(0), expectedResult);
    }

    @Test(expected = InvalidFormatOfFileException.class)
    public void parseFile_CsvFileWithoutHeader_throwInvalidFormatOfFileException() throws InvalidFormatOfFileException {
        CSVFileProperties csvFileProperties = new CSVFileProperties();
        BufferedReader reader = new BufferedReader(new StringReader("1;Book-1;Book;Robert C. Martin;Clean Code"));
        csvFileProperties.parseFile(reader);
    }



}