/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.editor.partitions;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

import de.fhflensburg.xmleditor.editor.highlighting.ColorManager;
import de.fhflensburg.xmleditor.editor.highlighting.IXMLColorConstants;
import de.fhflensburg.xmleditor.editor.highlighting.XMLWhitespaceDetector;

/**
 * @author user
 *
 */
public class CDATAScanner extends RuleBasedScanner {

	public CDATAScanner(ColorManager manager) {
		IToken declaration =
			new Token(
				new TextAttribute(manager.getColor(IXMLColorConstants.STRING)));
		IToken content =
			new Token(
				new TextAttribute(manager.getColor(IXMLColorConstants.ATTR)));

		this.setDefaultReturnToken(declaration);
		IRule[] rules = new IRule[2];

		// Add rule for attributes
		rules[0] = new MultiLineRule("[","]",content);
		// Add generic whitespace rule.
		rules[1] = new WhitespaceRule(new XMLWhitespaceDetector());

		setRules(rules);
	}
}
