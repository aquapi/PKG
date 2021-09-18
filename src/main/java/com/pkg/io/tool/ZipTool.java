package com.pkg.io.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.pkg.io.Folder;

public final class ZipTool {
    File[] file;

    /**
     * @param file all file to be ziped
     * @since 2.4
     */

    public ZipTool(File... file) {
        this.file = file;
    }

    /**
     * @param zipname name after zipping
     * @return ziped file
     * @throws IOException
     * @since 2.4
     */

    public File zipAs(String zipname) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipname);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        if (!file[0].isDirectory()) {
            if (file.length == 1) {
                FileInputStream fis = new FileInputStream(file[0]);
                singleZip(zipname, fos, zipOut, fis);
                fis.close();
            } else {
                multipleZip(zipname, fos, zipOut);
            }
        } else {
            zipFolder(file[0], file[0].getName(), zipOut);
        }
        zipOut.close();
        fos.close();
        return new File(zipname);
    }

    /**
     * @param zipname
     * @param fos
     * @param zipOut
     * @param fis
     * @throws IOException
     * @since 2.4
     */

    private void singleZip(String zipname, FileOutputStream fos, ZipOutputStream zipOut, FileInputStream fis)
            throws IOException {
        ZipEntry zipEntry = new ZipEntry(file[0].getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
    }

    /**
     * @param zipname
     * @param fos
     * @param zipOut
     * @throws IOException
     * @since 2.4
     */

    private void multipleZip(String zipname, FileOutputStream fos, ZipOutputStream zipOut) throws IOException {
        for (File srcFile : file) {
            if (srcFile.isDirectory()) {
                zipFolder(srcFile, srcFile.getName(), zipOut);
            }
            FileInputStream fis = new FileInputStream(srcFile);
            ZipEntry zipEntry = new ZipEntry(srcFile.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
    }

    /**
     * @param folder
     * @param folderName
     * @param zipOut
     * @throws IOException
     * @since 2.4
     */

    private void zipFolder(File folder, String folderName, ZipOutputStream zipOut) throws IOException {
        if (folder.isHidden()) {
            return;
        }
        if (folder.isDirectory()) {
            if (folderName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(folderName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(folderName + "/"));
                zipOut.closeEntry();
            }
            File[] children = folder.listFiles();
            for (File childFile : children) {
                zipFolder(childFile, folderName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(folder);
        ZipEntry zipEntry = new ZipEntry(folderName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    /**
     * @param zipfile file to be unzip
     * @return all files that have been unziped
     * @throws IOException
     * @since 2.4
     */

    @SuppressWarnings("resource")
    public static File[] unzip(File zipfile) throws IOException {
        byte[] buffer = new byte[1024];
        LinkedList<File> files = new LinkedList<>();
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipfile));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(new FileManipulator(zipfile).getParent(), zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                files.add(newFile);
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        File[] f = new File[files.size()];
        for (int i = 0; i < files.size(); i++)
            f[i] = files.get(i);
        return f;
    }

    /**
     * @param zipfile file to be unziped
     * @return unziped folder
     * @throws IOException
     * @since 2.4
     */

    public static File packedUnzip(File zipfile) throws IOException {
        File[] all = unzip(zipfile);
        String fl = new FileManipulator(all[0]).getParent().getAbsolutePath();
        Folder d = new Folder(fl + File.separator + zipfile.getName().split(".zip")[0]);
        d.mkdirs();
        d.add(all);
        return d.ptr();
    }

    /**
     * @param directory parent directory
     * @param zipEntry zip entry for file
     * @return new file with specified zip entry
     * @throws IOException
     * @since 2.4
     */

    public static File newFile(File directory, ZipEntry zipEntry) throws IOException {
        File destFile = new File(directory, zipEntry.getName());
        String destDirPath = directory.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target directory: " + zipEntry.getName());
        }
        return destFile;
    }
}
