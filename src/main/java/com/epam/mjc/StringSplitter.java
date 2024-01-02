package com.epam.mjc;

import java.util.*;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        var list = new ArrayList<String>();
        for (var i = 0; i < delimiters.size(); i++) {
            StringTokenizer stringTokenizer = new StringTokenizer(source, delimiters.toArray()[i].toString());
            while (stringTokenizer.hasMoreTokens()) {
                list.add(stringTokenizer.nextToken());
            }
            var joiner = new StringJoiner(" ");
            for (var str : list) {
                joiner.add(str);
            }
            source = joiner.toString();
            list = new ArrayList<>();
        }
        return Arrays.asList(source.split("\\s+"));
    }
}
