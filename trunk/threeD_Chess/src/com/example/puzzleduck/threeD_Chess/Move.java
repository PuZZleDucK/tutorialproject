package com.example.puzzleduck.threeD_Chess;

public class Move {
	//
	//typedef struct
	//{
	//  Coord xyzBefore;
	private Coord xyzBefore;
	//  Coord xyzAfter;
	private Coord xyzAfter;
	//  Piece *pVictim;
	private Piece pVictim;
	//  /*  Status of bHasMoved before move.  Relevant to Kings, Rooks, and Pawns.
	//   *  TRUE, FALSE, PROMOTE, CASTLE or EnPASSANT. */
	//  int nHadMoved;
	private boolean nHadMoved;
	//} Move;	
	
	public Move()
	{
		
		
	}

	
}
