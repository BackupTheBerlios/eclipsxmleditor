/*
 * Created on 09.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.util;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author user
 *
 * 
 */
public class ResourceUtil {

	public static IPath subtract(IPath path1, IPath path2) {
		if (path1.isPrefixOf(path2)) {
			IPath temp = path1;
			path1 = path2;
			path2 = temp;
		} else if (!path2.isPrefixOf(path1)) {
			throw new IllegalArgumentException(
				path1.toString() + " is no prefix of " + path2.toString() + " and vice versa");
		}
		return path1.removeFirstSegments(path2.segmentCount());
	}

	/**
	 * Helper method: it recursively creates a folder path.
	 * @param folder
	 * @param monitor
	 * @throws CoreException
	 * @see java.io.File#mkdirs()
	 */
	public static void createFolderHelper(IFolder folder, IProgressMonitor monitor) throws CoreException {
		if (!folder.exists()) {
			IContainer parent = folder.getParent();
			if (parent instanceof IFolder && (!((IFolder) parent).exists()))
				createFolderHelper((IFolder) parent, monitor);
			folder.create(false, true, monitor);
		}
	}
}
