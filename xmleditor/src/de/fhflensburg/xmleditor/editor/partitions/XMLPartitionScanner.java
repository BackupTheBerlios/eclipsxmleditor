package de.fhflensburg.xmleditor.editor.partitions;

import org.eclipse.jface.text.rules.*;

import de.fhflensburg.xmleditor.editor.partitions.rules.*;



public class XMLPartitionScanner extends RuleBasedPartitionScanner {
	public final static String XML_DEFAULT = "__xml_default";
	public final static String XML_PROCESSOR_INSTRUCTION = "__xml_processorinstruction";
	public final static String XML_COMMENT = "__xml_comment";
	public final static String XML_STARTTAG = "__xml_starttag";
	public final static String XML_ENDTAG = "__xml_endtag";
	public final static String XML_EMPTYTAG = "__xml_emptytag";
	public final static String XML_DECLARATION = "__xml_declaration";
	public final static String XML_ENTITY = "__xml_entity";
	public final static String XML_CDATA = "__xml_cdata";

	public XMLPartitionScanner() {

		IToken xmlComment = new Token(XML_COMMENT);
		IToken processorInstr = new Token(XML_PROCESSOR_INSTRUCTION);
		IToken startTag = new Token(XML_STARTTAG);
		IToken endTag = new Token(XML_ENDTAG);
		IToken emptyTag = new Token(XML_EMPTYTAG);
		IToken declaration = new Token(XML_DECLARATION);
		IToken entity = new Token(XML_ENTITY);
		IToken cdata = new Token(XML_CDATA);

		IPredicateRule[] rules = new IPredicateRule[8];

		rules[0] = new MultiLineRule("<!--", "-->", xmlComment);
		rules[1] = new MultiLineRule("<?", "?>", processorInstr);
		rules[2] = new StartTagRule(startTag);
		rules[3] = new EndTagRule(endTag);
		rules[4] = new EmptyTagRule(emptyTag);
		rules[5] = new DeclarationRule(declaration);
		rules[6] = new EntityRule(entity);
		rules[7] = new CDataRule(cdata);

		setPredicateRules(rules);
	}
}
