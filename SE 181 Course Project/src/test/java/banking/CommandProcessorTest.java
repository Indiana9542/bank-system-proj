package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor();
	}

	@Test
	public void create_savings_account_works_by_test_id() {
		commandProcessor.execute("create savings 11111111 0.6", bank);
		boolean actual = bank.checkAccountID(11111111);
		assertTrue(actual);
	}

	@Test
	public void create_savings_account_works_by_test_apr() {
		commandProcessor.execute("create savings 11111111 0.6", bank);
		double actual = bank.getAccount(11111111).getAPR();
		assertEquals(0.6, actual, 0.00001);
	}

	@Test
	public void create_savings_account_works_by_test_type() {
		commandProcessor.execute("create savings 11111111 0.6", bank);
		String actual = bank.getAccount(11111111).getClass().getName();
		assertEquals("banking.Savings", actual);
	}

	@Test
	public void create_checking_account_works_by_test_id() {
		commandProcessor.execute("create checking 22222222 0.6", bank);
		boolean actual = bank.checkAccountID(22222222);
		assertTrue(actual);
	}

	@Test
	public void create_checking_account_works_by_test_apr() {
		commandProcessor.execute("create checking 22222222 0.6", bank);
		double actual = bank.getAccount(22222222).getAPR();
		assertEquals(0.6, actual, 0.00001);
	}

	@Test
	public void create_checking_account_works_by_test_type() {
		commandProcessor.execute("create checking 22222222 0.6", bank);
		String actual = bank.getAccount(22222222).getClass().getName();
		assertEquals("banking.Checking", actual);
	}

	@Test
	public void create_cd_account_works_by_test_id() {
		commandProcessor.execute("create cd 33333333 0.6 1500.00", bank);
		boolean actual = bank.checkAccountID(33333333);
		assertTrue(actual);
	}

	@Test
	public void create_cd_account_works_by_test_apr() {
		commandProcessor.execute("create cd 33333333 0.6 1500.00", bank);
		double actual = bank.getAccount(33333333).getAPR();
		assertEquals(0.6, actual, 0.00001);
	}

	@Test
	public void create_cd_account_works_by_test_balance() {
		commandProcessor.execute("create cd 33333333 0.6 1500.00", bank);
		double actual = bank.getAccount(33333333).getBalance();
		assertEquals(1500.0, actual, 0.00001);
	}

	@Test
	public void create_cd_account_works_by_test_type() {
		commandProcessor.execute("create cd 33333333 0.6 1500.00", bank);
		String actual = bank.getAccount(33333333).getClass().getName();
		assertEquals("banking.CD", actual);
	}

	@Test
	public void deposit_in_savings_account_works() {
		Savings savings = new Savings(11111111, 0.6);
		bank.addAccount(savings);
		commandProcessor.execute("deposit 11111111 100.23", bank);
		double actual = bank.getAccount(11111111).getBalance();
		assertEquals(100.23, actual, 0.0001);
	}

	@Test
	public void deposit_in_checking_account_works() {
		Checking checking = new Checking(22222222, 0.6);
		bank.addAccount(checking);
		commandProcessor.execute("deposit 22222222 100.23", bank);
		double actual = bank.getAccount(22222222).getBalance();
		assertEquals(100.23, actual, 0.0001);
	}

	@Test
	public void withdraw_from_savings_account_works() {
		Savings savings = new Savings(11111111, 0);
		bank.addAccount(savings);
		commandProcessor.execute("deposit 11111111 300.00", bank);
		commandProcessor.execute("withdraw 11111111 150.00", bank);
		double actual = bank.getAccount(11111111).getBalance();
		assertEquals(150.00, actual, 0.0001);
	}

	@Test
	public void withdraw_from_checking_account_works() {
		Checking checking = new Checking(11111111, 0);
		bank.addAccount(checking);
		commandProcessor.execute("deposit 11111111 300.00", bank);
		commandProcessor.execute("withdraw 11111111 150.00", bank);
		double actual = bank.getAccount(11111111).getBalance();
		assertEquals(150.00, actual, 0.0001);
	}

	@Test
	public void deposit_twice_works() {
		Checking checking = new Checking(22222222, 0.6);
		bank.addAccount(checking);
		commandProcessor.execute("deposit 22222222 50.11", bank);
		commandProcessor.execute("deposit 22222222 50.11", bank);
		double actual = bank.getAccount(22222222).getBalance();
		assertEquals(100.22, actual, 0.0001);
	}

	@Test
	public void withdraw_twice_works() {
		Checking checking = new Checking(11111111, 0);
		bank.addAccount(checking);
		bank.depositInAccount(11111111, 600);
		commandProcessor.execute("withdraw 11111111 300.00", bank);
		commandProcessor.execute("withdraw 11111111 150.00", bank);
		double actual = bank.getAccount(11111111).getBalance();
		assertEquals(150, actual, 0.0001);
	}

	@Test
	public void can_transfer_between_accounts() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		bank.depositInAccount(11111111, 600);
		commandProcessor.execute("transfer 11111111 22222222 400", bank);
		assertEquals(200, bank.getAccount(11111111).getBalance());
		assertEquals(400, bank.getAccount(22222222).getBalance());
	}

	@Test
	public void transfer_only_as_much_as_withdrawn() {
		Checking checking = new Checking(11111111, 0);
		Savings savings = new Savings(22222222, 0);
		bank.addAccount(checking);
		bank.addAccount(savings);
		bank.depositInAccount(11111111, 300);
		commandProcessor.execute("transfer 11111111 22222222 400", bank);
		assertEquals(0, bank.getAccount(11111111).getBalance());
		assertEquals(300, bank.getAccount(22222222).getBalance());
	}

	@Test
	public void pass_time_works() {
		Checking checking = new Checking(11111111, 10);
		bank.addAccount(checking);
		bank.depositInAccount(11111111, 800);
		commandProcessor.execute("pass 1", bank);
		Double actual = bank.getAccount(11111111).getBalance();
		int age = bank.getAccount(11111111).getAccountAgeInMonths();
		assertEquals(806.6666, actual, 0.001);
		assertEquals(1, age);
	}

}
