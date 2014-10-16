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

import org.indilib.i4j.Constants.LightStates;
import org.indilib.i4j.Constants.PropertyStates;
import org.indilib.i4j.INDIException;

/**
 * A class representing a INDI Light Property with only one Light Element (with
 * the same name and label of the Property).
 * 
 * @author S. Alonso (Zerjillo) [zerjioi at ugr.es]
 * @version 1.36, January 11, 2014
 */
public class INDIOneElementLightProperty extends INDILightProperty {

    /**
     * Serialization id.
     */
    private static final long serialVersionUID = -243526454167834810L;

    /**
     * The Light Element.
     */
    private INDILightElement element;

    /**
     * Constructs an instance of <code>INDIOneElementLightProperty</code> with a
     * particular <code>driver</code>, <code>name</code>, <code>label</code>,
     * <code>group</code>, <code>state</code> and a initial
     * <code>lightState</code> for the Element.
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
     * @param lightState
     *            Initial state for the Element
     * @see INDILightProperty
     */
    public INDIOneElementLightProperty(INDIDriver driver, String name, String label, String group, PropertyStates state, LightStates lightState) {
        super(driver, name, label, group, state);

        element = new INDILightElement(this, name, label, lightState);
    }

    /**
     * Loads an instance of from a file or, if it cannot be loaded, constructs
     * it with a particular <code>driver</code>, <code>name</code>,
     * <code>label</code>, <code>group</code>, <code>state</code> and a initial
     * <code>lightState</code> for the Element. The property will autosave its
     * status to a file every time that it is changed.
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
     * @param lightState
     *            Initial state for the Element
     * @return The loaded text property or a new constructed one if cannot be
     *         loaded.
     * @see INDILightProperty
     */
    public static INDIOneElementLightProperty createSaveableOneElementLightProperty(INDIDriver driver, String name, String label, String group, PropertyStates state,
            LightStates lightState) {
        INDIOneElementLightProperty lp = loadOneElementLightProperty(driver, name);

        if (lp == null) {
            lp = new INDIOneElementLightProperty(driver, name, label, group, state, lightState);
            lp.setSaveable(true);
        }

        return lp;
    }

    /**
     * Loads a One Element Light Property from a file.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the property
     * @return The loaded light property or <code>null</code> if it could not be
     *         loaded.
     */
    private static INDIOneElementLightProperty loadOneElementLightProperty(INDIDriver driver, String name) {
        INDIProperty prop;

        try {
            prop = INDIProperty.loadFromFile(driver, name);
        } catch (INDIException e) { // Was not correctly loaded
            return null;
        }

        if (!(prop instanceof INDIOneElementLightProperty)) {
            return null;
        }

        INDIOneElementLightProperty tp = (INDIOneElementLightProperty) prop;
        tp.setSaveable(true);
        return tp;
    }

    /**
     * Constructs an instance of <code>INDIOneElementLightProperty</code> with a
     * particular <code>driver</code>, <code>name</code>, <code>label</code>,
     * <code>state</code> and a initial <code>lightState</code> for the Element.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the Property
     * @param label
     *            The label of the Property
     * @param state
     *            The initial state of the Property
     * @param lightState
     *            Initial state for the Element
     * @see INDILightProperty
     */
    public INDIOneElementLightProperty(INDIDriver driver, String name, String label, PropertyStates state, LightStates lightState) {
        super(driver, name, label, state);

        element = new INDILightElement(this, name, label, lightState);
    }

    /**
     * Loads an instance of from a file or, if it cannot be loaded, constructs
     * it with a particular <code>driver</code>, <code>name</code>,
     * <code>label</code>, <code>state</code> and a initial
     * <code>lightState</code> for the Element. The property will autosave its
     * status to a file every time that it is changed.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the Property
     * @param label
     *            The label of the Property
     * @param state
     *            The initial state of the Property
     * @param lightState
     *            Initial state for the Element
     * @return The loaded text property or a new constructed one if cannot be
     *         loaded.
     * @see INDILightProperty
     */
    public static INDIOneElementLightProperty createSaveableOneElementLightProperty(INDIDriver driver, String name, String label, PropertyStates state, LightStates lightState) {
        INDIOneElementLightProperty lp = loadOneElementLightProperty(driver, name);

        if (lp == null) {
            lp = new INDIOneElementLightProperty(driver, name, label, state, lightState);
            lp.setSaveable(true);
        }

        return lp;
    }

    /**
     * Constructs an instance of <code>INDIOneElementLightProperty</code> with a
     * particular <code>driver</code>, <code>name</code>, <code>state</code> and
     * a initial <code>lightState</code> for the Element.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the Property
     * @param state
     *            The initial state of the Property
     * @param lightState
     *            Initial state for the Element
     * @see INDILightProperty
     */
    public INDIOneElementLightProperty(INDIDriver driver, String name, PropertyStates state, LightStates lightState) {
        super(driver, name, state);

        element = new INDILightElement(this, name, lightState);
    }

    /**
     * Loads an instance of from a file or, if it cannot be loaded, constructs
     * it with a particular <code>driver</code>, <code>name</code>,
     * <code>state</code> and a initial <code>lightState</code> for the Element.
     * The property will autosave its status to a file every time that it is
     * changed.
     * 
     * @param driver
     *            The Driver to which this property is associated
     * @param name
     *            The name of the Property
     * @param state
     *            The initial state of the Property
     * @param lightState
     *            Initial state for the Element
     * @return The loaded text property or a new constructed one if cannot be
     *         loaded.
     * @see INDILightProperty
     */
    public static INDIOneElementLightProperty createSaveableOneElementLightProperty(INDIDriver driver, String name, PropertyStates state, LightStates lightState) {
        INDIOneElementLightProperty lp = loadOneElementLightProperty(driver, name);

        if (lp == null) {
            lp = new INDIOneElementLightProperty(driver, name, state, lightState);
            lp.setSaveable(true);
        }

        return lp;
    }

    /**
     * Gets the value of the Element.
     * 
     * @return The Value of the Element.
     * @see INDILightElement#getValue()
     */
    public LightStates getValue() {
        return element.getValue();
    }

    /**
     * Sets the value of the Element.
     * 
     * @param newValue
     *            The new value for the Element
     * @see INDILightElement#setValue(Object newValue)
     */
    public void setValue(Object newValue) {
        element.setValue(newValue);
    }
}
