/*
 * Created on 20.10.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;

/**
 * @author user
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RuleUtil {

	public static boolean hasCharBefore(ICharacterScanner scanner,int charToSearchFor,int[] thisCharsShouldNotExistBefore) {

		int c;
		int i = 0;
		do {
			c = scanner.read();
			i++;
		} while (c != charToSearchFor && !arrayContainsChar(c,thisCharsShouldNotExistBefore) && c != ICharacterScanner.EOF);
		for (; i > 0; i--) {
			scanner.unread();
		}
		if (c == charToSearchFor) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param c
	 * @param thisCharsShouldNotExistBefore
	 * @return
	 */
	private static boolean arrayContainsChar(int c, int[] thisCharsShouldNotExistBefore) {
		for (int i = 0; i < thisCharsShouldNotExistBefore.length; i++) {
			if (thisCharsShouldNotExistBefore[i]==c) {
				return true;
			}
		}
		return false;
	}
}
