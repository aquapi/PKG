package com.pkg.struct;

import java.util.ArrayList;
import java.util.Arrays;

import com.pkg.def.Static;

public class Block {
    protected Object data;
    protected transient Block[] connectBlocks;

    /**
     * @param <T>
     * @param data
     * @param b
     * @since 2.3
     */

    public <T> Block(T data, Block... b) {
        this.data = data;
        this.connectBlocks = b;
    }

    /**
     * @param b
     * @since 2.3
     */

    public Block(Block... b) {
        this(null, b);
    }

    /**
     * @param <T>
     * @param data
     * @since 2.3
     */

    public <T> Block(T data) {
        this(data, new Block[0]);
    }

    /**
     * Default constructor for implementation
     * 
     * @since 2.3
     */

    public Block() {
        this(new Block[0]);
    }

    /**
     * @param <T>
     * @param data new data
     * @since 2.3
     */

    public <T> void setData(T data) {
        this.data = data;
    }

    /**
     * @param <T>
     * @return data in type T
     * @since 2.3
     */

    @SuppressWarnings("unchecked")
    public <T> T getData() {
        return (T) data;
    }

    /**
     * @param b
     * @since 2.3
     */

    public void addConnect(Block b) {
        Block[] added = new Block[connectBlocks.length + 1];
        for (int i = 0; i < connectBlocks.length; i++) {
            added[i] = connectBlocks[i];
        }
        added[added.length - 1] = b;
        connectBlocks = added;

        Block[] add = new Block[b.connectBlocks.length + 1];
        for (int i = 0; i < b.connectBlocks.length; i++) {
            add[i] = b.connectBlocks[i];
        }
        add[add.length - 1] = this;
        b.connectBlocks = add;
    }

    /**
     * @param b
     * @since 2.3
     */

    public void removeConnect(Block b) {
        if (connectBlocks.length > 0) {
            Block[] remv = new Block[connectBlocks.length - 1];
            for (int i = 0; i < remv.length; i++) {
                if (Arrays.asList(connectBlocks).indexOf(b) >= i)
                    remv[i] = connectBlocks[i + 1];
                else
                    remv[i] = connectBlocks[i];
            }
            connectBlocks = remv;

        }
        if (b.connectBlocks.length > 0) {
            Block[] remv1 = new Block[b.connectBlocks.length - 1];
            for (int i = 0; i < remv1.length; i++) {
                if (Arrays.asList(b.connectBlocks).indexOf(this) >= i)
                    remv1[i] = b.connectBlocks[i + 1];
                else
                    remv1[i] = b.connectBlocks[i];
            }
            b.connectBlocks = remv1;
        }
    }

    /**
     * @return list of blocks that are connected to this block
     * @since 2.3
     */

    public Block[] connects() {
        return connectBlocks;
    }

    /**
     * @param b
     * @return true if this block equals to block b
     * @since 2.3
     */

    public boolean equals(Block b) {
        return this.data.equals(b.data);
    }

    /**
     * Convert this block to String
     * 
     * @since 2.3
     */

    private String toString(Block... remv) {
        String result = this.data.toString() + " - [";
        if (connects().length == 1) {
            result = this.data.toString() + " - " + connects()[0].data.toString();
            return result;
        }
        Block[] remvadded = remv;
        Block k = new Block();
        for (Block b : connectBlocks) {
            {
                k.data = b.data;
                k.connectBlocks = b.connectBlocks;
                ArrayList<Block> arr = new ArrayList<Block>() {
                    {
                        addAll(Arrays.asList(k.connectBlocks));
                        removeAll(Arrays.asList(remv));
                    }
                };
                Block[] arr1 = new Block[b.connectBlocks.length - remvadded.length];
                Static.convert(arr.toArray(), arr1);
                k.connectBlocks = arr1;
            }
            {
                ArrayList<Block> r = new ArrayList<Block>() {
                    {
                        addAll(Arrays.asList(remvadded));
                        addAll(Arrays.asList(connectBlocks));
                        remove(b);
                    }
                };
                Block[] kr = new Block[remvadded.length + connectBlocks.length - 1];
                Static.convert(r.toArray(), kr);
            }
            if (Arrays.asList(connectBlocks).indexOf(b) == connectBlocks.length - 1)
                result += k.toString(remvadded);
            else
                result += k.toString(remvadded) + ", ";
        }
        result += "]";
        if (result.endsWith("[]"))
            result = result.substring(0, result.length() - 5);
        if (connects().length > 0)
            if (result == this.data.toString() + " - [" + connects()[0].data.toString() + "]")
                result = this.data.toString() + " - " + connects()[0].data.toString();
        return result;
    }

    @Override
    public String toString() {
        return toString(this);
    }
}
