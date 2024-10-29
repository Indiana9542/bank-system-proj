package banking;

public class DepositValidator {

	public Boolean validate(String[] inputCommand, Bank bank) {

		if (inputCommand.length != 3) {
			return false;
		}

		if (getClassFromAccountID(inputCommand[1], bank).equals("banking.Savings")) {
			if (Float.parseFloat(inputCommand[2]) > 2500) {
				return false;
			}
		} else if (getClassFromAccountID(inputCommand[1], bank).equals("banking.Checking")) {
			if (Float.parseFloat(inputCommand[2]) > 1000) {
				return false;
			}
		} else {
			return false;
		}

		if (Integer.parseInt(inputCommand[2]) < 0) {
			return false;
		}

		return bank.checkAccountID(Integer.parseInt(inputCommand[1]));
	}

	public String getClassFromAccountID(String input, Bank bank) {
		return bank.getAccount(Integer.parseInt(input)).getClass().getName();
	}

}
