package de.fhflensburg.xmleditor.editor.partitions;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;

import de.fhflensburg.xmleditor.editor.highlighting.ColorManager;
import de.fhflensburg.xmleditor.editor.highlighting.IXMLColorConstants;
import de.fhflensburg.xmleditor.editor.highlighting.XMLWhitespaceDetector;

public class XMLScanner extends RuleBasedScanner {

	public XMLScanner(ColorManager manager) {
		IToken procInstr =
			new Token(
				new TextAttribute(
					manager.getColor(IXMLColorConstants.PROC_INSTR)));
		IRule[] rules = new IRule[2];
		//Add rule for processing instructions
		rules[0] = new SingleLineRule("<?", "?>", procInstr);
		// Add generic whitespace rule.
		rules[1] = new WhitespaceRule(new XMLWhitespaceDetector());

		setRules(rules);

		setDefaultReturnToken(
			new Token(
				new TextAttribute(
					manager.getColor(IXMLColorConstants.DEFAULT))));

	}
}
