package input.fileparser;

import error.InvalidFormatOfFileException;
import input.ProductMetadata;
import org.json.simple.JSONObject;
import org.junit.Test;
import output.OutputProduct;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.*;

public class XMLFilePropertiesTest {

    @Test
    public void parseFile_XMLFileWithFourProducts_shouldReturn4Products() throws InvalidFormatOfFileException {
        XMLFileProperties xmlFileProperties = new XMLFileProperties("store.xsd");
        BufferedReader reader = new BufferedReader(new StringReader(
                "<?xml version=\"1.0\"?>\n" +
                        "<sklep>\n" +
                        "    <produkt typ=\"płyta\" klucz=\"10\">\n" +
                        "        <identyfikator>plyta10</identyfikator>\n" +
                        "        <parametry>\n" +
                        "            <tytuł>Unloved</tytuł>\n" +
                        "            <wykonawca>Maciej Obara Quartet</wykonawca>\n" +
                        "            <gatunek>jazz</gatunek>\n" +
                        "            <cena>60</cena>\n" +
                        "        </parametry>\n" +
                        "    </produkt>\n" +
                        "    <produkt typ=\"płyta\" klucz=\"20\">\n" +
                        "        <identyfikator>plyta20</identyfikator>\n" +
                        "        <parametry>\n" +
                        "            <tytuł>Smooth Jazz Cafe. Volume 17</tytuł>\n" +
                        "            <wykonawca>Various Artists</wykonawca>\n" +
                        "            <gatunek>jazz</gatunek>\n" +
                        "            <cena>50</cena>\n" +
                        "        </parametry>\n" +
                        "    </produkt>\n" +
                        "    <produkt typ=\"komiks\" klucz=\"30\">\n" +
                        "        <identyfikator>komiks30</identyfikator>\n" +
                        "        <parametry>\n" +
                        "            <tytuł>Batman</tytuł>\n" +
                        "            <podtytuł>batman śmierć w rodzinie</podtytuł>\n" +
                        "        </parametry>\n" +
                        "    </produkt>\n" +
                        "    <produkt typ=\"komiks\" klucz=\"40\">\n" +
                        "        <identyfikator>komiks40</identyfikator>\n" +
                        "        <parametry>\n" +
                        "            <tytuł>Superman</tytuł>\n" +
                        "            <podtytuł>Superman Ostatni Syn Kryptona</podtytuł>\n" +
                        "        </parametry>\n" +
                        "    </produkt>\n" +
                        "</sklep>"
                ));
        List<OutputProduct> products = xmlFileProperties.parseFile(reader);
        assertEquals(products.size(), 4);
    }

    @Test
    public void parseFile_XMLFileWithProductWithKeyIdAndAuthor_shouldReturnProductObjectWithTheSameKeyIdAndAuthor() throws InvalidFormatOfFileException {
        XMLFileProperties xmlFileProperties = new XMLFileProperties("store.xsd");
        BufferedReader reader = new BufferedReader(new StringReader(
                "<?xml version=\"1.0\"?>\n" +
                        "<sklep>\n" +
                        "    <produkt typ=\"płyta\" klucz=\"10\">\n" +
                        "        <identyfikator>plyta10</identyfikator>\n" +
                        "        <parametry>\n" +
                        "            <tytuł>Unloved</tytuł>\n" +
                        "            <wykonawca>Maciej Obara Quartet</wykonawca>\n" +
                        "            <gatunek>jazz</gatunek>\n" +
                        "            <cena>60</cena>\n" +
                        "        </parametry>\n" +
                        "    </produkt>\n" +
                        "</sklep>"
        ));

        JSONObject expectedJsonObjectForAddAttributes = new JSONObject();
        expectedJsonObjectForAddAttributes.put("tytuł", "Unloved");
        expectedJsonObjectForAddAttributes.put("wykonawca", "Maciej Obara Quartet");
        expectedJsonObjectForAddAttributes.put("gatunek", "jazz");
        expectedJsonObjectForAddAttributes.put("cena", "60");

        OutputProduct expectedResult = OutputProduct
                .builder()
                .identifier("plyta10")
                .type("płyta")
                .key("10")
                .simpleAttributes(expectedJsonObjectForAddAttributes)
                .build();

        List<OutputProduct> products = xmlFileProperties.parseFile(reader);
        assertEquals(products.get(0), expectedResult);
    }

    @Test(expected = InvalidFormatOfFileException.class)
    public void parseFile_InvalidXmlFile_throwInvalidFormatOfFileException() throws InvalidFormatOfFileException {
        XMLFileProperties xmlFileProperties = new XMLFileProperties("store.xsd");
        BufferedReader reader = new BufferedReader(new StringReader(
                "<?xml version=\"1.0\"?>\n" +
                        "<sklepp>\n" +
                        "    <produkt typ=\"płyta\" klucz=\"10\">\n" +
                        "        <identyfikator>plyta10</identyfikator>\n" +
                        "        <parametry>\n" +
                        "            <tytuł>Unloved</tytuł>\n" +
                        "            <wykonawca>Maciej Obara Quartet</wykonawca>\n" +
                        "            <gatunek>jazz</gatunek>\n" +
                        "            <cena>60</cena>\n" +
                        "        </parametry>\n" +
                        "    </produkt>\n" +
                        "</sklepp>"
        ));
        xmlFileProperties.parseFile(reader);
    }
}