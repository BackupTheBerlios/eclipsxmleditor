/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.contentassist;

import de.fhflensburg.xmleditor.contentassist.model.XmlProposal;

/**
 * @author user
 *
 */
public interface XmlProposalManager {
	public XmlProposal get(String name);
	public XmlProposal[] findElementsFor(XmlProposal proposal,String prefix);
	public String[] findAttributesFor(XmlProposal proposal,String prefix);
}
