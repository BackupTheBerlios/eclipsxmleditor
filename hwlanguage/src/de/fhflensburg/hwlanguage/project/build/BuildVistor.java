/*
 * Created on 15.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.project.build;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author user
 *
 * 
 */
public class BuildVistor implements IResourceVisitor {

	private HwlBuilder builder;
	private IProgressMonitor monitor;
	private boolean fullBuild;
	private IResourceDelta delta;

	/**
	 * @param builder
	 * @param monitor
	 */
	public BuildVistor(HwlBuilder builder, boolean fullBuild, IResourceDelta delta, IProgressMonitor monitor) {
		this.builder = builder;
		this.monitor = monitor;
		this.fullBuild = fullBuild;
		this.delta = delta;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
	 */
	public boolean visit(IResource resource) throws CoreException {
		if (!fullBuild) {
			if (delta !=null && delta.findMember(resource.getProjectRelativePath())==null) {
				return true;
			} 
		}
		if (resource instanceof IFile) {
			this.builder.build((IFile)resource,monitor);
		} else {
			this.builder.build((IContainer)resource,monitor);
		}
		return true;
	}

}
