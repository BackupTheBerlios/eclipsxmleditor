/*
 * Created on 19.10.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.fhflensburg.xmleditor.contentassist;

import de.fhflensburg.xmleditor.contentassist.model.XmlProposal;

/**
 * @author user
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface XmlProposalManager {
	public XmlProposal get(String name);
	public XmlProposal[] findElementsFor(XmlProposal proposal,String prefix);
	public String[] findAttributesFor(XmlProposal proposal,String prefix);
}
