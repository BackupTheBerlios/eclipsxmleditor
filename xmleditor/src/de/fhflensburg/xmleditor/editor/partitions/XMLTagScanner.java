package de.fhflensburg.xmleditor.editor.partitions;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;

import de.fhflensburg.xmleditor.editor.highlighting.ColorManager;
import de.fhflensburg.xmleditor.editor.highlighting.IXMLColorConstants;
import de.fhflensburg.xmleditor.editor.highlighting.XMLWhitespaceDetector;
import de.fhflensburg.xmleditor.editor.partitions.rules.*;

public class XMLTagScanner extends RuleBasedScanner {

	public XMLTagScanner(ColorManager manager) {
		IToken string =
			new Token(
				new TextAttribute(manager.getColor(IXMLColorConstants.STRING)));
		IToken attributename =
			new Token(
				new TextAttribute(manager.getColor(IXMLColorConstants.ATTR)));

		IRule[] rules = new IRule[4];

		// Add rule for attributes
		rules[0] = new AttributeRule(attributename);
		// Add rule for double quotes
		rules[1] = new SingleLineRule("\"", "\"", string, '\\');
		// Add a rule for single quotes
		rules[2] = new SingleLineRule("'", "'", string, '\\');
		// Add generic whitespace rule.
		rules[3] = new WhitespaceRule(new XMLWhitespaceDetector());

		setRules(rules);

		this.setDefaultReturnToken(
			new Token(
				new TextAttribute(manager.getColor(IXMLColorConstants.TAG))));

	}
}
