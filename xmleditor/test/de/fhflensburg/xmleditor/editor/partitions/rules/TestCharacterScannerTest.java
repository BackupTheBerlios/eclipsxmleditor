/*
 * Created on 26.10.2003
 *
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;

import junit.framework.TestCase;

/**
 * @author user
 *
 */
public class TestCharacterScannerTest extends TestCase {
	public void testRead() {
		TestCharacterScanner scanner = new TestCharacterScanner("testing the testhelper");
		assertEquals('t',(char)scanner.read());
		assertEquals('e',(char)scanner.read());
		scanner.unread();
		assertEquals('e',(char)scanner.read());
		while (scanner.read() != ICharacterScanner.EOF);
		assertEquals(ICharacterScanner.EOF,scanner.read());
		
		scanner.unread();
		assertEquals('r',(char)scanner.read());
		
	}
}
