/*
 * Created on 09.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.exception;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import de.fhflensburg.hwlanguage.Constants;

/**
 * @author user
 *
 * 
 */
public class BuildException extends CoreException{

	/**
	 * @param status
	 */
	public BuildException(IStatus status) {
		super(status);
	}
	
	public BuildException(String message) {
		super(new Status(IStatus.ERROR,
		Constants.PLUGIN_ID,
		IStatus.ERROR,message,null));
	}

}
