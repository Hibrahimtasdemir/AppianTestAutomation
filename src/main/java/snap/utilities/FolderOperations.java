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

import java.io.File;

public class FolderOperations {

    /**
     * Manages the folder at the specified path. If the folder exists, it deletes all files and
     * subdirectories within it. If the folder does not exist, it attempts to create it.
     *
     * @param folderPath the path to the folder
     * @throws Exception if the folder cannot be created
     */
    public static void manageFolder(String folderPath) throws Exception {
        File folder = new File(folderPath);
        
        if (folder.exists() && folder.isDirectory()) {
            // If the folder exists and is a directory, delete all contents
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        manageFolder(file.getPath());  // Recursive call to manage subfolders
                    } else {
                        if (!file.delete()) {
                            System.out.println("Failed to delete file: " + file.getName());
                        }
                    }
                }
            }
        } else {
            // If the folder does not exist, create it
            if (!folder.mkdirs()) {
                throw new Exception("Unable to create the folder: " + folder.getAbsolutePath());
            }
        }
    }

    /**
     * Deletes all files in the specified folder, except for the file with the given name.
     *
     * @param folderPath the path to the folder
     * @param exemptedFile the name of the file that should not be deleted
     */
    public static void deleteFilesWithExempted(String folderPath, String exemptedFile) {
        File folder = new File(folderPath);
        
        if (folder.isDirectory()) {
            // If the folder is a directory, delete all files except the exempted one
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.getName().equals(exemptedFile)) {
                        if (file.delete()) {
                            System.out.println("Deleted file: " + file.getName());
                        } else {
                            System.out.println("Failed to delete file: " + file.getName());
                        }
                    }
                }
                System.out.println("All files (except " + exemptedFile + ") were deleted successfully.");
            } else {
                System.out.println("The directory is empty or inaccessible.");
            }
        } else {
            System.out.println("The provided path is not a directory.");
        }
    }
}
