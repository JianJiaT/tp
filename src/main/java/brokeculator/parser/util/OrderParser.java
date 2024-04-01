package brokeculator.parser.util;

import java.util.Arrays;

public class OrderParser {

    /**
     * Returns the positions of the keywords in the user input
     * @param userInput User input
     * @param keywords Keywords to search for
     * @return Positions of the keywords in the user input
     * @throws Exception If a mandatory keyword is missing
     */
    private static int[] retrieveKeywordPositions(String userInput, Keyword[] keywords) throws Exception {
        int[] keywordPositions = new int[keywords.length];
        for (int i = 0; i < keywords.length; i++) {
            keywordPositions[i] = userInput.indexOf(keywords[i].keywordMarker);
            if (keywordPositions[i] == -1 && !keywords[i].isOptional) {
                throw new Exception(keywords[i].keywordMeaning + " is missing");
            }
        }
        return keywordPositions;
    }

    /**
     * Compresses the keyword positions array by removing the -1 values
     * @param keywordPositions Keyword positions array
     * @return Compressed keyword positions array
     * @throws Exception If the input format is incorrect - an earlier keyword is found after a later keyword
     */
    private static int[] compressKeywordPositions(int[] keywordPositions) throws Exception {
        int[] compressedKeywordPositions = new int[keywordPositions.length];
        int compressedKeywordCount = 0;
        for (int i = 0; i < keywordPositions.length; i++) {
            if (keywordPositions[i] == -1) {
                continue;
            }
            boolean hasEarlierKeyword = compressedKeywordCount > 0;
            if (hasEarlierKeyword && keywordPositions[i] < compressedKeywordPositions[compressedKeywordCount - 1]) {
                throw new Exception("Input format is incorrect. Check the help menu");
            }
            compressedKeywordPositions[compressedKeywordCount] = keywordPositions[i];
            compressedKeywordCount++;
        }
        return Arrays.copyOf(compressedKeywordPositions, compressedKeywordCount);
    }

    /**
     * Retrieves the inputs for the provided keywords
     * @param userInput User input
     * @param keywords Keywords to search for
     * @param providedKeywordPositions Positions of the provided keywords
     * @param keywordPositions Positions of the keywords in the user input
     * @return Inputs for the provided keywords
     * @throws Exception If a mandatory keyword is missing or empty
     */
    private static String[] retrieveInputsForProvidedKeywords
    (String userInput, Keyword[] keywords, int[] providedKeywordPositions, int[] keywordPositions)
            throws Exception {
        String[] userInputs = new String[keywords.length];
        int providedKeywordCount = providedKeywordPositions.length;
        int parsedKeywordCount = 0;
        for (int i = 0; i < keywords.length; i++) {
            if (keywordPositions[i] == -1) {
                userInputs[i] = null;
                continue;
            }
            int startIndex = keywordPositions[i] + keywords[i].keywordMarker.length();
            int endIndex = parsedKeywordCount + 1 == providedKeywordCount ?
                    userInput.length() : providedKeywordPositions[parsedKeywordCount + 1];
            if (startIndex >= endIndex) {
                throw new Exception(keywords[i].keywordMeaning + " is missing");
            }
            userInputs[i] = userInput.substring(startIndex, endIndex).trim();
            if (userInputs[i].isBlank()) {
                throw new Exception(keywords[i].keywordMeaning + " cannot be empty");
            }
            parsedKeywordCount++;
        }
        return userInputs;
    }

    /**
     * Parses the user input to retrieve the inputs for the provided keywords
     * @param userInput User input
     * @param keywords Keywords to search for
     * @return Inputs for the provided keywords
     * @throws Exception If a mandatory keyword is missing or empty, or if the input format is incorrect
     */
    public static String[] parseOrder(String userInput, Keyword[] keywords)
            throws Exception {
        int[] keywordPositions = retrieveKeywordPositions(userInput, keywords);
        int[] providedKeywordPositions = compressKeywordPositions(keywordPositions);
        return retrieveInputsForProvidedKeywords
                (userInput, keywords, providedKeywordPositions, keywordPositions);
    }
}
