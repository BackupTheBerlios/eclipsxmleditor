/*
 * Created on 19.10.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

/**
 * @author user
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
			if (RuleUtil.hasCharBefore(scanner, '>', new int[] { '<' })) {
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
