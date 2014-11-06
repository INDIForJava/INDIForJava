package org.indilib.i4j.protocol;

/*
 * #%L
 * INDI Protocol implementation
 * %%
 * Copyright (C) 2012 - 2014 indiforjava
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

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class represents an INDI XML protocol element.
 * 
 * @param <T>
 *            type for the builder
 * @author Richard van Nieuwenhoven
 */
public abstract class DefElement<T> extends INDIProtocol<T> {

    /**
     * INDI XML protocol label attribute.
     */
    @XStreamAsAttribute
    private String label;

    /**
     * @return label attribute of the element .
     */
    public String getLabel() {
        return label;
    }

    /**
     * sets the label attribute of the element.
     * 
     * @param newLabel
     *            the label to set.
     * @return this for builder pattern.
     */
    @SuppressWarnings("unchecked")
    public T setLabel(String newLabel) {
        this.label = newLabel;
        return (T) this;
    }

    @Override
    public boolean isDef() {
        return true;
    }

    @Override
    public boolean isElement() {
        return true;
    }
}