/*
 * Created on 15.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.project.build;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.parsers.DOMParser;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
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

import de.fhflensburg.hwlanguage.Constants;
import de.fhflensburg.hwlanguage.HwLanguagePlugin;
import de.fhflensburg.hwlanguage.project.Properties;
import de.fhflensburg.hwlanguage.util.PlugInUtil;
import de.fhflensburg.hwlanguage.util.ResourceUtil;

/**
 * @author user
 *
 * 
 */
public class HwlBuilder {

	private Transformer transformer;
	private IFolder outputFolder;
	private IFolder inputFolder;

	public HwlBuilder(Transformer transformer, IFolder outputFolder, IFolder inputFolder) {
		this.transformer = transformer;
		this.outputFolder = outputFolder;
		this.inputFolder = inputFolder;
	}
	public void build(IContainer resource, IProgressMonitor monitor) throws CoreException {
		if (resource.equals(inputFolder)) {
			return;
		}
		IPath dest =
			outputFolder.getFullPath().append(ResourceUtil.subtract(resource.getFullPath(), inputFolder.getFullPath()));
		if (resource.getProject().findMember(dest.removeFirstSegments(1)) == null) {
			resource.copy(dest, 4, monitor);
			IFolder newFolder = (IFolder) resource.getProject().findMember(dest.removeFirstSegments(1));
			IResource[] members = newFolder.members();
			for (int i = 0; i < members.length; i++) {
				members[i].delete(true, monitor);
			}
		}
	}

	public void build(IFile resource, IProgressMonitor monitor) throws CoreException {
		DOMParser parser = new DOMParser();
		try {
			InputSource source = new InputSource(resource.getContents());
			source.setSystemId(resource.getLocation().toString());
			parser.parse(source);
			Document doc = parser.getDocument();
			buildMathMlImages(doc, resource, computeMathMlDest(), monitor);
			transform(doc, computeTransformDest(resource), monitor);
		} catch (Exception e) {
			throw new CoreException(PlugInUtil.getStatus(e));
		}
	}

	/**
	 * @param resource
	 * @return
	 */
	private String computeImageName(IFile resource) {
		IPath path = ResourceUtil.subtract(resource.getProjectRelativePath(), inputFolder.getProjectRelativePath());
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < path.segmentCount(); i++) {
			buff.append(path.segment(i)).append('_');
		}
		return buff.toString();
	}
	/**
	 * @return
	 */
	private IPath computeMathMlDest() {
		return outputFolder.getFullPath();
	}

	/**
	 * @param resource
	 * @return
	 */
	private IPath computeTransformDest(IResource resource) throws CoreException {
		Properties props = new Properties(resource.getProject());
		IPath srcRelativePath =
			ResourceUtil.subtract(resource.getProjectRelativePath(), inputFolder.getProjectRelativePath());
		return outputFolder.getLocation().append(srcRelativePath).removeFileExtension().addFileExtension(
			props.getFileExtension());
	}

	/**
	 * @param res
	 */
	private void transform(Document doc, IPath dest, IProgressMonitor monitor) throws TransformerException {
		try {
			transformer.transform(new DOMSource(doc.getDocumentElement()), new StreamResult(dest.toFile()));
		} catch (Exception e) {
			HwLanguagePlugin.getPlugIn().getLog().log(PlugInUtil.getStatus(e));
		}
	}

	private void buildMathMlImages(Document doc, IFile resource, IPath dest, IProgressMonitor monitor)
		throws CoreException, IOException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFolder imagefolder = root.getFolder(dest.append(Constants.MATH_ML_IMAGEPATH));
		if (!imagefolder.exists()) {
			ResourceUtil.createFolderHelper(imagefolder, monitor);
		}

		NodeList list = doc.getElementsByTagName(Constants.MATH_ML_TAGNAME);
		for (int i = 1; list.getLength()>0; i++) {
			Node node = list.item(0);
			if (node != null) {
				IFile file =
					root.getFile(imagefolder.getFullPath().append("/" + computeImageName(resource) + i + ".gif"));
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				new MathMlImage(node, Constants.MATH_ML_FONTSIZE).write(stream);

				if (file.exists()) {
					file.delete(true, true, monitor);
				}
				file.create(new ByteArrayInputStream(stream.toByteArray()), true, monitor);

				// replace Node with an imagetag 
				Element newNode = doc.createElement(Constants.IMAGE_TAGNAME);
				Attr attr = doc.createAttribute("src");
				attr.setValue(computeRelativePath(file.getLocation(), computeTransformDest(resource)));
				newNode.setAttributeNode(attr);
				node.getParentNode().replaceChild(newNode, node);
			} else {
				System.out.println("Ups");
			}
		}
	}
	/**
	 * @param file
	 * @param outputFolder2
	 * @return
	 */
	private String computeRelativePath(IPath image, IPath document) {
		StringBuffer buff = new StringBuffer();
		buff.append('.');
		final char SEP = '/';

		image = image.makeAbsolute();
		document = document.makeAbsolute();
		int equalPrefixes = image.matchingFirstSegments(document);
		image = image.removeFirstSegments(equalPrefixes);
		document = document.removeFirstSegments(equalPrefixes);

		for (int i = 0; i < document.segmentCount() - 1; i++) {
			buff.append(SEP).append("..");
		}

		for (int i = 0; i < image.segmentCount(); i++) {
			buff.append(SEP).append(image.segment(i));
		}
		return buff.toString();
	}
}
