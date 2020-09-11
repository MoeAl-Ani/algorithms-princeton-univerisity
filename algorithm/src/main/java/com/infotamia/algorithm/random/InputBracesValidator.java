package com.infotamia.algorithm.random;

import java.util.HashMap;
import java.util.Map;

public class InputBracesValidator {

    public static void main(String[] args) {
        // valid input if the opening and closing in order.
        String[] inputs = {"}}","{}", "{}()", "{()}", "[]", "[()]", "{", "{)", "{[}", "()[]{}", "([)]"};
        Map<Character, Character> openingClosingMap = new HashMap<>();
        openingClosingMap.put('{', '}');
        openingClosingMap.put('(', ')');
        openingClosingMap.put('[', ']');
        for (String input : inputs) {
            System.out.println("input = " + input + ", " + new InputValidator(input, openingClosingMap).isValid());

        }
    }

    private static class InputValidator {
        //ListImpl<Character> holder = new ListImpl<>();
        final Map<Character, Character> openingClosingMap;
        boolean isValid = false;
        InputValidator(String input, Map<Character, Character> openingClosingMap) {
            this.openingClosingMap = openingClosingMap;
            input = input.trim();
            char[] chars = input.toCharArray();
            int tempI = 0;
            int lo = 0;
            if (chars.length == 0 || chars.length % 2 != 0) return;
            for (int i = 0; i < chars.length; i++) {
                tempI = i;
                if (isOpening(chars[i])) {
                    continue;
                }
                if (isClosing(chars[i])) {
                    if (i == chars.length -1) {
                        updateCheck(chars, lo, chars.length);
                    } else if (isOpening(chars[++tempI])) {
                        updateCheck(chars, lo, tempI);
                        lo = tempI;
                    }
                }
            }
        }

        boolean isValid() {
            return isValid;
        }

        private boolean isOpening(Character ch) {
            return openingClosingMap.containsKey(ch);
        }
        private boolean isClosing(Character ch) {
            return openingClosingMap.containsValue(ch);
        }

        private void updateCheck(char[] items,  int lo, int hi) {
            int i = lo;
            int j = hi;
            while (i < j) {
                Character item = items[i++];
                Character closing = openingClosingMap.get(item);
                if (closing == null) {
                    isValid = false;
                    return;
                }
                if (items[--j] != closing) {
                    isValid = false;
                    return;
                }
            }
            isValid = true;
        }
    }
}
