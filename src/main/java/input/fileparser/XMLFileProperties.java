package input.fileparser;

import error.InvalidFormatOfFileException;
import input.ProductMetadata;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;
import output.OutputProduct;

import org.dom4j.Node;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class XMLFileProperties extends FileProperties{

    private String xmlSchemaFile;

    public XMLFileProperties(String xmlSchemaFile) {
        super(ProductMetadata.XML_ID.getPropertyName(),
                ProductMetadata.XML_TYPE.getPropertyName(),
                ProductMetadata.XML_KEY.getPropertyName());
        this.xmlSchemaFile = xmlSchemaFile;
    }

    @Override
    public List<OutputProduct> parseFile(BufferedReader reader) throws InvalidFormatOfFileException {
        Document document = readDocumentWIthSchemaValidation(reader, xmlSchemaFile);
        List<OutputProduct> products = new ArrayList<>();
        Element rootElement = document.getRootElement();
        Iterator itr = rootElement.elementIterator();

        while (itr.hasNext()) {
            Node node = (Node) itr.next();

            Node attributeNode = node.selectSingleNode(ProductMetadata.XML_PARAMS.getPropertyName());
            List<Node> attributeNodes = attributeNode.selectNodes("*");
            JSONObject attributes = new JSONObject();
            for(Node attribite : attributeNodes) {
                attributes.put(attribite.getName(), attribite.getText());
            }

            OutputProduct outputProduct = OutputProduct
                    .builder()
                    .key(node.valueOf("@" + getKey()))
                    .identifier(node.selectSingleNode(getId()).getText())
                    .type(node.valueOf("@" + getType()))
                    .simpleAttributes(attributes)
                    .build();

            products.add(outputProduct);
        }
        return products;
    }

    private Document readDocumentWIthSchemaValidation(Reader reader, String schemaFileName) throws InvalidFormatOfFileException {
        Document document = null;
        try {
            SAXParser parser = getXmlFileValidator(schemaFileName);
            SAXReader saxReader = new SAXReader(parser.getXMLReader());
            saxReader.setValidation(false);
            document = saxReader.read(reader);
            reader.close();
        } catch (DocumentException e) {
            log.error(e.getMessage());
            throw new InvalidFormatOfFileException();
        } catch (SAXException e) {
            log.error(e.getMessage());
            throw new InvalidFormatOfFileException();
        } catch (ParserConfigurationException e) {
            log.error(e.getMessage());
            throw new InvalidFormatOfFileException();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new InvalidFormatOfFileException();
        }
        return document;
    }

    private SAXParser getXmlFileValidator(String schemaXmlFile) throws SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SchemaFactory schemaFactory =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        factory.setSchema(schemaFactory.newSchema(
                new Source[] {new StreamSource(schemaXmlFile)}));

        SAXParser parser = factory.newSAXParser();
        return parser;
    }


}
