package org.indilib.i4j.protocol;

/*
 * #%L INDI Protocol implementation %% Copyright (C) 2012 - 2014 indiforjava %%
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Lesser Public License for more details. You should have received a copy of
 * the GNU General Lesser Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>. #L%
 */

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class represents an INDI XML protocol element.
 * 
 * @param <T>
 *            type for the builder
 * @author Richard van Nieuwenhoven
 */
public abstract class INDIProtocol<T> {

    /**
     * the name element attribute.
     */
    @XStreamAsAttribute
    private String name;

    /**
     * @return the name element attribute.
     */
    public String getName() {
        return name;
    }

    /**
     * set the name element attribute.
     * 
     * @param newName
     *            the new name of the attribute.
     * @return this for builder pattern.
     */
    @SuppressWarnings("unchecked")
    public T setName(String newName) {
        this.name = newName;
        return (T) this;
    }

    /**
     * @return is this a definition element.
     */
    public boolean isDef() {
        return false;
    }

    /**
     * @return is this a definition element.
     */
    public boolean isElement() {
        return false;
    }

    /**
     * @return is this a definition blob element.
     */
    public boolean isDefBlobElement() {
        return false;
    }

    /**
     * @return is this a blob element.
     */
    public boolean isBlob() {
        return false;
    }

    /**
     * @return is this a definition blob vector.
     */
    public boolean isDefBlobVector() {
        return false;
    }

    /**
     * @return is this a definition element.
     */
    public boolean isDefElement() {
        return false;
    }

    /**
     * @return is this a definition light element.
     */
    public boolean isDefLightElement() {
        return false;
    }

    /**
     * @return is this a definition light vector.
     */
    public boolean isDefLightVector() {
        return false;
    }

    /**
     * @return is this a definition number element.
     */
    public boolean isDefNumberElement() {
        return false;
    }

    /**
     * @return is this a definition number vector.
     */
    public boolean isDefNumberVector() {
        return false;
    }

    /**
     * @return is this a definition switch vector.
     */
    public boolean isDefSwitchVector() {
        return false;
    }

    /**
     * @return is this a switch.
     */
    public boolean isSwitch() {
        return false;
    }

    /**
     * @return is this a definition text element.
     */
    public boolean isDefTextElement() {
        return false;
    }

    /**
     * @return is this a definition text vector.
     */
    public boolean isDefTextVector() {
        return false;
    }

    /**
     * @return is this a definition vector.
     */
    public boolean isDefVector() {
        return false;
    }

    /**
     * @return is this a one element.
     */
    public boolean isOne() {
        return false;
    }

    /**
     * @return is this a one blob element.
     */
    public boolean isOneBlob() {
        return false;
    }

    /**
     * @return is this a one element.
     */
    public boolean isOneElement() {
        return false;
    }

    /**
     * @return is this a one light element.
     */
    public boolean isOneLight() {
        return false;
    }

    /**
     * @return is this a light element.
     */
    public boolean isLight() {
        return false;
    }

    /**
     * @return is this a one number element.
     */
    public boolean isOneNumber() {
        return false;
    }

    /**
     * @return is this a number element.
     */
    public boolean isNumber() {
        return false;
    }

    /**
     * @return is this a one text element.
     */
    public boolean isOneText() {
        return false;
    }

    /**
     * @return is this a text element.
     */
    public boolean isText() {
        return false;
    }

    /**
     * @return is this a set element.
     */
    public boolean isSet() {
        return false;
    }

    /**
     * @return is this a vector.
     */
    public boolean isVector() {
        return false;
    }

    /**
     * @return is this a set blob vector.
     */
    public boolean isSetBlobVector() {
        return false;
    }

    /**
     * @return is this a set light vector.
     */
    public boolean isSetLightVector() {
        return false;
    }

    /**
     * @return is this a set number vector.
     */
    public boolean isSetNumberVector() {
        return false;
    }

    /**
     * @return is this a set switch vector.
     */
    public boolean isSetSwitchVector() {
        return false;
    }

    /**
     * @return is this a set text vector.
     */
    public boolean isSetTextVector() {
        return false;
    }

    /**
     * @return is this a set vector.
     */
    public boolean isSetVector() {
        return false;
    }

    /**
     * @return is this a get properties.
     */
    public boolean isGetProperties() {
        return false;
    }

    /**
     * @return is this a get properties.
     */
    public boolean isNewBlobVector() {
        return false;
    }

    /**
     * @return is this a get properties.
     */
    public boolean isNewLightVector() {
        return false;
    }

    /**
     * @return is this a get properties.
     */
    public boolean isNewNumberVector() {
        return false;
    }

    /**
     * @return is this a get properties.
     */
    public boolean isNewSwitchVector() {
        return false;
    }

    /**
     * @return is this a get properties.
     */
    public boolean isNewTextVector() {
        return false;
    }

    /**
     * @return is this a get properties.
     */
    public boolean isNewVector() {
        return false;
    }

    /**
     * @return is this a get properties.
     */
    public boolean isNew() {
        return false;
    }
}