/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.contentassist;

import java.io.File;

/**
 * @author user
 */
public interface XmlProposalManagerFactory {
	public XmlProposalManager createManager(File file);
}
