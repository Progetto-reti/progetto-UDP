package org.example;

public class StringUtils {

    /**
     * Inverte una stringa.
     *
     * @param input La stringa da invertire.
     * @return La stringa invertita.
     * @throws IllegalArgumentException se l'input è null.
     */
    public static String reverse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        return new StringBuilder(input).reverse().toString();
    }

    /**
     * Verifica se una stringa è un palindromo.
     *
     * @param input La stringa da verificare.
     * @return true se la stringa è un palindromo, false altrimenti.
     * @throws IllegalArgumentException se l'input è null.
     */
    public static boolean isPalindrome(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        String reversed = reverse(input);
        return input.equals(reversed);
    }
    /**
     * Concatena due stringhe.
     *
     * @param str1 La prima stringa.
     * @param str2 La seconda stringa.
     * @return La stringa ottenuta concatenando str1 e str2.
     * @throws IllegalArgumentException se una delle stringhe è null.
     */
    public static String concatenate(String str1, String str2) {
        if (str1 == null || str2 == null) {
            throw new IllegalArgumentException("Input strings cannot be null");
        }

        return str1 + str2;
    }

    /**
     * Conta il numero di occorrenze di un carattere in una stringa.
     *
     * @param input La stringa in cui cercare.
     * @param target Il carattere da cercare.
     * @return Il numero di occorrenze del carattere nella stringa.
     * @throws IllegalArgumentException se l'input è null.
     */
    public static int countOccurrences(String input, char target) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        int count = 0;
        for (char c : input.toCharArray()) {
            if (c == target) {
                count++;
            }
        }
        return count;
    }

}
