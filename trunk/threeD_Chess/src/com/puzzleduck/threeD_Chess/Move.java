package com.puzzleduck.threeD_Chess;

public class Move {
	//
	//typedef struct
	//{
	//  Coord xyzBefore;
	public Coord xyzBefore;
	//  Coord xyzAfter;
	public Coord xyzAfter;
	//  Piece *pVictim;
	public Piece pVictim;
	//  /*  Status of bHasMoved before move.  Relevant to Kings, Rooks, and Pawns.
	//   *  TRUE, FALSE, PROMOTE, CASTLE or EnPASSANT. */
	//  int nHadMoved;
	public boolean nHadMoved;
	//} Move;	

	public Move()
	{
		xyzBefore = new Coord();
		xyzAfter = new Coord();
	}


}
