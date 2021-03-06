package com.pkg.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Scanner;

public class FileManipulator {
    public File file;

    /**
     * @param file construct new FileManipulator to work with input file
     * @throws FileNotFoundException
     * @since 1.0
     */

    public FileManipulator(File file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("No file given");
        }
        this.file = file;
        if (!file.exists())
            file.createNewFile();
    }

    /**
     * @param filename
     * @throws FileNotFoundException
     * @since 2.1
     */

    public FileManipulator(String filename) throws IOException {
        if (filename == null || filename == "") {
            throw new FileNotFoundException("No file given");
        }
        this.file = new File(filename);
        if (!file.exists())
            file.createNewFile();
    }

    /**
     * @param text
     * @throws IOException
     * @since 1.0
     */

    public void write(String text) throws IOException {
        FileWriter a = new FileWriter(this.file);
        a.write(text);
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
        if (file.isDirectory()) {
            new FileManipulator(j).mkdirs(this.file.listFiles());
            this.file.delete();
        } else {
            j.createNewFile();
            moveTo(j);
            file.delete();
        }
        this.file = j;
    }

    /**
     * @param p permission number
     * @since 2.4
     */

    public void permit(int p) {
        file.setExecutable(p == 4 || p == Permission.ALL || p == 6 || p == 5);
        file.setWritable(p == 2 || p == Permission.ALL || p == 6 || p == 3);
        file.setReadable(p == 1 || p == Permission.ALL || p == 3 || p == 5);
    }

    /**
     * @param f switch to handle another file
     * @since 2.4
     */

    public FileManipulator switchTo(File f) {
        this.file = f;
        return this;
    }

    /**
     * @apiNote make the current file hidden
     * @throws IOException
     * @since 2.4
     */

    public void hide() throws IOException {
        Files.setAttribute(file.toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
    }

    /**
     * @apiNote make the current file shown
     * @throws IOException
     * @since 2.4
     */

    public void show() throws IOException {
        Files.setAttribute(file.toPath(), "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);
    }

    /**
     * @param value of property system
     * @throws IOException
     * @since 2.4
     */

    public void setSystem(boolean value) throws IOException {
        DosFileAttributeView view = Files.getFileAttributeView(file.toPath(), DosFileAttributeView.class);
        view.setSystem(value);
    }

    /**
     * @param value of property article
     * @throws IOException
     * @since 2.4
     */

    public void setArchive(boolean value) throws IOException {
        DosFileAttributeView view = Files.getFileAttributeView(file.toPath(), DosFileAttributeView.class);
        view.setArchive(value);
    }

    /**
     * @param name
     * @param value type should have a predefined toString()
     * @throws IOException
     * @since 2.4
     */

    public void createAttribute(String name, Object value) throws IOException {
        UserDefinedFileAttributeView view = Files.getFileAttributeView(file.toPath(), UserDefinedFileAttributeView.class);
        view.write(name, Charset.defaultCharset().encode(value.toString()));
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
     * @return true if the directory or file has been deleted
     * @since 1.7
     */

    public boolean delete() {
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
