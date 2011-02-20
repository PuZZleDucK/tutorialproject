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
	
	public static final int c_king = 0;
	public static final int c_queen = 1;
	public static final int c_bishop = 2;
	public static final int c_knight = 3;
	public static final int c_rook = 4;
	public static final int c_prince = 5;
	public static final int c_princess = 6;
	public static final int c_abbey = 7;
	public static final int c_cannon = 8;
	public static final int c_galley = 9;
	public static final int c_pawn = 10;

	private static char[] pieceChar = {'k','q','b','k','r','p','s','a','c','g','i',' '};

	public Coord xyzPos;
	public int bwSide;
	public int nName;
	public boolean bVisible;
	public boolean bHasMoved;

	static int BLACK = c_queen;
	static int WHITE = c_king;
	static int NOCOL = -c_queen;

	static int king = c_king;
	static int queen = c_queen;
	static int bishop = c_bishop;
	static int knight = c_knight;
	static int rook = c_rook;
	static int prince = c_prince;
	static int princess = c_princess;
	static int abbey = c_abbey;
	static int cannon = c_cannon;
	static int galley = c_galley;
	static int pawn = c_pawn;
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
