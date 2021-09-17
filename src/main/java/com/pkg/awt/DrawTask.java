package com.pkg.awt;

import java.awt.Graphics;

public interface DrawTask {
    /**
     * @param g
     * @since 1.7
     * @see Static.Draw
     */
    public void draw(Graphics g);
}