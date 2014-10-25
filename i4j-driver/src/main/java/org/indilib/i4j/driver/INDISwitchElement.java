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

import org.indilib.i4j.Constants;
import org.indilib.i4j.Constants.SwitchRules;
import org.indilib.i4j.Constants.SwitchStatus;
import org.indilib.i4j.driver.util.INDIElementBuilder;
import org.w3c.dom.Element;

/**
 * A class representing a INDI Switch Element.
 * 
 * @author S. Alonso (Zerjillo) [zerjioi at ugr.es]
 * @version 1.11, March 26, 2012
 */
public class INDISwitchElement extends INDIElement {

    /**
     * Serialization id.
     */
    private static final long serialVersionUID = -8604396976296138696L;

    /**
     * Current Status value for this Switch Element.
     */
    private SwitchStatus status;

    /**
     * Constructs an instance of a <code>INDISwitchElement</code>. Using the
     * settings from the builder.
     * 
     * @param builder
     *            the builder with all the settings.
     */
    public INDISwitchElement(INDIElementBuilder<INDISwitchElement> builder) {
        super(builder);
        this.status = builder.switchValue();
    }

    @Override
    public INDISwitchProperty getProperty() {
        return (INDISwitchProperty) super.getProperty();
    }

    @Override
    public SwitchStatus getValue() {
        return status;
    }

    /**
     * Sets the Element value to a new value. This method ensures that if the
     * Switch Property rule is <code>AT_MOST_ONE</code> or
     * <code>ONE_OF_MANY</code> and the new value is <code>ON</code> the other
     * Switch Elements of the property are turn to <code>OFF</code>.
     * 
     * @param newValue
     *            The new value. If the <code>newValue</code> is not a valid
     *            <code>SwitchStatus</code>.
     */
    @Override
    public void setValue(Object newValue) {
        SwitchStatus ss = null;
        try {
            ss = (SwitchStatus) newValue;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Value for a Switch Element must be a SwitchStatus");
        }

        if (ss == SwitchStatus.ON) {
            INDISwitchProperty p = getProperty();

            if (p.getRule() == SwitchRules.AT_MOST_ONE || p.getRule() == SwitchRules.ONE_OF_MANY) { // If
                                                                                                    // only
                                                                                                    // one
                                                                                                    // ON
                                                                                                    // value
                                                                                                    // is
                                                                                                    // allowed
                                                                                                    // in
                                                                                                    // the
                                                                                                    // property,
                                                                                                    // set
                                                                                                    // all
                                                                                                    // of
                                                                                                    // them
                                                                                                    // to
                                                                                                    // OFF
                p.resetAllSwitches();
            }
        }

        this.status = ss;
    }

    @Override
    public String getXMLOneElement(boolean includeMinMaxStep) {
        String stat = Constants.getSwitchStatusAsString(status);

        String xml = "<oneSwitch name=\"" + this.getName() + "\">" + stat + "</oneSwitch>";

        return xml;
    }

    @Override
    public String getNameAndValueAsString() {
        return getName() + " - " + getValue();
    }

    @Override
    protected String getXMLDefElement() {
        String stat = Constants.getSwitchStatusAsString(status);

        String xml = "<defSwitch name=\"" + this.getName() + "\" label=\"" + getLabel() + "\">" + stat + "</defSwitch>";

        return xml;
    }

    @Override
    public Object parseOneValue(Element xml) {
        return Constants.parseSwitchStatus(xml.getTextContent().trim());
    }

    /**
     * @return true is the value of this switch is OFF.
     */
    public final boolean isOff() {
        return getValue() == SwitchStatus.OFF;
    }

    /**
     * @return true is the value of this switch is ON.
     */
    public final boolean isOn() {
        return getValue() == SwitchStatus.ON;
    }

    /**
     * set the value to off.
     */
    public final void setOff() {
        setValue(SwitchStatus.OFF);
    }

    /**
     * set the value to on.
     */
    public final void setOn() {
        setValue(SwitchStatus.OFF);
    }

}
