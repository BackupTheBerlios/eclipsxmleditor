/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.contentassist;

import de.fhflensburg.xmleditor.contentassist.schema.XmlProposalManagerFactorySchemaImpl;

/**
 * @author user
 *
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
