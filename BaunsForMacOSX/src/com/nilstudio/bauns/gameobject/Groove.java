package com.nilstudio.bauns.gameobject;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nilstudio.bauns.Resource;

public class Groove extends Group {

	//最底排定为第一行
	private Cell[][] cells ;
	private float cellWidth = 45;
	private GrooveConfig config = new GrooveConfig();
	
	private Group cellGroup = new Group();
	
	public Groove(GrooveConfig config){
		if(config==null){
			config = new GrooveConfig();
		}else{
			this.config = config;
		}
		this.init();
		setTouchable(Touchable.enabled);
		drop();
		
		Action _action = Actions.run(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int c =0 ;c<Groove.this.config.cellColCount;c++){
					final Cell cell = CellFactory.randomCell();
					addCell(Groove.this.config.cellRowCount-1, c, cell);
				}
				drop();
			}
		});
		Action sq = Actions.sequence(Actions.delay(2), _action);
		addAction(Actions.forever(sq));
	}
	
	public int getCellRowCount(){
		return this.config.cellRowCount;
	}
	
	public int getCellColCount(){
		return this.config.cellColCount;
	}
	
	public float getXWithPercent(float percentX){
		return (getX() + getCellColCount()*cellWidth * (percentX));
	}
	
	public float getYWithPercent(float percentY){
		return (getY() + getCellRowCount()*cellWidth * (percentY));
	}
	
	public int getRowWithY(float y){
		return (int) (( y-getY() ) / cellWidth) ;
	}
	
	public int getColWithX(float x){
		return (int) ((x -getX()) / cellWidth);
	}
	private void init(){
		this.addActor(new Image(Resource.grooveBg));
		addActor(cellGroup);
		cells = new Cell[config.cellRowCount][config.cellColCount];
		
		
//		addCell(0, 0, new Cell(Resource.greenCellTextureR,CellType.GREEN));
//		addCell(3, 5, new Cell(Resource.redCellTextureR,CellType.RED));

//		cells[2][4] = new Cell(Resource.greenCellTexture);
//		cells[2][4].setPosition(getX(4), getY(2));
//		cells[3][4] = new Cell(Resource.greenCellTexture);
//		cells[3][4].setPosition(getX(4), getY(3));
//		
//		cells[6][4] = new Cell(Resource.greenCellTexture);
//		cells[6][4].setPosition(getX(4), getY(6));
		
//		for(int r=0;r<config.cellRowCount;r++){
//			for(int c=0;c<config.cellColCount;c++){
//				if(cells[r][c]!=null){
//					addActor(cells[r][c]);
//				}
//			}
//		}
		
		this.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Actor actor = hit(x, y, true);
//                actor.setVisible(false);
                if(actor!=null && actor instanceof Cell ){
                	Cell cell = (Cell)actor;
                	if(cell.isDropping()){
                		return super.touchDown(event, x, y, pointer, button);
                	}
                    cell.getCellBehaviour().onHit(cells,cell, Groove.this,config.cellRowCount, config.cellColCount);
                    drop();
                }
                
                return super.touchDown(event, x, y, pointer, button);
			}
			
			public void touchDragged(InputEvent event, float x, float y, int pointer){
				super.touchDragged(event, x, y, pointer);
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				return super.mouseMoved(event, x, y);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				super.touchUp(event, x, y, pointer, button);
			}
			
			
		});
	}
	public void hit(int row,int col){
		System.out.println(row+" "+col);
		if( row<0 || row>=getCellRowCount() || col<0 || col>=getCellColCount()){ 
			return ;
		} 
    	if(cells[row][col]==null || cells[row][col].isDropping()){
    		return ;
    	}
    	cells[row][col].getCellBehaviour().onHit(cells,cells[row][col], this,config.cellRowCount, config.cellColCount);
	}
	public void addCell(int row,int col,Cell cell){
		if(row<0 || row>=config.cellRowCount || col<0 || col>=config.cellColCount || cell==null ||cells[row][col]!=null){
			return ;
		}
		
		cells[row][col] = cell;
		cell.setPosition(getX(col), getY(row));
		cell.set(row, col);
		cellGroup.addActor(cell);
	}
	public int getX(int column){
		return (int)((column*cellWidth));
	}
	public int getY(int row){
		return (int)((row*cellWidth));
	}
	
	public int getCellRealX(int col){
		return (int)(getX()+getX(col));
	}
	public int getCellRealY(int row){
		return (int)(getY(row)+getY());
	}
	public synchronized void drop(){
		for(int col = 0 ;col<config.cellColCount;col++){
			int rowIndex = -1;
			for(int row= 0 ;row<config.cellRowCount;row++){
				if(cells[row][col]==null){
					rowIndex = row;
					break;
				}
			}
			float dropDelayTime = 0;
			for(int _r = rowIndex+1;_r<config.cellRowCount;_r++){
				if(cells[_r][col]!=null && rowIndex>=0){
					final Cell cell = cells[_r][col];
//					cell.clearActions();
					Point movePoint = getPoint(rowIndex, col);
					Action delayAction = Actions.delay(dropDelayTime);
					dropDelayTime+=config.dropEachDelayTime;
					Action moveAction = Actions.moveTo(movePoint.x, movePoint.y, config.dropEachCellTime*(cell.getY()/cellWidth-rowIndex));
					Action moveEndAction = Actions.run(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							cell.setDropping(false);
						}
					});
					Action action = Actions.sequence(delayAction, moveAction,moveEndAction);
					cells[rowIndex][col] = cell;
					cell.set(rowIndex, col);
					cells[_r][col] = null;
					rowIndex++;
					cell.setDropping(true);
					cell.addAction(action);
				}
			}
		}
		
	}
	
	
	public Point getPoint(int row,int col){
		return new Point(getX(col),(int)(getY(row)));
	}
	
	
	public static class GrooveConfig{
		private int cellRowCount = 10;
		private int cellColCount = 9;
		private float dropEachCellTime = 0.07f;
		private float dropEachDelayTime = 0.002f;
//		private float cellTextureWidth = 150;
	}
	
	public static class Point{
		private int x,y;
		public Point(int x,int y){
			this.x = x;
			this.y = y;
		}
	}
}
