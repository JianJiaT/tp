package brokeculator.storage.parsing;

import java.util.Map;

public class FileKeyword {

    private static final Map<SaveableType, String> FILE_KEYWORDS = Map.of(
        SaveableType.EXPENSE, "--expense--", SaveableType.CATEGORY, "--category--", 
        SaveableType.EVENT, "--event--", SaveableType.CONNECTION, "--connection--"
    );

    /**
     * Formats the string representation of a saveable object with the keyword
     * @param saveableType Type of the saveable object
     * @param stringRepresentation Original string representation of the saveable object
     * @return Formatted string representation with the keyword
     */
    public static String formatWithKeyword(SaveableType saveableType, String stringRepresentation) {
        return FILE_KEYWORDS.get(saveableType) + stringRepresentation;
    }

    /**
     * Gets the saveable type of the object from the file string
     * @param fileString File string
     * @return Saveable type of the object
     */
    public static SaveableType getSaveableType(String fileString) {
        for (Map.Entry<SaveableType, String> entry : FILE_KEYWORDS.entrySet()) {
            String keyword = entry.getValue();
            SaveableType saveableType = entry.getKey();
            if (fileString.startsWith(keyword)) {
                return saveableType;
            }
        }
        return null;
    }

    /**
     * Removes the keyword from the file string
     * @param fileString File string
     * @return File string without the keyword
     */
    public static String removeKeyword(String fileString) {
        SaveableType saveableType = getSaveableType(fileString);
        if (saveableType == null) {
            return fileString;
        }
        return fileString.replace(FILE_KEYWORDS.get(saveableType), "");
    }
}
