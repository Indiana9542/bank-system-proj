package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SavingsTest {

	@Test
	public void savings_starts_empty() {
		Savings savings = new Savings(Savings.SAVINGS_ACCOUNTID, Savings.SAVINGS_APR);
		double actual = savings.getBalance();

		assertEquals(0, actual);
	}
}
