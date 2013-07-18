package com.nilstudio.bauns.gameobject;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nilstudio.bauns.Resource;

public class CellFactory {

	public enum CellType{
		GREEN,RED,ORANGE,BLUE,BOMB,BLINK
	}
	public static int BombCellPower = 4;
	public static int BombCreateRate = 50 ;
	private static int cellCreateCount = 0 ;
	
	public static int BlinkCellPower = 30 ;
	/**
	 * @param args
	 */
	
	public static Cell randomCell(){
		Random random = new Random();
		int number = random.nextInt(4);
		
		TextureRegion tRegion  =null;
		CellType type = null;
		CellBehaviour behaviour = null;
		switch (number) {
		case 0:
			tRegion = Resource.greenCellTextureR;
			type = CellType.GREEN;
			behaviour = getColorCellBehaviour();
			break;
		case 1:
			tRegion = Resource.blueCellTextureR;
			type = CellType.BLUE;
			behaviour = getColorCellBehaviour();
			break;
		case 2:
			tRegion = Resource.orangeCellTexureR;
			type = CellType.ORANGE;
			behaviour = getColorCellBehaviour();
			break;
		case 3:
			tRegion = Resource.redCellTextureR;
			type = CellType.RED;
			behaviour = getColorCellBehaviour();
			break;
		default:
			break;
		}
		if(cellCreateCount++%BombCreateRate==BombCreateRate-1){
			if(random.nextBoolean()){
				tRegion = Resource.greenCellTextureR;
				type = CellType.BLINK;
				behaviour = getBlinkCellBehaviour();
			}else{
				tRegion = Resource.bombCellTexureR;
				type = CellType.BOMB;
				behaviour = getBombCellBehaviour();
			}
		}
		final Cell cell = new Cell(tRegion, type);
		cell.setCellBehaviour(behaviour);
		return cell;
	}
	
	public static CellBehaviour getColorCellBehaviour(){
		CellBehaviour behaviour  = new CellBehaviour() {
			
			@Override
			public void onHit(Cell[][] cells,Cell cell, Groove groove, 
					int cellRowCount,int cellColCount) {
				
				float duration = 0.2f ;
				cell.setHasSearch(true);
				cell.removeFromGroove(cells, groove, duration);
				
				int cellRow = cell.getRow();
				int cellCol = cell.getCol();
				if(cellRow>0){
					if(cells[cellRow-1][cellCol]!=null 
							&& cells[cellRow-1][cellCol].getType()==cell.getType() 
							&& !cells[cellRow-1][cellCol].isDropping()
							&& !cells[cellRow-1][cellCol].isHasSearch()){
						cells[cellRow-1][cellCol].getCellBehaviour().onHit(cells, cells[cellRow-1][cellCol],groove,cellRowCount, cellColCount);
					}
				}
				if(cellRow<cellRowCount-1){
					if(cells[cellRow+1][cellCol]!=null 
							&& cells[cellRow+1][cellCol].getType()==cell.getType() 
							&& !cells[cellRow+1][cellCol].isDropping()
							&& !cells[cellRow+1][cellCol].isHasSearch()){
						cells[cellRow+1][cellCol].getCellBehaviour().onHit(cells, cells[cellRow+1][cellCol],groove, cellRowCount, cellColCount);
					}
				}
				if(cellCol>0){
					if(cells[cellRow][cellCol-1]!=null 
							&& cells[cellRow][cellCol-1].getType()==cell.getType() 
							&& !cells[cellRow][cellCol-1].isDropping()
							&& !cells[cellRow][cellCol-1].isHasSearch()){
						cells[cellRow][cellCol-1].getCellBehaviour().onHit(cells,cells[cellRow][cellCol-1], groove, cellRowCount, cellColCount);
					}
				}
				if(cellCol<cellColCount-1){
					if(cells[cellRow][cellCol+1]!=null 
							&& cells[cellRow][cellCol+1].getType()==cell.getType() 
							&& !cells[cellRow][cellCol+1].isDropping()
							&& !cells[cellRow][cellCol+1].isHasSearch()){
						cells[cellRow][cellCol+1].getCellBehaviour().onHit(cells,cells[cellRow][cellCol+1], groove, cellRowCount, cellColCount);
					}
				}
			}

			@Override
			public ArrayList<Cell> getlinkCells(Cell[][] cells,
					ArrayList<Cell> cellList,Cell cell, int cellRowCount, int cellColCount) {
				// TODO Auto-generated method stub
				return cellList;
			}

			@Override
			public void onStart(Cell cell) {
				// TODO Auto-generated method stub
				
			}
		};
		return behaviour;
	}

