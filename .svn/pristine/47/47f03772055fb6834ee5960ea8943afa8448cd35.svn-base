/*
 * ****************************************************************
 * Copyright � 2010 Documentatm (TM) Limited. All rights reserved.
 * DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * This software is the confidential and proprietary information of
 * Documentatm (TM) Limited ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Documentatm (TM) Limited.
 * *****************************************************************
 */

package com.docu.util

import org.apache.commons.io.FileUtils


class FileService {
    static transactional = false
    /*
    method to copyFile a file. it takes a source and a destination and copies the source to destination
     */
    /**
     *
     * @param pSourcePath
     * @param pDestinationPath
     * @return
     */
    public boolean copyFile(String pSourcePath, String pDestinationPath) {
        try {
            File sourceFile = new File(pSourcePath)
            File destinationFile = new File(pDestinationPath)
            FileReader fileReader = null
            FileWriter fileWriter = null
            if (destinationFile.exists()) {
                destinationFile.delete()
            }
            fileReader = new FileReader(sourceFile)
            fileWriter = new FileWriter(destinationFile);
            int perByte;
            while ((perByte = fileReader.read()) != -1) {
                fileWriter.write(perByte);
            }
            fileReader.close();
            fileWriter.close();
            fileReader = null
            fileWriter = null
            return true
        } catch (Exception ex) {
            return false
        } finally {

        }

    }
    /*
     method to delete a file
     @param pDeleteFile
    */
    public void deleteFile(String pDeletePath) {
        if (log.isDebugEnabled()) log.debug("Deleting file:" + pDeletePath)
        File deleteFile = new File(pDeletePath)
        if (deleteFile.exists()) {
            deleteFile.delete()
        }
    }

    /**
     *
     * @param dirPath
     * @return
     */
    public boolean makeDir(String dirPath) {
        boolean success = false
        File directory = new File(dirPath)
        if (!directory.exists()) {
            success = directory.mkdirs()
        } else {
            success = true
        }
        return success
    }

    /**
     *
     * @param fileName
     * @param inputStream
     * @return
     */
    public boolean makeFile(String fileName, String inputStreamString) {
        InputStream inputStream = new ByteArrayInputStream(inputStreamString.getBytes("UTF-8"))
        FileOutputStream fileOutputStream = new FileOutputStream(fileName)
        int by
        while ((by = inputStream.read()) != -1) {
            fileOutputStream.write(by)
        }
        inputStream.close()
        fileOutputStream.flush()
        fileOutputStream.close()
        return true
    }

// Deletes all files and subdirectories under dir.
// Returns true if all deletions were successful.
// If a deletion fails, the method stops attempting to delete and returns false.
    /**
     *
     * @param dirName
     * @return
     */
    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }

    public boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    public void delDirectory(String directoryName) throws IOException {
        FileUtils.deleteDirectory(new File(directoryName));
    }
}
