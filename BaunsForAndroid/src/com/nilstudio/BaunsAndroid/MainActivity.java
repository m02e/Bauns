package com.nilstudio.BaunsAndroid;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nilstudio.bauns.BaunsGame;

import android.os.Bundle;

public class MainActivity extends AndroidApplication {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;
		config.useGL20 = true;
		
		initialize(new BaunsGame(), config);
	}


}
