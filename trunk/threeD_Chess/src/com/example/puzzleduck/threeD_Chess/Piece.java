package com.example.puzzleduck.threeD_Chess;

import java.util.Stack;

public class Piece {

	public static final int c_select = -1; //used for signaling selected square
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
	public boolean bHasMoved;/* For king, rook, pawn only */
//	public boolean target;

	static int BLACK = 1;
	static int WHITE = 0;
	static int NOCOL = -1;

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

	public Piece (int type, int file, int rank, int level, int side) {
//EXAMPLE USAGE
//		Piece blackKing = new Piece(KING, //title/type
//									1, //file
//									1, //rank
//									1, //level
//									BLACK); //side?		
		xyzPos = new Coord();
		
		xyzPos.setxFile(file);
		xyzPos.setyRank(rank);
		xyzPos.setzLevel(level);
		nName = type;
		bwSide = side;
		bVisible = true;
		bHasMoved = false;
//		target = false;
	}

	public int getColor() {
		return bwSide;
		
	}

	public char getTypeChar() {
		return pieceChar[nName];
	}

	public void moveDown() {
		if(xyzPos.yRank < 7 )
		{
			xyzPos.yRank+=1;
		}else
		if(xyzPos.zLevel < 2)
		{
			xyzPos.yRank = 0;
			xyzPos.zLevel += 1;
		}
	}

	public void moveUp() {
		if(xyzPos.yRank > 0 )
		{
			xyzPos.yRank-=1;
		}else
		if(xyzPos.zLevel > 0)
		{
			xyzPos.yRank = 7;
			xyzPos.zLevel -= 1;
		}
	}

	public void moveLeft() {
		if(xyzPos.xFile > 0 )
		{
			xyzPos.xFile-=1;
		}
		
	}

	public void moveRight() {
		if(xyzPos.xFile < 7 )
		{
			xyzPos.xFile+=1;
		}
		
	}

