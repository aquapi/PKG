package com.pkg.struct;

import java.util.HashSet;

public class Structure {
    HashSet<Block> bs = new HashSet<>();

    /** 
     * @param b
     * @since 2.3
     */

    public Structure(Block... b) {
        for (Block s : b) {
            init(s);
        }
    }

    /**
     * Default constructor for inplementation
     * 
     * @since 2.3
     */

    public Structure() {
    }

    /**
     * @param b
     * @param remv
     * @since 2.3
     */

    private void init(Block b, HashSet<Block> remv) {
        bs.add(b);
        final HashSet<Block> rem = remv;
        boolean check = false;
        for (int i = 0; i < b.connects().length; i++) {
            remv.add(b);
            for (int l = 0; l < b.connects().length - 1; l++) {
                if (i <= l)
                    remv.add(b.connects()[l + 1]);
                else
                    remv.add(b.connects()[l]);
            }
            for (int k = 0; k < remv.size(); k++) {
                if (check) break;
                check = b.connectBlocks[i] == remv.toArray(new Block[0])[k];
            }
            if (check) {
                check = false;
                continue;
            }
            init(b.connects()[i], remv);
            remv = rem;
        }
    }

    /**
     * @param b block to add to blocks array
     * @since 2.3
     */

    private void init(Block b) {
        init(b, new HashSet<Block>() {
            {
                add(b);
            }
        });
    }

    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i < bs.size(); i++) {
            if (i == bs.size() - 1) {
                result += bs.toArray(new Block[0])[i].data;
                continue;
            }
            result += bs.toArray(new Block[0])[i].data + ", ";
        }
        result += "]";
        return result;
    }
}
