package com.pkg.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

public class Folder implements Serializable {
    private LinkedList<File> elements = new LinkedList<>();
    private LinkedList<Folder> next = new LinkedList<>();
    private String name;
    private File cursor;
    private Folder parent;
    private boolean created;

    /**
     * @param name    folder name
     * @param folders subfolders
     * @throws IOException
     * @since 1.9
     */

    public Folder(String name, Folder... folders) throws IOException {
        parent = null;
        cursor = new File(name);
        this.name = cursor.getAbsolutePath();
        created = cursor.exists();
        for (Folder e : folders) {
            next.add(e);
        }
        initChild(this);
    }

    /**
     * @param name folder name
     * @param file list of file in folder
     * @throws IOException
     * @since 1.9
     */

    public Folder(String name, File... file) throws IOException {
        parent = null;
        cursor = new File(name);
        this.name = cursor.getAbsolutePath();
        created = cursor.exists();
        for (File e : file) {
            elements.add(e);
        }
        initChild(this);
    }

    /**
     * @param file list of file in folder
     * @throws IOException
     * @since 1.9
     */

    public Folder(File... file) throws IOException {
        this("", file);
    }

    /**
     * @param folders subfolders
     * @throws IOException
     * @since 1.9
     */

    public Folder(Folder... folders) throws IOException {
        this("", folders);
    }

    /**
     * @param name folder name
     * @throws IOException
     * @since 1.9
     */

    public Folder(String name) throws IOException {
        cursor = new File(name);
        this.name = cursor.getAbsolutePath();
        parent = null;
        created = cursor.exists();
        initChild(this);
    }

    /**
     * Construct an unamed folder with no subfolder and file inside
     * 
     * @throws IOException
     * 
     * @since 1.9
     */

    public static Folder blank() throws IOException {
        return new Folder("");
    }

    /**
     * @return folder path
     * @since 1.9
     */

    public String getPath() {
        return name;
    }

    /**
     * @return folder name
     * @since 2.4
     */

    public String getName() {
        return cursor.getName();
    }

    /**
     * @return check whether this folder is virtual
     * @since 1.9
     */

    public boolean created() {
        return created;
    }

    /**
     * @param newname of the folder
     * @throws IOException
     * @since 1.9
     */

    public void setName(String newname) throws IOException {
        this.name = newname;
        if (created)
            remkdirs(parent());
    }

    /**
     * @param folder add new folder to subfolder list
     * @throws IOException
     * @since 1.9
     */

    public void add(Folder... folder) throws IOException {
        next.addAll(Arrays.asList(folder));
        for (Folder fl : folder) {
            fl.parent = this;
        }
        if (this.created())
            remkdirs(parent());
    }

    /**
     * @param file add new file to file list
     * @throws IOException
     * @since 1.9
     */

    public void add(File... file) throws IOException {
        elements.addAll(Arrays.asList(file));
        if (this.created())
            remkdirs(parent());
    }

    /**
     * @param folder remove a folder from subfolder list
     * @throws IOException
     * @since 1.9
     */

    public void delete(Folder folder) throws IOException {
        next.remove(folder);
        if (this.created())
            remkdirs(parent());
    }

    /**
     * @param file remove a file from file list
     * @throws IOException
     * @since 1.9
     */

    public void delete(File file) throws IOException {
        elements.remove(file);
        if (this.created())
            remkdirs(parent());
    }

    /**
     * @return all folder's file list
     * @since 1.9
     */

    public LinkedList<File> files() {
        return elements;
    }

    /**
     * @return subfolder list
     * @since 1.9
     */

    public LinkedList<Folder> folders() {
        return next;
    }

    /**
     * @return folder's parent
     * @throws IOException
     * @since 1.9
     */

    public Folder parent() throws IOException {
        if (parent != null)
            return parent;
        Folder f = new Folder(new FileManipulator(cursor).getParent().getAbsolutePath());
        initChild(f);
        return f;
    }

    /**
     * @param parent of current folder
     * @return task progress (done or undone)
     * @throws IOException
     * @since 1.9
     */

    public LinkedList<Boolean> mkdirs(Folder parent) throws IOException {
        LinkedList<Boolean> task = new LinkedList<>();
        created = true;
        if (parent == null) {
            task = mkdirs();
            return task;
        }
        if (!parent.created()) {
            parent.mkdirs(parent.parent());
        }
        parent.next.add(this);
        String path = parent.getName() + File.separator + this.getName();
        File fold = new File(path);
        task.add(fold.mkdir());
        this.parent = parent;
        for (File el : this.elements) {
            String filepath = path + File.separator + el.getName();
            File cr = new File(filepath);
            task.add(cr.createNewFile());
            if (el.exists()) {
                new FileManipulator(el).moveTo(cr);
                task.add(el.delete());
            }
        }
        final int x = next.size();
        for (int i = 0; i < x; i++) {
            task.addAll(next.get(i).mkdirs(this));
        }
        cursor = fold;
        return task;
    }

    /**
     * @return task progress (done or undone)
     * @throws IOException
     * @since 1.9
     */

