package output;

import input.ProductMetadata;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonGeneratorFromObjectTest {

    @Test
    public void prepareJsonStringForObjects_ProductWithIdKeyTypeAuthorTitle_ReturnObjectAsJsonString() {
        List<OutputProduct> products = new ArrayList<>();
        JSONObject expectedJsonObjectForAddAttributes = new JSONObject();
        expectedJsonObjectForAddAttributes.put(ProductMetadata.CSV_AUTHOR.getPropertyName(), "Robert C. Martin");
        expectedJsonObjectForAddAttributes.put(ProductMetadata.CSV_TITLE.getPropertyName(), "Clean Code");
        products.add(
            OutputProduct.builder()
            .key("1")
            .identifier("Book-1")
            .type("Book")
            .simpleAttributes(expectedJsonObjectForAddAttributes)
            .build()
        );
        String expectedJsonObject = "[\n" +
                "  {\n" +
                "    \"type\": \"Book\",\n" +
                "    \"key\": \"1\",\n" +
                "    \"identifier\": \"Book-1\",\n" +
                "    \"simpleAttributes\": {\n" +
                "      \"Author\": \"Robert C. Martin\",\n" +
                "      \"Title\": \"Clean Code\"\n" +
                "    }\n" +
                "  }\n" +
                "]";
        String jsonObject = new JsonGeneratorFromObject().prepareJsonStringForObjects(products);
        assertEquals(jsonObject, expectedJsonObject);

    }
}