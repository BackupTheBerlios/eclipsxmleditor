/*
 * Created on 19.10.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.fhflensburg.xmleditor.contentassist;

import java.io.File;

/**
 * @author user
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface XmlProposalManagerFactory {
	public XmlProposalManager createManager(File file);
}
