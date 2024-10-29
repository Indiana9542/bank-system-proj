package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {
	Validator validator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		validator = new Validator();
		bank = new Bank();
	}

	@Test
	public void can_pass_time() {
		Boolean actual = validator.validate("pass 5", bank);
		assertTrue(actual);
	}

	@Test
	public void pass_time_can_pass_1_month() {
		Boolean actual = validator.validate("pass 1", bank);
		assertTrue(actual);
	}

	@Test
	public void pass_time_can_pass_60_months() {
		Boolean actual = validator.validate("pass 60", bank);
		assertTrue(actual);
	}

	@Test
	public void cannot_pass_0_months() {
		Boolean actual = validator.validate("pass 0", bank);
		assertFalse(actual);
	}

	@Test
	public void cannot_pass_61_months() {
		Boolean actual = validator.validate("pass 61", bank);
		assertFalse(actual);
	}

	@Test
	public void cannot_pass_decimal_months() {
		Boolean actual = validator.validate("pass 15.61", bank);
		assertFalse(actual);
	}

	@Test
	public void command_is_case_insensitive() {
		Boolean actual = validator.validate("pAsS 40", bank);
		assertTrue(actual);
	}
}
