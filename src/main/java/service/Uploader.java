package service;

import input.fileparser.CSVFileProperties;
import input.fileparser.FilePropertiesResolver;
import input.fileparser.XMLFileProperties;
import input.loader.FileNamesLoader;
import input.loader.ProductLoader;
import output.JsonGeneratorFromObject;
import output.OutputProduct;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


@Slf4j
public class Uploader {
    private static final String CONF_FILE_NAME = "inputFiles";
    private static final String OUTPUT_FILE = "magazyn.json";
    private static final String XML_SCHEMA = "store.xsd";

    private final FileNamesLoader fileNamesLoader;
    private final ProductLoader productLoader;
    private final JsonGeneratorFromObject jsonGeneratorFromObject;

    public Uploader(FileNamesLoader fileNamesLoader, ProductLoader productLoader, JsonGeneratorFromObject jsonGeneratorFromObject) {
        this.fileNamesLoader = fileNamesLoader;
        this.productLoader = productLoader;
        this.jsonGeneratorFromObject = jsonGeneratorFromObject;
    }

    public static void main(String[] args) {
        new Uploader(new FileNamesLoader(),
                new ProductLoader(new FilePropertiesResolver(new CSVFileProperties(), new XMLFileProperties(XML_SCHEMA))),
                new JsonGeneratorFromObject()).generateOutputFileForInputs(CONF_FILE_NAME, OUTPUT_FILE);
    }

    public void generateOutputFileForInputs(String confFileName, String outputFileName) {
        try {
            Long startTime = System.currentTimeMillis();

            BufferedReader bufferedReader = new BufferedReader(new FileReader(confFileName));
            List<String> fileNames = fileNamesLoader.prepareFileNamesFromConfFile(bufferedReader);
            List<OutputProduct> products = productLoader.prepareProductsFromFileNames(fileNames);
            jsonGeneratorFromObject.generateOutput(products, outputFileName);

            log.info("Executing time: " + (System.currentTimeMillis() - startTime) + " ms");
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }




}
