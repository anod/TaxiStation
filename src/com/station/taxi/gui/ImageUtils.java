package com.station.taxi.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageUtils {

	private static final String IMAGE_PATH = "/com/station/taxi/gui/resources/";
	private static final String IMAGE_EXT = ".png";
	
	public static ImageIcon createImageIcon(String name) {
		Image image = createImage(name);
		return new ImageIcon(image);
	}

	public static Image createImage(String name) {
		if (name == null || name.isEmpty()) {
			throw new RuntimeException("name is not valid");
		}
		URL url = ImageUtils.class.getResource(IMAGE_PATH+name+IMAGE_EXT);
		return Toolkit.getDefaultToolkit().createImage(url);	
	}
}
