package de.fhflensburg.hwlanguage.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

import de.fhflensburg.hwlanguage.Constants;

/**
 * @author user
 */
public class Properties {

	/**
	* property qualified name for the source directory
	*/
	public static final QualifiedName INPUT_PROPERTY_NAME =
		new QualifiedName(Constants.PROPERTY_NAMESPACE, "source");

	/**
		* property qualified name for the output directory
		*/
	public static final QualifiedName OUTPUT_PROPERTY_NAME =
		new QualifiedName(Constants.PROPERTY_NAMESPACE, "output");

	/**
		* property qualified name for the stylesheet directory
		*/
	public static final QualifiedName XSL_PROPERTY_NAME =
		new QualifiedName(Constants.PROPERTY_NAMESPACE, "stylesheet");

	/**
		* property qualified name for the transformedfileextension
		*/
	public static final QualifiedName FILEEXTENSION_PROPERTY_NAME =
		new QualifiedName(Constants.PROPERTY_NAMESPACE, "fileextension");

	private IProject project;

	public Properties(IProject project) {
		this.project = project;
	}

	public String getInput() throws CoreException {
		return getProperty(INPUT_PROPERTY_NAME, Constants.DEFAULT_INPUT_DIR);
	}

	public String getStylesheet() throws CoreException {
		return getProperty(XSL_PROPERTY_NAME, Constants.DEFAULT_XSL_FILE);
	}

	public String getOutput() throws CoreException {
		return getProperty(OUTPUT_PROPERTY_NAME, Constants.DEFAULT_OUTPUT_DIR);
	}

	public void setInput(String input) throws CoreException {
		setProperty(INPUT_PROPERTY_NAME, input, Constants.DEFAULT_INPUT_DIR);
	}

	public String getFileExtension() throws CoreException {
		return getProperty(FILEEXTENSION_PROPERTY_NAME, Constants.DEFAULT_FILEEXTENSION);
	}

	public void setFileExtension(String extension) throws CoreException {
		setProperty(FILEEXTENSION_PROPERTY_NAME, extension, Constants.DEFAULT_FILEEXTENSION);
	}

	public void setStylesheet(String rules) throws CoreException {
		setProperty(XSL_PROPERTY_NAME, rules, Constants.DEFAULT_XSL_FILE);
	}

	public void setOutput(String output) throws CoreException {
		setProperty(OUTPUT_PROPERTY_NAME, output, Constants.DEFAULT_OUTPUT_DIR);
	}

	protected String getProperty(QualifiedName key, String def)
		throws CoreException {
		String value = project.getPersistentProperty(key);
		if (value == null || value.length() == 0)
			return def;
		else
			return value;
	}

	protected void setProperty(QualifiedName key, String value, String def)
		throws CoreException {
		if (value != null && value.length() != 0) {
			if (value.equals(def))
				project.setPersistentProperty(key, null);
			else
				project.setPersistentProperty(key, value);
		}
	}
	/**
	 * @return
	 */
	public IProject getProject() {
		return project;
	}

}