package de.fhflensburg.hwlanguage.filewizard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import de.fhflensburg.hwlanguage.util.MessageUtil;

/**
 * This class is the only page of the Readme file resource creation wizard.  
 * It subclasses the standard file resource creation page class, 
 * and consequently inherits the file resource creation functionality.
 *
 * This page provides users with the choice of creating sample headings for
 * sections and subsections.  Additionally, the option is presented to open
 * the file immediately for editing after creation.
 */
public class HWLFileCreationPage extends WizardNewFileCreationPage {
	private IWorkbench workbench;

	// widgets
	private Button openFileCheckbox;

	private Pattern pattern;

	/**
	 * Creates the page for the readme creation wizard.
	 *
	 * @param workbench  the workbench on which the page should be created
	 * @param selection  the current selection
	 */
	public HWLFileCreationPage(
		IWorkbench workbench,
		IStructuredSelection selection) {
		super("createHWLFilePage", selection); //$NON-NLS-1$
		this.setTitle(MessageUtil.getString("Create_HWL_File")); //$NON-NLS-1$
		this.setDescription(MessageUtil.getString("Create_HWL_File.desc")); //$NON-NLS-1$
		this.workbench = workbench;
	}
	/** (non-Javadoc)
	 * Method declared on IDialogPage.
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite composite = (Composite)getControl();
		//		open file for editing checkbox
		openFileCheckbox = new Button(composite, SWT.CHECK);
		openFileCheckbox.setText(MessageUtil.getString("Open_file_for_editing_when_done")); //$NON-NLS-1$
		openFileCheckbox.setSelection(true);
		this.setFileName("*.xml");
	}
	/**
	 * Creates a new file resource as requested by the user. If everything
	 * is OK then answer true. If not, false will cause the dialog
	 * to stay open.
	 *
	 * @return whether creation was successful
	 * @see ReadmeCreationWizard#performFinish()
	 */
	public boolean finish() {
		// create the new file resource
		IFile newFile = createNewFile();
		if (newFile == null)
			return false; // ie.- creation was unsuccessful

		// Since the file resource was created fine, open it for editing
		// if requested by the user
		try {
			if (openFileCheckbox.getSelection()) {
				IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
				IWorkbenchPage page = dwindow.getActivePage();
				if (page != null)
					page.openEditor(newFile);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** 
	 * The <code>ReadmeCreationPage</code> implementation of this
	 * <code>WizardNewFileCreationPage</code> method 
	 * generates sample headings for sections and subsections in the
	 * newly-created Readme file according to the selections of self's
	 * checkbox widgets
	 */
	protected InputStream getInitialContents() {
		return new ByteArrayInputStream(
			"<?xml version=\"1.0\"?>\n<document>\n</document>".getBytes());
	}
	/** (non-Javadoc)
	 * Method declared on WizardNewFileCreationPage.
	 */
	protected String getNewFileLabel() {
		return MessageUtil.getString("NewHWLFile"); //$NON-NLS-1$
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
	 */
	protected boolean validatePage() {
		if (pattern==null) {
			pattern = Pattern.compile("[a-zA-Z0-9_-]{1,}\\.xml");
		}
		if (pattern.matcher(this.getFileName()).matches()) {
			return super.validatePage();
		} else {
			return false;
		}
	}

}
