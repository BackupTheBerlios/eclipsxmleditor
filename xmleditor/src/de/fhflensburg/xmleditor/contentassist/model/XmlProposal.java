/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.contentassist.model;

import java.util.List;

/**
 * @author user
 *
 */
public interface XmlProposal {
	public String getName();
	public List getChildren();
	public List getAttributes();
}
