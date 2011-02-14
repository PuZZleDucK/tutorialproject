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
	
	private static char[] pieceChar = {'k','q','b','k','r','p','s','a','c','g','i',' '};

	public Coord xyzPos;
	public int bwSide;
	public int nName;
	public boolean bVisible;
	public boolean bHasMoved;

	static int BLACK = 1;
	static int WHITE = 0;
	static int NOCOL = -1;

	static int king = 0;
	static int queen = 1;
	static int bishop = 2;
	static int knight = 3;
	static int rook = 4;
	static int prince = 5;
	static int princess = 6;
	static int abbey = 7;
	static int cannon = 8;
	static int galley = 9;
	static int pawn = 10;
	static int none = 11;

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
		xyzPos = new Coord();
		
		xyzPos.setxFile(file);
		xyzPos.setyRank(rank);
		xyzPos.setzLevel(level);
//		  piece->nName = nType;
		nName = type;
		bwSide = side;
//		  piece->bVisible = TRUE;
		bVisible = true;
//		  piece->bHasMoved = FALSE;
		bHasMoved = false;
	//
//		  return piece;
//		}
		

	}

	public int getColor() {
		return bwSide;
		
	}

	public char getTypeChar() {
		return pieceChar[nName];
	}

	

	
	
	
	
}
