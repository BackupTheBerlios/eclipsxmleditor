package de.fhflensburg.hwlanguage.project;

import java.io.IOException;
import java.util.Map;

import javax.naming.ConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.fhflensburg.hwlanguage.exception.BuildException;
import de.fhflensburg.hwlanguage.util.PlugInUtil;

/**
 * 
 * @author user
 */
public class IncrementalBuilder extends IncrementalProjectBuilder {
	/**
	 * Retains a reference to the workbench that we can use throughout the build.
	 */
	private IWorkbench workbench;

	/**
	 * Creates a new XMBuilder.
	 */
	public IncrementalBuilder() {
		workbench = PlatformUI.getWorkbench();
	}

	/**
	 * This class displays an error dialog box in a separate thread.
	 * Typically used to synchronize with the UI thread.
	 */
	private static class DisplayError implements Runnable {
		private Shell shell = null;
		private IStatus status = null;
		public DisplayError(IWorkbench workbench, Exception x) {
			if (workbench != null
				&& workbench.getActiveWorkbenchWindow() != null) {
				shell = workbench.getActiveWorkbenchWindow().getShell();
			}
			status = PlugInUtil.getStatus(x);
		}
		public void run() {
			PlugInUtil.reportError(new BuildException(status), shell);
		}
	}

	/**
	 * Builds the project.
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int, Map, IProgressMonitor)
	 */
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) {
		try {
			IProject project = getProject();
			if (project != null && project.isAccessible()) {
				if (monitor == null) {
					monitor = new NullProgressMonitor();
				}
				saveDirtyEditors();
				IResource output = doBuild(monitor,kind == FULL_BUILD);
				output.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			}
		} catch (Exception x) {
			System.err.println("---");
			x.printStackTrace();
			System.err.println("---");
			Display display = Display.getCurrent();
			if (display == null)
				display = Display.getDefault();
			display.asyncExec(new DisplayError(workbench, x));
		}
		return new IProject[0];
	}

	
	private IResource doBuild(IProgressMonitor monitor, boolean build)
		throws CoreException, IOException, ConfigurationException {
		IProject project = getProject();
		IFolder inputFolder = null;
		IFile xslFile = null;
		IFolder outputFolder = null;
		Properties props = new Properties(project);
		try {
			xslFile = project.getFile(props.getStylesheet());
			inputFolder = project.getFolder(props.getInput());
			outputFolder = project.getFolder(props.getOutput());
		} catch (Exception e) {
			e.printStackTrace();
		}
		validate(xslFile);
		validate(inputFolder);
		validate(outputFolder);
		
		try {
			inputFolder.accept(new BuildVisitor(xslFile,outputFolder,inputFolder,monitor));
		} catch (TransformerConfigurationException e1) {
			throw new ConfigurationException("couldn't parse the Stylesheet:"+e1.getLocalizedMessage());
		} 
		return outputFolder;
	}

	/**
	 * @param IResource resource
	 */
	private void validate(IResource res) throws ConfigurationException {
		if (res==null) {
			throw new ConfigurationException("Check your config!");	
		} else if(!res.exists()) {
			throw new ConfigurationException(res.getName() +" does not exist!");
		}
	}


	/**
	 * Saves dirty editors (i.e. those editors that were modified but
	 * not saved) in the workbench. It takes care to run the saving process
	 * in the UI thread.
	 */
	private void saveDirtyEditors() {
		Display display = Display.getCurrent();
		if (display == null)
			display = Display.getDefault();
		display.syncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
				for (int i = 0; i < windows.length; i++) {
					IWorkbenchPage[] pages = windows[i].getPages();
					for (int j = 0; j < pages.length; j++)
						pages[j].saveAllEditors(false);
				}
			}
		});
	}
}