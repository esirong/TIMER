package com.esirong.timer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * 字符操作工具类
 * @author lanzheng
 * @date 2014年6月26日
 * @project COM.TOOLS
 * @package com.esirong.tools
 * @package Strings.java
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Strings {
    public static final String SINGLE_SPACE = " ";
    /**
     * Checks the passed in string to see if it is null or an empty string
     * 
     * @param s the string to check
     * @return true if null or ""
     */
    public static boolean isEmptyOrNull(String s) {
        return s == null || s.length() == 0;
    }
    
    /**
     * Checks the passed in string to see if it is null, empty, or blank; where
     * 'blank' is defined as consisting entirely of whitespace.
     * 
     * @param s the string to check
     * @return true if null or "" or all whitespace
     */
    public static boolean isBlank(String s) {
        return isEmptyOrNull(s) || s.trim().length() == 0;
    }
    
    //字符拼接
    public static String join(Collection<?> s, String delimiter) {
        StringBuilder builder = new StringBuilder();
        
        if (s == null) {
            return "";
        }
        
        Iterator<?> iter = s.iterator();
        
        while (iter.hasNext()) {
            builder.append(iter.next());
            
            if (!iter.hasNext()) {
                break;
            }
            
            builder.append(delimiter);
        }
        
        return builder.toString();
    }
    
    /**
     * 分割字符
     * 
     * @param s
     * @param delimeter
     * @return
     */
    public static ArrayList<String> split(String s, String delimeter) {
        if (Strings.isBlank(s)) {
            return new ArrayList<String>();
        }
        
        return new ArrayList<String>(Arrays.asList(s.split(delimeter)));
    }
    
  

    /**
     * Inserts a given string into another padding it with spaces. Is aware if
     * the insertion point has a space on either end and does not add extra
     * spaces.
     * 
     * @param s the string to insert into
     * @param insertAt the position to insert the string
     * @param stringToInsert the string to insert
     * @return the result of inserting the stringToInsert into the passed in
     *         string
     * @throws IndexOutOfBoundsException if the insertAt is negative, or
     *             insertAt is larger than the length of s String object
     */
    public static String insertPadded(String s, int insertAt, String stringToInsert) {
        if (Strings.isEmptyOrNull(stringToInsert)) {
            return s;
        }

        if (insertAt < 0) {
            throw new IndexOutOfBoundsException("Invalid insertAt of ["
                    + insertAt + "] for string [" + s + "]");
        }

        StringBuilder newText = new StringBuilder();

        if (insertAt > 0) {
            newText.append(s.substring(0, insertAt));

            if (newText.lastIndexOf(SINGLE_SPACE) != newText.length() - 1) {
                newText.append(SINGLE_SPACE);
            }

            newText.append(stringToInsert);
            String postItem = s.substring(insertAt);

            if (postItem.indexOf(SINGLE_SPACE) != 0) {
                newText.append(SINGLE_SPACE);
            }

            newText.append(postItem);
        } else {
            newText.append(stringToInsert);

            if (s.indexOf(SINGLE_SPACE) != 0) {
                newText.append(SINGLE_SPACE);
            }

            newText.append(s);
        }

        return newText.toString();
    }

    /**
     * Inserts a given string into another padding it with spaces. Is aware if
     * the insertion point has a space on either end and does not add extra
     * spaces. If the string-to-insert is already present (and not part of
     * another word) we return the original string unchanged.
     * 
     * @param s the string to insert into
     * @param insertAt the position to insert the string
     * @param stringToInsert the string to insert
     * @return the result of inserting the stringToInsert into the passed in
     *         string
     * @throws IndexOutOfBoundsException if the insertAt is negative, or
     *             insertAt is larger than the length of s String object
     */
    public static String insertPaddedIfNeeded(String s, int insertAt, String stringToInsert) {
        if (Strings.isEmptyOrNull(stringToInsert)) {
            return s;
        }

        boolean found = false;
        int startPos = 0;

        while ((startPos < s.length()) && (!found)) {
            int pos = s.indexOf(stringToInsert, startPos);

            if (pos < 0){
                break;  
            }
            

            startPos = pos + 1;
            int before = pos - 1;
            int after = pos + stringToInsert.length();

            if (((pos == 0) || (Character.isWhitespace(s.charAt(before)))) &&
                    ((after >= s.length()) || (Character.isWhitespace(s.charAt(after))))){
                found = true;
            }
               
        }

        if (found) {
            StringBuilder newText = new StringBuilder(s);

            if (newText.lastIndexOf(SINGLE_SPACE) != newText.length() - 1) {
                newText.append(SINGLE_SPACE);
            }

            return (newText.toString());
        } else{
            return (Strings.insertPadded(s, insertAt, stringToInsert));
        }
          
    }
    
    /**
     * 是不是Ip字段0~255
     * 
     * @param num
     * @return
     */
    public static boolean isIpNum(int num){
        return 256>num&&num>=0;
    }
}
