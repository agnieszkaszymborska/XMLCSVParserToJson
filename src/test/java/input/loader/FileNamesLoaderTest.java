package input.loader;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FileNamesLoaderTest {

    @Test
    public void prepareFileNamesFromConfFile_confFileContainsThreeFileNamesWithOneDuplicate_shouldReturnTwoFileNames() throws IOException {
        FileNamesLoader fileNamesLoader = new FileNamesLoader();
        BufferedReader reader = new BufferedReader(new StringReader("input/Store.csv\n" +
                "input/Store.csv\n" +
                "input/Sklep.xml"));
        List<String> fileNames = fileNamesLoader.prepareFileNamesFromConfFile(reader);
        assertEquals(fileNames.size(), 2);
    }

    @Test
    public void prepareFileNamesFromConfFile_confFileContainsTwoFileNamesStoreAndSklep_shouldReturnTwoFileNamesStoreAndSklep() throws IOException {
        FileNamesLoader fileNamesLoader = new FileNamesLoader();
        BufferedReader reader = new BufferedReader(new StringReader("input/Store.csv\n" +
                "input/Sklep.xml"));
        List<String> fileNames = fileNamesLoader.prepareFileNamesFromConfFile(reader);
        assertArrayEquals(fileNames.stream().toArray(String[]::new), new String []{"input/Store.csv", "input/Sklep.xml"});

    }
}