package com.hitch.hitch.utils;

/**
 * Created by Ankit Shah on 24-Sep-17.
 */


public class StringManuplation {

    private static String name;

    public static String toFullName(String username){
        return username.replace(".", " ");
    }
    public static String toUsername(String fullname){
        name = fullname.replace(" ", ".");
        name = name.toLowerCase();
        name = "@"+name;

        return name;
    }

    public static String expandUsername(String username){
        return username.replace(".", " ");
    }
    public static String condenseUsername(String username){
        return username.replace(" ", ".");
    }

    public static String getTags(String string){
        if(string.indexOf('#')>0){
            StringBuilder sb = new StringBuilder();
            char[] charArray = string.toCharArray();
            boolean foundWord = false;
            for(char c: charArray){
                if(c=='#'){
                    foundWord = true;
                    sb.append(c);
                } else {
                    if (foundWord)
                        sb.append(c);
                }

                if (c == ' '){
                    foundWord = false;
                }
            }
            String s = sb.toString().replace(" ", "").replace("#",",#");
            return s.substring(1, s.length());
        }
        return string;
    }

}
