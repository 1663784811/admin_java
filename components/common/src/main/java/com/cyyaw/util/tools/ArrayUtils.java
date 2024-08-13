package com.cyyaw.util.tools;


/***
 * 数组
 */
public class ArrayUtils {


    public static boolean isStr(String str, String[] strArray) {
        boolean is = false;
        for (int i = 0; i < strArray.length; i++) {
            if (strArray[i].equals(str)) {
                is = true;
                break;
            }
        }
        return is;
    }


}
