/*
 * Created on 19.10.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.fhflensburg.xmleditor.contentassist.schema;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;

import de.fhflensburg.xmleditor.contentassist.XmlProposalManagerImpl;

/**
 * @author user
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class XmlProposalManagerSchemaImpl extends XmlProposalManagerImpl {

	XmlProposalManagerSchemaImpl(File f) {
		XSDSchema schema = load(f);
		this.elements = loadMap(schema);
	}

	/**
	 * @param schema2
	 */
	private Map loadMap(XSDSchema schema) {
		Map map = new HashMap();
		EList eleDeclars = schema.getElementDeclarations();
		for (Iterator iter = eleDeclars.iterator(); iter.hasNext();) {
			XSDElementDeclaration def = (XSDElementDeclaration) iter.next();
			map.put(def.getAliasName(),new XmlProposalSchemaImpl(def,map));
		}
		return map;
	}

	/**
	 * @param f
	 */
	private XSDSchema load(File f) {
		//		String variable schemaURL is "FindTypesMissingFacets.xsd" or the URL to your schema
		//		Create a resource set and load the main schema file into it.

		ResourceSet resourceSet = new ResourceSetImpl();
		XSDResourceImpl xsdSchemaResource =
			(XSDResourceImpl) resourceSet.getResource(
				URI.createFileURI(f.getAbsolutePath()),
				true);

		//		getResources() returns an iterator over all the resources, therefore, the main resource
		//		and those that have been included, imported, or redefined.
		for (Iterator resources = resourceSet.getResources().iterator();
			resources.hasNext();
			/* no-op */
			) {
			// Return the first schema object found, which is the main schema 
			//   loaded from the provided schemaURL
			Resource resource = (Resource) resources.next();
			if (resource instanceof XSDResourceImpl) {
				XSDResourceImpl xsdResource = (XSDResourceImpl) resource;
				// This returns a org.eclipse.xsd.XSDSchema object
				return xsdResource.getSchema();
			}
		}
		throw new IllegalArgumentException(
			"No Schema found under URI:" + f.getAbsolutePath());
	}
}