	public Stack FindAllMoves(Board board) {
		// TODO Auto-generated method stub
		
//		/* Creates a stack of legal moves that this piece can take in this go */
		//This is where i need to update interface to shade/highlight possible moves to improve UI
//		Stack FindAllMoves(Piece piece)
//		{

		
		
		
		
		
		Stack moves;
		  Move move = new Move();
		  int bwEnemy;
		  int x, y, z;

			int CURX = (this.xyzPos.xFile);
			int CURY = (this.xyzPos.yRank);
			int CURZ = (this.xyzPos.zLevel);

		  moves = threeD_Chess.StackNew();
//		  if (moves == null)
//		  {
//		    return null;
//		  }

		  bwEnemy = ((this.bwSide == Piece.WHITE) ? Piece.BLACK : Piece.WHITE);
		  move.xyzBefore = this.xyzPos;

		  if (this.nName == Piece.knight)
		    {
		      for (y = threeD_Chess.MAX(0, CURY -2); y < threeD_Chess.MIN(threeD_Chess.RANKS, CURY +3); y++)
		        {
		          if (y == CURY)
		            continue;
		          for (x = threeD_Chess.MAX(0, CURX -2); x < threeD_Chess.MIN(threeD_Chess.FILES, CURX +3); x++)
		            {
		              if (x == CURX)
		                continue;

		              if (threeD_Chess.ABS(CURX-x) == threeD_Chess.ABS(CURY-y))
		                continue;
		              if ((board.getBoard()[CURZ][y][x] == threeD_Chess.NULL) || (board.getBoard()[CURZ][y][x].bwSide == bwEnemy))
		                {
		            	  move.xyzAfter = new Coord(x, y, CURZ);
//		                  move.xyzAfter.xFile = x;
//		                  move.xyzAfter.yRank = y;
//		                  move.xyzAfter.zLevel = CURZ;
		                  move.pVictim = board.getBoard()[CURZ][y][x];
		                  move.nHadMoved = this.bHasMoved;

//		                  if (!FakeMoveAndIsKingChecked( this, x, y, CURZ ))
			              if (!this.FakeMoveAndIsKingChecked( board, x, y, CURZ ))
		                  {
//			            	  threeD_Chess.StackPush(moves, move);
		                  }
		                } /* End valid move */
		            } /* End x loop */
		        } /* End y loop */
		    } /* End knight */
//		  else if (this.nName == Piece.cannon)
//		    {
//		      for (z = 0; z < threeD_Chess.LEVELS; z++)
//		        {
//		          if (z == CURZ)
//		            continue;
//
//		          for (y = threeD_Chess.MAX( 0, CURY -3 ); y < threeD_Chess.MIN( threeD_Chess.RANKS, CURY +4 ); y++)
//		            {
//		              if (y == CURY)
//		                continue;
//
//		              for (x = threeD_Chess.MAX( 0, CURX -3 ); x < threeD_Chess.MIN( threeD_Chess.FILES, CURX +4 ); x++)
//		                {
//		                  if (x == CURX)
//		                    continue;
//
//		                  if ((threeD_Chess.ABS(CURX-x) == threeD_Chess.ABS(CURY-y)) ||
//		                      (threeD_Chess.ABS(CURX-x) == threeD_Chess.ABS(CURZ-z)) ||
//		                      (threeD_Chess.ABS(CURY-y) == threeD_Chess.ABS(CURZ-z)))
//		                    continue;
//
//		                  if ((board.getBoard()[z][y][x] == threeD_Chess.NULL) || (board.getBoard()[z][y][x].bwSide == bwEnemy))
//		                    {
//		                      move.xyzAfter.xFile = x;
//		                      move.xyzAfter.yRank = y;
//		                      move.xyzAfter.zLevel = z;
//		                      move.pVictim = board.getBoard()[z][y][x];
//		                      move.nHadMoved = this.bHasMoved;
//
//		                      if (!this.FakeMoveAndIsKingChecked( board, x, y, z ))
//		                      {
//		                    	  threeD_Chess.StackPush(moves, move);
//		                      }
//		                    } /* End valid move */
//		                } /* End x loop */
//		            } /* End y loop */
//		        } /* End z loop */
//		    } /* End cannon */
//		  else if (this.nName == Piece.pawn) /* Don't bother searching for en passant */
//		    {
//		      y = ((this.bwSide == Piece.WHITE) ? 1 : -1);
//
//		      for (x = threeD_Chess.MAX(0, CURX-1); x < threeD_Chess.MIN(threeD_Chess.FILES, CURX+2); ++x)
//		        {
//		          /* Due to the complexity of the conditional this time,
//		           * I've opted for aborting when illegal instead of
//		           * proceeding when legal. */
//		          if ((x == CURX) && (board.getBoard()[CURZ][CURY+y][CURX] != threeD_Chess.NULL))
//		            continue;
//
//		          if ( (x != CURX) && 
//		        		  ((board.getBoard()[CURZ][CURY + y][x] == threeD_Chess.NULL) || ((board.getBoard()[CURZ][CURY + y][x]).bwSide != bwEnemy)) )
//		          {
//		            continue;
//		          }
//		          move.xyzAfter.xFile = x;
//		          move.xyzAfter.yRank = CURY + y;
//		          move.xyzAfter.zLevel = CURZ;
//		          move.pVictim = board.getBoard()[CURZ][CURY + y][x];
//		          move.nHadMoved = this.bHasMoved;
//
//		          if (!this.FakeMoveAndIsKingChecked(board, x, CURY+y, CURZ))
//		          {
//		        	  threeD_Chess.StackPush(moves, move);
//		          }
////		          /* This next conditional is for the two-forward move:
////		           * it only happens when the previous attempt was the one-forward
////		           * move and makes assumptions based on that fact. */
//		          if ( (x==CURX) && (this.bHasMoved == threeD_Chess.FALSE) && (board.getBoard()[CURZ][CURY + y+y][CURX] == threeD_Chess.NULL) )
//		            {
//		              move.xyzAfter.yRank += y;
//		              if (!this.FakeMoveAndIsKingChecked(board, CURX, CURY+y+y, CURZ))
//		            	  threeD_Chess.StackPush(moves, move);
//		            }
//		        } /* End x loop */
//		    } /* End pawn */
//		  else
//		    {
//		      int d, dist;
//		      Piece pEncountered;
//
//		      /*
//		       * The king and prince can only move one square;
//		       * all others can move MAX(FILES,RANKS)-1.
//		       * For a regular board, this is 7.  (Not 8: If you moved 8
//		       * in any direction you would be off the edge of the board)
//		       */
//		      if ((this.nName == Piece.king) || (this.nName == Piece.prince))
//		        dist = 1;
//		      else
//		        dist = threeD_Chess.MAX(threeD_Chess.FILES, threeD_Chess.RANKS) -1;
//
//		      for (z = -1; z <= 1; ++z)
//		      {
//		       /*
//		        * Cater for pieces that can't change level.
//		        */
//			       if (((this.nName == Piece.prince) || (this.nName == Piece.princess) || (this.nName == Piece.abbey)
//			    		   || (this.nName == Piece.galley)) && (z != 0))
//		         continue;
//
//		       for (y = -1; y <= 1; ++y)
//		       {
//		         for (x = -1; x <= 1; ++x)
//		         {
//		           if ((x==0) && (y==0) && (z==0))
//		             continue;
//
//		           /*
//		            * Cater for the pieces that can only move
//		            * horizontally/vertically.
//		            */
//		           if (((this.nName == Piece.rook) || (this.nName == Piece.galley)) && !threeD_Chess.HORZ(x, y))
//		             continue;
////		           /*
////		            * Cater for the pieces that can only move
////		            * diagonally.
////		            */
//		           else if (((this.nName == Piece.bishop) || (this.nName == Piece.abbey)) && !threeD_Chess.DIAG(x, y))
//		             continue;
//
//		           for (d = 1; d <= dist; ++d)
//		           {
////			             pEncountered = TraverseDir(this, x, y, z, d);
//			             pEncountered = this.TraverseDir(board, x, y, z, d);
//		             if (this.IsMoveLegal(board, pEncountered))
//		               {
//		                 move.xyzAfter = pEncountered.xyzPos;
//		                 move.pVictim = pEncountered;
//		                 move.nHadMoved = this.bHasMoved;
//
//		                 /* Check for putting own king in check */
//		                 if (!this.FakeMoveAndIsKingChecked(board,
//		                		 pEncountered.xyzPos.xFile, pEncountered.xyzPos.yRank, pEncountered.xyzPos.zLevel))
//		                	 threeD_Chess.StackPush(moves, move);
//		               }
//
//		             if (pEncountered != board.getSQUARE_EMPTY())
//		               break; /* No point on continuing in this direction if
//		                       * we've hit a piece or the edge of the board.. */
//		           } /* End d loop */
//		         } /* End x loop */
//		       } /* End y loop */
//		     } /* End z loop */
//		    }


//		  return moves;
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  return null;
		  
		  
		  
//		}

		
	}

