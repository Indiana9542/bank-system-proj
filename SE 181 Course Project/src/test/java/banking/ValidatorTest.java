package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorTest {
	Validator validator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		validator = new Validator();
		bank = new Bank();
	}

	@Test
	public void too_few_arguments() {
		Boolean actual = validator.validate("create", bank);
		assertFalse(actual);
	}

	@Test
	public void too_many_arguments() {
		Boolean actual = validator.validate("create savings 11111111 0.6 10000 20", bank);
		assertFalse(actual);
	}
}
