package de.fhflensburg.hwlanguage.project;

import org.eclipse.core.runtime.*;
import org.eclipse.core.resources.*;

import de.fhflensburg.hwlanguage.Constants;

/**
 * @author user
 */
public class Nature implements IProjectNature {
	private IProject project;

	public void configure() throws CoreException {
		IProjectDescription description = project.getDescription();
		if (!hasBuildSpec(description.getBuildSpec())) {
			ICommand[] old = description.getBuildSpec(),
				specs = new ICommand[old.length + 1];
			System.arraycopy(old, 0, specs, 0, old.length);
			ICommand command = description.newCommand();
			command.setBuilderName(Constants.BUILDER_ID);
			specs[old.length] = command;
			description.setBuildSpec(specs);
			project.setDescription(description, new NullProgressMonitor());
		}
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	private boolean hasBuildSpec(ICommand[] commands) {
		return getBuildSpecCount(commands) != 0;
	}

	private int getBuildSpecCount(ICommand[] commands) {
		int count = 0;
		for (int i = 0; i < commands.length; i++)
			if (commands[i].getBuilderName().equals(Constants.BUILDER_ID))
				count++;
		return count;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 */
	public void deconfigure() throws CoreException {
	}
}