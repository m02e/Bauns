package com.nilstudio.bauns.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Ball extends Image {

	private boolean isTouch = false ;
	private float startX,startY;
	
	private float toX ,toY;
	private int boundaryStartX , boundaryStartY ;
	private int boundaryWidth , boundaryHeight ;
	
	private float percentX ,percentY;
	
	private boolean isShuted = false ;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();

	private int test_tmp = 0;
	public Ball(TextureRegion textureRegion,int boundaryStartX,int boundaryStartY,int boundaryWidth,int boundaryHeight){
		super(textureRegion);
		this.boundaryStartX = boundaryStartX ; this.boundaryStartY = boundaryStartY;
		this.boundaryWidth = boundaryWidth ;this.boundaryHeight = boundaryHeight;
		setOrigin(getWidth()/2, getHeight()/2);
		init();
	}
	
	private void init(){

	}

	public void setStartPosition(float x,float y){
		startX = x;
		startY = y;
		setTouchPosition(x, y);
	}
	
	public void setTouchPosition(float x,float y){
//		if(x>boundaryStartX && x<boundaryStartX+boundaryWidth 
//				&&y>boundaryStartY && y<boundaryStartY+boundaryHeight
//				&& y<=startY){
//			setPosition(x-getWidth()/2, y);
//		}else{
//			
//		}
		if(x>boundaryStartX && x<boundaryStartX+boundaryWidth){
			setX(x-getWidth()/2);
		}
		if(y>boundaryStartY && y<boundaryStartY+boundaryHeight){
			if(y>=startY){
				y = startY;
			}
			setY(y);
			
		}
		
	}

	public float getStartX() {
		return startX;
	}

	public void reset(){
		setTouchPosition(startX, startY);
		setScale(1f);
		isTouch = false;
		isShuted = false;
		toX = toY = 0;
	}
	
	public float getToX() {
		return toX;
	}

	public float getToY() {
		return toY;
	}

	public void setToGrooveXY(float toX,float toY){
		this.toX = toX;
		this.toY = toY;
	}
	@Override
	public void draw(SpriteBatch batch, float arg1) {
		// TODO Auto-generated method stub
//		if(isTouch){
//			pixmap.drawLine((int)(getX()-getWidth()/2), (int)(getY()-getHeight()/2), (int)toX, (int)toY);
//			batch.draw(texture, (int)(getX()-getWidth()/2), (int)(getY()-getHeight()/2));
//		}
		batch.end();  
		
		if(!isShuted){
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());  
			shapeRenderer.setTransformMatrix(batch.getTransformMatrix());  
	       
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.line(boundaryStartX,boundaryStartY+boundaryHeight , getX()+getWidth()/2,getY()+getHeight()/2);
			shapeRenderer.end();
			
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.line(boundaryStartX+boundaryWidth,boundaryStartY+boundaryHeight , getX()+getWidth()/2,getY()+getHeight()/2);
			shapeRenderer.end();
			
		}
		
		if(isTouch && toY>=getY()){
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.line(toX,toY , getX()+getWidth()/2,getY()+getHeight()/2);
			shapeRenderer.end();
		}

		 batch.begin();  
		super.draw(batch, arg1);
	}

	public float getStartY() {
		return startY;
	}

	public boolean isTouch() {
		return isTouch;
	}

	public void setTouch(boolean isTouch) {
		this.isTouch = isTouch;
	}
	
	public float getPercentOnX(){
		return (getX()+getWidth()/2 - boundaryStartX ) / boundaryWidth;
	}
	
	
	public float getPercentOnY(){
		
		return (startY - getY() ) / (startY-boundaryStartY) ;
	}
	
	public void moveTo(float duration,Runnable endMoveRunnable){
		float x = toX- getWidth()/2;
		float y = toY - getHeight()/2;
		Action firstHalfMoveAction  = Actions.moveTo( (x+getX())/2, (y+getY())/2, duration/2);
		Action firstHalfScaleAction = Actions.scaleTo(1.3f, 1.3f, duration/2);
		
		Action firstHalfAction = Actions.parallel(firstHalfMoveAction, firstHalfScaleAction);
		
		Action lastHalfMoveAction  = Actions.moveTo( x, y, duration/2);
		Action lastHalfScaleAction = Actions.scaleTo(0.5f, 0.5f, duration/2);
		
		Action lastHalfAction = Actions.parallel(lastHalfMoveAction, lastHalfScaleAction);
		Action endAction = null;
		if(endMoveRunnable!=null){
			endAction = Actions.run(endMoveRunnable);
			this.addAction(Actions.sequence(firstHalfAction, lastHalfAction,Actions.scaleTo(0.05f, 0.05f, 0.1f),endAction));
		}else{
			this.addAction(Actions.sequence(firstHalfAction, lastHalfAction,Actions.scaleTo(0.05f, 0.05f, 0.1f)));
		}
		isShuted = true;
		
		
	}
	
	public boolean isContain(float x,float y){
		if(x>getX()-getWidth() && x<getX()+2 *getWidth() && y>getY() - getHeight() && y<getY()+2 * getHeight()){
			return true;
		}
		return false;
	}
	
}
