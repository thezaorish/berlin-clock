package com.thezaorish.berlinclock.model;

import com.google.common.base.Joiner;

import java.util.List;

import static com.thezaorish.berlinclock.model.BerlinClockLampType.R;
import static com.thezaorish.berlinclock.model.BerlinClockLampType.Y;
import static java.util.Arrays.asList;

public enum BerlinClockRowConfig {

	SECONDS(asList(Y)),
	FIVE_HOURS_EACH(asList(R, R, R, R)),
	ONE_HOUR_EACH(asList(R, R, R, R)),
	FIVE_MINUTES_EACH(asList(Y, Y, R, Y, Y, R, Y, Y, R, Y, Y)),
	ONE_MINUTE_EACH(asList(Y, Y, Y, Y));

	private final List<BerlinClockLampType> switchedOnLamps;

	BerlinClockRowConfig(List<BerlinClockLampType> switchedOn) {
		this.switchedOnLamps = switchedOn;
	}

	public String getSwitchedOn() {
		return Joiner.on("").join(switchedOnLamps);
	}

	public int numberOfLamps() {
		return switchedOnLamps.size();
	}

}
