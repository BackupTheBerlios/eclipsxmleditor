/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.contentassist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.fhflensburg.xmleditor.contentassist.model.XmlProposal;

/**
 * @author user
 *
 */
public class XmlProposalManagerImpl implements XmlProposalManager {

	protected Map elements = new HashMap();

	/**
	 * @param elements2
	 */
	protected XmlProposalManagerImpl(Map elements) {
		this.elements = elements;
	}

	protected XmlProposalManagerImpl() {
	}

	public static final XmlProposalManager create(Map elements) {
		return new XmlProposalManagerImpl(elements);
	}

	/* (non-Javadoc)
	 * @see de.flensburg.contentassist.XmlProposalManager#get(java.lang.String)
	 */
	public XmlProposal get(String name) {
		return (XmlProposal) elements.get(name);
	}

	/* (non-Javadoc)
	 * @see de.flensburg.contentassist.XmlProposalManager#findElementsFor(de.flensburg.contentassist.model.XmlProposal, java.lang.String)
	 */
	public XmlProposal[] findElementsFor(XmlProposal proposal, String prefix) {
		List childs = proposal.getChildren();
		List props = new ArrayList();
		for (Iterator iter = childs.iterator(); iter.hasNext();) {
			XmlProposal element = (XmlProposal) iter.next();
			if (element.getName().startsWith(prefix)) {
				props.add(element);
			}
		}
		return (XmlProposal[]) props.toArray(new XmlProposal[props.size()]);
	}

	/* (non-Javadoc)
	 * @see de.flensburg.contentassist.XmlProposalManager#findAttributesFor(de.flensburg.contentassist.model.XmlProposal, java.lang.String)
	 */
	public String[] findAttributesFor(XmlProposal proposal, String prefix) {
		List childs = proposal.getAttributes();
		List attrs = new ArrayList();
		for (Iterator iter = childs.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			if (element.startsWith(prefix)) {
				attrs.add(element);
			}
		}
		return (String[]) attrs.toArray(new String[attrs.size()]);
	}

}
