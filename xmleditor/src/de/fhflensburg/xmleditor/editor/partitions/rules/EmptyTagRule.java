package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.*;

import de.fhflensburg.xmleditor.editor.util.*;

public class EmptyTagRule extends MultiLineRule {

	public EmptyTagRule(IToken token) {
		super("<", ">", token);
	}
	protected boolean sequenceDetected(
		ICharacterScanner scanner,
		char[] sequence,
		boolean eofAllowed) {
		int c = scanner.read();
		if (sequence[0] == '<') {
			CharacterScannerDeco decoScanner = new CharacterScannerDeco(scanner); 
			if (decoScanner.firstMatch(new int[] {'<','>'})=='>') {
				if (c == '?') {
					// processing instruction - abort
					scanner.unread();
					return false;
				} else if (c == '!') {
					scanner.unread();
					// comment - abort
					return false;
				} else if (c == '/') {
					scanner.unread();
					// endtag - abort
					return false;
				}
			}
		} else if (sequence[0] == '>') {
			scanner.unread();
			scanner.unread();
			c = scanner.read();
			if (c != '/') {
				// no emptytag - abort
				return false;
			}
		}
		return super.sequenceDetected(scanner, sequence, eofAllowed);
	}
}
