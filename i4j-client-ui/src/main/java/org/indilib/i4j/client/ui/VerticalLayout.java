package org.indilib.i4j.client.ui;

/*
 * #%L
 * INDI for Java Client UI Library
 * %%
 * Copyright (C) 2013 - 2014 indiforjava
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * A vertical layout manager similar to java.awt.FlowLayout. Like FlowLayout
 * components do not expand to fill available space except when the horizontal
 * alignment is <code>BOTH</code> in which case components are stretched
 * horizontally. Unlike FlowLayout, components will not wrap to form another
 * column if there isn't enough space vertically. VerticalLayout can optionally
 * anchor components to the top or bottom of the display area or center them
 * between the top and bottom. Revision date 12th July 2001
 * 
 * @author Colin Mummery e-mail: colin_mummery@yahoo.com
 *         Homepage:www.kagi.com/equitysoft - Based on 'FlexLayout' in Java
 *         class libraries Vol 2 Chan/Lee Addison-Wesley 1998
 */
public class VerticalLayout implements LayoutManager {

    /**
     * standard vertical vgap of 5.
     */
    private static final int STD_VGAP_OF_5 = 5;

    /**
     * The horizontal alignment constant that designates centering. Also used to
     * designate center anchoring.
     */
    public static final int CENTER = 0;

    /**
     * The horizontal alignment constant that designates right justification.
     */
    public static final int RIGHT = 1;

    /**
     * The horizontal alignment constant that designates left justification.
     */
    public static final int LEFT = 2;

    /**
     * The horizontal alignment constant that designates stretching the
     * component horizontally.
     */
    public static final int BOTH = 3;

    /**
     * The anchoring constant that designates anchoring to the top of the
     * display area.
     */
    public static final int TOP = 1;

    /**
     * The anchoring constant that designates anchoring to the bottom of the
     * display area.
     */
    public static final int BOTTOM = 2;

    /**
     * the vertical vgap between components...defaults to 5.
     */
    private int vgap;

    /**
     * LEFT, RIGHT, CENTER or BOTH...how the components are justified.
     */
    private int alignment;

    /**
     * TOP, BOTTOM or CENTER ...where are the components positioned in an
     * overlarge space.
     */
    private int anchor;

    // Constructors
    /**
     * Constructs an instance of VerticalLayout with a vertical vgap of 5
     * pixels, horizontal centering and anchored to the top of the display area.
     */
    public VerticalLayout() {
        this(STD_VGAP_OF_5, CENTER, TOP);
    }

    /**
     * Constructs a VerticalLayout instance with horizontal centering, anchored
     * to the top with the specified vgap.
     * 
     * @param vgap
     *            An int value indicating the vertical seperation of the
     *            components
     */
    public VerticalLayout(int vgap) {
        this(vgap, CENTER, TOP);
    }

    /**
     * Constructs a VerticalLayout instance anchored to the top with the
     * specified vgap and horizontal alignment.
     * 
     * @param vgap
     *            An int value indicating the vertical seperation of the
     *            components
     * @param alignment
     *            An int value which is one of
     *            <code>RIGHT, LEFT, CENTER, BOTH</code> for the horizontal
     *            alignment.
     */
    public VerticalLayout(int vgap, int alignment) {
        this(vgap, alignment, TOP);
    }

    /**
     * Constructs a VerticalLayout instance with the specified vgap, horizontal
     * alignment and anchoring.
     * 
     * @param vgap
     *            An int value indicating the vertical seperation of the
     *            components
     * @param alignment
     *            An int value which is one of
     *            <code>RIGHT, LEFT, CENTER, BOTH</code> for the horizontal
     *            alignment.
     * @param anchor
     *            An int value which is one of <code>TOP, BOTTOM, CENTER</code>
     *            indicating where the components are to appear if the display
     *            area exceeds the minimum necessary.
     */
    public VerticalLayout(int vgap, int alignment, int anchor) {
        this.vgap = vgap;
        this.alignment = alignment;
        this.anchor = anchor;
    }

    /**
     * calculate the layout size.
     * 
     * @param parent
     *            the parent container
     * @param minimum
     *            reduce to the minimum?
     * @return the diamentions calculated.
     */
    private Dimension layoutSize(Container parent, boolean minimum) {
        Dimension dim = new Dimension(0, 0);
        Dimension d;
        synchronized (parent.getTreeLock()) {
            int n = parent.getComponentCount();
            for (int i = 0; i < n; i++) {
                Component c = parent.getComponent(i);
                if (c.isVisible()) {
                    d = minimum ? c.getMinimumSize() : c.getPreferredSize();
                    dim.width = Math.max(dim.width, d.width);
                    dim.height += d.height;
                    if (i > 0) {
                        dim.height += vgap;
                    }
                }
            }
        }
        Insets insets = parent.getInsets();
        dim.width += insets.left + insets.right;
        dim.height += insets.top + insets.bottom + vgap + vgap;
        return dim;
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        synchronized (parent.getTreeLock()) {
            int n = parent.getComponentCount();
            Dimension pd = parent.getSize();
            int y = 0;
            // work out the total size
            for (int i = 0; i < n; i++) {
                Component c = parent.getComponent(i);
                Dimension d = c.getPreferredSize();
                y += d.height + vgap;
            }
            y -= vgap; // otherwise there's a vgap too many
            // Work out the anchor paint
            if (anchor == TOP) {
                y = insets.top;
            } else if (anchor == CENTER) {
                y = (pd.height - y) / 2;
            } else {
                y = pd.height - y - insets.bottom;
            }
            // do layout
            for (int i = 0; i < n; i++) {
                Component c = parent.getComponent(i);
                Dimension d = c.getPreferredSize();
                int x = insets.left;
                int wid = d.width;
                if (alignment == CENTER) {
                    x = (pd.width - d.width) / 2;
                } else if (alignment == RIGHT) {
                    x = pd.width - d.width - insets.right;
                } else if (alignment == BOTH) {
                    wid = pd.width - insets.left - insets.right;
                }
                c.setBounds(x, y, wid, d.height);
                y += d.height + vgap;
            }
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return layoutSize(parent, false);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return layoutSize(parent, false);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        // not used
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        // not used
    }

}
