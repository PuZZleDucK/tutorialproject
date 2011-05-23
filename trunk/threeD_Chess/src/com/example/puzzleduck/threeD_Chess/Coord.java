package com.example.puzzleduck.threeD_Chess;

//typedef struct
//{
//  unsigned
//    xFile  :3,
//    yRank  :3,
//    zLevel :2;
//} Coord;


public class Coord {

	public int thisFile, thisRank, thisLevel;
	
	
	
	public Coord()
	{
		thisFile = 0;
		thisRank = 0;
		thisLevel = 0;
	}
	
	public Coord(int newLevel, int newRank, int newFile)//[thisLevel][thisRank][thisFile]
	{
		thisFile = newFile;
		thisRank = newRank;
		thisLevel = newLevel;
	}
	
	
	
	
	public int getFile() {
		return thisFile;
	}

	public void setFile(int newFile) {
		this.thisFile = newFile;
	}

	public int getRank() {
		return thisRank;
	}

	public void setRank(int newRank) {
		this.thisRank = newRank;
	}

	public int getLevel() {
		return thisLevel;
	}

	public void setLevel(int newLevel) {
		this.thisLevel = newLevel;
	}

	
	
}
