package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckingTest {

	@Test
	public void checking_starts_empty() {
		Checking checking = new Checking(22222222, 5.0);
		double actual = checking.getBalance();

		assertEquals(0, actual);
	}
}
