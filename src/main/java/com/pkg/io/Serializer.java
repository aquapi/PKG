package com.pkg.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer {

    /**
     * Block user from creating Serializer instance
     * 
     * @since 2.2
     */

    public Serializer() {
        throw new IllegalCallerException("User are not allowed to create an instance of this APIs");
    }

    /**
     * @param <T>
     * @param object
     * @param target
     * @throws IOException
     * @since 1.9
     */

    public static <T extends Serializable> void save(T object, File target) throws IOException {
        FileOutputStream out = new FileOutputStream(target);
        ObjectOutputStream save = new ObjectOutputStream(out);
        save.writeObject(object);
        target.setWritable(false);
        save.close();
    }

    /**
     * @param <T>
     * @param data
     * @return object by data file
     * @throws IOException
     * @throws ClassNotFoundException
     * @since 1.9
     */

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T load(File data) throws IOException, ClassNotFoundException {
        T f = null;
        FileInputStream fos = new FileInputStream(data);
        ObjectInputStream os = new ObjectInputStream(fos);
        f = (T) os.readObject();
        os.close();
        return f;
    }
}
