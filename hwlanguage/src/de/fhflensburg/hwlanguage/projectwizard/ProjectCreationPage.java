/*
 * Created on 02.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.projectwizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import de.fhflensburg.hwlanguage.util.MessageUtil;

/**
 * @author user
 *
 * 
 */
public class ProjectCreationPage extends WizardNewProjectCreationPage {

	private IWorkbench workbench;

	/**
	 * @param pageName
	 */
	public ProjectCreationPage(String pageName) {
		super(pageName);
	}

	/**
	 * @param workbench
	 * @param selection
	 */
	public ProjectCreationPage(
		IWorkbench workbench,
		IStructuredSelection selection) {
		super("new Project");
		this.setTitle(MessageUtil.getString("Create_HWL_Project")); //$NON-NLS-1$
		this.setDescription(MessageUtil.getString("Create_HWL_Project_description")); //$NON-NLS-1$
		this.workbench = workbench;

	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, 0);
		GridLayout gd = new GridLayout();
		gd.numColumns = 2;
		composite.setLayout(gd);
		Label label = new Label(composite, 16384);
		label.setText("test");
		label.setLayoutData(new GridData());
		
		setControl(composite);
	}
	/**
	 * @return
	 */
	public boolean finish() {
		return false;
	}

}
