/*package com.ru.tgra.lab1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ru.tgra.lab1.Lab1Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Njall Hilmar Lab 1"; // or whatever you like
		config.width = 1024;  //experiment with
		config.height = 768;  //the window size

		new LwjglApplication(new Lab1Game(), config);
	}
} */

package com.ru.tgra.lab1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ru.tgra.lab1.LabFirst3DGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Programming Assignment 5"; // or whatever you like
		config.width = 1536;  //experiment with
		config.height = 864;  //the window size
		config.x = 150;
		config.y = 50;


		new LwjglApplication(new LabFirst3DGame(), config);
	}
}
