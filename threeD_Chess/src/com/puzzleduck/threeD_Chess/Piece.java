package com.puzzleduck.threeD_Chess;

import java.util.Stack;

import android.content.Context;
import android.graphics.drawable.Drawable;

//import com.example.puzzleduck.threeD_Chess.Move;

public class Piece {

	private static final int TITLES = 11;
	protected static final int selectionIndicator = -1; //used for signaling selected square
	protected static final int king = 0;
	protected static final int queen = 1;
	protected static final int bishop = 2;
	protected static final int knight = 3;
	protected static final int rook = 4;
	protected static final int prince = 5;
	protected static final int princess = 6;
	protected static final int abbey = 7;
	protected static final int cannon = 8;
	protected static final int galley = 9;
	protected static final int pawn = 10;
	protected static int none = 11;
	
	static int BLACK = 1;
	static int WHITE = 0;
	static int NOCOL = -1;

	private static char[] pieceChar = {'k','q','b','k','r','p','s','a','c','g','i',' '};

	public Coord xyzPos;
	public int thisSide;
	public int thisType;
	public boolean bVisible;
	public boolean bHasMoved;/* For king, rook, pawn only */
	private Drawable thisDrawable;


//	static int king = c_king;
//	static int queen = c_queen;
//	static int bishop = c_bishop;
//	static int knight = c_knight;
//	static int rook = c_rook;
//	static int prince = c_prince;
//	static int princess = c_princess;
//	static int abbey = c_abbey;
//	static int cannon = c_cannon;
//	static int galley = c_galley;
//	static int pawn = c_pawn;

	public Piece (int type, int file, int rank, int level, int side, Context context) {
		//EXAMPLE USAGE
		//		Piece blackKing = new Piece(KING, //title/type
		//									1, //file
		//									1, //rank
		//									1, //level
		//									BLACK); //side?		
		//		xyzPos = new Coord();//int newLevel, int newRank, int newFile
		//		xyzPos.setFile(file);
		//		xyzPos.setRank(rank);
		//		xyzPos.setLevel(level);
		xyzPos = new Coord(level, rank, file);

		thisType = type;
		thisSide = side;
		bVisible = true;
		bHasMoved = false;
		
		//switch on type :D
		thisDrawable = context.getResources().getDrawable( R.drawable.k_king);

	}

	public int getColor() {
		return thisSide;

	}

	public char getTypeChar() {
		return pieceChar[thisType];
	}

	public void moveDown() {
		if(xyzPos.thisRank < 7 )
		{
			xyzPos.thisRank+=1;
		}else
			if(xyzPos.thisLevel < 2)
			{
				xyzPos.thisRank = 0;
				xyzPos.thisLevel += 1;
			}
	}

	public void moveUp() {
		if(xyzPos.thisRank > 0 )
		{
			xyzPos.thisRank-=1;
		}else
			if(xyzPos.thisLevel > 0)
			{
				xyzPos.thisRank = 7;
				xyzPos.thisLevel -= 1;
			}
	}

	public void moveLeft() {
		if(xyzPos.thisFile > 0 )
		{
			xyzPos.thisFile-=1;
		}

	}

	public void moveRight() {
		if(xyzPos.thisFile < 7 )
		{
			xyzPos.thisFile+=1;
		}

	}

