/*
 * Created on 10.11.2003
 *
 * 
 */
package de.fhflensburg.hwlanguage.project.build;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import net.sourceforge.jeuclid.DOMMathBuilder;
import net.sourceforge.jeuclid.MathBase;

import org.shetline.io.GIFOutputStream;
import org.w3c.dom.Node;

/**
 * @author user
 *
 * 
 */
public class MathMlImage {

	private MathBase base;

	/**
	 * @param node
	 */
	public MathMlImage(Node node, int fontsize) {
		base = new MathBase(new DOMMathBuilder(node).getMathRootElement(), "Default", Font.PLAIN, fontsize, fontsize);
	}

	public void write(OutputStream stream) throws IOException {
		int width = base.getWidth()+5;
		int height = base.getHeight()+5;

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();

		Color transparency = new Color(78, 91, 234);

		g.setColor(transparency);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);

		base.paint(g);
		try {
			GIFOutputStream.writeGIF(stream, image, GIFOutputStream.ORIGINAL_COLOR, transparency);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
