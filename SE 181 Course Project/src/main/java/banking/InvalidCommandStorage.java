package banking;

import java.util.ArrayList;

public class InvalidCommandStorage {

	ArrayList<String> invalidCommandLog;

	public InvalidCommandStorage() {
		invalidCommandLog = new ArrayList<>();
	}

	public void store(String invalidCommand) {
		invalidCommandLog.add(invalidCommand);
	}

	public boolean hasCommand(String command) {
		return invalidCommandLog.contains(command);
	}

	public ArrayList<String> getLog() {
		return invalidCommandLog;
	}
}
