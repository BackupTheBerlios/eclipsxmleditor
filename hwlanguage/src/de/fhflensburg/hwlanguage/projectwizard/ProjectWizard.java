package de.fhflensburg.hwlanguage.projectwizard;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import de.fhflensburg.hwlanguage.Constants;
import de.fhflensburg.hwlanguage.util.MessageUtil;
import de.fhflensburg.hwlanguage.util.PlugInUtil;
import de.fhflensburg.hwlanguage.util.ResourceUtil;

/**
 * Creates a new XM project in the Eclipse workbench.
 * Populates the project with default files.
 * 
 * @author bmarchal
 */
public class ProjectWizard extends Wizard implements INewWizard {
	/**
	 * The main page on the wizard: collects the project name and location.
	 */
	private WizardNewProjectCreationPage namePage;
	
	/**
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setNeedsProgressMonitor(true);
	}

	/**
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	public void addPages() {
		try {
			super.addPages();
			namePage = new WizardNewProjectCreationPage("NewHWLProjectWizard");
			namePage.setTitle(MessageUtil.getString("newprojectname"));
			namePage.setDescription(MessageUtil.getString("newprojectdesc"));
			addPage(namePage);
		} catch (Exception x) {
			PlugInUtil.reportError(x,this.getShell());
		}
	}

	/**
	 * User has clicked "Finish", we create the project.
	 * In practice, it calls the createProject() method in the appropriate thread.
	 * @see #createProject(IProjectMonitor)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		try {
			getContainer().run(false, true, new WorkspaceModifyOperation() {
				protected void execute(IProgressMonitor monitor) {
					createProject(
						monitor != null ? monitor : new NullProgressMonitor());
				}
			});
		} catch (InvocationTargetException x) {
			PlugInUtil.reportError(x,this.getShell());
			return false;
		} catch (InterruptedException x) {
			PlugInUtil.reportError(x,this.getShell());
			return false;
		}
		return true;
	}

	/**
	 * This is the actual implementation for project creation.
	 * @param monitor reports progress on this object
	 */
	protected void createProject(IProgressMonitor monitor) {
		monitor.beginTask(MessageUtil.getString("creatingproject"), 50);
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			monitor.subTask(MessageUtil.getString("creatingdirectories"));
			IProject project = root.getProject(namePage.getProjectName());
			IProjectDescription description =
				ResourcesPlugin.getWorkspace().newProjectDescription(
					project.getName());
			if (!Platform.getLocation().equals(namePage.getLocationPath()))
				description.setLocation(namePage.getLocationPath());
			project.create(description, monitor);
			monitor.worked(10);
			project.open(monitor);
			description = project.getDescription();
			description.setNatureIds(new String[] { Constants.NATURE_ID });
			project.setDescription(
				description,
				new SubProgressMonitor(monitor, 10));
			IPath projectPath = project.getFullPath(),
				srcPath = projectPath.append(Constants.DEFAULT_INPUT_DIR),
				rulesPath = projectPath.append(Constants.DEFAULT_XSL_DIR),
				publishPath = projectPath.append(Constants.DEFAULT_OUTPUT_DIR);
			IFolder srcFolder = root.getFolder(srcPath),
				rulesFolder = root.getFolder(rulesPath),
				publishFolder = root.getFolder(publishPath);
			ResourceUtil.createFolderHelper(srcFolder, monitor);
			ResourceUtil.createFolderHelper(rulesFolder, monitor);
			ResourceUtil.createFolderHelper(publishFolder, monitor);
			monitor.worked(10);
			monitor.subTask(MessageUtil.getString("creatingfiles"));
			IPath indexPath = srcPath.append("index.xml"),
				defaultPath = rulesPath.append("default.xsl");
			IFile indexFile = root.getFile(indexPath),
				defaultFile = root.getFile(defaultPath);
			Class clasz = getClass();
			InputStream indexIS =
				clasz.getResourceAsStream(
					"/resources/index.xml"),
				defaultIS =
					clasz.getResourceAsStream(
						"/resources/default.xsl");
			indexFile.create(
				indexIS,
				false,
				new SubProgressMonitor(monitor, 10));
			defaultFile.create(
				defaultIS,
				false,
				new SubProgressMonitor(monitor, 10));
		} catch (CoreException x) {
			PlugInUtil.reportError(x,this.getShell());
		} finally {
			monitor.done();
		}
	}

	
}