package com.thezaorish.berlinclock;

import com.google.common.base.Joiner;
import com.thezaorish.berlinclock.model.BerlinClockRow;

import java.util.List;

import static com.thezaorish.berlinclock.model.BerlinClockRowConfig.*;
import static java.util.Arrays.asList;

public class BerlinClock {

	private static final int DENOMINATOR = 5;
	private static final String SEPARATOR = System.getProperty("line.separator");

	private final BerlinClockRow every2Seconds = new BerlinClockRow(SECONDS);
	private final BerlinClockRow every5Hours = new BerlinClockRow(FIVE_HOURS_EACH);
	private final BerlinClockRow every1Hour = new BerlinClockRow(ONE_HOUR_EACH);
	private final BerlinClockRow every5Minutes = new BerlinClockRow(FIVE_MINUTES_EACH);
	private final BerlinClockRow every1Minute = new BerlinClockRow(ONE_MINUTE_EACH);

	public String getTime(int hours, int minutes, int seconds) {
		validate(hours, minutes, seconds);
		List<String> unitsOfTime = getTimeInternal(hours, minutes, seconds);
		return formatAsMultiLineString(unitsOfTime);
	}

	private void validate(int hours, int minutes, int seconds) {
		if (hours < 0 || hours > 24) {
			throw new IllegalArgumentException("Hours must be between 0 and 24!");
		}
		if (minutes < 0 || minutes > 59) {
			throw new IllegalArgumentException("Minutes must be between 0 and 59!");
		}
		if (seconds < 0 || seconds > 59) {
			throw new IllegalArgumentException("Seconds must be between 0 and 59!");
		}
		if (hours == 24 && (minutes > 0 || seconds > 0)) {
			throw new IllegalArgumentException("Invalid date!");
		}
	}

	private String formatAsMultiLineString(List<String> unitsOfTime) {
		return Joiner.on(SEPARATOR).join(unitsOfTime);
	}

	List<String> getTimeInternal(int hours, int minutes, int seconds) {
		return asList(getSeconds(seconds), getTopHours(hours), getBottomHours(hours), getTopMinutes(minutes), getBottomMinutes(minutes));
	}

	String getSeconds(int seconds) {
		return every2Seconds.switchOn(1 - seconds % 2);
	}

	String getTopHours(int hours) {
		return every5Hours.switchOn(hours / DENOMINATOR);
	}
	String getBottomHours(int hours) {
		return every1Hour.switchOn(hours % DENOMINATOR);
	}

	String getTopMinutes(int minutes) {
		return every5Minutes.switchOn(minutes / DENOMINATOR);
	}
	String getBottomMinutes(int minutes) {
		return every1Minute.switchOn(minutes % DENOMINATOR);
	}

}
