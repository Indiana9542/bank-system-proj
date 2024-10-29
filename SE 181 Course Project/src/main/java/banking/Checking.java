package banking;

public class Checking extends BankAccount {

	public static final int CHECKING_ACCOUNT_ID = 22222222;
	public static final double CHECKING_APR = 5.0;

	public Checking(int accountID, double apr) {
		super(accountID, apr);
	}

	@Override
	public void passTime(Integer time, Bank bank) {
		if (!accountClosed) {
			super.passTime(time, bank);
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
