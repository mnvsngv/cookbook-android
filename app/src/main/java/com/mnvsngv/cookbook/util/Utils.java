package com.mnvsngv.cookbook.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static String convertListToCsv(List<String> list) {
        StringBuilder builder = new StringBuilder();

        for(String string : list) {
            builder.append(string).append(", ");
        }

        if(builder.length() > 0) {
            builder.delete(builder.length() - 2, builder.length());
        }
        return builder.toString();
    }

    public static List<String> convertCsvToList(String csv) {
        if(csv.length() == 0) return new ArrayList<>();
        return Arrays.asList(csv.split("\\s*,\\s*"));
    }
}
