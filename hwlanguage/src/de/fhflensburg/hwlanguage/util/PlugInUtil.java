/*
 * Created on 09.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.util;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;

import de.fhflensburg.hwlanguage.Constants;

/**
 * @author user
 * provides useful helpermethods
 * 
 */
public class PlugInUtil {
	/**
	 * Create an IStatus object from an exception.
	 * @param x exception to process
	 * @return IStatus status object for the above exception
	 */
	public static IStatus getStatus(Exception x) {
		if (x instanceof CoreException) {
			return ((CoreException) x).getStatus();
		} else {
			return new Status(
				IStatus.ERROR,
				Constants.PLUGIN_ID,
				IStatus.ERROR,
				x.getMessage() != null ? x.getMessage() : x.toString(),
				x);
		}
	}

	/**
	 * Displays an error that occured during the project creation.
	 * @param x details on the error
	 * @param shell
	 */
	public static void reportError(Exception x,Shell shell) {
		ErrorDialog.openError(
			shell,
			MessageUtil.getString("dialogtitle"),
			MessageUtil.getString("projecterror"),
			getStatus(x));
	}
}
