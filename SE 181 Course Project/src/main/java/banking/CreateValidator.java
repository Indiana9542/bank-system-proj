package banking;

public class CreateValidator {

	public Boolean validate(String[] inputCommand, Bank bank) {

		if (inputCommand[1].equals("cd")) {
			if (inputCommand.length != 5) {
				return false;
			}
			if (1000 > Float.parseFloat(inputCommand[4]) || 10000 < Float.parseFloat(inputCommand[4])) {
				return false;
			}
		} else if (inputCommand[1].equals("savings") || inputCommand[1].equals("checking")) {
			if (inputCommand.length != 4) {
				return false;
			}
		} else {
			return false;
		}

		int accountID;
		double apr;

		try {
			accountID = Integer.parseInt(inputCommand[2]);
			apr = Double.parseDouble(inputCommand[3]);
		} catch (NumberFormatException e) {
			return false;
		}

		if (bank.checkAccountID(accountID)) {
			return false;
		}

		if (inputCommand[2].length() != 8) {
			return false;
		}

		if (apr < 0.0 || apr > 10.0) {
			return false;
		}

		return true;
	}

}
