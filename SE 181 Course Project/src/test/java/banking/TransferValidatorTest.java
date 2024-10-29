package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {
	Validator validator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		validator = new Validator();
		bank = new Bank();
	}

	@Test
	public void can_transfer_from_checking_to_checking() {
		Checking checking1 = new Checking(11111111, 0);
		Checking checking2 = new Checking(22222222, 0);
		bank.addAccount(checking1);
		bank.addAccount(checking2);
		Boolean actual = validator.validate("transfer 11111111 22222222 300", bank);
		assertTrue(actual);
	}

	@Test
	public void can_transfer_from_checking_to_savings() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 11111111 22222222 300", bank);
		assertTrue(actual);
	}

	@Test
	public void can_transfer_from_savings_to_checking() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 22222222 11111111 300", bank);
		assertTrue(actual);
	}

	@Test
	public void can_transfer_from_savings_to_savings() {
		Savings savings1 = new Savings(11111111, 0);
		Savings savings2 = new Savings(22222222, 0);
		bank.addAccount(savings1);
		bank.addAccount(savings2);
		Boolean actual = validator.validate("transfer 11111111 22222222 300", bank);
		assertTrue(actual);
	}

	@Test
	public void cannot_transfer_from_savings_to_cd() {
		Savings savings = new Savings(11111111, 0);
		CD cd = new CD(22222222, 0, 2000);
		bank.addAccount(savings);
		bank.addAccount(cd);
		Boolean actual = validator.validate("transfer 11111111 22222222 300", bank);
		assertFalse(actual);
	}

	@Test
	public void cannot_transfer_from_checking_to_cd() {
		Checking checking = new Checking(11111111, 0);
		CD cd = new CD(22222222, 0, 2000);
		bank.addAccount(checking);
		bank.addAccount(cd);
		Boolean actual = validator.validate("transfer 11111111 22222222 300", bank);
		assertFalse(actual);
	}

	@Test
	public void cannot_transfer_from_cd_to_savings() {
		Savings savings = new Savings(11111111, 0);
		CD cd = new CD(22222222, 0, 2000);
		bank.addAccount(savings);
		bank.addAccount(cd);
		Boolean actual = validator.validate("transfer 22222222 11111111 300", bank);
		assertFalse(actual);
	}

	@Test
	public void cannot_transfer_from_cd_to_checking() {
		Checking checking = new Checking(11111111, 0);
		CD cd = new CD(22222222, 0, 2000);
		bank.addAccount(checking);
		bank.addAccount(cd);
		Boolean actual = validator.validate("transfer 22222222 11111111 300", bank);
		assertFalse(actual);
	}

	@Test
	public void cannot_transfer_from_cd_to_cd() {
		CD cd1 = new CD(11111111, 0, 2000);
		CD cd2 = new CD(22222222, 0, 2000);
		bank.addAccount(cd1);
		bank.addAccount(cd2);
		Boolean actual = validator.validate("transfer 22222222 11111111 300", bank);
		assertFalse(actual);
	}

	@Test
	public void lower_bound_for_savings_exists() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 22222222 11111111 0", bank);
		assertTrue(actual);
	}

	@Test
	public void lower_bound_for_savings_fails() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 22222222 11111111 -0.01", bank);
		assertFalse(actual);
	}

	@Test
	public void lower_bound_for_checking_exists() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 11111111 22222222 0", bank);
		assertTrue(actual);
	}

	@Test
	public void lower_bound_for_checking_fails() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 11111111 22222222 -0.01", bank);
		assertFalse(actual);
	}

	@Test
	public void upper_bound_for_savings_exists() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 22222222 11111111 1000.00", bank);
		assertTrue(actual);
	}

	@Test
	public void upper_bound_for_savings_fails() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 22222222 11111111 1000.01", bank);
		assertFalse(actual);
	}

	@Test
	public void upper_bound_for_checking_exists() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 11111111 22222222 400.00", bank);
		assertTrue(actual);
	}

	@Test
	public void upper_bound_for_checking_fails() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 11111111 22222222 400.01", bank);
		assertFalse(actual);
	}

	@Test
	public void fails_when_first_id_does_not_exist() {
		Checking checking = new Checking(11111111, 0);
		bank.addAccount(checking);
		Boolean actual = validator.validate("transfer 11111111 22222222 300", bank);
		assertFalse(actual);
	}

	@Test
	public void fails_when_second_id_does_not_exist() {
		Checking checking = new Checking(11111111, 0);
		bank.addAccount(checking);
		Boolean actual = validator.validate("transfer 22222222 11111111 300", bank);
		assertFalse(actual);
	}

	@Test
	public void fails_when_both_ids_do_not_exist() {
		Checking checking = new Checking(11111111, 0);
		bank.addAccount(checking);
		Boolean actual = validator.validate("transfer 22222222 11111111 300", bank);
		assertFalse(actual);
	}

	@Test
	public void first_id_too_short() {
		Checking checking = new Checking(1111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 1111111 22222222 300", bank);
		assertFalse(actual);
	}

	@Test
	public void second_id_too_short() {
		Checking checking = new Checking(1111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 11111111 2222222 300", bank);
		assertFalse(actual);
	}

	@Test
	public void both_ids_too_short() {
		Checking checking = new Checking(1111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 1111111 2222222 300", bank);
		assertFalse(actual);
	}

	@Test
	public void first_id_too_long() {
		Checking checking = new Checking(1111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 111111111 22222222 300", bank);
		assertFalse(actual);
	}

	@Test
	public void second_id_too_long() {
		Checking checking = new Checking(1111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 11111111 222222222 300", bank);
		assertFalse(actual);
	}

	@Test
	public void both_ids_too_long() {
		Checking checking = new Checking(1111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("transfer 111111111 222222222 300", bank);
		assertFalse(actual);
	}

	@Test
	public void first_id_cannot_be_letters() {
		Boolean actual = validator.validate("transfer abcdefgh 22222222 300", bank);
		assertFalse(actual);
	}

	@Test
	public void second_id_cannot_be_letters() {
		Boolean actual = validator.validate("transfer 1111111 abcdefgh 300", bank);
		assertFalse(actual);
	}

	@Test
	public void both_ids_cannot_be_letters() {
		Boolean actual = validator.validate("transfer zxcvbnmm abcdefgh 300", bank);
		assertFalse(actual);
	}

	@Test
	public void balance_cannot_be_letters() {
		Boolean actual = validator.validate("transfer 11111111 22222222 abc", bank);
		assertFalse(actual);
	}

	@Test
	public void not_case_sensitive() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Boolean actual = validator.validate("tRaNSFer 11111111 22222222 300", bank);
		assertTrue(actual);
	}

	@Test
	public void typo_not_allowed() {
		Boolean actual = validator.validate("trans 11111111 22222222 300.27", bank);
		assertFalse(actual);
	}

	@Test
	public void too_few_arguments() {
		Boolean actual = validator.validate("transfer 11111111 22222222", bank);
		assertFalse(actual);
	}

	@Test
	public void too_many_arguments() {
		Boolean actual = validator.validate("transfer 11111111 22222222 300.27 23", bank);
		assertFalse(actual);
	}

}
