package input.loader;

import error.IncorrectFileTypeException;
import error.InvalidFormatOfFileException;
import input.fileparser.FileProperties;
import input.fileparser.FilePropertiesResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import output.OutputProduct;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductLoader {

    private final FilePropertiesResolver filePropertiesResolver;

    public ProductLoader(FilePropertiesResolver filePropertiesResolver) {
        this.filePropertiesResolver = filePropertiesResolver;
    }

    public List<OutputProduct> prepareProductsFromFileNames(List<String> fileNames) {
        final List<OutputProduct> products = new ArrayList<>();
        fileNames.stream().forEach( fileName -> {

            BufferedReader reader = prepareReaderFromFileName(fileName);
            FileProperties fileProperties = prepareFilePropertiesForFileName(fileName);
            List<OutputProduct> loadedProducts = loadProductsFromFile(fileProperties, reader);
            products.addAll(loadedProducts);

            log.info(loadedProducts.size() + " products from file " + fileName);
        });

        return products;
    }

    private List<OutputProduct> loadProductsFromFile(FileProperties fileProperties, BufferedReader reader) {
        List<OutputProduct> loadedProducts = new ArrayList<>();
        try {
            loadedProducts.addAll(fileProperties.parseFile(reader));
        } catch (InvalidFormatOfFileException e) {
            log.error(e.getMessage());
        }
        return loadedProducts;
    }


    private BufferedReader prepareReaderFromFileName(String fileName) {
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(fileName));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return reader;
    }

    private FileProperties prepareFilePropertiesForFileName(String fileName) {
        String fileExt = FilenameUtils.getExtension(fileName);
        FileProperties fileProperties = null;
        try {
            fileProperties =  filePropertiesResolver.resolveFileByFileExtension(fileExt);
        } catch (IncorrectFileTypeException e) {
            log.error(e.getMessage());
        }
        return fileProperties;
    }


}
