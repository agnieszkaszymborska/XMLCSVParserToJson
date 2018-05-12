package input;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductMetadata {

    XML_ID("identyfikator"),
    XML_TYPE("typ"),
    XML_KEY("klucz"),
    XML_PARAMS("parametry"),
    CSV_ID("identifier"),
    CSV_TYPE("type"),
    CSV_KEY("key"),
    CSV_AUTHOR("Author"),
    CSV_TITLE("Title");

    private final String propertyName;

}
