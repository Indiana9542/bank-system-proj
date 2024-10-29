package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	Validator validator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		validator = new Validator();
		bank = new Bank();
	}

	@Test
	public void can_deposit_into_savings() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		Boolean actual = validator.validate("deposit 11111111 1100", bank);
		assertTrue(actual);
	}

	@Test
	public void can_deposit_into_checking() {
		Checking checking = new Checking(11111111, 0.6);
		bank.addAccount(checking);
		Boolean actual = validator.validate("deposit 11111111 900", bank);
		assertTrue(actual);
	}

	@Test
	public void cannot_deposit_into_cd() {
		CD cd = new CD(11111111, 0.6, 1000);
		bank.addAccount(cd);
		Boolean actual = validator.validate("deposit 11111111 900", bank);
		assertFalse(actual);
	}

	@Test
	public void cannot_deposit_negative_amount() {
		Checking checking = new Checking(11111111, 0.6);
		bank.addAccount(checking);
		Boolean actual = validator.validate("deposit 11111111 -900", bank);
		assertFalse(actual);
	}

	@Test
	public void can_deposit_zero_checking() {
		Checking checking = new Checking(11111111, 0.6);
		bank.addAccount(checking);
		Boolean actual = validator.validate("deposit 11111111 0", bank);
		assertTrue(actual);
	}

	@Test
	public void can_deposit_zero_savings() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		Boolean actual = validator.validate("deposit 11111111 0", bank);
		assertTrue(actual);
	}

	@Test
	public void savings_deposit_upper_bound_exists() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		Boolean actual = validator.validate("deposit 11111111 2500.01", bank);
		assertFalse(actual);
	}

	@Test
	public void savings_deposit_highest_bound_exists() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		Boolean actual = validator.validate("deposit 11111111 2500", bank);
		assertTrue(actual);
	}

	@Test
	public void checking_deposit_upper_bound_exists() {
		Checking checking = new Checking(11111111, 0.6);
		bank.addAccount(checking);
		Boolean actual = validator.validate("deposit 11111111 1000.01", bank);
		assertFalse(actual);
	}

	@Test
	public void checking_deposit_highest_bound_exists() {
		Checking checking = new Checking(11111111, 0.6);
		bank.addAccount(checking);
		Boolean actual = validator.validate("deposit 11111111 1000", bank);
		assertTrue(actual);
	}

	@Test
	public void command_too_short() {
		Checking checking = new Checking(11111111, 0.6);
		bank.addAccount(checking);
		Boolean actual = validator.validate("deposit 11111111", bank);
		assertFalse(actual);
	}

	@Test
	public void command_too_long() {
		Checking checking = new Checking(11111111, 0.6);
		bank.addAccount(checking);
		Boolean actual = validator.validate("deposit 11111111 1000 1.3", bank);
		assertFalse(actual);
	}

	@Test
	public void missing_deposit_command() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		Boolean actual = validator.validate("11111111 1100", bank);
		assertFalse(actual);
	}

	@Test
	public void missing_account_id() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		Boolean actual = validator.validate("deposit 1100", bank);
		assertFalse(actual);
	}

	@Test
	public void case_insensitive() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		Boolean actual = validator.validate("dEPOsit 11111111 1100", bank);
		assertTrue(actual);
	}

	@Test
	public void typo_in_command() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		Boolean actual = validator.validate("depsit 11111111 1100", bank);
		assertFalse(actual);
	}

}
