/*
 * Created on 19.10.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
