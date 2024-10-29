package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeTest {
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
	}

	@Test
	public void can_pass_time_for_savings() {
		Savings savings = new Savings(11111111, 3);
		bank.addAccount(savings);
		bank.depositInAccount(11111111, 1000);
		bank.passTime(1);
		double actual = bank.getAccount(11111111).getBalance();
		assertEquals(1, bank.getAccount(11111111).getAccountAgeInMonths());
		assertEquals(1002.5, actual, 0.001);
	}

	@Test
	public void can_pass_time_for_checking() {
		Checking checking = new Checking(11111111, 3);
		bank.addAccount(checking);
		bank.depositInAccount(11111111, 1000);
		bank.passTime(1);
		double actual = bank.getAccount(11111111).getBalance();
		assertEquals(1, bank.getAccount(11111111).getAccountAgeInMonths());
		assertEquals(1002.5, actual, 0.001);
	}

	@Test
	public void can_pass_time_for_cd() {
		CD cd = new CD(11111111, 2.1, 2000);
		bank.addAccount(cd);
		bank.passTime(1);
		double actual = bank.getAccount(11111111).getBalance();
		assertEquals(1, bank.getAccount(11111111).getAccountAgeInMonths());
		assertEquals(2014.0367, actual, 0.001);
	}

	@Test
	public void can_pass_multiple_months_for_savings() {
		Savings savings = new Savings(11111111, 2.1);
		bank.addAccount(savings);
		bank.depositInAccount(11111111, 1000);
		bank.passTime(4);
		double actual = bank.getAccount(11111111).getBalance();
		assertEquals(4, bank.getAccount(11111111).getAccountAgeInMonths());
		assertEquals(1007.018, actual, 0.001);
	}

	@Test
	public void can_pass_multiple_months_for_checking() {
		Checking checking = new Checking(11111111, 2.1);
		bank.addAccount(checking);
		bank.depositInAccount(11111111, 1000);
		bank.passTime(4);
		double actual = bank.getAccount(11111111).getBalance();
		// assertEquals(4, bank.getAccount(11111111).getAccountAgeInMonths());
		assertEquals(1007.018, actual, 0.001);
	}

	@Test
	public void can_pass_multiple_months_for_cd() {
		CD cd = new CD(11111111, 2.1, 1000);
		bank.addAccount(cd);
		bank.passTime(2);
		double actual = bank.getAccount(11111111).getBalance();
		assertEquals(2, bank.getAccount(11111111).getAccountAgeInMonths());
		assertEquals(1014.086, actual, 0.001);
	}

	@Test
	public void account_minimum_balance_fee_exists() {
		Checking checking = new Checking(11111111, 0);
		bank.addAccount(checking);
		bank.depositInAccount(11111111, 75);
		bank.passTime(1);
		double actual = bank.getAccount(11111111).getBalance();
		assertEquals(50, actual, 0.001);
	}

	@Test
	public void zero_balance_results_in_closed_account() {
		Checking checking = new Checking(11111111, 0);
		bank.addAccount(checking);
		bank.passTime(1);
		boolean actual = bank.checkAccountID(11111111);
		assertFalse(actual);
		assertEquals(0, bank.getNumAccounts());
	}
}
