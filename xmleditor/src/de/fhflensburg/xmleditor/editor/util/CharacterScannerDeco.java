/*
 * Created on 20.10.2003
 * 
 */
package de.fhflensburg.xmleditor.editor.util;

import java.util.Arrays;

import org.eclipse.jface.text.rules.ICharacterScanner;

/**
 * @author user
 * 
 */
public class CharacterScannerDeco implements ICharacterScanner {
	private ICharacterScanner scanner;

	public CharacterScannerDeco(ICharacterScanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * returns the first char from the array wich was found in the text
	 * @param chars
	 * @return
	 */
	public int firstMatch(int[] chars) {
		int c;
		int i = 0;
		Arrays.sort(chars);
		do {
			c = scanner.read();
			i++;
		} while (
			Arrays.binarySearch(chars, c) == -1 && c != ICharacterScanner.EOF);
		for (; i > 0; i--) {
			scanner.unread();
		}
		return c;
	}

	public void forwardTo(int character) {
		int c;
		do {
			c = scanner.read();
		} while (c != character && c != ICharacterScanner.EOF);
		if (c != ICharacterScanner.EOF) {
			scanner.unread();
		}
	}

	public void backTo(int character) {
		int c;
		do {
			scanner.unread();
			c = scanner.read();
			scanner.unread();
		} while (c != character && c != ICharacterScanner.EOF);
	}

	/**
	 * @return
	 */
	public int getColumn() {
		return scanner.getColumn();
	}

	/**
	 * @return
	 */
	public char[][] getLegalLineDelimiters() {
		return scanner.getLegalLineDelimiters();
	}

	/**
	 * @return
	 */
	public int read() {
		return scanner.read();
	}

	/**
	 * 
	 */
	public void unread() {
		scanner.unread();
	}

}
