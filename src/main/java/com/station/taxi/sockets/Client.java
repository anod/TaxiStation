/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

/**
 *
 * @author alex
 */
public interface Client {
	
	public boolean connect();
	
	public void close();

	public void readInput();
}
