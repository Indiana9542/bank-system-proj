package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {
	Validator validator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		validator = new Validator();
		bank = new Bank();
	}

	@Test
	public void create_command_works_for_savings() {
		Boolean actual = validator.validate("Create savings 12345678 0.6", bank);
		assertTrue(actual);
	}

	@Test
	public void create_command_works_for_checking() {
		Boolean actual = validator.validate("Create checking 11111111 0.6", bank);
		assertTrue(actual);
	}

	@Test
	public void create_command_works_for_cd() {
		Boolean actual = validator.validate("Create cd 33333333 0.6 1200", bank);
		assertTrue(actual);
	}

	@Test
	public void create_command_fails_for_wrong_account_type() {
		Boolean actual = validator.validate("Create account 12345678 0.6", bank);
		assertFalse(actual);
	}

	@Test
	public void create_command_fails_for_duplicate_id() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		Boolean actual = validator.validate("Create checking 11111111 0.6", bank);
		assertFalse(actual);
	}

	@Test
	public void apr_lower_bound_exists() {
		Boolean actual = validator.validate("Create savings 11111111 -0.01", bank);
		assertFalse(actual);
	}

	@Test
	public void apr_upper_bound_exists() {
		Boolean actual = validator.validate("Create savings 11111111 10.01", bank);
		assertFalse(actual);
	}

	@Test
	public void apr_lowest_bound_exists() {
		Boolean actual = validator.validate("Create savings 11111111 0", bank);
		assertTrue(actual);
	}

	@Test
	public void apr_highest_bound_exists() {
		Boolean actual = validator.validate("Create savings 11111111 10", bank);
		assertTrue(actual);
	}

	@Test
	public void cd_account_balance_lower_bound_exists() {
		Boolean actual = validator.validate("Create cd 11111111 0.6 999.99", bank);
		assertFalse(actual);
	}

	@Test
	public void cd_account_balance_upper_bound_exists() {
		Boolean actual = validator.validate("Create cd 11111111 0.6 10000.01", bank);
		assertFalse(actual);
	}

	@Test
	public void cd_account_balance_lowest_bound_exists() {
		Boolean actual = validator.validate("Create cd 11111111 0.6 1000", bank);
		assertTrue(actual);
	}

	@Test
	public void cd_account_balance_highest_bound_exists() {
		Boolean actual = validator.validate("Create cd 11111111 0.6 10000", bank);
		assertTrue(actual);
	}

	@Test
	public void account_id_lower_bound_exists() {
		Boolean actual = validator.validate("create savings 1234567 0.6", bank);
		assertFalse(actual);
	}

	@Test
	public void account_id_upper_bound_exists() {
		Boolean actual = validator.validate("create savings 123456789 0.6", bank);
		assertFalse(actual);
	}

	@Test
	public void cd_too_few_arguments() {
		Boolean actual = validator.validate("Create cd 11111111 10000", bank);
		assertFalse(actual);
	}

	@Test
	public void cd_too_many_arguments() {
		Boolean actual = validator.validate("Create cd 11111111 0.6 10000 12", bank);
		assertFalse(actual);
	}

	@Test
	public void cd_negative_balance() {
		Boolean actual = validator.validate("Create cd 11111111 0.6 -10000 12", bank);
		assertFalse(actual);
	}

	@Test
	public void cd_no_balance() {
		Boolean actual = validator.validate("Create cd 11111111 0.6", bank);
		assertFalse(actual);
	}

	@Test
	public void savings_or_checking_too_few_arguments() {
		Boolean actual = validator.validate("Create savings 11111111", bank);
		assertFalse(actual);
	}

	@Test
	public void savings_or_checking_too_many_arguments() {
		Boolean actual = validator.validate("Create savings 11111111 0.6 12", bank);
		assertFalse(actual);
	}

	@Test
	public void case_does_not_matter() {
		Boolean actual = validator.validate("cReATE Cd 11111111 0.6 10000", bank);
		assertTrue(actual);
	}

	@Test
	public void typo_in_command() {
		Boolean actual = validator.validate("ceate savings 11111111 0.6", bank);
		assertFalse(actual);
	}

	@Test
	public void command_missing_create() {
		Boolean actual = validator.validate("savings 11111111 0.6", bank);
		assertFalse(actual);
	}

	@Test
	public void command_missing_account_type() {
		Boolean actual = validator.validate("create 11111111 0.6", bank);
		assertFalse(actual);
	}

	@Test
	public void missing_apr() {
		Boolean actual = validator.validate("create savings 11111111", bank);
		assertFalse(actual);
	}

	@Test
	public void account_id_is_letters() {
		Boolean actual = validator.validate("create savings aaaaaaaa 0.6", bank);
		assertFalse(actual);
	}

	@Test
	public void apr_is_letters() {
		Boolean actual = validator.validate("create savings 11111111 abc", bank);
		assertFalse(actual);
	}

}
