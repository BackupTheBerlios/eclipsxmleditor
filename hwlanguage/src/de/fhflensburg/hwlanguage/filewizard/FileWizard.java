/*
 * Created on 01.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.filewizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import de.fhflensburg.hwlanguage.util.ImageUtil;
import de.fhflensburg.hwlanguage.util.MessageUtil;

/**
 * @author user
 *
 * 
 */
public class FileWizard extends Wizard implements INewWizard {

	private IStructuredSelection selection;
	private IWorkbench workbench;
	private FileCreationPage mainPage;
	/** (non-Javadoc)
	 * Method declared on Wizard.
	 */
	public void addPages() {
		mainPage = new FileCreationPage(workbench, selection);
		addPage(mainPage);
	}
	/** (non-Javadoc)
	 * Method declared on INewWizard
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(MessageUtil.getString("NewHWLFile")); //$NON-NLS-1$
		setDefaultPageImageDescriptor(ImageUtil.README_WIZARD_BANNER);
	}
	/** (non-Javadoc)
	 * Method declared on IWizard
	 */
	public boolean performFinish() {
		return mainPage.finish();
	}
}
