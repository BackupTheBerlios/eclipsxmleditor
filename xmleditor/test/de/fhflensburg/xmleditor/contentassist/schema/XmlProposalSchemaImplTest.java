/*
 * Created on 19.10.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.fhflensburg.xmleditor.contentassist.schema;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;
import de.fhflensburg.xmleditor.contentassist.XmlProposalManager;
import de.fhflensburg.xmleditor.contentassist.XmlProposalManagerFactory;
import de.fhflensburg.xmleditor.contentassist.XmlProposalManagerFactoryFactory;
import de.fhflensburg.xmleditor.contentassist.model.XmlProposal;

/**
 * @author user
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class XmlProposalSchemaImplTest extends TestCase {

	private XmlProposalManager manager;
	private final static String elementname = "person";

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		File f = new File("test/test.xsd");
		XmlProposalManagerFactory factory = XmlProposalManagerFactoryFactory.create();
		manager = factory.createManager(f);
		
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetName() {
		XmlProposal prop = manager.get(elementname);
		assertNotNull(prop);
		assertEquals(elementname,prop.getName());
	}

	public void testGetChildren() {
		XmlProposal prop = manager.get(elementname);
		assertNotNull(prop);
		
	}

	public void testGetAttributes() {
		XmlProposal prop = manager.get(elementname);
		assertNotNull(prop);
		List attrs = prop.getAttributes();
		assertEquals(1,attrs.size());
		assertEquals("gender",attrs.iterator().next());
	}

}
