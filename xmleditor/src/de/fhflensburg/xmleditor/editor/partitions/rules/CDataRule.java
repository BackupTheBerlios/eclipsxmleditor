/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

/**
 * @author user
 *
 */
public class CDataRule extends MultiLineRule {

	/**
	 * @param cdata
	 */
	public CDataRule(IToken token) {
		super("<![CDATA", "]>", token);
	}
	
}
