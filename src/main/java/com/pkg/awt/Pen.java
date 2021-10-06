package com.pkg.awt;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;

/**
 * Idea from 'turtle' package in Python. See 'turtle' package here
 * {@link https://docs.python.org/3/library/turtle.html} <blockquote>
 * 
 * <pre>
 * Pen pen = new Pen();
 * pen.forward(90);
 * pen.rotate(45);
 * pen.forward(90);
 * </pre>
 * 
 * </blockquote>
 * 
 * @since 1.8
 */

public final class Pen extends JPanel {
    private static final long serialVersionUID = 1L;
    private LinkedList<PenTask> k = new LinkedList<>();
    public BufferedImage drawer;
    private Graphics drawerG;
    private int locateX, locateY, pnW, pnH, degree = 0;
    private boolean penup = false;
    public final int dfLocationX, dfLocationY;
    public static final int MACfullScreenX = 1366, MACfullScreenY = 768;

    /**
     * @param locateX default x location
     * @param locateY default y location
     * @param frW     frame width
     * @param frH     frame height
     * @since 1.8
     */

    public Pen(int locateX, int locateY, int pnW, int pnH) {
        this.locateX = locateX;
        this.locateY = locateY;
        this.pnW = pnW;
        this.pnH = pnH;
        drawer = new BufferedImage(pnW, pnH, BufferedImage.TYPE_INT_RGB);
        this.setVisible(true);
        this.setSize(pnW, pnH);
        drawerG = drawer.getGraphics();
        this.dfLocationX = locateX;
        this.dfLocationY = locateY;
    }

    /**
     * set a panel with MAC fullscreen draw frame
     * 
     * @param locateX default x location
     * @param locateY default y location
     * @since 1.8
     */

    public Pen(int locateX, int locateY) {
        this(locateX, locateY, MACfullScreenX, MACfullScreenY);
    }

    /**
     * construct a pen with x = 0, y = 0
     * 
     * @since 1.8
     */

    public Pen() {
        this(MACfullScreenX / 2, MACfullScreenY / 2);
    }

    /**
     * @param c color of the drawer
     * @since 2.4
     */

    public void setColor(Color c) {
        drawerG.setColor(c);
    }

    /**
     * @param locateX new x location
     * @param locateY new y location
     * @since 1.8
     */

    public void goTo(int locateX, int locateY) {
        k.add((e) -> e.goTo(locateX, locateY));
        this.locateX = locateX;
        this.locateY = locateY;
    }

    /**
     * no drawing until {@link Pen#pendown()} is called
     * 
     * @since 1.8
     */

    public void penup() {
        k.add((e) -> e.penup());
        penup = true;
    }

    /**
     * can draw until {@link Pen#penup()} is called
     * 
     * @since 1.8
     */

    public void pendown() {
        k.add((e) -> e.pendown());
        penup = false;
    }

    /**
     * @param newX draw to newX location
     * @param newY draw to newY location
     * @since 1.8
     */

    public void drawLine(int newX, int newY) {
        k.add((e) -> e.drawLine(newX, newY));
        if (!penup)
            drawerG.drawLine(this.locateX, this.locateY, newX, newY);
        goTo(newX, newY);
    }

    /**
     * @param degree to rotate right
     * @since 1.8
     * @see Pen#forward
     */

    public void rotate(int degree) {
        k.add((e) -> e.rotate(degree));
        this.degree = this.degree + degree;
    }

    /**
     * @param step to move forward
     * @since 1.8
     * @see Pen#rotate
     */

    public void forward(int step) {
        if (!penup)
            drawLine((int) (locateX + step * Math.cos((degree) * Math.PI / 180)),
                    (int) (locateY + step * Math.sin((degree) * Math.PI / 180)));
        else
            goTo((int) (locateX + step * Math.cos((degree) * Math.PI / 180)),
                    (int) (locateY + step * Math.sin((degree) * Math.PI / 180)));
    }

    /**
     * @param step to move backward
     * @since 1.8
     */

    public void backward(int step) {
        forward(-step);
    }

    /**
     * point to default location
     * 
     * @since 1.8
     */

    public void locationReset() {
        goTo(dfLocationX, dfLocationY);
    }

    /**
     * @return current location
     * @since 1.8
     */

    public int[] currentLocation() {
        return new int[] { locateX, locateY };
    }

    /**
     * @return current image
     * @since 1.9
     */

    public Image getImage() {
        return drawer;
    }

    /**
     * @apiNote undo a task
     * @since 2.3
     */

    public void undo() {
        Pen p = new Pen(dfLocationX, dfLocationY, pnW, pnH);
        for (int i = 0; i < k.size() - 1; i++) {
            k.get(i).doTask(p);
        }
        this.accessibleContext = p.accessibleContext;
        this.degree = p.degree;
        this.drawer = p.drawer;
        this.drawerG = p.drawerG;
        this.k = p.k;
        this.listenerList = p.listenerList;
        this.locateX = p.locateX;
        this.locateY = p.locateY;
        this.penup = p.penup;
        this.pnH = p.pnH;
        this.pnW = p.pnW;
        this.ui = p.ui;
    }

    /**
     * clear current image
     * 
     * @since 1.8
     */

    public void clear() {
        degree = 0;
        locationReset();
        drawer = new Pen(locateX, locateY, pnW, pnH).drawer;
    }

    /**
     * delete this pen
     * 
     * @since 2.2
     */

    public void delete() {
        clear();
        locateX = locateY = pnW = pnH = degree = 0;
        goTo(locateX, locateY);
        drawer = null;
        drawerG = null;
        penup = false;
        System.gc();
    }

    /**
     * paint the current picture
     * 
     * @since 1.8
     */

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawer, 0, 0, this);
    }
}