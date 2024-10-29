package banking;

public class PassTimeValidator {

	public boolean validate(String[] inputCommand, Bank bank) {
		if (inputCommand.length != 2) {
			return false;
		}

		int timeToPass;

		try {
			timeToPass = Integer.parseInt(inputCommand[1]);
		} catch (NumberFormatException e) {
			return false;
		}

		if (timeToPass < 1 || timeToPass > 60) {
			return false;
		}

		return true;
	}
}
