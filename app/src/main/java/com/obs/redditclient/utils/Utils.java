package com.obs.redditclient.utils;

import java.text.SimpleDateFormat;

/**
 * @author Pedro Cánovas
 * @mail wcanovas@gmail.com
 */

public class Utils {

    public static String getDate(long f) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss' 'yyyy-MM-dd");
        formatter.setLenient(false);

        String curTime = formatter.format(f);
        return curTime;
    }

    public static String[] split(String strString, String strDelimiter) {
        String[] strArray;
        int iOccurrences = 0;
        int iIndexOfInnerString = 0;
        int iIndexOfDelimiter = 0;
        int iCounter = 0;

        // Check for null input strings.
        if (strString == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }

        // Check for null or empty delimiter strings.
        if (strDelimiter.length() <= 0 || strDelimiter == null) {
            throw new IllegalArgumentException("Delimeter cannot be null or empty.");
        }

        // If strString begins with delimiter then remove it in order
        // to comply with the desired format.
        if (strString.startsWith(strDelimiter)) {
            strString = strString.substring(strDelimiter.length());
        }

        // If strString does not end with the delimiter then add it
        // to the string in order to comply with the desired format.
        if (!strString.endsWith(strDelimiter)) {
            strString += strDelimiter;
        }

        // Count occurrences of the delimiter in the string.
        // Occurrences should be the same amount of inner strings.
        while ((iIndexOfDelimiter = strString.indexOf(strDelimiter, iIndexOfInnerString)) != -1) {
            iOccurrences += 1;
            iIndexOfInnerString = iIndexOfDelimiter + strDelimiter.length();
        }

        // Declare the array with the correct size.
        strArray = new String[iOccurrences];

        // Reset the indices.
        iIndexOfInnerString = 0;
        iIndexOfDelimiter = 0;

        // Walk across the string again and this time add the
        // strings to the array.
        while ((iIndexOfDelimiter = strString.indexOf(strDelimiter, iIndexOfInnerString)) != -1) {
            // Add string to array.
            strArray[iCounter] = strString.substring(iIndexOfInnerString, iIndexOfDelimiter);

            // Increment the index to the next character after
            // the next delimiter.
            iIndexOfInnerString = iIndexOfDelimiter + strDelimiter.length();

            // Inc the counter.
            iCounter += 1;
        }

        return strArray;
    }

    public static String escapeHtml(CharSequence text) {
        StringBuilder out = new StringBuilder();
        withinStyle(out, text, 0, text.length());
        return out.toString();
    }

    private static void withinStyle(StringBuilder out, CharSequence text,
                                    int start, int end) {
        for (int i = start; i < end; i++) {
            char c = text.charAt(i);

            if (c == '<') {
                out.append("&lt;");
            } else if (c == '>') {
                out.append("&gt;");
            } else if (c == '&') {
                out.append("&amp;");
            } else if (c >= 0xD800 && c <= 0xDFFF) {
                if (c < 0xDC00 && i + 1 < end) {
                    char d = text.charAt(i + 1);
                    if (d >= 0xDC00 && d <= 0xDFFF) {
                        i++;
                        int codepoint = 0x010000 | (int) c - 0xD800 << 10 | (int) d - 0xDC00;
                        out.append("&#").append(codepoint).append(";");
                    }
                }
            } else if (c > 0x7E || c < ' ') {
                out.append("&#").append((int) c).append(";");
            } else if (c == ' ') {
                while (i + 1 < end && text.charAt(i + 1) == ' ') {
                    out.append("&nbsp;");
                    i++;
                }

                out.append(' ');
            } else {
                out.append(c);
            }
        }
    }

}
