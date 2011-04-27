package com.example.puzzleduck.threeD_Chess;

import java.util.Stack;

public class Board {
	private Stack MoveStack;
	private Piece[][][] Board;
	boolean[][][] targets;
	private Piece[][] Muster;
	private int bwToMove;
	private Piece SQUARE_EMPTY;
	private static Piece SQUARE_INVALID;
	private int[] TitleCount;

	public Board(Piece[][][] board, Piece[][] muster, int[] titleCount) {
		Board = board;
		Muster = muster;
		TitleCount = titleCount;
		
		targets = new boolean[threeD_Chess.LEVELS][threeD_Chess.RANKS][threeD_Chess.FILES];
		
//		for(int i = --threeD_Chess.LEVELS; i >= 0; i--)
//		{
//			for(int j = --threeD_Chess.RANKS; j >= 0; j--)
//			{
//				for(int k = --threeD_Chess.FILES; k >= 0; k--)
//				{
//					targets[i][j][k] = false;
//				}
//			}
//		}
	}

	public Stack getMoveStack() {
		return MoveStack;
	}

	public void setMoveStack(Stack moveStack) {
		MoveStack = moveStack;
	}

	public Piece[][][] getBoard() {
		return Board;
	}

	public void setBoard(Piece[][][] board) {
		Board = board;
	}

	public Piece[][] getMuster() {
		return Muster;
	}

	public void setMuster(Piece[][] muster) {
		Muster = muster;
	}

	public int getBwToMove() {
		return bwToMove;
	}

	public void setBwToMove(int bwToMove) {
		this.bwToMove = bwToMove;
	}

	public Piece getSQUARE_EMPTY() {
		return SQUARE_EMPTY;
	}

	public void setSQUARE_EMPTY(Piece sQUARE_EMPTY) {
		SQUARE_EMPTY = sQUARE_EMPTY;
	}

	public static Piece getSQUARE_INVALID() {
		return SQUARE_INVALID;
	}

	public void setSQUARE_INVALID(Piece sQUARE_INVALID) {
		SQUARE_INVALID = sQUARE_INVALID;
	}

	public int[] getTitleCount() {
		return TitleCount;
	}

	public void setTitleCount(int[] titleCount) {
		TitleCount = titleCount;
	}

	public Piece getPieceAt(int i, int j, int k) {
		// TODO Auto-generated method stub
		return Board[i][j][k];
	}

	public void setTarget(int xFile, int yRank, int zLevel, boolean on) {
		targets[xFile][yRank][zLevel] = on;
		
	}
}