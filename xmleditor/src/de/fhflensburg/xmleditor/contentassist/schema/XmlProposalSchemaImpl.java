/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.contentassist.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;

import de.fhflensburg.xmleditor.contentassist.model.XmlProposal;

/**
 * @author user
 *
 */
public class XmlProposalSchemaImpl implements XmlProposal {
	private List attributes;
	private List children;
	private Map elementMap;

	private Logger log = Logger.getLogger(this.getClass().toString());
	private String name;
	private XSDElementDeclaration eleDecl;

	protected XmlProposalSchemaImpl(XSDElementDeclaration decl, Map map) {
		this.name = decl.getAliasName();
		this.elementMap = map;
		this.eleDecl = decl;
		
	}
	/* (non-Javadoc)
	 * @see de.flensburg.contentassist.model.XmlProposal#getAttributes()
	 */
	public List getAttributes() {
		if (this.attributes == null) {
			this.attributes = loadAttributes(eleDecl,new ArrayList());
		}
		return this.attributes;
	}

	/* (non-Javadoc)
	 * @see de.flensburg.contentassist.model.XmlProposal#getChildren()
	 */
	public List getChildren() {
		if (this.children == null) {
			this.children = loadChildren(eleDecl);
		}
		return this.children;
	}
	/* (non-Javadoc)
	 * @see de.flensburg.contentassist.model.XmlProposal#getName()
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @param decl
	 * @return
	 */
	private List loadAttributes(XSDElementDeclaration decl, List attrs) {
		for (TreeIterator iter = decl.getType().eAllContents(); iter.hasNext();) {
			Object element = (Object) iter.next();
			if (element instanceof XSDAttributeDeclaration) {
				attrs.add(((XSDAttributeDeclaration) element).getAliasName());
			} else if (element instanceof XSDTypeDefinition) {
				loadAttributes((XSDElementDeclaration) element, attrs);
			} else {
				log.log(Level.INFO, element.toString());
			}

		}
		return attrs;
	}

	/**
	 * @param typeDef2
	 * @return
	 */
	private List loadChildren(XSDElementDeclaration decl) {
		List childs = new ArrayList();
		
		for (TreeIterator iter = decl.getType().eAllContents(); iter.hasNext();) {
			Object element = (Object) iter.next();
			if (element instanceof XSDElementDeclaration) {
				XSDElementDeclaration ele = (XSDElementDeclaration) element;
				XmlProposal prop = (XmlProposal) this.elementMap.get(ele.getAliasName());
				if (prop==null) {
					prop = new XmlProposalSchemaImpl(ele,elementMap);
					this.elementMap.put(ele.getAliasName(),prop);	
				}
				childs.add(prop);
			} else {
				log.log(Level.INFO, element.toString());
			}
		}
		return childs;
	}

}
