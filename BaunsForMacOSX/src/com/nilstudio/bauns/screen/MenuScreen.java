package com.nilstudio.bauns.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nilstudio.bauns.BaunsGame;
import com.nilstudio.bauns.BaunsGameConfig;
import com.nilstudio.bauns.Resource;
import com.nilstudio.bauns.gameobject.Ball;
import com.nilstudio.bauns.gameobject.Groove;

public class MenuScreen implements Screen {

	private Texture texture = BaunsGame.loadTexture("data/background.png");
	private Stage stage = new Stage(BaunsGameConfig.SCREEN_WIDTH, BaunsGameConfig.SCREEN_HEIGHT, true);
	
	private Groove groove = new Groove(null);
	private Ball ball = new Ball(Resource.ballTextureR,30,10,420,200);
	
	public MenuScreen(){
		init();
		Gdx.input.setInputProcessor(stage);
	}
	private void init(){
		Image background = new Image(texture);
		background.setPosition(0, 0);
		stage.addActor(background);
		
		groove.setPosition(36, 250);
		
		stage.addActor(groove);
		stage.addActor(ball);
		ball.setTouchable(Touchable.disabled);
		ball.setStartPosition(240, 150);
		final Runnable onBallShutEnd = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				groove.hit(groove.getRowWithY(ball.getToY()), groove.getColWithX(ball.getToX()));
				ball.reset();
			}
			
		};
		stage.addListener(new InputListener(){

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				// TODO Auto-generated method stub
				if(ball.isTouch()){
					ball.setTouchPosition(x, y);
					float toX  = groove.getXWithPercent(1-ball.getPercentOnX());
					float toY = groove.getYWithPercent(ball.getPercentOnY());
					ball.setToGrooveXY(toX,toY);
				}
				super.touchDragged(event, x, y, pointer);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				ball.setTouch(false);
				ball.moveTo(0.5f, onBallShutEnd);
				super.touchUp(event, x, y, pointer, button);
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				if(ball.isContain(x, y)){
					ball.setTouch(true);
					return true;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	private static int betweenConvert(float value){
		if((int)value< value){
			return (int)(value+1);
		}
		return (int)value;
	}
	
//	public static void main(String[] arg){
//		float f = 0f;
//		System.out.println(betweenConvert(f));
//	}
}
