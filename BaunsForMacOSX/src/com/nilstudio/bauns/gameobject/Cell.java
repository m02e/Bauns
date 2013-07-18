package com.nilstudio.bauns.gameobject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nilstudio.bauns.gameobject.CellFactory.CellType;

public class Cell extends Image {

	private float frameTime = 0.1f;
	private int row,col ;
	private boolean isDropping = false ;
	private CellBehaviour cellBehaviour = null;
	public static float scaleValue = 1f;
	private CellType type = null;
	private boolean hasSearch = false ;

	public Cell(TextureRegion textureRegion,CellType type){
		super(textureRegion);
		this.setScale(scaleValue);
		setAlign(Align.center);
		setOrigin(getWidth()/2, getHeight()/2);
		this.type = type;
		
	}
	
	public void set(int row,int col){
		this.row = row;
		this.col = col;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	
	public boolean isHasSearch() {
		return hasSearch;
	}

	public void setHasSearch(boolean hasSearch) {
		this.hasSearch = hasSearch;
	}

	public CellType getType() {
		return type;
	}

	public void setType(CellType type) {
		this.type = type;
	}

	public CellBehaviour getCellBehaviour() {
		return this.cellBehaviour;
	}

	public void setCellBehaviour(CellBehaviour cellBehaviour) {
		this.cellBehaviour = cellBehaviour;
		if(cellBehaviour!=null){
			cellBehaviour.onStart(this);
		}
	}
	
	public boolean isDropping() {
		return isDropping;
	}

	public void setDropping(boolean isDropping) {
		this.isDropping = isDropping;
	}

	public void playAnimates(final TextureRegionDrawable[] drawables){
		this.clearActions();
		Action perFrameEndAction = Actions.run(new Runnable() {
			private int drawableIndex=0;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				drawableIndex++;
				drawableIndex %=drawables.length;
				setDrawable(drawables[drawableIndex]);
			}
		});
		
		Action perFrameDelayAction = Actions.delay(frameTime);
		Action perFrameAction = Actions.sequence(perFrameDelayAction,perFrameEndAction) ;
		
		Action playAction = Actions.forever(perFrameAction);
		this.addAction(playAction);
	}
	
	
	public void removeFromGroove(final Cell[][] cells,final Groove groove,float duration){
		setTouchable(Touchable.disabled);
		this.clearActions();
		
		Action scaleAction = Actions.scaleTo(0.1f, 0.1f,duration);
		Action endAction = Actions.run(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Cell.this.remove();
				cells[row][col] = null;
				groove.drop();
			}
		});
		this.addAction(Actions.sequence(scaleAction, endAction));
		
	}
	
	public void onHit(Cell[][] cells,Groove groove,int cellRowCount,int cellColCount){
		
			Cell cell = this;
//			cell.hasSearch = true;
//			int cellRow = cell.getRow();
//			int cellCol = cell.getCol();
//			if(cellRow>0){
//				if(cells[cellRow-1][cellCol]!=null && cells[cellRow-1][cellCol].getType()==cell.getType() && !cells[cellRow-1][cellCol].hasSearch){
//					cells[cellRow-1][cellCol].onHit(cells, groove, cellRowCount, cellColCount);
//				}
//			}
//			if(cellRow<cellRowCount-1){
//				if(cells[cellRow+1][cellCol]!=null && cells[cellRow+1][cellCol].getType()==cell.getType() && !cells[cellRow+1][cellCol].hasSearch){
//					cells[cellRow+1][cellCol].onHit(cells, groove, cellRowCount, cellColCount);
//					System.out.println(cellRow-1+" "+cellCol);
//				}
//			}
//			if(cellCol>0){
//				if(cells[cellRow][cellCol-1]!=null && cells[cellRow][cellCol-1].getType()==cell.getType() && !cells[cellRow][cellCol-1].hasSearch){
//					cells[cellRow][cellCol-1].onHit(cells, groove, cellRowCount, cellColCount);
//					System.out.println(cellRow-1+" "+cellCol);
//				}
//			}
//			if(cellCol<cellColCount-1){
//				if(cells[cellRow][cellCol+1]!=null && cells[cellRow][cellCol+1].getType()==cell.getType() && !cells[cellRow][cellCol+1].hasSearch){
//					cells[cellRow][cellCol+1].onHit(cells, groove, cellRowCount, cellColCount);
//					System.out.println(cellRow-1+" "+cellCol);
//				}
//			}
			cell.removeFromGroove(cells, groove, 0.3f);
	}
	public void change(TextureRegionDrawable drawable,CellType type){
		setDrawable(drawable);
		setType(type);
	}
	public void onEliminate(Groove groove){
		groove.drop();
	}
	
}
