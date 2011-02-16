package com.example.puzzleduck.threeD_Chess;

import java.util.Stack;

public class Board {
	private Stack MoveStack;
	private Piece[][][] Board;
	private Piece[][] Muster;
	private int bwToMove;
	private Piece SQUARE_EMPTY;
	private Piece SQUARE_INVALID;
	private int[] titleCount;

	public Board(Piece[][][] board, Piece[][] muster, int[] titleCount) {
		Board = board;
		Muster = muster;
		this.titleCount = titleCount;
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

	public Piece getSQUARE_INVALID() {
		return SQUARE_INVALID;
	}

	public void setSQUARE_INVALID(Piece sQUARE_INVALID) {
		SQUARE_INVALID = sQUARE_INVALID;
	}

	public int[] getTitleCount() {
		return titleCount;
	}

	public void setTitleCount(int[] titleCount) {
		this.titleCount = titleCount;
	}
}