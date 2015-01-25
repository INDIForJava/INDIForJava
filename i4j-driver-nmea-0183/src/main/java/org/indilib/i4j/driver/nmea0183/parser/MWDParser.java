package org.indilib.i4j.driver.nmea0183.parser;

/*
 * #%L
 * INDI for Java NMEA 0183 stream driver
 * %%
 * Copyright (C) 2012 - 2015 indiforjava
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

import net.sf.marineapi.nmea.parser.SentenceParser;
import net.sf.marineapi.nmea.sentence.TalkerId;

import org.indilib.i4j.driver.nmea0183.sentence.MWDSentence;

/**
 * Wind speed and direction.
 * 
 * @author Richard van Nieuwenhoven
 */
public class MWDParser extends SentenceParser implements MWDSentence {

    public static final String MWDSentenceId = "MWD";

    /**
     * Wind direction, degrees True, to the nearest 0,1 degree.
     */
    private static int WIND_DIRECTION_TRUE = 0;

    /**
     * T = true
     */
    private static int WIND_DIRECTION_TRUE_UNIT = 1;

    /**
     * Wind direction, degrees Magnetic, to the nearest 0,1 degree.
     */
    private static int WIND_DIRECTION_MAGNETIC = 2;

    /**
     * M = magnetic.
     */
    private static int WIND_DIRECTION_MAGNETIC_UNIT = 3;

    /**
     * Wind speed, knots, to the nearest 0,1 knot.
     */
    private static int WIND_SPEED_KNOTS = 4;

    /**
     * N = knots.
     */
    private static int WIND_SPEED_KNOTS_UNIT = 5;

    /**
     * Wind speed, meters per second, to the nearest 0,1 m/s.
     */
    private static int WIND_SPEED_METERS = 6;

    /**
     * M = meters per second
     */
    private static int WIND_SPEED_METERS_UNIT = 7;

    /**
     * Creates a new instance of MWVParser.
     * 
     * @param nmea
     *            MWV sentence String
     */
    public MWDParser(String nmea) {
        super(nmea, MWDSentenceId);
    }

    /**
     * Creates a new empty instance of MWVParser.
     * 
     * @param talker
     *            Talker id to set
     */
    public MWDParser(TalkerId talker) {
        super(talker, MWDSentenceId, 20);
    }

    /**
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     *         not available.
     */
    @Override
    public double getTrueWindDirection() {
        if (hasValue(WIND_DIRECTION_TRUE) && hasValue(WIND_DIRECTION_TRUE_UNIT) && getStringValue(WIND_DIRECTION_TRUE_UNIT).equalsIgnoreCase("T")) {
            return getDoubleValue(WIND_DIRECTION_TRUE);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     *         not available.
     */
    @Override
    public double getMagneticWindDirection() {
        if (hasValue(WIND_DIRECTION_MAGNETIC) && hasValue(WIND_DIRECTION_MAGNETIC_UNIT) && getStringValue(WIND_DIRECTION_MAGNETIC_UNIT).equalsIgnoreCase("M")) {
            return getDoubleValue(WIND_DIRECTION_MAGNETIC);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Wind speed, meters per second, to the nearest 0,1 m/s. NaN if not
     *         available.
     */
    @Override
    public double getWindSpeedNots() {
        if (hasValue(WIND_SPEED_KNOTS) && hasValue(WIND_SPEED_KNOTS_UNIT) && getStringValue(WIND_SPEED_KNOTS_UNIT).equalsIgnoreCase("N")) {
            return getDoubleValue(WIND_SPEED_KNOTS);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Wind speed, meters per second, to the nearest 0,1 m/s. NaN if not
     *         available.
     */
    @Override
    public double getWindSpeed() {
        if (hasValue(WIND_SPEED_METERS) && hasValue(WIND_SPEED_METERS_UNIT) && getStringValue(WIND_SPEED_METERS_UNIT).equalsIgnoreCase("M")) {
            return getDoubleValue(WIND_SPEED_METERS);
        } else {
            return Double.NaN;
        }
    }

}
