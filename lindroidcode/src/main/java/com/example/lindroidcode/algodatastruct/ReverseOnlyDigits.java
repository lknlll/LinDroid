package com.example.lindroidcode.algodatastruct;

import java.util.Stack;

public class ReverseOnlyDigits {
    public static String reverseOnlyDigits(String s){
        Stack<Character> digitStack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                digitStack.push(c);
            }
        }
        StringBuilder reverseSb = new StringBuilder();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                reverseSb.append(digitStack.pop());
            }else {
                reverseSb.append(c);
            }
        }
        return reverseSb.toString();
    }

    public static String reverseOnlyDigitsSaveMemory(String s){
        char[] chars = s.toCharArray();
        int i = 0, j= chars.length-1;
        do {
            char a = chars[i];
            char b = chars[j];
            if (Character.isDigit(a) && Character.isDigit(b)) {
                //swap
                chars[i] = b;
                chars[j] = a;
                i++;
                j--;
            } else {
                if (!Character.isDigit(a)) {
                    i++;
                }
                if (!Character.isDigit(b)) {
                    j--;
                }
            }
        } while (i < j);
        return new String(chars);
    }
}
