/*
 * Created on 26.10.2003
 *
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;

import de.fhflensburg.xmleditor.editor.util.CharacterScannerDeco;

import junit.framework.TestCase;

/**
 * @author user
 * 
 */
public class ScannerUtilTest extends TestCase {

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFirstMatch() {
		CharacterScannerDeco scanner =
			new CharacterScannerDeco(new TestCharacterScanner("abcdef"));
		int result = scanner.firstMatch(new int[] { 'c', 'x', 'f' });
		assertEquals('c', result);

		scanner = new CharacterScannerDeco(new TestCharacterScanner("abcdef"));
		result =
			((CharacterScannerDeco) scanner).firstMatch(
				new int[] { 'l', 'x', 't' });
		assertEquals(ICharacterScanner.EOF, result);
	}

	public void testForwardTo() {
		CharacterScannerDeco scanner =
			new CharacterScannerDeco(new TestCharacterScanner("abcdef"));
		scanner.forwardTo('c');
		assertEquals('c', (char) scanner.read());
		scanner.forwardTo('x');
		int c = scanner.read();
		assertEquals(ICharacterScanner.EOF, c);

	}

	public void testBackTo() {
		CharacterScannerDeco scanner =
			new CharacterScannerDeco(new TestCharacterScanner("abcdef"));
		scanner.forwardTo('x');
		assertEquals(ICharacterScanner.EOF, scanner.read());
		
		scanner.backTo('b');
		assertEquals('b', (char) scanner.read());
	}

}