	boolean IsMoveLegal(Board board, Piece defender) {

//		private boolean IsMoveLegal(Piece attacker, Piece defender)
//		{
			if (defender == board.getSQUARE_EMPTY())
				return threeD_Chess.TRUE;
			  if (defender == board.getSQUARE_INVALID())
			    {
				  threeD_Chess.n3DcErr = threeD_Chess.E3DcSIMPLE;
			      return threeD_Chess.FALSE;
			    }
			  else if ( this.bwSide == defender.bwSide )
			    {
				  threeD_Chess.n3DcErr = threeD_Chess.E3DcBLOCK;
			      return threeD_Chess.FALSE;
			    }
			return true;
//		}
		
		
//		return false;
	}

	Piece TraverseDir(Board board, int xDir, int yDir, int zDir, int new_d) {

//		
//		private Piece TraverseDir(Piece piece, int xDir, int yDir, int zDir, int dist)
//		{
		  int x, y, z, d = 0;

//		  /* Most move at least one in a real direction */
		  if ((new_d == 0) || ((xDir == 0) && (yDir == 0) && (zDir == 0)))
		    {
		      Board.getSQUARE_INVALID().xyzPos.xFile = threeD_Chess.UINT_MAX;
		      Board.getSQUARE_INVALID().xyzPos.yRank = threeD_Chess.UINT_MAX;
		      Board.getSQUARE_INVALID().xyzPos.zLevel = threeD_Chess.UINT_MAX;

		      return Board.getSQUARE_INVALID();
		    }

		  if ((this.nName != Piece.knight) && (this.nName != Piece.cannon))
		    {
//		      /* Make all directions be 1, -1 or 0 */
		      if (xDir != 0) xDir /= threeD_Chess.ABS(xDir);
		      if (yDir != 0) yDir /= threeD_Chess.ABS(yDir);
		      if (zDir != 0) zDir /= threeD_Chess.ABS(zDir);
		    }
		  else
			  new_d = 1;
			  x = this.xyzPos.xFile;
			  y = this.xyzPos.yRank;
			  z = this.xyzPos.zLevel;
		  do{
		      x += xDir;
		      y += yDir;
		      z += zDir;

		      if (!((x >= 0) && (y >= 0) && (z >= 0) && (x < threeD_Chess.FILES) && (y < threeD_Chess.RANKS) && (z < threeD_Chess.LEVELS)))
		        {
		          Board.getSQUARE_INVALID().xyzPos.xFile = x;
		          Board.getSQUARE_INVALID().xyzPos.yRank = y;
		          Board.getSQUARE_INVALID().xyzPos.zLevel = z;
		          return Board.getSQUARE_INVALID();
		        }

		      if (board.getBoard()[z][y][x] != null)
		        {
		          if (board.getBoard()[z][y][x].bwSide == this.bwSide)
		            {
		              Board.getSQUARE_INVALID().xyzPos.xFile = x;
		              Board.getSQUARE_INVALID().xyzPos.yRank = y;
		              Board.getSQUARE_INVALID().xyzPos.zLevel = z;
		              return Board.getSQUARE_INVALID();
		            }
		          else
		          {
		            return board.getBoard()[z][y][x];
		          }
		        }
		    } while (++d < new_d);

//		  /*
//		   * At this point, because we haven't returned, we know these things:
//		   *  We have not encountered another piece.
//		   *  We have moved dist spaces.
//		   */
		  if ((x >= 0) && (y >= 0) && (z >= 0) && (z < threeD_Chess.LEVELS) && (y < threeD_Chess.RANKS) && (x < threeD_Chess.FILES))
		    {
//		      /* Valid (empty) square */
		      board.getSQUARE_EMPTY().xyzPos.xFile = x;
		      board.getSQUARE_EMPTY().xyzPos.yRank = y;
		      board.getSQUARE_EMPTY().xyzPos.zLevel = z;

		      return board.getSQUARE_EMPTY();
		    }

//		   * We fell off the board. Go back one place to the last valid
//		   * location.
		  Board.getSQUARE_INVALID().xyzPos.xFile = x - xDir;
		  Board.getSQUARE_INVALID().xyzPos.yRank = y - yDir;
		  Board.getSQUARE_INVALID().xyzPos.zLevel = z - zDir;

		  return Board.getSQUARE_INVALID();
//		}
		
		
		
		
		
//		return null;
	}

