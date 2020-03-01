package com.docu.commons.util

import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import java.util.zip.*

@Singleton
class ZipUtil {

    public static final Log log = LogFactory.getLog(ZipUtil.class);
    private static final String DAT_EXTENSION = ".dat"

    public static String createZipFile(String sqlFilePath, String zipFilePath, String zipEntityName) throws Exception {
        File sqlFile = new File(sqlFilePath)
        byte[] buf = sqlFile.getBytes()
        CRC32 crc = new CRC32()
        ZipOutputStream zipOutputStream = new ZipOutputStream((OutputStream) new FileOutputStream(zipFilePath))
        zipOutputStream.setLevel(9)
        ZipEntry entry = new ZipEntry(zipEntityName + DAT_EXTENSION)
        entry.setSize((long) buf.length)
        crc.reset()
        crc.update(buf)
        entry.setCrc(crc.getValue())
        zipOutputStream.putNextEntry(entry)
        zipOutputStream.write(buf, 0, buf.length)
        zipOutputStream.finish()
        zipOutputStream.close()
        zipOutputStream = null
        entry = null
        crc = null
        return zipEntityName
    }
    /**
     * Unzip zip file
     * @param sqlZipFile
     * @return boolean true is success
     */
    public boolean unzipSqlZipFile(File sqlZipFile, String dirName) throws Exception {
        final int BUFFER = 2048;
//      String destinationPath = "D:\\";
        byte[] buf = new byte[BUFFER];
        ZipInputStream zipInputStream = null;
        ZipEntry zipEntry = null;
        zipInputStream = new ZipInputStream(new FileInputStream(sqlZipFile));
        zipEntry = zipInputStream.getNextEntry();
        String entryName = null
        while (zipEntry != null) {
            //for each entry to be extracted
            entryName = zipEntry.getName();
            int length = 0;
            FileOutputStream fileOutputStream = null;
            File newFile = new File(entryName);
            String directory = newFile.getParent();
            if (directory == null) {
                if (newFile.isDirectory())
                    break;
            }
            fileOutputStream = new FileOutputStream(dirName + entryName);
            while ((length = zipInputStream.read(buf, 0, BUFFER)) > -1)
                fileOutputStream.write(buf, 0, length);
            fileOutputStream.close();
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
        return true
    }
    /**
     * Getting byte array from a file
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    /**
     *
     *
     * @param srcFolder
     * @param destZipFile
     * @throws Exception
     */
    public static void zipFolder(String srcFolder, String destZipFile) throws Exception {
        log.info("ZipUtil::zipFolder::Enter")
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);
        addFolderToZip("", srcFolder, zip);
        zip.flush();
        zip.close();
        log.info("ZipUtil::zipFolder::Enter")
    }

    /**
     *
     * @param path
     * @param srcFile
     * @param zip
     * @throws Exception
     */
    private static void addFileToZip(String path, String srcFile, ZipOutputStream zip)
    throws Exception {
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream fileInputStream = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + CommonConstants.FILE_SEPARATOR + folder.getName()));
            while ((len = fileInputStream.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    /**
     *
     * @param path
     * @param srcFolder
     * @param zip
     * @throws Exception
     */
    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
    throws Exception {
        File folder = new File(srcFolder);

        for (String fileName: folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZip(path + CommonConstants.FILE_SEPARATOR + folder.getName(), srcFolder +
                        CommonConstants.FILE_SEPARATOR + fileName, zip);
            }
        }
    }

/**
 * @param zipFile the zip file that needs to be unzipped
 * @param destFolder the folder into which unzip the zip file and create the folder structure
 */
    public static void unzipFolder(String zipFile, String destFolder) {
        try {
            ZipFile zf = new ZipFile(zipFile);
            Enumeration<? extends ZipEntry> zipEnum = zf.entries();
            String dir = destFolder;

            while (zipEnum.hasMoreElements()) {
                ZipEntry item = (ZipEntry) zipEnum.nextElement();

                if (item.isDirectory()) {
                    File newdir = new File(dir + File.separator + item.getName());
                    newdir.mkdir();
                } else {
                    String newfilePath = dir + File.separator + item.getName();
                    File newFile = new File(newfilePath);
                    if (!newFile.getParentFile().exists()) {
                        newFile.getParentFile().mkdirs();
                    }

                    InputStream is = zf.getInputStream(item);
                    FileOutputStream fos = new FileOutputStream(newfilePath);
                    int ch;
                    while ((ch = is.read()) != -1) {
                        fos.write(ch);
                    }
                    is.close();
                    fos.close();
                }
            }
            zf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}