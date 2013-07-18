package com.nilstudio.bauns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Resource {

	public static TextureRegion grooveBg = loadTextureRegion("data/shadows.png");
	
	public static TextureRegion ballTextureR = loadTextureRegion("data/ball.png");
	public static TextureRegion greenCellTextureR = loadTextureRegion("data/greenCell.png");
	public static TextureRegion blueCellTextureR= loadTextureRegion("data/blueCell.png");
	public static TextureRegion redCellTextureR = loadTextureRegion("data/redCell.png");
	public static TextureRegion orangeCellTexureR = loadTextureRegion("data/orangeCell.png");
	public static TextureRegion bombCellTexureR = loadTextureRegion("data/bombCell.png");
	
	public static TextureRegionDrawable greenCellTextureD = new TextureRegionDrawable(greenCellTextureR);
	public static TextureRegionDrawable blueCellTextureD= new TextureRegionDrawable(blueCellTextureR);
	public static TextureRegionDrawable redCellTextureRD = new TextureRegionDrawable(redCellTextureR);
	public static TextureRegionDrawable orangeCellTexureD = new TextureRegionDrawable(orangeCellTexureR);
	public static TextureRegionDrawable bombCellTexureD = new TextureRegionDrawable(bombCellTexureR);
	
	public static TextureRegion loadTextureRegion (String file) {
		Texture.setEnforcePotImages(false);
		Texture texture = new Texture(Gdx.files.internal(file));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return new TextureRegion(texture);
	}
}