    public LinkedList<Boolean> mkdirs() throws IOException {
        LinkedList<Boolean> task = new LinkedList<>();
        if (!created) {
            created = true;
            if (elements.size() == 0 && next.size() == 0) {
                task.add(mkdir());
                return task;
            }
            File fl = new File(name);
            if (!fl.exists()) {
                task.add(fl.mkdir());
                for (File el : elements) {
                    File cr = new File(name + File.separator + el.getName());
                    task.add(cr.createNewFile());
                    if (el.exists()) {
                        new FileManipulator(el).moveTo(cr);
                        task.add(el.delete());
                    }
                }
                final int x = next.size();
                for (int i = 0; i < x; i++) {
                    task.addAll(next.get(i).mkdirs(this));
                }
            }
            cursor = fl;
        }
        return task;
    }

    /**
     * @return true if folder created
     * @since 2.4
     */

    private boolean mkdir() {
        return new File(name).mkdirs();
    }

    /**
     * @param f
     * @return true if clear success
     * @since 2.3
     */

    private boolean clear(File f) {
        File[] allContents = f.listFiles();
        boolean success = false;
        if (allContents != null && allContents.length != 0) {
            for (File file : allContents) {
                clear(file);
            }
            success = true;
        }
        return success;
    }

    /**
     * @return true if success
     * @since 2.3
     */

    public boolean clear() {
        return clear(cursor);
    }

    /**
     * @param parent
     * @return this folder in parent folder
     * @throws IOException
     * @since 1.9
     */

    public LinkedList<Boolean> remkdirs(Folder parent) throws IOException {
        LinkedList<Boolean> task = new LinkedList<>();
        task.add(new FileManipulator(cursor).delete());
        task.addAll(this.mkdirs(parent));
        return task;
    }

    /**
     * @param target
     * @throws IOException
     * @since 1.9
     */

    public void saveTo(File target) throws IOException {
        FileOutputStream out = new FileOutputStream(target);
        ObjectOutputStream save = new ObjectOutputStream(out);
        save.writeObject(this);
        target.setWritable(false);
        save.close();
    }

    /**
     * @param data
     * @return Folder by saved data
     * @throws IOException
     * @throws ClassNotFoundException
     * @since 2.0
     */

    public static Folder load(File data) throws IOException, ClassNotFoundException {
        Folder f = null;
        FileInputStream fos = new FileInputStream(data);
        ObjectInputStream os = new ObjectInputStream(fos);
        f = (Folder) os.readObject();
        os.close();
        return f;
    }

    /**
     * @return true if success
     * @throws IOException
     * @since 2.4
     */

    public boolean extract() throws IOException {
        return new FileManipulator(ptr()).extractDirectory();
    }

    /**
     * @return zipped file
     * @throws IOException
     * @since 2.4
     */

    public File zip() throws IOException {
        Zipper p = new Zipper();
        for (File f : files()) {
            p.add(f);
        }
        for (Folder f : folders()) {
            p.add(f.ptr());
        }
        return p.zipAs(name + ".zip");
    }

    /**
     * @return this folder in actual file
     * @since 2.0
     */

    public File ptr() {
        return cursor;
    }

    /**
     * @return true if this directory is deleted, false otherwise
     * @throws Throwable
     * @since 1.9
     */

    public boolean delete() throws Throwable {
        System.gc();
        next = null;
        elements = null;
        this.finalize();
        return cursor == null ? false : new FileManipulator(cursor).delete();
    }

    public Folder get(String name) {
        for (Folder f : folders()) {
            if (f.getPath() == name || f.getName() == name)
                return f;
        }
        return null;
    }

    public File find(String name) {
        for (File f : files()) {
            if (f.getAbsolutePath() == name || f.getName() == name)
                return f;
        }
        return null;
    }

    // TODO: In test

    /**
     * @param f
     * @throws IOException
     * @since 2.4
     */

    private void initChild(Folder f) throws IOException {
        File[] list = f.cursor.listFiles();
        if (list != null && list.length != 0) {
            for (File e : list) {
                if (!e.isDirectory())
                    f.elements.add(e);
                else {
                    Folder target = new Folder(e.getAbsolutePath());
                    initChild(target);
                    f.next.add(target);
                }
            }
        }
    }

    // TODO: End

    /**
     * @param tab
     * @return a string representation of this object
     * @since 2.4
     */

    public String toString(String tab) {
        String result = cursor.getAbsolutePath() + "\n";
        for (Folder folder : folders()) {
            result += tab + "  " + folder.toString(tab + "  ") + "\n";
        }
        for (File file : files()) {
            result += tab + "  " + file.getAbsolutePath() + "\n";
        }
        return result;
    }

    @Override
    public String toString() {
        return toString("");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void finalize() throws Throwable {
        super.finalize();
    }

    /**
     * @param j
     * @return true if this folder is equavalent to j
     * @since 2.3
     */

    public boolean equals(Folder j) {
        return (this.name == j.name && this.parent.files().equals(j.parent.files()));
    }

    /**
     * Serial Version
     * 
     * @since 1.9
     */
    @Serial
    private static final long serialVersionUID = 1L;
}
