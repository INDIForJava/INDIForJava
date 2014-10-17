package org.indilib.i4j.driver;

/*
 * #%L
 * INDI for Java Driver Library
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.indilib.i4j.Constants.PropertyPermissions;
import org.indilib.i4j.Constants.PropertyStates;
import org.indilib.i4j.INDIException;

/**
 * A class representing a INDI Text Property with only one Text Element (with
 * the same name and label of the Property).
 * 
 * @author S. Alonso (Zerjillo) [zerjioi at ugr.es]
 * @version 1.36, November 23, 2013
 */
public class INDIOneElementTextProperty extends INDITextProperty {

    /**
     * Serialization id.
     */
    private static final long serialVersionUID = 8306207724773408216L;

    /**
     * The Text Element.
     */
    private INDITextElement element;

    /**
     * Constructs an instance of <code>INDIOneElementTextProperty</code> with a
     * particular <code>driver</code>, <code>name</code>, <code>label</code>,
     * <code>group</code>, <code>state</code>, <code>permission</code>,
     * <code>timeout</code> and a initial <code>value</code> for the Element.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the Property
     * @param label
     *            The label of the Property
     * @param group
     *            The group of the Property
     * @param state
     *            The initial state of the Property
     * @param permission
     *            The permission of the Property
     * @param timeout
     *            The timeout of the Property
     * @param value
     *            Initial value for the Element
     * @see INDITextProperty
     */
    public INDIOneElementTextProperty(INDIDriver driver, String name, String label, String group, PropertyStates state, PropertyPermissions permission, int timeout,
            String value) {
        super(driver, name, label, group, state, permission, timeout);

        element = new INDITextElement(this, name, label, value);
    }

    /**
     * Loads an instance of from a file or, if it cannot be loaded, constructs
     * it with a particular <code>driver</code>, <code>name</code>,
     * <code>label</code>, <code>group</code>, <code>state</code>,
     * <code>permission</code>, <code>timeout</code> and a initial
     * <code>value</code> for the Element. The property will autosave its status
     * to a file every time that it is changed.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the Property
     * @param label
     *            The label of the Property
     * @param group
     *            The group of the Property
     * @param state
     *            The initial state of the Property
     * @param permission
     *            The permission of the Property
     * @param timeout
     *            The timeout of the Property
     * @param value
     *            Initial value for the Element
     * @return The loaded text property or a new constructed one if cannot be
     *         loaded.
     * @see INDITextProperty
     */
    public static INDIOneElementTextProperty createSaveableOneElementTextProperty(INDIDriver driver, String name, String label, String group, PropertyStates state,
            PropertyPermissions permission, int timeout, String value) {
        INDIOneElementTextProperty tp = loadOneElementTextProperty(driver, name);

        if (tp == null) {
            tp = new INDIOneElementTextProperty(driver, name, label, group, state, permission, timeout, value);
            tp.setSaveable(true);
        }

        return tp;
    }

    /**
     * Loads a One Element Text Property from a file.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the property
     * @return The loaded text property or <code>null</code> if it could not be
     *         loaded.
     */
    private static INDIOneElementTextProperty loadOneElementTextProperty(INDIDriver driver, String name) {
        INDIProperty<?> prop;

        try {
            prop = INDIProperty.loadFromFile(driver, name);
        } catch (INDIException e) { // Was not correctly loaded
            return null;
        }

        if (!(prop instanceof INDIOneElementTextProperty)) {
            return null;
        }

        INDIOneElementTextProperty tp = (INDIOneElementTextProperty) prop;
        tp.setSaveable(true);
        return tp;
    }

    /**
     * Constructs an instance of <code>INDIOneElementTextProperty</code> with a
     * particular <code>driver</code>, <code>name</code>, <code>label</code>,
     * <code>group</code>, <code>state</code>, <code>permission</code> and a
     * initial <code>value</code> for the Element.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the Property
     * @param label
     *            The label of the Property
     * @param group
     *            The group of the Property
     * @param state
     *            The initial state of the Property
     * @param permission
     *            The permission of the Property
     * @param value
     *            Initial value for the Element
     * @see INDITextProperty
     */
    public INDIOneElementTextProperty(INDIDriver driver, String name, String label, String group, PropertyStates state, PropertyPermissions permission, String value) {
        super(driver, name, label, group, state, permission);

        element = new INDITextElement(this, name, label, value);
    }

    /**
     * Loads an instance of from a file or, if it cannot be loaded, constructs
     * it with a particular <code>driver</code>, <code>name</code>,
     * <code>label</code>, <code>group</code>, <code>state</code>,
     * <code>permission</code> and a initial <code>value</code> for the Element.
     * The property will autosave its status to a file every time that it is
     * changed.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the Property
     * @param label
     *            The label of the Property
     * @param group
     *            The group of the Property
     * @param state
     *            The initial state of the Property
     * @param permission
     *            The permission of the Property
     * @param value
     *            Initial value for the Element
     * @return The loaded text property or a new constructed one if cannot be
     *         loaded.
     * @see INDITextProperty
     */
    public static INDIOneElementTextProperty createSaveableOneElementTextProperty(INDIDriver driver, String name, String label, String group, PropertyStates state,
            PropertyPermissions permission, String value) {
        INDIOneElementTextProperty tp = loadOneElementTextProperty(driver, name);

        if (tp == null) {
            tp = new INDIOneElementTextProperty(driver, name, label, group, state, permission, value);
            tp.setSaveable(true);
        }

        return tp;
    }

    /**
     * Constructs an instance of <code>INDIOneElementTextProperty</code> with a
     * particular <code>driver</code>, <code>name</code>, <code>label</code>,
     * <code>state</code>, <code>permission</code> and a initial
     * <code>value</code> for the Element.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the Property
     * @param label
     *            The label of the Property
     * @param state
     *            The initial state of the Property
     * @param permission
     *            The permission of the Property
     * @param value
     *            Initial value for the Element
     * @see INDITextProperty
     */
    public INDIOneElementTextProperty(INDIDriver driver, String name, String label, PropertyStates state, PropertyPermissions permission, String value) {
        super(driver, name, label, state, permission);

        element = new INDITextElement(this, name, label, value);
    }

    /**
     * Loads an instance of from a file or, if it cannot be loaded, constructs
     * it with a particular <code>driver</code>, <code>name</code>,
     * <code>label</code>, <code>state</code>, <code>permission</code> and a
     * initial <code>value</code> for the Element. The property will autosave
     * its status to a file every time that it is changed.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the Property
     * @param label
     *            The label of the Property
     * @param state
     *            The initial state of the Property
     * @param permission
     *            The permission of the Property
     * @param value
     *            Initial value for the Element
     * @return The loaded text property or a new constructed one if cannot be
     *         loaded.
     * @see INDITextProperty
     */
    public static INDIOneElementTextProperty createSaveableOneElementTextProperty(INDIDriver driver, String name, String label, PropertyStates state,
            PropertyPermissions permission, String value) {
        INDIOneElementTextProperty tp = loadOneElementTextProperty(driver, name);

        if (tp == null) {
            tp = new INDIOneElementTextProperty(driver, name, label, state, permission, value);
            tp.setSaveable(true);
        }

        return tp;
    }

    /**
     * Gets the value of the Element.
     * 
     * @return The Value of the Element.
     * @see INDITextElement#getValue()
     */
    public String getValue() {
        return element.getValue();
    }

    /**
     * Sets the value of the Element.
     * 
     * @param newValue
     *            The new value for the Element
     * @see INDITextElement#setValue(Object newValue)
     */
    public void setValue(Object newValue) {
        element.setValue(newValue);
    }
}
