package org.indilib.i4j.driver.annotation;

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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.indilib.i4j.Constants.PropertyPermissions;
import org.indilib.i4j.Constants.PropertyStates;
import org.indilib.i4j.Constants.SwitchRules;

/**
 * Use this annotation on a field in your driver oder extension and a element
 * will be injected in the dirver with the attributes specified.
 * 
 * @author Richard van Nieuwenhoven
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.FIELD
})
public @interface InjectProperty {

    /**
     * One minute in seconds.
     */
    int ONE_MINUTE_SECONDS = 60;

    /**
     * the permissions for the property, defaults to RW.
     */
    PropertyPermissions permission() default PropertyPermissions.RW;

    /**
     * the timeout for the property defaults to 60.
     */
    int timeout() default ONE_MINUTE_SECONDS;

    /**
     * name of the property (mandatory).
     */
    String name();

    /**
     * label of the property (mandatory).
     */
    String label();

    /**
     * the initial state of the property.
     */
    PropertyStates state() default PropertyStates.IDLE;

    /**
     * the tab group to use for this property (mandatory if it is not in a
     * group).
     */
    String group() default "";

    /**
     * should the value of this property be saved in a property file? defaults
     * to false.
     */
    boolean saveable() default false;

    /**
     * if this property is a switch property what rule should apply? defaults to
     * ONE_OF_MANY.
     */
    SwitchRules switchRule() default SwitchRules.ONE_OF_MANY;

}
