/*
 * Created on 09.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.util;

import org.eclipse.core.runtime.IPath;

/**
 * @author user
 *
 * 
 */
public class PathUtil {

	public static IPath subtract(IPath path1,IPath path2) {
		if (path1.isPrefixOf(path2)) {
			IPath temp = path1;
			path1 = path2;
			path2 = temp;
		} else if (!path2.isPrefixOf(path1)) {
			throw new IllegalArgumentException(path1.toString()+" is no prefix of "+path2.toString()+" and vice versa");
		}
		return path1.removeFirstSegments(path2.segmentCount());
	}
	
}
