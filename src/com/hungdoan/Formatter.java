package com.hungdoan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Formatter {

    private static final List<Character> DATA_TYPES = Arrays.asList('d', 'f', 's');

    /**
     * Returns string formatted like C, or throws exception if anything wrong.
     *
     * @param format format specification
     * @param args   values to format
     * @return string formatted like C.
     */
    public static String formatString(String format, Object... args) {
        StringBuilder stringBuilder = new StringBuilder();
        int formatLength = format.length();
        int currentArgIndex = 0;

        for (int i = 0; i < formatLength; i++) {
            char currentFormatChar = format.charAt(i);
            if (currentFormatChar == '%') {
                char type = format.charAt(++i);
                switch (type) {
                    case 'd': {
                        stringBuilder.append(((Number) args[currentArgIndex++]).intValue());
                        break;
                    }
                    case 'f': {
                        stringBuilder.append(((Number) args[currentArgIndex++]).doubleValue());
                        break;
                    }
                    case 's': {
                        stringBuilder.append(args[currentArgIndex++]);
                        break;
                    }
                    default: {
                        throw new RuntimeException("Unknown format type: " + type);
                    }
                }
                continue;
            }
            // normal case
            stringBuilder.append(currentFormatChar);
        }
        return stringBuilder.toString();
    }

    /**
     * Returns scanned values, or throws exception if anything wrong.
     *
     * @param format format specification
     * @param source string to scan
     * @return scanned values
     */
    public static Object[] scanFormatString(String format, String source) {
        List<Object> outputObjectList = new ArrayList();
        int sourceLength = source.length();
        int formatLength = format.length();
        int currentSourceIndex = 0;

        for (int currentFormatIndex = 0; currentFormatIndex < formatLength; currentFormatIndex++) {
            char currentFormatChar = format.charAt(currentFormatIndex);
            if (currentFormatChar == '%') {
                char type = format.charAt(++currentFormatIndex);
                if (!DATA_TYPES.contains(type)) {
                    throw new RuntimeException("Invalid format has been provided !");
                }
                int starterMatchedGroupIndex = currentSourceIndex;
                currentSourceIndex = findTheEndMatchedGroupIndex(source, sourceLength, starterMatchedGroupIndex, currentSourceIndex, type);
                addObjectToDestinationArray(outputObjectList, source, starterMatchedGroupIndex, currentSourceIndex, type);
                continue;
            }

            // Bunk off the case which we encounter some normal character which is not need to concern if it matched the actual value we expected from format string
            // and then both increase the current index source
            // and the currentFormatIndex to continue the loop
            if (source.charAt(currentSourceIndex++) != currentFormatChar) {
                throw new RuntimeException("Unexpected character appears which is not contained in the input format");
            }
        }
        return outputObjectList.toArray();
    }

    /**
     * Checking is this character is number or string or whatever
     *
     * @param type
     * @param value
     * @param distance
     * @return
     */
    private static boolean isAcceptedTypeCharacter(char type, char value, int distance) {
        switch (type) {
            case 'd': {
                boolean isNegative = (distance == 0) && (value == '-');
                return isNegative || "0123456789".indexOf(value) > -1;
            }
            case 'f': {
                return "-0123456789.+Ee".indexOf(value) > -1;
            }
            case 's': {
                return Character.isLetterOrDigit(value);
            }
            default: {
                throw new RuntimeException("Unknown format type: " + type);
            }
        }
    }

    /**
     * Find the end index of matched group values based on type
     *
     * @param source
     * @param sourceLength
     * @param currentSourceIndex
     * @param type
     * @return
     */
    private static int findTheEndMatchedGroupIndex(String source, int sourceLength, int starterMatchedGroupIndex, int currentSourceIndex, char type) {
        boolean isIndexValid = currentSourceIndex < sourceLength;
        char currentSourceChar = source.charAt(currentSourceIndex);
        int distance = currentSourceIndex - starterMatchedGroupIndex;

        while (isIndexValid && isAcceptedTypeCharacter(type, currentSourceChar, distance)) {
            currentSourceIndex++;
            isIndexValid = currentSourceIndex < sourceLength;
            if (isIndexValid == false) {
                break;
            }
            currentSourceChar = source.charAt(currentSourceIndex);
            distance = currentSourceIndex - starterMatchedGroupIndex;
        }

        return currentSourceIndex;
    }

    /**
     * Cast the String value to the correct type and add it as an object to the destination array
     * which currently is still a List object at this method
     *
     * @param outputObjectList
     * @param source
     * @param starterMatchedGroupIndex
     * @param endMatchedGroupIndex
     * @param type
     */
    private static void addObjectToDestinationArray(List<Object> outputObjectList, String source, int starterMatchedGroupIndex, int endMatchedGroupIndex, char type) {
        String value = source.substring(starterMatchedGroupIndex, endMatchedGroupIndex);
        switch (type) {
            case 'd': {
                outputObjectList.add(Integer.parseInt(value));
                break;
            }
            case 'f': {
                outputObjectList.add(Double.parseDouble(value));
                break;
            }
            default: {
                outputObjectList.add(value);
                break;
            }
        }
    }
}
