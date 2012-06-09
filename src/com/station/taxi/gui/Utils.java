package com.station.taxi.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

public class Utils {

    private static final String TEXTS_BUNDLE = "com.station.taxi.gui.TextsBundle";
	private static final String IMAGES_PATH = "../images/";

    
    public static ResourceBundle getTextResourceBundle() {
    	return ResourceBundle.getBundle(TEXTS_BUNDLE, Locale.getDefault());
    }
    
	public static ImageIcon createImageIcon(String name) {
    	Image image = getImage(name);
    	return new ImageIcon(image);
    }
    
    private static Image getImage(String name) {
    	if (name == null || name.isEmpty()) {
    		throw new RuntimeException("name is not valid");
    	}
        return Toolkit.getDefaultToolkit().createImage(IMAGES_PATH+name);	
    }
}
