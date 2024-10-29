package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {
	Validator validator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		validator = new Validator();
		bank = new Bank();
	}

	@Test
	public void can_withdraw_from_savings() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		bank.depositInAccount(11111111, 300.27);
		Boolean actual = validator.validate("withdraw 11111111 100.11", bank);
		assertTrue(actual);
	}

	@Test
	public void can_withdraw_from_checking() {
		Checking checking = new Checking(11111111, 0.6);
		bank.addAccount(checking);
		bank.depositInAccount(11111111, 300.27);
		Boolean actual = validator.validate("withdraw 11111111 100.11", bank);
		assertTrue(actual);
	}

	@Test
	public void can_withdraw_from_cd_after_12_months() {
		CD cd = new CD(11111111, 0, 1800.27);
		bank.addAccount(cd);
		bank.passTime(12);
		boolean actual = validator.validate("withdraw 11111111 2000", bank);
		assertTrue(actual);
	}

	@Test
	public void cannot_withdraw_negative_from_savings() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		bank.depositInAccount(11111111, 300.00);
		Boolean actual = validator.validate("withdraw 11111111 -100.00", bank);
		assertFalse(actual);
	}

	@Test
	public void cannot_withdraw_negative_from_checking() {
		Checking checking = new Checking(11111111, 0.6);
		bank.addAccount(checking);
		bank.depositInAccount(11111111, 300.27);
		Boolean actual = validator.validate("withdraw 11111111 -100.11", bank);
		assertFalse(actual);
	}

	@Test
	public void can_withdraw_zero_from_savings() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		bank.depositInAccount(11111111, 300.00);
		Boolean actual = validator.validate("withdraw 11111111 0", bank);
		assertTrue(actual);
	}

	@Test
	public void can_withdraw_zero_from_checking() {
		Checking checking = new Checking(11111111, 0.6);
		bank.addAccount(checking);
		bank.depositInAccount(11111111, 300.27);
		Boolean actual = validator.validate("withdraw 11111111 0", bank);
		assertTrue(actual);
	}

	@Test
	public void cannot_withdraw_before_twelve_months_pass_cd() {
		CD cd = new CD(11111111, 0, 1800.27);
		bank.addAccount(cd);
		bank.passTime(11);
		boolean actual = validator.validate("withdraw 11111111 2000", bank);
		assertFalse(actual);
	}

	@Test
	public void cannot_withdraw_twice_from_savings_account_in_one_month() {
		Savings savings = new Savings(11111111, 0);
		bank.addAccount(savings);
		bank.depositInAccount(11111111, 300);
		bank.withdrawFromAccount(11111111, 150);
		boolean actual = validator.validate("withdraw 11111111 150", bank);
		assertFalse(actual);
	}

	@Test
	public void can_withdraw_twice_from_savings_account_in_two_months() {
		Savings savings = new Savings(11111111, 0);
		bank.addAccount(savings);
		bank.depositInAccount(11111111, 300);
		bank.withdrawFromAccount(11111111, 150);
		bank.passTime(1);
		boolean actual = validator.validate("withdraw 11111111 150", bank);
		assertTrue(actual);
	}

	@Test
	public void too_few_arguments() {
		Boolean actual = validator.validate("Withdraw 11111111", bank);
		assertFalse(actual);
	}

	@Test
	public void too_many_arguments() {
		Boolean actual = validator.validate("Withdraw 11111111 100.23 23", bank);
		assertFalse(actual);
	}

	@Test
	public void letter_as_id_fails() {
		Boolean actual = validator.validate("Withdraw aaaaaaaa 100.23", bank);
		assertFalse(actual);
	}

	@Test
	public void letter_as_amount_fails() {
		Boolean actual = validator.validate("Withdraw 11111111 aaa", bank);
		assertFalse(actual);
	}

	@Test
	public void account_id_too_short_fails() {
		Boolean actual = validator.validate("Withdraw 1234567 100.23", bank);
		assertFalse(actual);
	}

	@Test
	public void account_id_too_long_fails() {
		Boolean actual = validator.validate("Withdraw 123456798 100.23", bank);
		assertFalse(actual);
	}

	@Test
	public void typo_fails() {
		Boolean actual = validator.validate("Witraw 123456798 100.23", bank);
		assertFalse(actual);
	}
}
