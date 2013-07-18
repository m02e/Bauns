package com.nilstudio.bauns;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nilstudio.bauns.screen.MenuScreen;

public class BaunsGame extends Game{

	public static SpriteBatch BATCHER ;
	@Override
	public void create() {
		// TODO Auto-generated method stub
		Texture.setEnforcePotImages(false);
		BATCHER = new SpriteBatch();
		System.out.println("run");
		this.setScreen(new MenuScreen());
	}

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
}
