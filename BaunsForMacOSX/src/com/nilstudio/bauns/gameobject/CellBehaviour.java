package com.nilstudio.bauns.gameobject;

import java.util.ArrayList;


public interface CellBehaviour {

	public void onStart(Cell cell);
	public void onHit(Cell[][] cells,Cell hitCell,Groove groove,int cellRowCount,int cellColCount);

	public ArrayList<Cell> getlinkCells(Cell[][] cells,ArrayList<Cell> cellList,Cell cell,int cellRowCount,int cellColCount);
}
