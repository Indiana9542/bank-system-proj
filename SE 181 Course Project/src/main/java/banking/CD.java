package banking;

public class CD extends BankAccount {

	public static final int CD_ACCOUNTID = 3333333;
	public static final double CD_APR = 5.0;
	public static final double CD_BALANCE = 50.05;

	public CD(int accountID, double apr, double balance) {
		super(accountID, apr);
		this.balance = balance;
	}

	@Override
	public void passTime(Integer time, Bank bank) {
		if (!accountClosed) {
			super.passTime(time, bank);
			double monthlyRate = apr / 100 / 12;
			for (int i = 0; i < time * 4; i++) {
				if (balance <= 0) {
					bank.addAccountToBeClosed(accountID);
					accountClosed = true;
					continue;
				}
				if (time % 4 == 0 && balance < 100.00) {
					balance = balance - 25.00;
				}
				balance = balance + (balance * monthlyRate);
			}
		}
	}
}