	public Stack<Move> FindAllMoves(Board board) {

		//		/* Creates a stack of legal moves that this piece can take in this go */
		//This is where i will flag to shade/highlight possible moves during draw

		Stack<Move> allPossibleMoves = new Stack<Move>();
		Move currentMove = new Move();
		int enemyColor;
		int currentLevel, currentRank, currentFile = 0;

		int originFile = (this.xyzPos.thisFile);
		int originRank = (this.xyzPos.thisRank);
		int originLevel = (this.xyzPos.thisLevel);
		enemyColor = ((this.thisSide == Piece.WHITE) ? Piece.BLACK : Piece.WHITE);

		if (this.thisType == Piece.knight)
		{
			for (currentFile = 0; currentFile < ThreeD_ChessActivity.LEVELS; currentFile++)
			{
//				if (currentFile == originLevel) // probably should be f or l not a mix of both
				if (currentFile == originFile)
					continue;

				for (currentRank = ThreeD_ChessActivity.MAX( 0, originRank -3 ); currentRank < ThreeD_ChessActivity.MIN( ThreeD_ChessActivity.RANKS, originRank +4 ); currentRank++)
				{
					if (currentRank == originRank)
						continue;

					for (currentLevel = ThreeD_ChessActivity.MAX( 0, originFile -3 ); currentLevel < ThreeD_ChessActivity.MIN( ThreeD_ChessActivity.FILES, originFile +4 ); currentLevel++)
					{
//						if (currentLevel == originFile)
						if (currentLevel == originLevel)
							continue;

						if (ThreeD_ChessActivity.ABS(originFile-currentLevel) == ThreeD_ChessActivity.ABS(originRank-currentRank))
							continue;
						if ((board.getBoard()[originLevel][currentRank][currentLevel] == null) || (board.getBoard()[originLevel][currentRank][currentLevel].thisSide == enemyColor))
						{
							currentMove.xyzAfter.setFile(currentFile);
							//		                  move.xyzAfter.xFile = x;
							currentMove.xyzAfter.setRank(currentRank);
							//		                  move.xyzAfter.yRank = y;
							currentMove.xyzAfter.setLevel(originLevel);
							//		                  move.xyzAfter.zLevel = CURZ;
							currentMove.pVictim = board.getBoard()[originLevel][currentRank][currentLevel];
							currentMove.nHadMoved = this.bHasMoved;

							//		                  if (!FakeMoveAndIsKingChecked( this, x, y, CURZ ))
							if (!this.FakeMoveAndIsKingChecked( board, currentLevel, currentRank, originLevel ))
							{
								//			            	  ThreeD_ChessActivity.StackPush(moves, move);
								allPossibleMoves.push(currentMove);

							}
						} /* End valid move */
					} /* End x loop */
				} /* End y loop */
			} /* End z loop */
		} /* End knight */
		else if (this.thisType == Piece.cannon)
		{
			for (currentFile = 0; currentFile < ThreeD_ChessActivity.LEVELS; currentFile++)
			{
//				if (currentFile == originLevel)
				if (currentFile == originFile)
					continue;

				for (currentRank = ThreeD_ChessActivity.MAX( 0, originRank -3 ); currentRank < ThreeD_ChessActivity.MIN( ThreeD_ChessActivity.RANKS, originRank +4 ); currentRank++)
				{
					if (currentRank == originRank)
						continue;

					for (currentLevel = ThreeD_ChessActivity.MAX( 0, originFile -3 ); currentLevel < ThreeD_ChessActivity.MIN( ThreeD_ChessActivity.FILES, originFile +4 ); currentLevel++)
					{
//						if (currentLevel == originFile)
						if (currentLevel == originLevel)
							continue;

						if ((ThreeD_ChessActivity.ABS(originFile-currentLevel) == ThreeD_ChessActivity.ABS(originRank-currentRank)) ||
								(ThreeD_ChessActivity.ABS(originFile-currentLevel) == ThreeD_ChessActivity.ABS(originLevel-currentFile)) ||
								(ThreeD_ChessActivity.ABS(originRank-currentRank) == ThreeD_ChessActivity.ABS(originLevel-currentFile)))
							continue;

						if ((board.getBoard()[currentFile][currentRank][currentLevel] == ThreeD_ChessActivity.NULL) || (board.getBoard()[currentFile][currentRank][currentLevel].thisSide == enemyColor))
						{
							currentMove.xyzAfter.thisFile = currentLevel;
							currentMove.xyzAfter.thisRank = currentRank;
							currentMove.xyzAfter.thisLevel = currentFile;
							currentMove.pVictim = board.getBoard()[currentFile][currentRank][currentLevel];
							currentMove.nHadMoved = this.bHasMoved;

							if (!this.FakeMoveAndIsKingChecked( board, currentLevel, currentRank, currentFile ))
							{
								//		                    	  ThreeD_ChessActivity.StackPush(moves, move);
								allPossibleMoves.push(currentMove);
							}
						} /* End valid move */
					} /* End x loop */
				} /* End y loop */
			} /* End z loop */
		} /* End cannon */
		else if (this.thisType == Piece.pawn) /* Don't bother searching for en passant */
		{
			//			  not sure what this was meant to do... removing:   ohhh, got it now moving in positive dir for white and negative for black
			//		      currentY_Rank = ((this.bwSide == Piece.WHITE) ? 1 : -1);

			//not sure why starting rank is origin - 3... because I cut and pasted from elsewhere...
			//
			//		      for (currentY_Rank = ThreeD_ChessActivity.MAX( 0, originY_Rank -3 ); currentY_Rank < ThreeD_ChessActivity.MIN( ThreeD_ChessActivity.RANKS, originY_Rank +4 ); currentY_Rank++)

			//			    y = ((this.bwSide == Piece.WHITE) ? 1 : -1);

			for (currentRank = ThreeD_ChessActivity.MAX(0, originRank); currentRank < ThreeD_ChessActivity.MIN(ThreeD_ChessActivity.FILES, originRank+2); ++currentRank)
			{
				/* Due to the complexity of the conditional this time,
				 * I've opted for aborting when illegal instead of
				 * proceeding when legal. */
				//abort: 
				if ((currentRank == originRank) && (board.getBoard()[originLevel][originRank][currentFile] != null))
					continue;

				//something fishy here!!!
				if ( (currentFile != originFile) && 
						((board.getBoard()[originLevel][originRank][currentFile] == null) || ((board.getBoard()[originLevel][originRank][currentFile]).thisSide != enemyColor)) )
				{
					continue;
				}
				currentMove.xyzAfter.thisFile = currentFile;
				currentMove.xyzAfter.thisRank = originRank;
				currentMove.xyzAfter.thisLevel = originLevel;
				currentMove.pVictim = board.getBoard()[originLevel][originRank][currentFile];
				currentMove.nHadMoved = this.bHasMoved;

				if (!this.FakeMoveAndIsKingChecked(board, currentFile, originRank, originLevel))
				{
					//		        	  ThreeD_ChessActivity.StackPush(moves, move);
					allPossibleMoves.push(currentMove);
				}
				//		          /* This next conditional is for the two-forward move:
				//		           * it only happens when the previous attempt was the one-forward
				//		           * move and makes assumptions based on that fact. */
				if ( (currentFile==originFile) && (this.bHasMoved == ThreeD_ChessActivity.FALSE) && (board.getBoard()[originLevel][originRank][currentFile] == ThreeD_ChessActivity.NULL) )
				{
					//		              currentMove.xyzAfter.yRank += currentY_Rank;
					currentMove.xyzAfter.thisRank += 1; //should be color dependant... + for player - for enemy
					if (!this.FakeMoveAndIsKingChecked(board, currentFile, originRank, originLevel))
						//		            	  ThreeD_ChessActivity.StackPush(moves, move);
						allPossibleMoves.push(currentMove);
				}
			} /* End x loop */
		} /* End pawn */
		else
		{
			int d, dist;
			Piece pEncountered;

			/*
			 * The king and prince can only move one square;
			 * all others can move MAX(FILES,RANKS)-1.
			 * For a regular board, this is 7.  (Not 8: If you moved 8
			 * in any direction you would be off the edge of the board)
			 */
			if ((this.thisType == Piece.king) || (this.thisType == Piece.prince))
				dist = 1;
			else
				dist = ThreeD_ChessActivity.MAX(ThreeD_ChessActivity.FILES, ThreeD_ChessActivity.RANKS) -1;

			for (currentFile = -1; currentFile <= 1; ++currentFile)
			{
				/*
				 * Cater for pieces that can't change level.
				 */
				if (((this.thisType == Piece.prince) || (this.thisType == Piece.princess) || (this.thisType == Piece.abbey)
						|| (this.thisType == Piece.galley)) && (currentFile != 0))
					continue;

				for (currentRank = -1; currentRank <= 1; ++currentRank)
				{
					for (currentLevel = -1; currentLevel <= 1; ++currentLevel)
					{
						if ((currentLevel==0) && (currentRank==0) && (currentFile==0))
							continue;

						/*
						 * Cater for the pieces that can only move
						 * horizontally/vertically.
						 */
						if (((this.thisType == Piece.rook) || (this.thisType == Piece.galley)) && !ThreeD_ChessActivity.HORZ(currentLevel, currentRank))
							continue;
						//		           /*
						//		            * Cater for the pieces that can only move
						//		            * diagonally.
						//		            */
						else if (((this.thisType == Piece.bishop) || (this.thisType == Piece.abbey)) && !ThreeD_ChessActivity.DIAG(currentLevel, currentRank))
							continue;

						for (d = 1; d <= dist; ++d)
						{
							//			             pEncountered = TraverseDir(this, x, y, z, d);
							pEncountered = this.TraverseDir(board, currentLevel, currentRank, currentFile, d);
							if (this.IsMoveLegal(board, pEncountered))
							{
								currentMove.xyzAfter = pEncountered.xyzPos;
								currentMove.pVictim = pEncountered;
								currentMove.nHadMoved = this.bHasMoved;

								/* Check for putting own king in check */
								if (!this.FakeMoveAndIsKingChecked(board,
										pEncountered.xyzPos.thisFile, pEncountered.xyzPos.thisRank, pEncountered.xyzPos.thisLevel))
									//		                	 ThreeD_ChessActivity.StackPush(moves, move);
									allPossibleMoves.push(currentMove);
							}

							if (pEncountered != board.getSQUARE_EMPTY())
								break; /* No point on continuing in this direction if
								 * we've hit a piece or the edge of the board.. */
						} /* End d loop */
					} /* End x loop */
				} /* End y loop */
			} /* End z loop */
		}

		return allPossibleMoves;
		//		}


	}

