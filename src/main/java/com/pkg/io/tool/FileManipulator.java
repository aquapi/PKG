package com.pkg.io.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class FileManipulator {
    public File file;

    /**
     * @param file construct new FileManipulator to work with input file
     * @throws FileNotFoundException
     * @since 1.0
     */

    public FileManipulator(File file) throws FileNotFoundException {
        if (file == null) {
            throw new FileNotFoundException("No file given");
        }
        this.file = file;
    }

    /**
     * @param filename
     * @throws FileNotFoundException
     * @since 2.1
     */

    public FileManipulator(String filename) throws FileNotFoundException {
        if (filename == null || filename == "") {
            throw new FileNotFoundException("No file given");
        }
        this.file = new File(filename);
    }

    /**
     * @param file
     * @param writer
     * @throws IOException
     * @since 1.0
     */

    public void write(String writer) throws IOException {
        FileWriter a = new FileWriter(this.file);
        a.write(writer);
        a.close();
    }

    /**
     * @return current file data
     * @throws IOException
     * @since 1.7
     */

    public String read() throws IOException {
        Scanner reader = new Scanner(this.file);
        String data = "";
        while (reader.hasNextLine()) {
            data = data + reader.nextLine() + "\n";
        }
        reader.close();
        return data;
    }

    /**
     * @param content to be added in current file
     * @throws IOException
     * @since 1.0
     */

    public void add(String content) throws IOException {
        String current = this.read();
        String added = current + content;
        this.clean();
        this.write(added);
    }

    /**
     * @param targetFile file to be paste
     * @throws IOException
     * @since 1.7
     */

    public void pasteTo(File targetFile) throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new FileOutputStream(targetFile);
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * @param newName clone current file to a new file with newName
     * @throws IOException
     * @since 1.7
     */

    public void clone(String newName) throws IOException {
        File x = new File(newName);
        x.createNewFile();
        this.pasteTo(x);
    }

    /**
     * @param file to move data to
     * @throws IOException
     * @since 1.7
     */

    public void moveTo(File file) throws IOException {
        this.pasteTo(file);
        new FileOutputStream(this.file).close();
    }

    /**
     * @param dir parent of this file
     * @throws IOException
     * @since 2.2
     */

    public void move(File dir) throws IOException {
        if (dir == null || !dir.isDirectory() || !dir.exists())
            throw new IOException("cannot move this file to specified directory");
        File j = new File(dir.getAbsolutePath() + File.separator + this.file.getName());
        j.createNewFile();
        moveTo(j);
    }

    /**
     * clean current file data
     * 
     * @throws IOException
     * @since 1.7
     */

    public void clean() throws IOException {
        new FileOutputStream(this.file).close();
    }

    /**
     * @param directory target directory
     * @return true if the directory has been deleted
     * @since 1.7
     */

    private boolean deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null && allContents.length != 0) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directory.delete();
    }

    /**
     * @param dir
     * @return true if extract success
     * @since 2.2
     */

    private boolean extract(File dir) {
        try {
            String k = dir.toPath().toAbsolutePath().getParent().toString();
            File[] fj = dir.listFiles();
            if (fj != null && fj.length != 0) {
                for (File file : fj) {
                    File cr = new File(k + File.separator + file.getName());
                    if (!file.isDirectory()) {
                        cr.createNewFile();
                        new FileManipulator(cr).clean();
                        new FileManipulator(cr).add(new FileManipulator(file).read());
                    } else {
                        new FileManipulator(cr).mkdirs(file.listFiles());
                    }
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * @param target
     * @param child
     * @throws IOException
     * @since 2.2
     */

    private void mkdirs(File target, File... child) throws IOException {
        target.mkdir();
        String subfiles_path = target.getAbsolutePath() + File.separator;
        if (child != null && child.length != 0) {
            for (File g : child) {
                if (g != null) {
                    if (g.isDirectory()) {
                        File k = new File(subfiles_path + g.getName());
                        mkdirs(k, g.listFiles());
                        continue;
                    }
                    File pt = new File(subfiles_path + g.getName());
                    pt.createNewFile();
                    new FileManipulator(g).pasteTo(pt);
                }
            }
        }
    }

    /**
     * @param child make directory with child file or folder
     * @throws IOException
     * @since 2.2
     */

    public void mkdirs(File... child) throws IOException {
        mkdirs(this.file, child);
    }

    /**
     * @return true if success
     * @since 2.2
     */

    public boolean extractDirectory() {
        return extract(this.file);
    }

    /**
     * @param directory target directory
     * @return true if the directory has been deleted
     * @since 1.7
     */

    public boolean deleteDirectory() {
        return deleteDirectory(this.file);
    }

    /**
     * @return this file's parent
     * @since 2.0
     */

    public File getParent() {
        StringBuffer path = new StringBuffer(this.file.getAbsolutePath());
        path.delete(path.indexOf(this.file.getName()) - 1, path.length());
        return new File(path.toString());
    }
}
