package banking;

public class Validator {
	CreateValidator createValidator;
	DepositValidator depositValidator;
	PassTimeValidator passTimeValidator;
	TransferValidator transferValidator;
	WithdrawValidator withdrawValidator;

	public Validator() {
		createValidator = new CreateValidator();
		depositValidator = new DepositValidator();
		passTimeValidator = new PassTimeValidator();
		transferValidator = new TransferValidator();
		withdrawValidator = new WithdrawValidator();
	}

	public Boolean validate(String inputCommand, Bank bank) {
		String[] splitInput = inputCommand.toLowerCase().split(" ");

		if (splitInput.length < 2) {
			return false;
		}

		if (splitInput[0].equals("create")) {
			return createValidator.validate(splitInput, bank);
		} else if (splitInput[0].equals("deposit")) {
			return depositValidator.validate(splitInput, bank);
		} else if (splitInput[0].equals("pass")) {
			return passTimeValidator.validate(splitInput, bank);
		} else if (splitInput[0].equals("transfer")) {
			return transferValidator.validate(splitInput, bank);
		} else if (splitInput[0].equals("withdraw")) {
			return withdrawValidator.validate(splitInput, bank);
		} else {
			return false;
		}
	}
}
