package com.pkg.awt;

import javax.swing.JColorChooser;
import java.awt.Container;
import java.awt.Color;

public final class ColorPicker extends Container {
    private static final long serialVersionUID = 1L;
    public Color color;

    /**
     * @param title
     * @param defaultColor
     * @since 1.4
     */

    public ColorPicker(String title, Color defaultColor) {
        color = JColorChooser.showDialog(this, title, defaultColor);
    }
}
