package input.fileparser;

import error.InvalidFormatOfFileException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import output.OutputProduct;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;

/**
 * Represent file in any format, it contains main properties name of every format (id, type, key)
 */
@AllArgsConstructor
@Getter
@Slf4j
public abstract class FileProperties{
    private final String id;
    private final String type;
    private final String key;

    public abstract List<OutputProduct> parseFile(BufferedReader reader) throws InvalidFormatOfFileException;
}
