package com.pkg.util;

import java.io.Serial;
import java.io.Serializable;

public final class Pair<E, D> implements Serializable, Comparable<Pair<E, D>> {
    private E e1 = null;
    private D e2 = null;

    /**
     * Construct an empty pair
     */

    public Pair() {
    }

    /**
     * @param e1 in pair
     * @since 2.0
     */

    public Pair(E e1) {
        this.e1 = e1;
    }

    /**
     * @param e1 in pair
     * @param e2 in pair
     * @since 2.0
     */

    public Pair(E e1, D e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    /**
     * @param val to be set
     * @since 2.0
     */

    public void setpair(E val) {
        e1 = val;
    }

    /**
     * @param val1 to be set
     * @param val2 to be set
     * @since 2.0
     */

    public void setpair(E val1, D val2) {
        this.e1 = val1;
        this.e2 = val2;
    }

    /**
     * @return true if one member is null
     * @since 2.0
     */

    public boolean isMissing() {
        return e1 != null && e2 != null;
    }

    /**
     * @return true if this pair is empty
     * @since 2.0
     */

    public boolean isEmpty() {
        return e1 == null && e2 == null;
    }

    /**
     * @param val to check
     * @return true if this pair has val
     * @since 2.0
     */

    public <T> boolean contains(T val) {
        return e1 == val || e2 == val;
    }

    /**
     * @return first element
     * @since 2.0
     */

    public E getFirst() {
        return e1;
    }

    /**
     * @return second element
     * @since 2.0
     */

    public D getLast() {
        return e2;
    }

    @Override
    public String toString() {
        return "(" + e1 + ", " + e2 + ")";
    }

    /**
     * Serial Version
     * 
     * @since 2.0
     */

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(Pair<E, D> o) {
        int res = ((Comparable<E>) e1).compareTo(o.getFirst());
        if (res != 0) {
            return res;
        }
        return ((Comparable<D>) e2).compareTo(o.getLast());
    }
}
