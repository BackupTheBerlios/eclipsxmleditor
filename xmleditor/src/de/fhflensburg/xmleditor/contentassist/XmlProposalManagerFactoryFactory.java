/*
 * Created on 19.10.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.fhflensburg.xmleditor.contentassist;

import de.fhflensburg.xmleditor.contentassist.schema.XmlProposalManagerFactorySchemaImpl;

/**
 * @author user
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class XmlProposalManagerFactoryFactory {
	private static XmlProposalManagerFactory factory;
	public static final XmlProposalManagerFactory create() {
		if (factory ==null) {
			factory = new XmlProposalManagerFactorySchemaImpl();
		}
		return factory;
	}
}
