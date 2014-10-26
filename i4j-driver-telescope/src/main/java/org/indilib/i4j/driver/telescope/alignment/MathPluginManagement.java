package org.indilib.i4j.driver.telescope.alignment;

/*
 * #%L
 * INDI for Java Abstract Telescope Driver
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

import static org.indilib.i4j.Constants.PropertyStates.OK;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import org.indilib.i4j.Constants.SwitchRules;
import org.indilib.i4j.Constants.SwitchStatus;
import org.indilib.i4j.driver.INDIDriverExtension;
import org.indilib.i4j.driver.INDISwitchElement;
import org.indilib.i4j.driver.INDISwitchElementAndValue;
import org.indilib.i4j.driver.INDISwitchProperty;
import org.indilib.i4j.driver.INDITextElement;
import org.indilib.i4j.driver.INDITextElementAndValue;
import org.indilib.i4j.driver.INDITextProperty;
import org.indilib.i4j.driver.annotation.InjectElement;
import org.indilib.i4j.driver.annotation.InjectProperty;
import org.indilib.i4j.driver.event.SwitchEvent;
import org.indilib.i4j.driver.event.TextEvent;
import org.indilib.i4j.driver.telescope.INDITelescope;

public class MathPluginManagement extends INDIDriverExtension<INDITelescope> {

    private final static String ALIGNMENT_TAB = "Alignment";

    private Map<String, IMathPlugin> plugins;

    @InjectProperty(name = "ALIGNMENT_SUBSYSTEM_MATH_PLUGINS", label = "Math Plugins", group = ALIGNMENT_TAB)
    private INDISwitchProperty alignmentSubsystemMathPlugins;

    @InjectElement(name = BuiltInMathPlugin.INBUILT_MATH_PLUGIN_NAME, label = BuiltInMathPlugin.INBUILT_MATH_PLUGIN_LABEL, switchValue = SwitchStatus.ON)
    private INDISwitchElement builtInMathPluginElement;

    @InjectProperty(name = "ALIGNMENT_SUBSYSTEM_MATH_PLUGIN_INITIALISE", label = "(Re)Initialise Plugin", group = ALIGNMENT_TAB, switchRule = SwitchRules.AT_MOST_ONE)
    private INDISwitchProperty alignmentSubsystemMathPluginInitialise;

    @InjectElement(name = "ALIGNMENT_SUBSYSTEM_MATH_PLUGIN_INITIALISE", label = "OK")
    private INDISwitchElement alignmentSubsystemMathPluginInitialiseElement;

    @InjectProperty(name = "ALIGNMENT_SUBSYSTEM_ACTIVE", label = "Activate alignment subsystem", group = ALIGNMENT_TAB, switchRule = SwitchRules.AT_MOST_ONE)
    private INDISwitchProperty alignmentSubsystemActive;

    @InjectElement(name = "ALIGNMENT SUBSYSTEM ACTIVE", label = "Alignment Subsystem Active")
    private INDISwitchElement alignmentSubsystemActiveElement;

    /**
     * The following property is used for configuration purposes only and is not
     * exposed to the client.
     */
    @InjectProperty(name = "ALIGNMENT_SUBSYSTEM_CURRENT_MATH_PLUGIN", label = "Current Math Plugin", group = ALIGNMENT_TAB, saveable = true)
    private INDITextProperty alignmentSubsystemCurrentMathPlugin;

    @InjectElement(name = "ALIGNMENT ALIGNMENT_SUBSYSTEM_CURRENT_MATH_PLUGIN ACTIVE", label = "Current Math Plugin", textValue = BuiltInMathPlugin.INBUILT_MATH_PLUGIN_NAME)
    private INDITextElement alignmentSubsystemCurrentMathPluginElement;

    private IMathPlugin plugin;

    private final InMemoryDatabase inMemoryDatabase = new InMemoryDatabase();

    public MathPluginManagement(INDITelescope driver) {
        super(driver);
        enumeratePlugins();
        for (IMathPlugin mathPlugin : plugins.values()) {
            this.alignmentSubsystemMathPlugins.newElement().name(mathPlugin.id()).label(mathPlugin.name()).create();
        }
        alignmentSubsystemMathPlugins.setEventHandler(new SwitchEvent() {

            @Override
            public void processNewValue(Date date, INDISwitchElementAndValue[] elementsAndValues) {
                newAlignmentSubsystemMathPluginsValue(elementsAndValues);
            }
        });
        alignmentSubsystemMathPluginInitialise.setEventHandler(new SwitchEvent() {

            @Override
            public void processNewValue(Date date, INDISwitchElementAndValue[] elementsAndValues) {
                newAlignmentSubsystemMathPluginInitialiseValue();
            }
        });
        alignmentSubsystemActive.setEventHandler(new SwitchEvent() {

            @Override
            public void processNewValue(Date date, INDISwitchElementAndValue[] elementsAndValues) {
                newAlignmentSubsystemActiveValue(elementsAndValues);
            }
        });
        alignmentSubsystemCurrentMathPlugin.setEventHandler(new TextEvent() {

            @Override
            public void processNewValue(Date date, INDITextElementAndValue[] elementsAndValues) {
                newAlignmentSubsystemCurrentMathPluginValue(elementsAndValues);
            }
        });
    }

    private void enumeratePlugins() {
        plugins = new HashMap<>();
        ServiceLoader<IMathPlugin> loader = ServiceLoader.load(IMathPlugin.class, Thread.currentThread().getContextClassLoader());
        Iterator<IMathPlugin> iter = loader.iterator();
        while (iter.hasNext()) {
            IMathPlugin iMathPlugin = (IMathPlugin) iter.next();
            plugins.put(iMathPlugin.id(), iMathPlugin);
        }
    }

    private void newAlignmentSubsystemActiveValue(INDISwitchElementAndValue[] elementsAndValues) {
        alignmentSubsystemActive.setState(OK);
        alignmentSubsystemActive.setValues(elementsAndValues);

        // Update client
        driver.updateProperty(alignmentSubsystemActive);
    }

    private void newAlignmentSubsystemCurrentMathPluginValue(INDITextElementAndValue[] elementsAndValues) {
        INDISwitchElement currentPlugin = alignmentSubsystemMathPlugins.getOnElement();
        alignmentSubsystemMathPlugins.setState(OK);
        alignmentSubsystemCurrentMathPlugin.setValues(elementsAndValues);
        // in java no difference between buildin and other
        // Unload old plugin if required
        resetPluginIfChanged(currentPlugin);
    }

    private void newAlignmentSubsystemMathPluginInitialiseValue() {
        alignmentSubsystemMathPluginInitialise.setState(OK);
        alignmentSubsystemMathPluginInitialise.resetAllSwitches();
        // Update client
        driver.updateProperty(alignmentSubsystemMathPluginInitialise);
        // Initialise or reinitialise the current math plugin
        initialise();
    }

    private void newAlignmentSubsystemMathPluginsValue(INDISwitchElementAndValue[] elementsAndValues) {
        INDISwitchElement currentPlugin = alignmentSubsystemMathPlugins.getOnElement();
        alignmentSubsystemMathPlugins.setValues(elementsAndValues);
        alignmentSubsystemMathPlugins.setState(OK);
        resetPluginIfChanged(currentPlugin);
    }

    private IMathPlugin plugin() {
        if (plugin == null) {
            String message = null;
            if ((plugin = plugins.get(alignmentSubsystemCurrentMathPluginElement.getValue())) != null) {
                plugin.create();
                alignmentSubsystemMathPlugins.setOnlyOneSwitchOn(alignmentSubsystemMathPlugins.getElement(plugin.id()));
            } else {
                message = "MathPluginManagement - cannot load plugin " + alignmentSubsystemCurrentMathPluginElement.getValue() + "\n";
            }
            // Update client
            driver.updateProperty(alignmentSubsystemMathPlugins, message);
        }
        return plugin;
    }

    private void resetPluginIfChanged(INDISwitchElement currentPlugin) {
        INDISwitchElement newPlugin = alignmentSubsystemMathPlugins.getOnElement();

        if (newPlugin != currentPlugin) {
            // New plugin requested
            // Unload old plugin if required
            if (plugin != null) {
                plugin.destroy();
                plugin = null;
            }
            plugin();
        }
    }

    @Override
    public void connect() {
        addProperty(this.alignmentSubsystemMathPlugins);
        addProperty(this.alignmentSubsystemMathPluginInitialise);
        addProperty(this.alignmentSubsystemActive);
    }

    @Override
    public void disconnect() {
        removeProperty(this.alignmentSubsystemMathPlugins);
        removeProperty(this.alignmentSubsystemMathPluginInitialise);
        removeProperty(this.alignmentSubsystemActive);
    }

    // These must match the function signatures in MathPlugin

    public MountAlignment getApproximateMountAlignment() {
        return plugin().getApproximateMountAlignment();
    }

    public boolean initialise() {
        return plugin().initialise(this.inMemoryDatabase);
    }

    public void setApproximateMountAlignment(MountAlignment approximateAlignment) {
        plugin().setApproximateMountAlignment(approximateAlignment);
    }

    public boolean transformCelestialToTelescope(double rightAscension, double declination, double julianOffset, TelescopeDirectionVector apparentTelescopeDirectionVector) {
        if (alignmentSubsystemActiveElement.isOn())
            return plugin().transformCelestialToTelescope(rightAscension, declination, julianOffset, apparentTelescopeDirectionVector);
        else
            return false;
    }

    public boolean transformTelescopeToCelestial(TelescopeDirectionVector apparentTelescopeDirectionVector, DoubleRef rightAscension, DoubleRef declination) {
        if (alignmentSubsystemActiveElement.isOn())
            return plugin().transformTelescopeToCelestial(apparentTelescopeDirectionVector, rightAscension, declination);
        else
            return false;
    }

}
