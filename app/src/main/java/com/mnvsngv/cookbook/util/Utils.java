package com.mnvsngv.cookbook.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    /*public static String convertListToCsv(List<String> list) {
        return String.join(", ", list);
    }*/

    public static List<String> convertCsvToList(String csv) {
        if(csv.length() == 0) return new ArrayList<>();
        return Arrays.asList(csv.split("\\s*,\\s*"));
    }
}
