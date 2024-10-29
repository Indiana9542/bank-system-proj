package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CDTest {

	@Test
	public void CD_has_starting_balance() {
		CD cd = new CD(CD.CD_ACCOUNTID, CD.CD_APR, CD.CD_BALANCE);

		assertEquals(CD.CD_BALANCE, cd.getBalance());
	}
}
