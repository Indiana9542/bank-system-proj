package banking;

import java.util.ArrayList;

public abstract class BankAccount {
	boolean accountClosed = false;
	double apr;
	double balance;
	int accountID;
	int accountAgeInMonths;
	ArrayList<String> commandHistory;

	public BankAccount(int accountID, double apr) {
		this.accountID = accountID;
		this.apr = apr;
		commandHistory = new ArrayList<>();
	}

	public void withdraw(double moneyWithdrawal) {
		if (moneyWithdrawal > balance) {
			this.balance = 0;
		} else {
			this.balance -= moneyWithdrawal;
		}
	}

	public double getBalance() {
		return balance;
	}

	public double getAPR() {
		return apr;
	}

	public int getAccountAgeInMonths() {
		return accountAgeInMonths;
	}

	public boolean getAccountClosed() {
		return accountClosed;
	}

	public boolean getWithdrawThisMonth() {
		return false;
	}

	public ArrayList<String> getCommandHistory() {
		return commandHistory;
	}

	public void deposit(double moneyDeposit) {
		this.balance += moneyDeposit;
	}

	public void passTime(Integer time, Bank bank) {
		accountAgeInMonths += time;
	}

	public void addToCommandHistory(String command) {
		commandHistory.add(command);
	}
}
