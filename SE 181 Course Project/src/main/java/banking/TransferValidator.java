package banking;

public class TransferValidator {

	public boolean validate(String[] inputCommand, Bank bank) {
		if (inputCommand.length != 4) {
			return false;
		}

		if (inputCommand[1].length() != 8 || inputCommand[2].length() != 8) {
			return false;
		}

		String firstAccountType;
		String secondAccountType;
		int firstAccountID;
		int secondAccountID;
		Double transferAmount;

		try {
			firstAccountType = getClassFromAccountID(inputCommand[1], bank);
			secondAccountType = getClassFromAccountID(inputCommand[2], bank);
			firstAccountID = Integer.parseInt(inputCommand[1]);
			secondAccountID = Integer.parseInt(inputCommand[2]);
			transferAmount = Double.parseDouble(inputCommand[3]);
		} catch (NumberFormatException | NullPointerException e) {
			return false;
		}

		if (firstAccountType.equals("banking.Savings")) {
			if (bank.getAccount(firstAccountID).getWithdrawThisMonth() || transferAmount > 1000.00
					|| transferAmount < 0) {
				return false;
			}
		} else if (firstAccountType.equals("banking.Checking")) {
			if (transferAmount > 400.00 || transferAmount < 0) {
				return false;
			}
		} else {
			return false;
		}

		if (secondAccountType.equals("banking.CD")) {
			return false;
		}
		return bank.checkAccountID(firstAccountID) && bank.checkAccountID(secondAccountID);
	}

	public String getClassFromAccountID(String input, Bank bank) {
		return bank.getAccount(Integer.parseInt(input)).getClass().getName();
	}
}
