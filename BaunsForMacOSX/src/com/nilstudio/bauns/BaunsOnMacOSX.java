package com.nilstudio.bauns;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class BaunsOnMacOSX {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		float scaleValue = 1.4f;
		new LwjglApplication(new BaunsGame(), "BaunsForMacOSX - MOZE", (int)(480/scaleValue), (int)(800/scaleValue), false);
	}

}
