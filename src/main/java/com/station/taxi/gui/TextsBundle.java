package com.station.taxi.gui;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Wrapper above Resources Bundle with texts
 * @author alex
 */
public class TextsBundle {

	private TextsBundle() {
		// do not instantiate
	}

	private static final String BUNDLE_NAME = "com.station.taxi.gui.TextsBundle"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = loadBundle();
	private static ResourceBundle loadBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME);
	}

	/**
	 * Get string by key
	 * @param key
	 * @return 
	 */
	public static String getString(String key) {
		try {
			ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}
}
