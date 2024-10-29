package banking;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Bank {
	Map<Integer, BankAccount> accountsList;
	Map<Integer, BankAccount> accountsToBeClosed;
	int numAccounts;

	Bank() {
		accountsList = new LinkedHashMap<>();
		accountsToBeClosed = new HashMap<>();
	}

	public int getNumAccounts() {
		return accountsList.size();
	}

	public void addAccount(BankAccount newAccount) {
		accountsList.put(newAccount.accountID, newAccount);
	}

	public BankAccount getAccount(int accountID) {
		return accountsList.get(accountID);
	}

	public Map<Integer, BankAccount> getAccountsList() {
		return accountsList;
	}

	public void depositInAccount(int accountID, double depositAmount) {
		accountsList.get(accountID).deposit(depositAmount);
	}

	public void withdrawFromAccount(int accountID, double withdrawalAmount) {
		accountsList.get(accountID).withdraw(withdrawalAmount);
	}

	public Boolean checkAccountID(int accountID) {
		return accountsList.containsKey(accountID);
	}

	public void addAccountToBeClosed(int accountID) {
		accountsToBeClosed.put(accountID, accountsList.get(accountID));
	}

	public void passTime(Integer time) {
		accountsList.forEach((accountID, account) -> {
			account.passTime(time, this);
		});

		accountsToBeClosed.forEach((accountID, account) -> {
			accountsList.remove(accountID, account);
		});

		accountsToBeClosed = new HashMap<>();

	}
}