	boolean IsMoveLegal(Board board, Piece defender) {

		//		private boolean IsMoveLegal(Piece attacker, Piece defender)
		//		{
		if (defender == board.getSQUARE_EMPTY())
			return ThreeD_ChessActivity.TRUE;
		if (defender == Board.getSQUARE_INVALID())
		{
			ThreeD_ChessActivity.n3DcErr = ThreeD_ChessActivity.E3DcSIMPLE;
			return ThreeD_ChessActivity.FALSE;
		}
		else if ( this.thisSide == defender.thisSide )
		{
			ThreeD_ChessActivity.n3DcErr = ThreeD_ChessActivity.E3DcBLOCK;
			return ThreeD_ChessActivity.FALSE;
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
			Board.getSQUARE_INVALID().xyzPos.thisFile = ThreeD_ChessActivity.UINT_MAX;
			Board.getSQUARE_INVALID().xyzPos.thisRank = ThreeD_ChessActivity.UINT_MAX;
			Board.getSQUARE_INVALID().xyzPos.thisLevel = ThreeD_ChessActivity.UINT_MAX;

			return Board.getSQUARE_INVALID();
		}

		if ((this.thisType != Piece.knight) && (this.thisType != Piece.cannon))
		{
			//		      /* Make all directions be 1, -1 or 0 */
			if (xDir != 0) xDir /= ThreeD_ChessActivity.ABS(xDir);
			if (yDir != 0) yDir /= ThreeD_ChessActivity.ABS(yDir);
			if (zDir != 0) zDir /= ThreeD_ChessActivity.ABS(zDir);
		}
		else
			new_d = 1;
		x = this.xyzPos.thisFile;
		y = this.xyzPos.thisRank;
		z = this.xyzPos.thisLevel;
		do{
			x += xDir;
			y += yDir;
			z += zDir;

			if (!((x >= 0) && (y >= 0) && (z >= 0) && (x < ThreeD_ChessActivity.FILES) && (y < ThreeD_ChessActivity.RANKS) && (z < ThreeD_ChessActivity.LEVELS)))
			{
				Board.getSQUARE_INVALID().xyzPos.thisFile = x;
				Board.getSQUARE_INVALID().xyzPos.thisRank = y;
				Board.getSQUARE_INVALID().xyzPos.thisLevel = z;
				return Board.getSQUARE_INVALID();
			}

			if (board.getBoard()[z][y][x] != null)
			{
				if (board.getBoard()[z][y][x].thisSide == this.thisSide)
				{
					Board.getSQUARE_INVALID().xyzPos.thisFile = x;
					Board.getSQUARE_INVALID().xyzPos.thisRank = y;
					Board.getSQUARE_INVALID().xyzPos.thisLevel = z;
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
		if ((x >= 0) && (y >= 0) && (z >= 0) && (z < ThreeD_ChessActivity.LEVELS) && (y < ThreeD_ChessActivity.RANKS) && (x < ThreeD_ChessActivity.FILES))
		{
			//		      /* Valid (empty) square */
			board.getSQUARE_EMPTY().xyzPos.thisFile = x;
			board.getSQUARE_EMPTY().xyzPos.thisRank = y;
			board.getSQUARE_EMPTY().xyzPos.thisLevel = z;

			return board.getSQUARE_EMPTY();
		}

		//		   * We fell off the board. Go back one place to the last valid
		//		   * location.
		Board.getSQUARE_INVALID().xyzPos.thisFile = x - xDir;
		Board.getSQUARE_INVALID().xyzPos.thisRank = y - yDir;
		Board.getSQUARE_INVALID().xyzPos.thisLevel = z - zDir;

		return Board.getSQUARE_INVALID();
		//		}



		//		return null;
	}

	boolean FakeMoveAndIsKingChecked(Board board, int x, int y, int z) {

		//		/* Check move re. putting own king in check */
		//		public Boolean FakeMoveAndIsKingChecked( Piece piece, int x, int y, int z)
		//		{
		Piece temp;
		Boolean retVal;
		Coord xyz;

		xyz = this.xyzPos;
		temp = board.getBoard()[z][y][x];
		if ( temp != ThreeD_ChessActivity.NULL )
		{
			temp.bVisible = ThreeD_ChessActivity.FALSE;
		}
		board.getBoard()[z][y][x] = this;
		board.getBoard()[xyz.thisLevel][xyz.thisRank][xyz.thisFile] = null;

		if (this.thisType == Piece.king)
		{
			//		      /* We're moving the king, so it's xyzPos may not be accurate.
			//		       * check manually. */
			retVal = (SquareThreatened( board, (this.thisSide == Piece.WHITE) ? Piece.BLACK : Piece.WHITE, x, y, z ) != ThreeD_ChessActivity.NULL) ;
		}
		else
		{
			retVal = IsKingChecked(board, this.thisSide);
		}

		board.getBoard()[z][y][x] = temp;
		if ( temp != ThreeD_ChessActivity.NULL )
		{
			temp.bVisible = ThreeD_ChessActivity.TRUE;
		}
		board.getBoard()[xyz.thisLevel][xyz.thisRank][xyz.thisFile] = this;

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

		return ( SquareThreatened(board, (bwSide == Piece.WHITE) ? Piece.BLACK : Piece.WHITE, xyz.thisFile, xyz.thisRank, xyz.thisLevel ) != ThreeD_ChessActivity.NULL );
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
		for (i = 0; i != title && i < TITLES; ++i)
			count += board.getTitleCount()[i];

		if (i == TITLES)
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
		//	return 0;
	}

	public Piece SquareThreatened(Board board, int bwSide, int xFile, int yRank, int zLevel)
	{
		int pieceIdx;

		for (pieceIdx = 0; pieceIdx < ThreeD_ChessActivity.PIECES; ++pieceIdx)
		{

			boolean isVisible = board.getMuster()[bwSide][pieceIdx].bVisible;
			//		  boolean canMove   = board.getMuster()[bwSide][pieceIdx].PieceMayMove( board, xFile, yRank, zLevel );
			boolean canMove   = board.getMuster()[bwSide][pieceIdx].PieceMayMove( board, xFile, yRank, zLevel );
			if ( isVisible && canMove )
				return board.getMuster()[bwSide][pieceIdx];
		}

		return null;
	}

	//[Level][Rank][File]
	boolean PieceMayMove(Board board, int newLevel, int newRank, int newFile) {
		//		private boolean PieceMayMove(Piece piece, int xNew, int yNew, int zNew)
		//boolean retval;
		if (!this.bVisible)
		{
			//				  ThreeD_ChessActivity.n3DcErr = ThreeD_ChessActivity.E3DcINVIS;
			return ThreeD_ChessActivity.FALSE;
		}

		//		  /* Do bits which are the same for all pieces first */
		if (newFile == this.xyzPos.thisFile &&
				newRank == this.xyzPos.thisRank &&
				newLevel == this.xyzPos.thisLevel)
		{
			//			  ThreeD_ChessActivity.n3DcErr = ThreeD_ChessActivity.E3DcSIMPLE;
			return ThreeD_ChessActivity.FALSE;//can't move to same spot
		}

		//		  Piece targetPiece = board.getBoard()[zNew][yNew][xNew];
		Piece targetPiece = board.getPieceAt( newLevel, newRank, newFile );//[Level][Rank][File]
		boolean notNull = targetPiece != null;
		//		  boolean isVisible = board.getBoard()[zNewLevel][yNewRank][xNewFile].bVisible == true;
		//		  boolean isSameSide = board.getBoard()[zNewLevel][yNewRank][xNewFile].bwSide == this.bwSide;
		boolean isVisible = board.getPieceAt( newLevel, newRank, newFile ).bVisible == true;
		boolean isSameSide = board.getPieceAt( newLevel, newRank, newFile ).thisSide == this.thisSide;//[Level][Rank][File]
		if ( notNull &&
				isVisible &&
				isSameSide)
		{
			//			  ThreeD_ChessActivity.n3DcErr = ThreeD_ChessActivity.E3DcBLOCK;
			return ThreeD_ChessActivity.FALSE;  /* Can't take a piece on your team */
		}
		return ThreeD_ChessActivity.FALSE;  /* if all else fails... Fail!*/

		//		}
		//	
		//		return false;
	}


}
