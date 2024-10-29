package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
	}

	@Test
	public void bank_initially_has_no_accounts() {
		int actual = bank.getNumAccounts();

		assertEquals(0, actual);
	}

	@Test
	public void can_create_one_account_in_bank() {
		CD cd = new CD(CD.CD_ACCOUNTID, CD.CD_APR, CD.CD_BALANCE);
		bank.addAccount(cd);
		int actual = bank.getNumAccounts();

		assertEquals(1, actual);
	}

	@Test
	public void can_create_two_accounts_in_bank() {
		CD cd = new CD(CD.CD_ACCOUNTID, CD.CD_APR, CD.CD_BALANCE);
		Savings savings = new Savings(Savings.SAVINGS_ACCOUNTID, Savings.SAVINGS_APR);
		bank.addAccount(cd);
		bank.addAccount(savings);
		int actual = bank.getNumAccounts();

		assertEquals(2, actual);
	}

	@Test
	public void correct_account_retrieved_from_bank() {
		CD cd = new CD(CD.CD_ACCOUNTID, CD.CD_APR, CD.CD_BALANCE);
		bank.addAccount(cd);
		BankAccount actual = bank.getAccount(CD.CD_ACCOUNTID);

		assertEquals(cd, actual);
	}

	@Test
	public void can_deposit_in_account_through_bank() {
		CD cd = new CD(CD.CD_ACCOUNTID, CD.CD_APR, CD.CD_BALANCE);
		bank.addAccount(cd);
		bank.depositInAccount(CD.CD_ACCOUNTID, 10.15);

		assertEquals(CD.CD_BALANCE + 10.15, cd.getBalance());
	}

	@Test
	public void can_deposit_twice_in_account_through_bank() {
		CD cd = new CD(CD.CD_ACCOUNTID, CD.CD_APR, CD.CD_BALANCE);
		bank.addAccount(cd);
		bank.depositInAccount(CD.CD_ACCOUNTID, 10.15);
		bank.depositInAccount(CD.CD_ACCOUNTID, 10.15);

		assertEquals(CD.CD_BALANCE + 20.30, cd.getBalance());
	}

	@Test
	public void can_withdraw_from_account_through_bank() {
		CD cd = new CD(CD.CD_ACCOUNTID, CD.CD_APR, CD.CD_BALANCE);
		bank.addAccount(cd);
		bank.withdrawFromAccount(CD.CD_ACCOUNTID, 10.15);

		assertEquals(CD.CD_BALANCE - 10.15, cd.getBalance());
	}

	@Test
	public void can_withdraw_twice_from_account_through_bank() {
		CD cd = new CD(CD.CD_ACCOUNTID, CD.CD_APR, CD.CD_BALANCE);
		bank.addAccount(cd);
		bank.withdrawFromAccount(CD.CD_ACCOUNTID, 10);
		bank.withdrawFromAccount(CD.CD_ACCOUNTID, 10);

		assertEquals(CD.CD_BALANCE - 20, cd.getBalance());
	}

}
