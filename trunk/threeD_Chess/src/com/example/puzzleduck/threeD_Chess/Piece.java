package com.example.puzzleduck.threeD_Chess;

//typedef struct
//{
//  Coord xyzPos;
//  Colour bwSide;
//  Title nName;
//  unsigned bVisible :1,
//           bHasMoved:1; /* For king, rook, pawn only */
//} Piece;


public class Piece {

	public Coord xyzPos;
	public int bwSide;
	public int nName;
	public boolean bVisible;
	public boolean bHasMoved;

	public Piece(int type, int file, int rank, int level, int side) {
//EXAMPLE USAGE
//		Piece blackKing = new Piece(KING, //title/type
//									1, //file
//									1, //rank
//									1, //level
//									BLACK); //side?		
//		Global Piece *
//		PieceNew(const Title nType,
//		         const File x, const Rank y, const Level z,
//		         const Colour col)
//		{
//		  Piece *piece;
//		  piece = (Piece *)malloc(sizeof(Piece));
//		  if (!piece)
//		    return NULL;

//		  piece->xyzPos.xFile = x;
//		  piece->xyzPos.yRank = y;
//		  piece->xyzPos.zLevel = z;
		xyzPos.xFile = file;
		xyzPos.yRank = rank;
		xyzPos.zLevel = level;
//		  piece->nName = nType;
		bwSide = type;
//		  piece->bVisible = TRUE;
		bVisible = true;
//		  piece->bHasMoved = FALSE;
		bHasMoved = false;
	//
//		  return piece;
//		}
		

	}

	

	
	
	
	
}
