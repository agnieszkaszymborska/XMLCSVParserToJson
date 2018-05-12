package output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JsonGeneratorFromObject {
    public void generateOutput(List<OutputProduct> products, String outputFileName) {

        File file = new File(outputFileName);
        String productsJson = prepareJsonStringForObjects(products);
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(productsJson);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public String prepareJsonStringForObjects(List<OutputProduct> products) {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        return g.toJson(products);
    }
}
