package banking;

public class Savings extends BankAccount {

	public static final int SAVINGS_ACCOUNTID = 11111111;
	public static final double SAVINGS_APR = 5.0;
	boolean withdrawThisMonth = false;

	public Savings(int accountID, double apr) {
		super(accountID, apr);
	}

	@Override
	public boolean getWithdrawThisMonth() {
		return withdrawThisMonth;
	}

	@Override
	public void withdraw(double moneyWithdrawal) {
		if (!withdrawThisMonth) {
			if (moneyWithdrawal > balance) {
				this.balance = 0;
			} else {
				this.balance -= moneyWithdrawal;
			}
			withdrawThisMonth = true;
		}
	}

	@Override
	public void passTime(Integer time, Bank bank) {
		if (!accountClosed) {
			super.passTime(time, bank);
			withdrawThisMonth = false;
			double monthlyRate = apr / 100 / 12;
			for (int i = 0; i < time; i++) {
				if (balance <= 0) {
					bank.addAccountToBeClosed(accountID);
					accountClosed = true;
					break;
				}
				if (balance < 100.00) {
					balance = balance - 25.00;
				}
				balance = balance + (balance * monthlyRate);
			}
		}
	}
}