	public static CellBehaviour getBombCellBehaviour(){
		CellBehaviour behaviour = new CellBehaviour() {
			
			@Override
			public void onHit(Cell[][] cells, Cell hitCell, Groove groove,
					int cellRowCount, int cellColCount) {
				// TODO Auto-generated method stub
				float duration = 0.3f ;
				hitCell.setHasSearch(true);
				hitCell.removeFromGroove(cells, groove, duration);
				
				int cellRow = hitCell.getRow();
				int cellCol = hitCell.getCol();
				for(int r = cellRow-BombCellPower/2;r<cellRowCount && r<=cellRow+BombCellPower/2;r++){
					r = r<0 ? 0:r;
					for(int c = cellCol-BombCellPower/2;c<cellColCount && c<=cellCol+BombCellPower/2;c++){
						c = c<0 ? 0:c ;
						if(cells[r][c]!=null && !cells[r][c].isDropping()){
							cells[r][c].removeFromGroove(cells, groove, duration);
						}
					}
				}
			}

			@Override
			public ArrayList<Cell> getlinkCells(Cell[][] cells,
					ArrayList<Cell> cellList, Cell cell,int cellRowCount, int cellColCount) {
				// TODO Auto-generated method stub
				return cellList;
			}

			@Override
			public void onStart(Cell cell) {
				// TODO Auto-generated method stub
				
			}

		};
		
		return behaviour;
	}
	
	public static CellBehaviour getBlinkCellBehaviour(){
		CellBehaviour behaviour = new CellBehaviour() {
			private  TextureRegionDrawable[] drawables  = new TextureRegionDrawable[4];
			
			@Override
			public void onHit(Cell[][] cells, Cell hitCell, Groove groove,
					int cellRowCount, int cellColCount) {
				// TODO Auto-generated method stub
				ArrayList<Cell> cellList = new ArrayList<Cell>();
				getlinkCells(cells, cellList, hitCell, cellRowCount, cellColCount);
				int blinkCellPower = BlinkCellPower;
				
				for(Cell c:cellList){
					c.setHasSearch(false);
					if(blinkCellPower<=0){
						continue;
					}
					c.clearActions();
					c.change(Resource.greenCellTextureD, CellType.GREEN);
					c.setCellBehaviour(getColorCellBehaviour());
					blinkCellPower--;
					
				}
			}

			@Override
			public ArrayList<Cell> getlinkCells(Cell[][] cells,
					ArrayList<Cell> cellList, Cell cell,int cellRowCount, int cellColCount) {
				// TODO Auto-generated method stub
				if(cell==null){
					return cellList;
				}
				int cellRow = cell.getRow();
				int cellCol = cell.getCol();
				cell.setHasSearch(true);
				cellList.add(cell);
				if(cellRow>0){
					if(cells[cellRow-1][cellCol]!=null 
							&& !cells[cellRow-1][cellCol].isDropping()
							&& !cells[cellRow-1][cellCol].isHasSearch()){
						getlinkCells(cells, cellList, cells[cellRow-1][cellCol], cellRowCount, cellColCount);
					}
				}
				if(cellRow<cellRowCount-1){
					if(cells[cellRow+1][cellCol]!=null 
							&& !cells[cellRow+1][cellCol].isDropping()
							&& !cells[cellRow+1][cellCol].isHasSearch()){
						getlinkCells(cells, cellList, cells[cellRow+1][cellCol], cellRowCount, cellColCount);
					}
				}
				if(cellCol>0){
					if(cells[cellRow][cellCol-1]!=null 
							&& !cells[cellRow][cellCol-1].isDropping()
							&& !cells[cellRow][cellCol-1].isHasSearch()){
						getlinkCells(cells, cellList, cells[cellRow][cellCol-1], cellRowCount, cellColCount);
					}
				}
				if(cellCol<cellColCount-1){
					if(cells[cellRow][cellCol+1]!=null 
							&& !cells[cellRow][cellCol+1].isDropping()
							&& !cells[cellRow][cellCol+1].isHasSearch()){
						getlinkCells(cells, cellList, cells[cellRow][cellCol+1], cellRowCount, cellColCount);
					}
				}
				return cellList;
			}

			@Override
			public void onStart(final Cell cell) {
				// TODO Auto-generated method stub
				System.out.println("create blink");
				drawables[0] = Resource.greenCellTextureD;
				drawables[1] = Resource.blueCellTextureD;
				drawables[2] = Resource.orangeCellTexureD;
				drawables[3] = Resource.redCellTextureRD;
				Action perFrameEndAction = Actions.run(new Runnable() {
					private int drawableIndex=0;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						drawableIndex++;
						drawableIndex %=drawables.length;
						cell.setDrawable(drawables[drawableIndex]);
					}
				});
				
				Action perFrameDelayAction = Actions.delay(0.03f);
				Action perFrameAction = Actions.sequence(perFrameDelayAction,perFrameEndAction) ;
				
				Action playAction = Actions.forever(perFrameAction);
				cell.addAction(playAction);
			}

		};
		return behaviour;
	}
}
