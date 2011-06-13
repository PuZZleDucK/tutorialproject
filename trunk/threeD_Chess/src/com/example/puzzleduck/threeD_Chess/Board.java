package com.example.puzzleduck.threeD_Chess;

import java.util.Stack;

public class Board {
	private Stack MoveStack;
	private Piece[][][] Board; //[Level][Rank][File]
	private Piece[][] Muster;
	private int bwToMove;
	private Piece SQUARE_EMPTY;
	private static Piece SQUARE_INVALID;
	private int[] TitleCount;

	public Board(Piece[][][] board, Piece[][] muster, int[] titleCount) {//[Level][Rank][File]
		Board = board;
		Muster = muster;
		TitleCount = titleCount;
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

	public Piece getPieceAt(int newLevel, int newRank, int newFile) {//[Level][Rank][File]
		// from renderer: [thisLevel][thisRank][thisFile]... forget the rest of this crap:
		//[level][left-right][fwd-bk] -- zNewLevel, yNewRank, xNewFile    //[level][left-right_file][fwd-bk_rank]
		return Board[newLevel][newRank][newFile];
	}

	public void setPieceAt(int newLevel, int newRank, int newFile, Piece object) {
		Board[newLevel][newRank][newFile] = object;

	}
}