	boolean FakeMoveAndIsKingChecked(Board board, int x, int y, int z) {
		
//		/* Check move re. putting own king in check */
//		public Boolean FakeMoveAndIsKingChecked( Piece piece, int x, int y, int z)
//		{
			  Piece temp;
		  Boolean retVal = false;
		  Coord xyz;

		  xyz = this.xyzPos;
		  temp = board.getBoard()[z][y][x];
		  if ( temp != threeD_Chess.NULL )
		  {
			    temp.bVisible = threeD_Chess.FALSE;
		  }
		  board.getBoard()[z][y][x] = this;
		  board.getBoard()[xyz.zLevel][xyz.yRank][xyz.xFile] = null;

		  if (this.nName == Piece.king)
		    {
//		      /* We're moving the king, so it's xyzPos may not be accurate.
//		       * check manually. */
		      retVal = (SquareThreatened( board, (this.bwSide == Piece.WHITE) ? Piece.BLACK : Piece.WHITE, x, y, z ) != threeD_Chess.NULL) ;
		    }
		  else
		  {
			  //removed to trace bug
//			    retVal = IsKingChecked(board, this.bwSide);
		  }

		  board.getBoard()[z][y][x] = temp;
		  if ( temp != threeD_Chess.NULL )
		  {
			    temp.bVisible = threeD_Chess.TRUE;
		  }
		  board.getBoard()[xyz.zLevel][xyz.yRank][xyz.xFile] = this;

		  return retVal;
//		}	
		
		
		//return false;
	}

	
	
	
	
Boolean IsKingChecked(Board board, int bwSide) {

//	 * Return TRUE if the king is checked in the current board layout.
//	public Boolean IsKingChecked( int bwSide )
//	{
//	  Coord xyz = board.getMuster()[ bwSide ][ MusterIdx(board, Piece.king, 0 ) ].xyzPos;
	  Coord xyz = board.getMuster()[ bwSide ][ MusterIdx(board, Piece.king, 0 ) ].xyzPos;

	  boolean retVal = false;
	  int side = (bwSide == Piece.WHITE) ? Piece.BLACK : Piece.WHITE;
	  retVal = SquareThreatened(board, side, xyz.xFile, xyz.yRank, xyz.zLevel ) != threeD_Chess.NULL;
	  
	  
	  return ( retVal );
//	}
	
//		return null;
	}

//	/*
//	 * Returns a pointer to any one piece of the specified colour threatening
//	 * the mentioned square.  Will return NULL if the square is not
//	 * threatened.
//	 */

