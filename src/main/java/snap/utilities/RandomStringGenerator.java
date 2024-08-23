/*
 * Copyright (c) 2024 Arjit Yadav
 *
 * Permission is hereby granted to use, copy, modify, and distribute this code for any purpose, with or without
 * modifications, subject to the following conditions:
 *
 * 1. This notice shall be included in all copies or substantial portions of the code.
 * 2. Suggestions and improvements are welcome and can be submitted via pull requests or issues on the GitHub repository.
 *
 * THE CODE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT,
 * OR OTHERWISE, ARISING FROM, OUT OF, OR IN CONNECTION WITH THE CODE OR THE USE OR OTHER DEALINGS IN THE CODE.
 */

package snap.utilities;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.security.SecureRandom;

public class RandomStringGenerator {

    // Constants representing character sets
    private static final String UPPER_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    
    // SecureRandom instance for generating random values
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a random alphabetic string.
     *
     * @param length the length of the string to generate
     * @param includeUpper whether to include uppercase alphabetic characters
     * @param includeLower whether to include lowercase alphabetic characters
     * @return a random alphabetic string of the specified length
     */
    public static String randomAlpha(int length, boolean includeUpper, boolean includeLower) {
        String alphaChars = (includeUpper ? UPPER_ALPHA : "") + (includeLower ? LOWER_ALPHA : "");
        return generateRandomString(alphaChars, length);
    }

    /**
     * Generates a random numeric string.
     *
     * @param length the length of the string to generate
     * @return a random numeric string of the specified length
     */
    public static String randomNumeric(int length) {
        return generateRandomString(NUMERIC, length);
    }

    /**
     * Generates a random alphanumeric string.
     *
     * @param length the length of the string to generate
     * @param includeUpper whether to include uppercase alphabetic characters
     * @param includeLower whether to include lowercase alphabetic characters
     * @return a random alphanumeric string of the specified length
     */
    public static String randomAlphanumeric(int length, boolean includeUpper, boolean includeLower) {
        String alphaChars = (includeUpper ? UPPER_ALPHA : "") + (includeLower ? LOWER_ALPHA : "");
        return generateRandomString(alphaChars + NUMERIC, length);
    }

    /**
     * Generates a random string from the specified character set.
     *
     * @param characters the set of characters to use for generating the string
     * @param length the length of the string to generate
     * @return a random string of the specified length
     * @throws IllegalArgumentException if the character set is empty
     */
    public static String generateRandomString(String characters, int length) {
        if (characters.isEmpty()) {
            throw new IllegalArgumentException("Character set cannot be empty");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(RANDOM.nextInt(characters.length())));
        }
        return sb.toString();
    }

    /**
     * Generates a string of lorem ipsum text.
     *
     * @param charCount the number of words to generate
     * @return a string containing lorem ipsum text of the specified word count
     */
    public static String generateLoremIpsumText(int charCount) {
        Lorem lorem = LoremIpsum.getInstance();
        return lorem.getWords(charCount);
    }
}
