/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.contentassist.schema;

import java.io.File;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;

import de.fhflensburg.xmleditor.contentassist.XmlProposalManager;
import de.fhflensburg.xmleditor.contentassist.XmlProposalManagerFactory;

/**
 * @author user
 *
 */
public class XmlProposalManagerFactorySchemaImpl
	implements XmlProposalManagerFactory {

	/* (non-Javadoc)
	 * @see de.flensburg.contentassist.XmlProposalManagerFactory#createManager(java.io.File)
	 */
	public XmlProposalManager createManager(File file) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xsd", new XSDResourceFactoryImpl());
		return new XmlProposalManagerSchemaImpl(file);
	}

}
