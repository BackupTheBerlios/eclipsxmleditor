
package de.fhflensburg.hwlanguage.util;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;

import de.fhflensburg.hwlanguage.HwLanguagePlugin;
/**
 * Convenience class for storing references to image descriptors
 * used by the readme tool.
 */
public class ImageUtil {
	static final URL BASE_URL =
		HwLanguagePlugin.getPlugIn().getDescriptor().getInstallURL();
	public static final ImageDescriptor README_WIZARD_BANNER;

	static {
		String iconPath = "icons/"; 
		String prefix = iconPath ; 
		README_WIZARD_BANNER = createImageDescriptor(prefix + "sample.gif");
	}
	/**
	 * Utility method to create an <code>ImageDescriptor</code>
	 * from a path to a file.
	 */
	private static ImageDescriptor createImageDescriptor(String path) {
		ImageDescriptor imageDesc = null;
		try {
			URL url = new URL(BASE_URL, path);
			imageDesc = ImageDescriptor.createFromURL(url);
		} catch (Exception e) {
			imageDesc = ImageDescriptor.getMissingImageDescriptor();
		}
		return imageDesc;
	}
}
