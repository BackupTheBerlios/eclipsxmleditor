/*
 * Created on 21.10.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

public class EndTagRule extends MultiLineRule {

	public EndTagRule(IToken token) {
		super("<", ">", token);
	}
	protected boolean sequenceDetected(
		ICharacterScanner scanner,
		char[] sequence,
		boolean eofAllowed) {
		int c = scanner.read();
		if (sequence[0] == '<') {
			if (RuleUtil.hasCharBefore(scanner, '>', new int[] { '<' })) {
				if (c != '/') {
					// no Endtag - abort
					scanner.unread();
					return false;
				}
			}
		}
		return super.sequenceDetected(scanner, sequence, eofAllowed);
	}
}
