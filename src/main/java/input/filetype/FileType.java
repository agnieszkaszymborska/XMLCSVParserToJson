package input.filetype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileType {
    XML("xml"),
    CSV("csv");

    private final String fileExtension;

    public static FileType getFileFormatByFileExtension(String fileExtension) {
        for(FileType fileFormat : FileType.values()) {
            if(fileFormat.getFileExtension().equals(fileExtension)) {
                return fileFormat;
            }
        }
        return null;
    }
}
