/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

import de.fhflensburg.xmleditor.editor.util.*;

/**
 * @author user
 *
 */
public class DeclarationRule extends MultiLineRule {

	public DeclarationRule(IToken token) {
		super("<!", ">", token);
	}
	protected boolean sequenceDetected(
		ICharacterScanner scanner,
		char[] sequence,
		boolean eofAllowed) {
			
		if (sequence[0] == '<') {
			CharacterScannerDeco decoScanner = new CharacterScannerDeco(scanner); 
			if (decoScanner.firstMatch(new int[] {'<','>'})=='>') {
				int c = scanner.read();
				if (c == '!') {
					c = scanner.read();
					if (!Character.isUpperCase((char) c)) {
						scanner.unread();
						scanner.unread();
						return false;
					}
				}
			}
		}
		return super.sequenceDetected(scanner, sequence, eofAllowed);
	}

}
