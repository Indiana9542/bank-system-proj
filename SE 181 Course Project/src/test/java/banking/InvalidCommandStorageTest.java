package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvalidCommandStorageTest {

	InvalidCommandStorage invalidCommandStorage;

	@BeforeEach
	public void setUp() {
		invalidCommandStorage = new InvalidCommandStorage();
	}

	@Test
	public void command_is_stored() {
		invalidCommandStorage.store("create 0.6");
		boolean actual = invalidCommandStorage.hasCommand("create 0.6");
		assertTrue(actual);
	}

	@Test
	public void can_get_specific_string() {
		invalidCommandStorage.store("create savings 12 0.6");
		invalidCommandStorage.store("deposit 12345678 9999.99");
		String actual = invalidCommandStorage.getLog().get(1);
		assertEquals("deposit 12345678 9999.99", actual);
	}
}
