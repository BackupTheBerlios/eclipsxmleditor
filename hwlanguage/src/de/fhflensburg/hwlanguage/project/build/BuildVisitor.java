/*
 * Created on 09.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.project.build;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.xerces.parsers.DOMParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.fhflensburg.hwlanguage.Constants;
import de.fhflensburg.hwlanguage.project.Properties;
import de.fhflensburg.hwlanguage.util.ResourceUtil;

/**
 * @author user
 *
 * 
 */
public class BuildVisitor implements IResourceVisitor {

	private IFolder outputFolder;
	private IProgressMonitor progressMonitor;
	private IFolder inputFolder;
	private Transformer transformer;
	private Properties properties;
	private static int runningNumber = 0;

	/**
	 * @param xslFile
	 * @param xslFile2
	 */
	public BuildVisitor(IFile xslFile, IFolder outputFolder, IFolder inputFolder, IProgressMonitor monitor)
		throws TransformerConfigurationException, CoreException {
		this.outputFolder = outputFolder;
		this.inputFolder = inputFolder;
		this.progressMonitor = monitor;
		this.properties = new Properties(xslFile.getProject());

		TransformerFactory tFactory = TransformerFactory.newInstance();
		this.transformer = tFactory.newTransformer(new StreamSource(xslFile.getContents()));

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
	 */
	public boolean visit(IResource resource) throws CoreException {
		if (inputFolder.equals(resource)) {
			// Do nothing go to the children
			return true;
		}
		IPath dest =
			outputFolder.getFullPath().append(ResourceUtil.subtract(resource.getFullPath(), inputFolder.getFullPath()));
		if (resource.getType() == IResource.FILE) {
			IFile res = (IFile) resource;
			// Do we have an HWL-File?
			if (res.getFileExtension().equals(Constants.HWL_FILE_EXTENSION)) {
				try {
					transform(res, dest,progressMonitor);
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (CoreException e) {
					e.printStackTrace();
				}
			} else {
				// If not just copy it to the outputdir
				IFile file = (IFile) resource.getProject().findMember(dest.removeFirstSegments(1));
				if (file != null) {
					file.delete(true, true, this.progressMonitor);
				}
				resource.copy(dest, true, this.progressMonitor);
			}
			return false;
		} else if (resource.getType() == IResource.FOLDER) {
			if (resource.getProject().findMember(dest.removeFirstSegments(1)) == null) {
				resource.copy(dest, 4, this.progressMonitor);
				IFolder newFolder = (IFolder) resource.getProject().findMember(dest.removeFirstSegments(1));
				IResource[] members = newFolder.members();
				for (int i = 0; i < members.length; i++) {
					members[i].delete(true, this.progressMonitor);
				}
			}
			return true;
		} else {
			return true;
		}
	}

	/**
	 * @param res
	 */
	private void transform(IFile res, IPath dest, IProgressMonitor monitor) throws SAXException, IOException, CoreException {
		System.out.print(res.getName() + " wird transformiert...");
		DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(res.getContents()));
			Document doc = parser.getDocument();
			buildMathMlImages(doc, monitor);
			transformer.transform(
				new DOMSource(doc.getDocumentElement()),
				new StreamResult(
					res
						.getProject()
						.getLocation()
						.append(dest.removeFirstSegments(1))
						.removeFileExtension()
						.addFileExtension(properties.getFileExtension())
						.toFile()));
			System.out.println("Done!");
		} catch (TransformerException e) {
			System.err.println("The following error occured: " + e);
		}

	}

	private void buildMathMlImages(Document doc, IProgressMonitor monitor) throws CoreException, IOException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFolder imagefolder = root.getFolder(outputFolder.getFullPath().append(Constants.MATH_ML_IMAGEPATH));
		if (!imagefolder.exists()) {
			ResourceUtil.createFolderHelper(imagefolder, monitor);
		}

		NodeList list = doc.getElementsByTagName(Constants.MATH_ML_TAGNAME);
		for (int i = 0, x = list.getLength(); i < x; i++) {
			Node node = list.item(i);
			IFile file = root.getFile(imagefolder.getFullPath().append("/" + (runningNumber++) + ".gif"));
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			new MathMlImage(node, Constants.MATH_ML_FONTSIZE).write(stream);

			file.create(new ByteArrayInputStream(stream.toByteArray()), true, monitor);


			// replace Node with an imagetag 
			Element newNode = doc.createElement(Constants.IMAGE_TAGNAME);
			Attr attr = doc.createAttribute("src");
			attr.setValue(file.getLocation().toString());
			newNode.setAttributeNode(attr);
			node.getParentNode().replaceChild(newNode, node);
		}
	}

}
