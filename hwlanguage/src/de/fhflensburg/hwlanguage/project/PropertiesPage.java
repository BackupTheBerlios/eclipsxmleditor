package de.fhflensburg.hwlanguage.project;

import javax.naming.ConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;

import de.fhflensburg.hwlanguage.Constants;
import de.fhflensburg.hwlanguage.util.MessageUtil;
import de.fhflensburg.hwlanguage.util.PlugInUtil;

/**
 * @author user
 */
public class PropertiesPage extends PropertyPage implements SelectionListener {
	private Text inputText, transformText, outputText, transformedFileExtension;
	private FileDialog transformDialog;
	private DirectoryDialog inputDialog, outputDialog;
	private Button transformDialogButton, inputDialogButton, outputDialogButton;
	private Group group;
	private Properties properties;

	private Control buildUI(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		RowLayout rowLayout = new RowLayout();
		rowLayout.type = SWT.VERTICAL;
		rowLayout.wrap = false;
		composite.setLayout(rowLayout);

		group = new Group(composite, SWT.NONE);
		group.setText(MessageUtil.getString("properties"));
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		group.setLayout(gridLayout);
		Label label = new Label(group, SWT.RIGHT);
		label.setText(MessageUtil.getString("input"));
		inputText = new Text(group, SWT.SINGLE);
		GridData gridData = new GridData();
		gridData.widthHint = 150;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		inputText.setLayoutData(gridData);
		inputDialogButton = new Button(group, SWT.LEFT);
		inputDialogButton.setText(MessageUtil.getString("find"));
		inputDialogButton.addSelectionListener(this);

		label = new Label(group, SWT.RIGHT);
		label.setText(MessageUtil.getString("output"));
		outputText = new Text(group, SWT.LEFT);
		gridData = new GridData();
		outputText.setLayoutData(
			new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		outputDialogButton = new Button(group, SWT.LEFT);
		outputDialogButton.setText(MessageUtil.getString("find"));
		outputDialogButton.addSelectionListener(this);

		label = new Label(group, SWT.RIGHT);
		label.setText(MessageUtil.getString("transform"));
		transformText = new Text(group, SWT.LEFT);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		transformText.setLayoutData(gridData);
		transformDialogButton = new Button(group, SWT.LEFT);
		transformDialogButton.setText(MessageUtil.getString("find"));
		transformDialogButton.addSelectionListener(this);

		label = new Label(group, SWT.RIGHT);
		label.setText(MessageUtil.getString("transform.fileextension"));
		transformedFileExtension = new Text(group, SWT.LEFT);
		gridData = new GridData();
		transformedFileExtension.setLayoutData(
			new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		new Label(group, SWT.RIGHT);

		return composite;
	}

	public void readProperties() throws CoreException {
		inputText.setText(properties.getInput());
		transformText.setText(properties.getStylesheet());
		outputText.setText(properties.getOutput());
		transformedFileExtension.setText(properties.getFileExtension());
	}

	public void writeProperties() throws CoreException {
		properties.setInput(inputText.getText());
		properties.setStylesheet(transformText.getText());
		properties.setOutput(outputText.getText());
		properties.setFileExtension(transformedFileExtension.getText());
	}

	public Control createContents(Composite parent) {
		Control control = buildUI(parent);
		try {
			IAdaptable adaptable = getElement();
			if (adaptable instanceof IProject) {
				properties = new Properties((IProject) adaptable);
				readProperties();
			}
		} catch (CoreException x) {
			PlugInUtil.reportError(
				x,
				PlatformUI
					.getWorkbench()
					.getActiveWorkbenchWindow()
					.getShell());
		}
		return control;
	}

	public boolean performOk() {
		try {
			writeProperties();
		} catch (CoreException x) {
			PlugInUtil.reportError(
				x,
				PlatformUI
					.getWorkbench()
					.getActiveWorkbenchWindow()
					.getShell());
		}
		return super.performOk();
	}

	public void performApply() {
		try {
			writeProperties();
		} catch (CoreException x) {
			PlugInUtil.reportError(
				x,
				PlatformUI
					.getWorkbench()
					.getActiveWorkbenchWindow()
					.getShell());
		}
		super.performApply();
	}

	public void performDefaults() {
		inputText.setText(Constants.DEFAULT_INPUT_DIR);
		transformText.setText(Constants.DEFAULT_XSL_FILE);
		outputText.setText(Constants.DEFAULT_OUTPUT_DIR);
		super.performDefaults();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		IProject project = this.properties.getProject();
		if (e.getSource().equals(transformDialogButton)) {
			if (transformDialog == null) {
				transformDialog = new FileDialog(this.getShell(), SWT.OPEN);
				transformDialog.setText(
					MessageUtil.getString("transformDialog"));
				transformDialog.setFilterExtensions(new String[] { "*.xsl" });
				transformDialog.setFilterPath(
					project.getLocation().toOSString());
			}
			IPath path = new Path(transformDialog.open());
			path =
				path.removeFirstSegments(project.getLocation().segmentCount());
			IResource res = project.findMember(path);
			if (res != null && !(res instanceof IProject)) {
				transformText.setText(
					res.getProjectRelativePath().toOSString());
			} else {
				PlugInUtil.reportError(
					new ConfigurationException(
						MessageUtil.getString("transformDialog.error")),
					this.getShell());
			}
		} else if (e.getSource().equals(inputDialogButton)) {
			if (inputDialog == null) {
				inputDialog = new DirectoryDialog(this.getShell(), SWT.OPEN);
				inputDialog.setText(MessageUtil.getString("inputDialog"));
				inputDialog.setMessage(
					MessageUtil.getString("inputDialogMessage"));
				inputDialog.setFilterPath(project.getLocation().toOSString());
			}
			IPath path = new Path(inputDialog.open());
			path =
				path.removeFirstSegments(project.getLocation().segmentCount());
			IResource res = project.findMember(path);
			if (res != null && !(res instanceof IProject)) {
				inputText.setText(res.getProjectRelativePath().toString());
			} else {
				PlugInUtil.reportError(
					new ConfigurationException(
						MessageUtil.getString("inputDialog.error")),
					this.getShell());
			}
		} else if (e.getSource().equals(outputDialogButton)) {
			if (outputDialog == null) {
				outputDialog = new DirectoryDialog(this.getShell(), SWT.OPEN);
				outputDialog.setText(MessageUtil.getString("outputDialog"));
				outputDialog.setMessage(
					MessageUtil.getString("outputDialogMessage"));
				outputDialog.setFilterPath(project.getLocation().toString());
			}
			IPath path = new Path(outputDialog.open());
			path =
				path.removeFirstSegments(project.getLocation().segmentCount());
			IResource res = project.findMember(path);
			if (res != null && !(res instanceof IProject)) {
				outputText.setText(res.getProjectRelativePath().toString());
			} else {
				PlugInUtil.reportError(
					new ConfigurationException(
						MessageUtil.getString("outputDialog.error")),
					this.getShell());
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}