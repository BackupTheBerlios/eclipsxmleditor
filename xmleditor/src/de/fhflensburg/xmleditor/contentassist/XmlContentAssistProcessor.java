/*
 * Created on 11.10.2003
 *
 */
package de.fhflensburg.xmleditor.contentassist;

import java.util.HashMap;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import de.fhflensburg.xmleditor.contentassist.model.XmlProposal;
import de.fhflensburg.xmleditor.editor.partitions.XMLPartitionScanner;

/**
 * @author user
 *
 */
public class XmlContentAssistProcessor implements IContentAssistProcessor {
	private XmlProposalManager manager;
	public XmlContentAssistProcessor() {
		// initialize empty defaultmanager
		manager = XmlProposalManagerImpl.create(new HashMap());
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	public ICompletionProposal[] computeCompletionProposals(
		ITextViewer viewer,
		int documentOffset) {
		ICompletionProposal[] proposals = null;
		IDocument document = viewer.getDocument();
		try {
			XmlProposal curr = getCurrentElement(viewer, documentOffset);
			String prefix = getPrefix(viewer, documentOffset);
			if (isInTag(viewer, documentOffset)) {
				// Attributeproposals
				String[] attrs = manager.findAttributesFor(curr, prefix);
				proposals = new CompletionProposal[attrs.length];
				for (int i = 0; i < attrs.length; i++) {
					proposals[i] =
						new CompletionProposal(
							attrs[i] + "=\"\"",
							documentOffset - prefix.length(),
							prefix.length(),
							documentOffset
								- prefix.length()
								+ attrs[i].length()
								+ 2);
				}
			} else {
				// ElementProposals
				XmlProposal[] eles = manager.findElementsFor(curr, prefix);
				proposals = new CompletionProposal[eles.length];
				for (int i = 0; i < eles.length; i++) {
					String name = eles[i].getName();
					proposals[i] =
						new CompletionProposal(
							name,
							documentOffset - prefix.length(),
							prefix.length(),
							documentOffset - prefix.length() + name.length());
				}
			}
			System.out.println(document.getContentType(documentOffset));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return proposals;
	}

	/**
	 * @param viewer
	 * @param documentOffset
	 * @return
	 */
	private boolean isInTag(ITextViewer viewer, int documentOffset) {
		
		return false;
	}
	/**
	 * @param viewer
	 * @param documentOffset
	 * @return
	 */
	private String getPrefix(ITextViewer viewer, int documentOffset) {
		try {
			StringBuffer buff = new StringBuffer();
			IDocument doc = viewer.getDocument();
			char c;
			for (int i=documentOffset;Character.isJavaIdentifierPart((c=doc.getChar(i)));i--) {
				buff.append(c);
			}
			return buff.toString();
		} catch (BadLocationException e) {
			return null;
		}
	}
	/**
	 * @param viewer
	 * @param documentOffset
	 * @return
	 */
	private XmlProposal getCurrentElement(
		ITextViewer viewer,
		int offset) {
		IDocument doc = viewer.getDocument();
		try {
			ITypedRegion part = doc.getPartition(offset);
			if (part.getType().equals(XMLPartitionScanner.XML_EMPTYTAG) || 
				part.getType().equals(XMLPartitionScanner.XML_STARTTAG)) {
				String name = doc.get();		
			}
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IContextInformation[] computeContextInformation(
		ITextViewer viewer,
		int documentOffset) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getCompletionProposalAutoActivationCharacters()
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
	 */
	public String getErrorMessage() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
	 */
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

}
