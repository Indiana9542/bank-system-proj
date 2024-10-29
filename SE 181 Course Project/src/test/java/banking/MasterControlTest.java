package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	List<String> input;
	MasterControl masterControl;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(bank, new Validator(), new CommandProcessor(), new InvalidCommandStorage());
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("depositt 12345678 100", actual);
	}

	@Test
	void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));
	}

	@Test
	void invalid_to_create_accounts_with_the_same_id() {
		input.add("creat checking 12345678 1.0");
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);

		assertEquals("creat checking 12345678 1.0", actual.get(0));
	}

	@Test
	void created_account_in_output() {
		input.add("create checking 11111111 0.1");
		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("Checking 11111111 0.00 0.10", actual.get(0));
	}

	@Test
	void created_account_history_in_output() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("withdraw 12345678 200");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 500.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("withdraw 12345678 200", actual.get(2));
	}

	@Test
	void invalid_commands_in_output() {
		input.add("crete chec 12345678 0.01");
		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("crete chec 12345678 0.01", actual.get(0));
	}

	@Test
	void valid_commands_come_before_invalid_commands() {
		input.add("crete chec 12345678 0.01");
		input.add("Create savings 12345678 0.6");
		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 0.00 0.60", actual.get(0));
		assertEquals("crete chec 12345678 0.01", actual.get(1));
	}

	@Test
	void closed_accounts_not_in_output() {
		input.add("create savings 12345678 0.8");
		input.add("Pass 1");
		input.add("create checking 11111111 0.1");
		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("Checking 11111111 0.00 0.10", actual.get(0));
	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}
}
