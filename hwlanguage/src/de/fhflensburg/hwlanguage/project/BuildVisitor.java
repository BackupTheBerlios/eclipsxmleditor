/*
 * Created on 09.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.project;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import de.fhflensburg.hwlanguage.Constants;
import de.fhflensburg.hwlanguage.util.PathUtil;

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

	/**
	 * @param xslFile
	 * @param xslFile2
	 */
	public BuildVisitor(
		IFile xslFile,
		IFolder outputFolder,
		IFolder inputFolder,
		IProgressMonitor monitor) throws TransformerConfigurationException, CoreException {
		this.outputFolder = outputFolder;
		this.inputFolder = inputFolder;
		this.progressMonitor = monitor;
		this.properties = new Properties(xslFile.getProject());

		TransformerFactory tFactory = TransformerFactory.newInstance();
		this.transformer =
			tFactory.newTransformer(new StreamSource(xslFile.getContents()));

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
			outputFolder.getFullPath().append(
				PathUtil.subtract(
					resource.getFullPath(),
					inputFolder.getFullPath()));
		if (resource.getType() == IResource.FILE) {
			IFile res = (IFile) resource;
			// Do we have an HWL-File?
			if (res.getFileExtension().equals(Constants.HWL_FILE_EXTENSION)) {
				transform(res, dest);
			} else {
				// If not just copy it to the outputdir
				IFile file =
					(IFile) resource.getProject().findMember(
						dest.removeFirstSegments(1));
				if (file != null) {
					file.delete(true, true, this.progressMonitor);
				}
				resource.copy(dest, true, this.progressMonitor);
			}
			return false;
		} else if (resource.getType() == IResource.FOLDER) {
			if (resource.getProject().findMember(dest.removeFirstSegments(1))
				== null) {
				resource.copy(dest, true, this.progressMonitor);
			}
			return true;
		} else {
			return true;
		}
	}

	/**
	 * @param res
	 */
	private void transform(IFile res, IPath dest) throws CoreException {
		System.out.print(res.getName() + " wird transformiert...");
		try {
			transformer.transform(new StreamSource(res.getContents()),
								  new StreamResult(res.getProject().getLocation().append(dest.removeFirstSegments(1)).removeFileExtension().addFileExtension(properties.getFileExtension()).toFile()));
			System.out.println("Done!");
		} catch (TransformerException e) {
			System.err.println("The following error occured: " + e);
		}

	}

}
