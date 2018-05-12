package input.loader;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@Slf4j
public class FileNamesLoader {

    public List<String> prepareFileNamesFromConfFile(BufferedReader br) throws IOException {
        Set<String> listFiles = new HashSet<>();
        String line = br.readLine();
        int fileCounter = 1;
        while (line != null) {
            listFiles.add(line);
            log.info("File " + fileCounter + " " + line);
            fileCounter++;
            line = br.readLine();
        }
        br.close();
        return new ArrayList<>(listFiles);
    }
}
