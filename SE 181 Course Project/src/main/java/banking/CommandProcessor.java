package banking;

public class CommandProcessor {

	public void execute(String command, Bank bank) {
		String[] splitCommand = command.toLowerCase().split(" ");

		if (splitCommand[0].equals("create")) {
			executeCreate(splitCommand, bank);
		} else if (splitCommand[0].equals("deposit")) {
			executeDeposit(splitCommand, bank);
		} else if (splitCommand[0].equals("withdraw")) {
			executeWithdraw(splitCommand, bank);
		} else if (splitCommand[0].equals("transfer")) {
			executeTransfer(splitCommand, bank);
		} else if (splitCommand[0].equals("pass")) {
			executePass(splitCommand, bank);
		}

		if (!splitCommand[0].equals("pass") && !splitCommand[0].equals("create")) {
			if (splitCommand[0].equals("transfer")) {
				int firstAccountID = Integer.parseInt(splitCommand[1]);
				int secondAccountID = Integer.parseInt(splitCommand[2]);
				bank.getAccount(firstAccountID).addToCommandHistory(command);
				bank.getAccount(secondAccountID).addToCommandHistory(command);
			} else {
				int accountID = Integer.parseInt(splitCommand[1]);
				bank.getAccount(accountID).addToCommandHistory(command);
			}
		}
	}

	public void executeCreate(String[] command, Bank bank) {
		int newAccountID = Integer.parseInt(command[2]);
		double newAccountAPR = Double.parseDouble(command[3]);
		BankAccount newAccount = new Savings(0, 0);

		if (command[1].equals("savings")) {
			newAccount = new Savings(newAccountID, newAccountAPR);
		} else if (command[1].equals("checking")) {
			newAccount = new Checking(newAccountID, newAccountAPR);
		} else if (command[1].equals("cd")) {
			double newAccountBalance = Float.parseFloat(command[4]);
			newAccount = new CD(newAccountID, newAccountAPR, newAccountBalance);
		}

		bank.addAccount(newAccount);
	}

	private void executeDeposit(String[] command, Bank bank) {
		int accountID = Integer.parseInt(command[1]);
		double depositAmount = Double.parseDouble(command[2]);

		bank.depositInAccount(accountID, depositAmount);
	}

	private void executeWithdraw(String[] command, Bank bank) {
		int accountID = Integer.parseInt(command[1]);
		double withdrawAmount = Double.parseDouble(command[2]);

		bank.withdrawFromAccount(accountID, withdrawAmount);
	}

	private void executeTransfer(String[] command, Bank bank) {
		int sendingAccountID = Integer.parseInt(command[1]);
		int receivingAccountID = Integer.parseInt(command[2]);
		double transferAmount = Double.parseDouble(command[3]);
		double sendingAccountBalance = bank.getAccount(sendingAccountID).getBalance();

		if (transferAmount > sendingAccountBalance) {
			bank.withdrawFromAccount(sendingAccountID, sendingAccountBalance);
			bank.depositInAccount(receivingAccountID, sendingAccountBalance);
		} else {
			bank.withdrawFromAccount(sendingAccountID, transferAmount);
			bank.depositInAccount(receivingAccountID, transferAmount);
		}
	}

	private void executePass(String[] command, Bank bank) {
		int timeToPass = Integer.parseInt(command[1]);
		bank.passTime(timeToPass);
	}
}
