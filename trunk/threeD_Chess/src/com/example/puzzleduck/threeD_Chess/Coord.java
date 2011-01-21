package com.example.puzzleduck.threeD_Chess;

//typedef struct
//{
//  unsigned
//    xFile  :3,
//    yRank  :3,
//    zLevel :2;
//} Coord;


public class Coord {

	public int xFile, yRank, zLevel;
	
	public int getxFile() {
		return xFile;
	}

	public void setxFile(int xFile) {
		this.xFile = xFile;
	}

	public int getyRank() {
		return yRank;
	}

	public void setyRank(int yRank) {
		this.yRank = yRank;
	}

	public int getzLevel() {
		return zLevel;
	}

	public void setzLevel(int zLevel) {
		this.zLevel = zLevel;
	}

	public Coord()
	{
		xFile = 0;
		yRank = 0;
		zLevel = 0;
	}
	
	
}
