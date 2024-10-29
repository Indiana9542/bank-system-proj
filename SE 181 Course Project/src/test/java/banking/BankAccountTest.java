package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountTest {
	CD cd;

	@BeforeEach
	public void setUp() {
		cd = new CD(CD.CD_ACCOUNTID, CD.CD_APR, CD.CD_BALANCE);
	}

	@Test
	public void apr_is_input_value() {
		double actual = cd.getAPR();

		assertEquals(CD.CD_APR, actual);
	}

	@Test
	public void deposit_increases_balance() {
		cd.deposit(25.25);

		assertEquals(CD.CD_BALANCE + 25.25, cd.getBalance());
	}

	@Test
	public void depositing_twice_works_as_intended() {
		cd.deposit(50.25);
		cd.deposit(50.25);

		assertEquals(CD.CD_BALANCE + (2 * 50.25), cd.getBalance());
	}

	@Test
	public void withdraw_decreases_balance() {
		cd.withdraw(5.05);

		assertEquals(CD.CD_BALANCE - 5.05, cd.getBalance());
	}

	@Test
	public void withdraw_over_balance_causes_zero_balance() {
		cd.withdraw(100);

		assertEquals(0, cd.getBalance());
	}

	@Test
	public void withdrawing_twice_works_as_expected() {
		cd.withdraw(10.11);
		cd.withdraw(10.11);

		assertEquals(CD.CD_BALANCE - 20.22, cd.getBalance());
	}

}
