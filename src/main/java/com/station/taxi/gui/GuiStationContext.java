package com.station.taxi.gui;

import com.station.taxi.spring.StationContext;
import org.springframework.context.ApplicationContext;

/**
 * Station Context for Swing GUI
 * @author alex
 */
public class GuiStationContext extends StationContext {
	
	public GuiStationContext(ApplicationContext context) {
		super(context);
	}
}
