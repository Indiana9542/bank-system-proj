package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MasterControl {
	private Bank bank;
	private Validator validator;
	private CommandProcessor commandProcessor;
	private InvalidCommandStorage invalidCommandStorage;

	public MasterControl(Bank bank, Validator validator, CommandProcessor commandProcessor,
			InvalidCommandStorage invalidCommandStorage) {
		this.bank = bank;
		this.validator = validator;
		this.commandProcessor = commandProcessor;
		this.invalidCommandStorage = invalidCommandStorage;
	}

	public List<String> start(List<String> input) {
		List<String> outputList = new ArrayList<>();

		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);

		for (String command : input) {
			if (validator.validate(command, bank)) {
				commandProcessor.execute(command, bank);
			} else {
				invalidCommandStorage.store(command);
			}
		}
		bank.getAccountsList().forEach((accountID, account) -> {
			String accountType = account.getClass().getName();
			if (accountType.equals("banking.Savings")) {
				outputList.add("Savings " + accountID + " " + decimalFormat.format(account.getBalance()) + " "
						+ decimalFormat.format(account.getAPR()));
			} else if (accountType.equals("banking.Checking")) {
				outputList.add("Checking " + accountID + " " + decimalFormat.format(account.getBalance()) + " "
						+ decimalFormat.format(account.getAPR()));
			} else {
				outputList.add("Cd " + accountID + " " + decimalFormat.format(account.getBalance()) + " "
						+ decimalFormat.format(account.getAPR()));
			}
			outputList.addAll(account.getCommandHistory());
		});
		outputList.addAll(invalidCommandStorage.getLog());
		return outputList;
	}
}
