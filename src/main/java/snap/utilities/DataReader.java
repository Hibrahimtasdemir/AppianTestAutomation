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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import snap.constants.CommonConstants;

public class DataReader {
    
    // Static instance variables to hold properties and file stream
    static Properties property;
    static FileInputStream propertyFile;

    /**
     * Retrieves the value associated with a given key from the data.properties file.
     *
     * @param key the key to look up in the properties file
     * @return the value associated with the key, or null if the key is not found
     */
    public static String getValue(String key) {
        try {
            // Initialize properties and load the data file
            property = new Properties();
            propertyFile = new FileInputStream(CommonConstants.getDataFilePath());
            property.load(propertyFile);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to locate data.properties file.");
        } catch (IOException e) {
            System.out.println("Unable to open or load data.properties file.");
        } finally {
            // Ensure the FileInputStream is closed to prevent resource leaks
            if (propertyFile != null) {
                try {
                    propertyFile.close();
                } catch (IOException e) {
                    System.out.println("Unable to close the data.properties file.");
                }
            }
        }
        return property.getProperty(key);
    }
}
