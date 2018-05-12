package input.fileparser;

import error.InvalidFormatOfFileException;
import input.ProductMetadata;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONObject;
import output.OutputProduct;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CSVFileProperties extends FileProperties{
    private final static char DELIMITER = ';';
    public CSVFileProperties() {
        super(ProductMetadata.CSV_ID.getPropertyName(),
                ProductMetadata.CSV_TYPE.getPropertyName(),
                ProductMetadata.CSV_KEY.getPropertyName());
    }

    @Override
    public List<OutputProduct> parseFile(BufferedReader reader) throws InvalidFormatOfFileException {
        List<OutputProduct> products = new ArrayList<>();
        try {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withDelimiter(DELIMITER)
                    .withIgnoreHeaderCase());

            boolean isCorrectHeader = csvParser.getHeaderMap() != null
                    && csvParser.getHeaderMap().get(ProductMetadata.CSV_AUTHOR.getPropertyName()) == null;
            if(isCorrectHeader) {
                throw new InvalidFormatOfFileException();
            }

            products.addAll(prepareProductsForCSVFile(csvParser));

            reader.close();
            csvParser.close();

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return products;
    }

    private List<OutputProduct> prepareProductsForCSVFile(CSVParser csvParser) {
        List<OutputProduct> products = new ArrayList<>();
        for (CSVRecord csvRecord : csvParser) {
            JSONObject attributes = new JSONObject();
            String author = csvRecord.get(ProductMetadata.CSV_AUTHOR.getPropertyName());
            String title = csvRecord.get(ProductMetadata.CSV_TITLE.getPropertyName());
            attributes.put(ProductMetadata.CSV_AUTHOR.getPropertyName(), author);
            attributes.put(ProductMetadata.CSV_TITLE.getPropertyName(), title);

            OutputProduct outputProduct = OutputProduct
                    .builder()
                    .key(csvRecord.get(0))
                    .identifier(csvRecord.get(getId()))
                    .type(csvRecord.get(getType()))
                    .simpleAttributes(attributes)
                    .build();
            products.add(outputProduct);
        }
        return products;
    }
}