	static int MusterIdx(Board board, int title, int thisCount) {
//		private int MusterIdx(int title, int thisCount)
//		{
		  int i, count = 0;
//		  for (i = 0; i != name && i < TITLES; ++i)
			  for (i = 0; i != title && i < threeD_Chess.getTITLES(); ++i)
			    count += board.getTitleCount()[i];
			
			  if (i == threeD_Chess.getTITLES())
			    return 47; /* 47 is a hack; it is a legal array index that is only
			                * valid for pawns */
			
//			  if (nth < titleCount[name])
				  if (thisCount < board.getTitleCount()[title])
			    {
				      return count + thisCount;
//				      return count + nth;
			    }
			  /* else */
			  return 47;
//		}
//		
//		
//		
//	return 0;
}

	public Piece SquareThreatened(Board board, int bwSide, int xFile, int yRank, int zLevel)
	{
	  int pieceIdx;

	  for (pieceIdx = 0; pieceIdx < threeD_Chess.PIECES; ++pieceIdx)
	    {
	      if (board.getMuster()[bwSide][pieceIdx].bVisible && board.getMuster()[bwSide][pieceIdx].PieceMayMove( board, xFile, yRank, zLevel ))
	        return board.getMuster()[bwSide][pieceIdx];
	    }

	  return null;
	}

	boolean PieceMayMove(Board board, int xNew, int yNew, int zNew) {

		
//		
//		private boolean PieceMayMove(Piece piece, int xNew, int yNew, int zNew)
//		{
			  boolean retval;

			  if (!this.bVisible)
		    {
				  threeD_Chess.n3DcErr = threeD_Chess.E3DcINVIS;
		      return threeD_Chess.FALSE;
		    }

//		  /* Do bits which are the same for all pieces first */
		  if (xNew == this.xyzPos.xFile &&
		      yNew == this.xyzPos.yRank &&
		      zNew == this.xyzPos.zLevel)
		    {
			  threeD_Chess.n3DcErr = threeD_Chess.E3DcSIMPLE;
		      return threeD_Chess.FALSE;//can't move to same spot
		    }

		  if ((board.getBoard()[zNew][yNew][xNew] != threeD_Chess.NULL) &&
		      (board.getBoard()[zNew][yNew][xNew].bVisible == threeD_Chess.TRUE) &&
		      (board.getBoard()[zNew][yNew][xNew].bwSide == this.bwSide))
		    {
			  threeD_Chess.n3DcErr = threeD_Chess.E3DcBLOCK;
		      return threeD_Chess.FALSE;  /* Can't take a piece on your team */
		    }
	      	return threeD_Chess.FALSE;  /* if all else fails... Fail!*/

//		}
//
//		
//		
//		
//		
//		return false;
	}
	
	
	
	
	
	
	
	
}
