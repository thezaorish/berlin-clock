package com.thezaorish.berlinclock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BerlinClockTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private BerlinClock berlinClock = new BerlinClock();

	@Test
	public void topLampShouldBlinkOnAndOffEveryTwoSeconds() {
		assertThat(berlinClock.getSeconds(0), is("Y"));
		assertThat(berlinClock.getSeconds(1), is("O"));
		assertThat(berlinClock.getSeconds(59), is("O"));
	}

	@Test
	public void topHoursShouldLightRedLampForEvery5Hours() {
		assertThat(berlinClock.getTopHours(0), is("OOOO"));
		assertThat(berlinClock.getTopHours(7), is("ROOO"));
		assertThat(berlinClock.getTopHours(13), is("RROO"));
		assertThat(berlinClock.getTopHours(23), is("RRRR"));
		assertThat(berlinClock.getTopHours(24), is("RRRR"));
	}

	@Test
	public void bottomHoursShouldLightRedLampForEveryHourLeftFromTopHours() {
		assertThat(berlinClock.getBottomHours(0), is("OOOO"));
		assertThat(berlinClock.getBottomHours(13), is("RRRO"));
		assertThat(berlinClock.getBottomHours(23), is("RRRO"));
		assertThat(berlinClock.getBottomHours(24), is("RRRR"));
	}

	@Test
	public void topMinutesShouldHave3rd6thAnd9thLampsInRedToIndicateFirstQuarterHalfAndLastQuarter() {
		String minutes32 = berlinClock.getTopMinutes(32);
		assertThat(minutes32.substring(2, 3), is("R"));
		assertThat(minutes32.substring(5, 6), is("R"));
		assertThat(minutes32.substring(8, 9), is("O"));
	}

	@Test
	public void topMinutesShouldLightYellowLampForEvery5MinutesUnlessItIsFirstQuarterHalfOrLastQuarter() {
		assertThat(berlinClock.getTopMinutes(0), is("OOOOOOOOOOO"));
		assertThat(berlinClock.getTopMinutes(17), is("YYROOOOOOOO"));
		assertThat(berlinClock.getTopMinutes(59), is("YYRYYRYYRYY"));
	}

	@Test
	public void bottomMinutesShouldLightYellowLampForEveryMinuteLeftFromTopMinutes() {
		assertThat(berlinClock.getBottomMinutes(0), is("OOOO"));
		assertThat(berlinClock.getBottomMinutes(17), is("YYOO"));
		assertThat(berlinClock.getBottomMinutes(59), is("YYYY"));
	}

	@Test
	public void clockShouldResultInCorrectSecondsHoursAndMinutes_000000() {
		List<String> berlinTime = berlinClock.getTimeInternal(0, 0, 0);
		List<String> expected = asList("Y", "OOOO", "OOOO", "OOOOOOOOOOO", "OOOO");

		assertThat(berlinTime, is(expected));
	}

	@Test
	public void clockShouldResultInCorrectSecondsHoursAndMinutes_113701() {
		List<String> berlinTime = berlinClock.getTimeInternal(11, 37, 1);
		List<String> expected = asList("O", "RROO", "ROOO", "YYRYYRYOOOO", "YYOO");

		assertThat(berlinTime, is(expected));
	}

	@Test
	public void clockShouldResultInCorrectSecondsHoursAndMinutes_163716() {
		List<String> berlinTime = berlinClock.getTimeInternal(16, 37, 16);
		List<String> expected = asList("Y", "RRRO", "ROOO", "YYRYYRYOOOO", "YYOO");

		assertThat(berlinTime, is(expected));
	}

	@Test
	public void clockShouldResultInCorrectSecondsHoursAndMinutes_165006() {
		List<String> berlinTime = berlinClock.getTimeInternal(16, 50, 6);
		List<String> expected = asList("Y", "RRRO", "ROOO", "YYRYYRYYRYO", "OOOO");

		assertThat(berlinTime, is(expected));
	}

	@Test
	public void clockShouldResultInCorrectSecondsHoursAndMinutes_235959() {
		List<String> berlinTime = berlinClock.getTimeInternal(23, 59, 59);
		List<String> expected = asList("O", "RRRR", "RRRO", "YYRYYRYYRYY", "YYYY");

		assertThat(berlinTime, is(expected));
	}

	@Test
	public void clockShouldResultInCorrectSecondsHoursAndMinutes_235959_multiline() {
		String berlinTime = berlinClock.getTime(23, 59, 59);
		String expected = "O\nRRRR\nRRRO\nYYRYYRYYRYY\nYYYY";

		assertThat(berlinTime, is(expected));
	}

	@Test
	public void clockShouldRejectNegativeHours() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Hours must be between 0 and 24!");

		berlinClock.getTime(-1, 0, 0);
	}

	@Test
	public void clockShouldRejectHoursBiggerThan24() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Hours must be between 0 and 24!");

		berlinClock.getTime(25, 0, 0);
	}

	@Test
	public void clockShouldRejectNegativeMinutes() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Minutes must be between 0 and 59!");

		berlinClock.getTime(12, -1, 0);
	}

	@Test
	public void clockShouldRejectMinutesBiggerThan59() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Minutes must be between 0 and 59!");

		berlinClock.getTime(21, 60, 0);
	}

	@Test
	public void clockShouldRejectNegativeSeconds() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Seconds must be between 0 and 59!");

		berlinClock.getTime(12, 0, -1);
	}

	@Test
	public void clockShouldRejectSecondsBiggerThan59() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Seconds must be between 0 and 59!");

		berlinClock.getTime(21, 58, 60);
	}

	@Test
	public void clockShouldRejectInvalidDate() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid date!");

		berlinClock.getTime(24, 0, 1);
	}

}
