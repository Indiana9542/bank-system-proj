package banking;

public class WithdrawValidator {

	public boolean validate(String[] inputCommand, Bank bank) {
		if (inputCommand.length != 3) {
			return false;
		}

		if (inputCommand[1].length() != 8) {
			return false;
		}

		int accountID;
		double withdrawAmount;

		try {
			accountID = Integer.parseInt(inputCommand[1]);
			withdrawAmount = Double.parseDouble(inputCommand[2]);
		} catch (NumberFormatException e) {
			return false;
		}

		if (getClassFromAccountID(inputCommand[1], bank).equals("banking.Savings")) {
			if (withdrawAmount < 0 || withdrawAmount > 1000.00 || bank.getAccount(accountID).getWithdrawThisMonth()) {
				return false;
			}
		} else if (getClassFromAccountID(inputCommand[1], bank).equals("banking.Checking")) {
			if (withdrawAmount < 0 || withdrawAmount > 400.00) {
				return false;
			}
		} else {
			if (bank.getAccount(accountID).getAccountAgeInMonths() < 12
					|| bank.getAccount(accountID).getBalance() > withdrawAmount) {
				return false;
			}
		}

		return bank.checkAccountID(accountID);
	}

	public String getClassFromAccountID(String input, Bank bank) {
		return bank.getAccount(Integer.parseInt(input)).getClass().getName();
	}
}
