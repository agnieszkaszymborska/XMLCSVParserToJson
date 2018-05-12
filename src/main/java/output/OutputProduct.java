package output;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.json.simple.JSONObject;

/**
 * Result object for single object in input file
 */
@Builder
@EqualsAndHashCode
public class OutputProduct {
    private String type;
    private String key;
    private String identifier;
    private JSONObject simpleAttributes;

}
