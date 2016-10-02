package com.thezaorish.berlinclock.model;

import static com.thezaorish.berlinclock.model.BerlinClockLampType.O;

public class BerlinClockRow {

	private final BerlinClockRowConfig config;

	public BerlinClockRow(BerlinClockRowConfig rowConfig) {
		this.config = rowConfig;
	}

	public String switchOn(int numberOfLampsToSwitchOn) {
		return switchOff(config.numberOfLamps() - numberOfLampsToSwitchOn);
	}

	private String switchOff(int numberOfLampsToSwitchOff) {
		StringBuilder interim = new StringBuilder();
		int numberOfLamps = config.numberOfLamps();

		for (int i = 0; i < numberOfLamps - numberOfLampsToSwitchOff; i++) {
			interim.append(config.getSwitchedOn().charAt(i));
		}
		for (int i = numberOfLamps - numberOfLampsToSwitchOff; i < numberOfLamps; i++) {
			interim.append(O);
		}
		return interim.toString();
	}

}
