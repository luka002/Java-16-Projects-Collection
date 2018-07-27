package hr.fer.zemris.java.hw06.crypto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testHexttobyte() {
		byte[] data = Util.hextobyte("01aE22");

		assertArrayEquals(new byte[] { 1, -82, 34 }, data);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHexttobyteWrongLength() {
		Util.hextobyte("1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHexttobyteWrongHexNumber() {
		Util.hextobyte("rr");
	}

	@Test
	public void testHexttobyteEmptyInput() {
		byte[] data = Util.hextobyte("");

		assertArrayEquals(new byte[] {}, data);
	}
	
	@Test
	public void testBytetohex() {
		String data = Util.bytetohex(new byte[] {1, -82, 34});

		assertEquals("01ae22", data);
	}
	
	@Test
	public void testBytetohexEmptyInput() {
		String data = Util.bytetohex(new byte[] {});

		assertEquals("", data);
	}

}
