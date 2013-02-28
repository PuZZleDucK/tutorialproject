package com.puzzleduck.threeD_Chess;

import java.util.Iterator;
import java.util.Stack;

//import com.puzzleduck.threeD_Chess.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

class ThreeD_Renderer extends SurfaceView implements SurfaceHolder.Callback {

	private static float touchX = 0;
	private static float touchY = 0;
	
	class threeD_Thread extends Thread {

		private Bitmap mBackgroundImage;  // the background of the animation canvas */
		private int mCanvasHeight = 0; // height of the surface/canvas.
		private int mCanvasWidth = 0; // width of the surface/canvas.
		private Handler mHandler; // Message handler used by thread to interact with TextView */

		private Drawable kingImage;
		private Drawable queenImage;
		private Drawable bishopImage;
		private Drawable knightImage;
		private Drawable rookImage;
		private Drawable princeImage;
		private Drawable princessImage;
		private Drawable abbeyImage;
		private Drawable cannonImage;
		private Drawable galleyImage;
		private Drawable pawnImage;

		private int mMode; // state of the game. One of READY, RUNNING, PAUSE, LOSE, or WIN */
		private boolean mRun = false; //  whether the surface has been created & is ready to draw */
		private SurfaceHolder mSurfaceHolder; // Handle to the surface manager object we interact with */

		//Color
		//		private ColorFilter cf;
		//		float[] matrix = new float[] {
		//				1, 0, 0, 0, 1,
		//				0, 1, 0, 0, 1,
		//				0, 0, 1, 0, 1,
		//				0, 0, 0, 1, 0,
		//		};
		float[] redMatrix = new float[] {
				//   R     G     B     A     X
				/* R */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
				/* G */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				/* B */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				/* A */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
		};
		float[] blueMatrix = new float[] {
				/* R */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				/* G */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				/* B */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
				/* A */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
		};
		float[] blackishMatrix = new float[] {
				/* R */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				/* G */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				/* B */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				/* A */	0.0f, 0.0f, 0.0f, 0.5f, 0.0f,
		};
		float[] whiteMatrix = new float[] {
				/* R */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
				/* G */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
				/* B */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
				/* A */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
		};
		float[] greenMatrix = new float[] {
				/* R */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				/* G */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
				/* B */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				/* A */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
		};
		float[] cyanMatrix = new float[] {
				/* R */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				/* G */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
				/* B */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
				/* A */	0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
		};

		public threeD_Thread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
			mSurfaceHolder = surfaceHolder; 
			mHandler = handler; // get handles to some important objects
			Resources res = context.getResources();
			
			kingImage = context.getResources().getDrawable( R.drawable.k_king);  // cache handles to our drawables
			queenImage = context.getResources().getDrawable( R.drawable.k_queen);
			bishopImage = context.getResources().getDrawable( R.drawable.k_bishop);
			knightImage = context.getResources().getDrawable( R.drawable.k_knight);
			rookImage = context.getResources().getDrawable( R.drawable.k_rook);
			princeImage = context.getResources().getDrawable( R.drawable.k_prince);
			princessImage = context.getResources().getDrawable( R.drawable.k_princess);
			abbeyImage = context.getResources().getDrawable( R.drawable.k_abbey);
			cannonImage = context.getResources().getDrawable( R.drawable.k_cannon);
			galleyImage = context.getResources().getDrawable( R.drawable.k_galley);
			pawnImage = context.getResources().getDrawable( R.drawable.k_pawn);
			mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.background);
		} //threeD_Thread

		
		public void run() {
			while (mRun) {
				Canvas c = null;
				try {
					c = mSurfaceHolder.lockCanvas(null);
					synchronized (mSurfaceHolder) {
						doDraw(c);
					}
				} finally {
					//	                    // do this in a finally so that if an exception is thrown
					//	                    // during the above, we don't leave the Surface in an
					//	                    // inconsistent state
					if (c != null) {
						mSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}

		//	         * Dump game state to the provided Bundle. Typically called when the
		//	         * Activity is being suspended.
		public Bundle saveState(Bundle map) {
			synchronized (mSurfaceHolder) {
				if (map != null) {
					//	                    map.putInt(KEY_DIFFICULTY, Integer.valueOf(mDifficulty));
				}
			}
			return map;
		}

		//	         * Used to signal the thread whether it should be running or not.
		//	         * Passing true allows the thread to run; passing false will shut it
		//	         * down if it's already running. Calling start() after this was most
		//	         * recently called with false will result in an immediate shutdown.
		public void setRunning(boolean b) {
			mRun = b;
		}

		
		public void setSurfaceSize(int width, int height) {   // Callback invoked when dimensions change. */
			synchronized (mSurfaceHolder) {   // synchronized to make sure these all change atomically
				mCanvasWidth = width;
				mCanvasHeight = height;
				// don't forget to resize the background image
				mBackgroundImage = Bitmap.createScaledBitmap( mBackgroundImage, width, height, true);
			}
		}

		
		public boolean doKeyDown(int keyCode, KeyEvent msg) {
			synchronized (mSurfaceHolder) {
				switch(keyCode)
				{
				case(KeyEvent.KEYCODE_DPAD_DOWN): // 20 down...
					ThreeD_ChessActivity.selectedSquare.moveDown();
				break;

				case(KeyEvent.KEYCODE_DPAD_UP): // 19 up...
					ThreeD_ChessActivity.selectedSquare.moveUp();
				break;

				case(KeyEvent.KEYCODE_DPAD_LEFT): // 21 left...
					ThreeD_ChessActivity.selectedSquare.moveLeft();
				break;

				case(KeyEvent.KEYCODE_DPAD_RIGHT): // 22 right....
					ThreeD_ChessActivity.selectedSquare.moveRight();
				break;
				case(KeyEvent.KEYCODE_DPAD_CENTER): // 22 right....
					Piece thisPiece = ThreeD_ChessActivity.Board.getPieceAt(
							ThreeD_ChessActivity.selectedSquare.xyzPos.thisFile,
							ThreeD_ChessActivity.selectedSquare.xyzPos.thisRank,
							ThreeD_ChessActivity.selectedSquare.xyzPos.thisLevel
					);
				if(thisPiece != null)
				{
					ThreeD_ChessActivity.Board.setPossibleMoveStack(thisPiece.FindAllMoves(ThreeD_ChessActivity.Board));
//					thisPiece.FindAllMoves(ThreeD_ChessActivity.Board);
					//then flag possible moves on display
					
					
					
					
				}

				break;

				//					        			case(82): // . 82 menu...
				//					        			break;


				}				

				return false;
			}
		}

		
		boolean doKeyUp(int keyCode, KeyEvent msg) {    // Handles a key-up event.
			boolean handled = false;

			synchronized (mSurfaceHolder) {
					if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
							|| keyCode == KeyEvent.KEYCODE_SPACE) {
						handled = true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
							|| keyCode == KeyEvent.KEYCODE_Q
							|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
							|| keyCode == KeyEvent.KEYCODE_W) {
						handled = true;
					}
//				}
			}
			return handled;
		}

		private void doDraw(Canvas canvas) {
			int debugX = 10;
			int debugY = 110;
			Paint thisPaint = new Paint();
			thisPaint.setARGB(255, 0, 0, 0);
			mCanvasHeight = canvas.getHeight();
			mCanvasWidth = canvas.getWidth();
			//	            // so this is like clearing the screen.
			canvas.drawBitmap(mBackgroundImage, 0, 0, null);
			
			boolean isPortrait = mCanvasHeight - mCanvasWidth > 0;//if taller than wide
			canvas.drawText("isPortrait   = "+ isPortrait, debugX, debugY+=15, thisPaint);
			canvas.drawText("X-width   = "+ mCanvasWidth, debugX, debugY+=15, thisPaint);
			canvas.drawText("Y-height  = "+ mCanvasHeight, debugX, debugY+=15, thisPaint);
			thisPaint.setStyle(Style.STROKE); 
			canvas.drawRect(5, 5, mCanvasWidth-5, mCanvasHeight-5, thisPaint);
			
			int marginWidth = 5;
			
			int maxCellHeight;
			int maxCellWidth;
			//if portrait, split boards up and down... staggered ... need to fit two accross and down
			if(isPortrait)
			{
				maxCellHeight = (mCanvasHeight-(2*marginWidth))/(2*8);
				maxCellWidth = (mCanvasWidth-(2*marginWidth))/(2*8);
				
			}else//else, split left to right... slanted... only need to fit three accross
			{
				maxCellHeight = (mCanvasHeight-(2*marginWidth))/(8);
				maxCellWidth = (mCanvasWidth-(2*marginWidth))/(3*8);
				
			}
			canvas.drawText("maxCellHeight  = "+ maxCellHeight, debugX, debugY+=15, thisPaint);
			canvas.drawText("maxCellWidth   = "+ maxCellWidth, debugX, debugY+=15, thisPaint);
			int cellSize = maxCellHeight > maxCellWidth ? maxCellWidth : maxCellHeight;
			canvas.drawText("   CellWidth   = "+ cellSize, debugX, debugY+=15, thisPaint);

			//TOUCH INTERFACE
			canvas.drawCircle(touchX, touchY, 20, thisPaint);
			
			
			//			Log.d("chessDraw","chessDraw ::: cell      = " + cellSize);
			
			
			
			//			Log.d("chessDraw","chessDraw ::: isPortrait   = " + isPortrait);
			
			
			
			int heightMargin = (mCanvasHeight - (3*8*cellSize))/4; //leaving two 'margins worth' of space to split boards
			int widthMargin = (mCanvasWidth - (8*cellSize)) /4; // div 3 for three levels centered
			

//			Log.d("chessDraw","chessDraw ::: 8*3 cell      = " + (3*8*cellSize));
//			Log.d("chessDraw","chessDraw ::: 8* cell       = " + (8*cellSize));
//			Log.d("chessDraw","chessDraw ::: h marg        = " + heightMargin);
//			Log.d("chessDraw","chessDraw ::: v marg        = " + widthMargin);

			int levelOffsetX = cellSize*8;

			Drawable cellBackImage;
			int totalXOffset = 0;
			int totalYOffset = 0; 

			for (int thisLevel = 0; thisLevel < ThreeD_ChessActivity.LEVELS; ++thisLevel)//[Level][Rank][File]
			{
				for (int thisRank = 0; thisRank < ThreeD_ChessActivity.RANKS; ++thisRank)
				{
					for (int thisFile = 0; thisFile < ThreeD_ChessActivity.FILES; ++thisFile)
					{
						if( (thisRank + thisFile) % 2 == 0 )// BACKGROUND
						{
							cellBackImage = getResources().getDrawable(R.drawable.metal_light); //crash on orientation change???
							//cellBackImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
						}else
						{
							cellBackImage = getResources().getDrawable(R.drawable.metal_dark);
							//cellBackImage.setColorFilter(new ColorMatrixColorFilter(blackishMatrix));
						}
						if(thisLevel == ThreeD_ChessActivity.selectedSquare.xyzPos.thisLevel &&  //check for selected square and highlight "green"
								thisRank == ThreeD_ChessActivity.selectedSquare.xyzPos.thisRank &&
								thisFile == ThreeD_ChessActivity.selectedSquare.xyzPos.thisFile)
						{
							//cellBackImage.setColorFilter(new ColorMatrixColorFilter(greenMatrix));
							cellBackImage = getResources().getDrawable(R.drawable.metal_green);
							
						}
						// check for possible moves and 	highlight altMatrix 
						Stack<Move> possibleMoves = ThreeD_ChessActivity.Board.getPossibleMoveStack();
						if (possibleMoves != null) 
						{
							Iterator<Move> movesIterator = possibleMoves.iterator();
							while (movesIterator.hasNext()) {
								Move currentMove = movesIterator.next();
								if (thisLevel == currentMove.xyzAfter.getLevel()
										&& thisRank == currentMove.xyzAfter.getRank()
										&& thisFile == currentMove.xyzAfter.getFile()) 
								{
									//cellBackImage.setColorFilter(new ColorMatrixColorFilter(cyanMatrix));
									cellBackImage = getResources().getDrawable(R.drawable.metal_blue);
									
								}

							}
						}
						
						
						totalXOffset = widthMargin + (thisFile*cellSize) + (thisLevel*levelOffsetX) + thisLevel*10;
						totalYOffset = heightMargin + (thisRank*cellSize) + (thisLevel*heightMargin) + thisLevel*50;// + thisLevel*levelSplit;
						cellBackImage.setBounds(
								totalXOffset,
								totalYOffset, 
								cellSize+totalXOffset, 
								cellSize+totalYOffset);								

						//cellBackImage.setColorFilter(null);
						cellBackImage.draw(canvas);
						
						
						//Debug: LEVELS
						thisPaint.setARGB(255, 255, 0, 0);
						canvas.drawText("L-"+thisLevel, totalXOffset+10, totalYOffset+10, thisPaint);
						thisPaint.setARGB(255, 0, 255, 0);
						canvas.drawText("R-"+thisRank, totalXOffset+10, totalYOffset+20, thisPaint);
						thisPaint.setARGB(255, 0, 0, 255);
						canvas.drawText("F-"+thisFile, totalXOffset+10, totalYOffset+30, thisPaint);
						//RANKS
					    //A row of the chessboard. Specific ranks are referred to by number,
						//first rank, second rank, …, eighth rank. Unlike the case with 
						//files, rank names are always given from the point of view of each 
						//individual player
					    
						//Debug: FILES
						//A column of the chessboard. A specific file can be named either using its 
						//position in algebraic notation, a–h... 0-7
						

						//not sure we need this null check, should not come up
						if(ThreeD_ChessActivity.Board.getBoard()[thisLevel][thisRank][thisFile] != null)//[Level][Rank][File]
						{
							switch(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).thisType)
							{
							case(Piece.pawn):
								pawnImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);	
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								pawnImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								pawnImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}								
							pawnImage.draw(canvas);
							break;
							case(Piece.galley):
								galleyImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);		//[Level][Rank][File]
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								galleyImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								galleyImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
							galleyImage.draw(canvas);
							break;
							case(Piece.cannon):
								cannonImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);	
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								cannonImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								cannonImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
							cannonImage.draw(canvas);
							break;
							case(Piece.abbey):
								abbeyImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);	//[Level][Rank][File]
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								abbeyImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								abbeyImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
							abbeyImage.draw(canvas);
							break;
							case(Piece.princess):
								princessImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);	
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								princessImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								princessImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
							princessImage.draw(canvas);
							break;
							case(Piece.prince):
								princeImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);	
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								princeImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								princeImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
							princeImage.draw(canvas);
							break;
							case(Piece.rook):
								rookImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);	
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								rookImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								rookImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
							rookImage.draw(canvas);
							break;
							case(Piece.knight):
								knightImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);	
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								knightImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								knightImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
							knightImage.draw(canvas);
							break;
							case(Piece.bishop):
								bishopImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);	
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								bishopImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								bishopImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
							bishopImage.draw(canvas);
							break;
							case(Piece.queen):
								queenImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);	
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								queenImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								queenImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
							queenImage.draw(canvas);
							break;
							case(Piece.king):
								kingImage.setBounds(
										totalXOffset,
										totalYOffset, 
										cellSize+totalXOffset, 
										cellSize+totalYOffset);	
							if(ThreeD_ChessActivity.Board.getPieceAt(thisLevel, thisRank, thisFile).getColor() == Piece.BLACK)
							{
								kingImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								kingImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
							kingImage.draw(canvas);
							break;
							default:
								//									noneImage.draw(canvas);//hope it don't happen
								break;
							}
						}else{//might not need..ok, i do

						}
					}//for
				}//for
			}//for

		}//doDraw
		
	}//3d_thread

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODOne Auto-generated method stub
		touchX = event.getX();
		touchY = event.getY();
//		System.out.println("Touch ("+touchX+","+touchY+")");
		return super.onTouchEvent(event);
	}
	

	private threeD_Thread thread; //	    /** The thread that actually draws the animation */

	public ThreeD_Renderer(Context context, AttributeSet attrs) {
		super(context, attrs);

		SurfaceHolder holder = getHolder();
		holder.addCallback(this);   // register our interest in hearing about changes to our surface

		thread = new threeD_Thread(holder, context, new Handler() {   // create thread only; it's started in surfaceCreated()
			@Override
			public void handleMessage(Message m) {
			}
		});
		setFocusable(true); // make sure we get key events
	}

	
	public threeD_Thread getThread() { //	     * Fetches the animation thread 
		return thread;
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {   // Callback invoked when the surface dimensions change.
		thread.setSurfaceSize(width, height);
	}

	
	public void surfaceCreated(SurfaceHolder holder) {  // invoked when the Surface has been created and is ready to be used
		// start the thread here so that we don't busy-wait in run()
		// waiting for the surface to be created
		thread.setRunning(true);
		thread.start();
	}

	
	public void surfaceDestroyed(SurfaceHolder holder) {   // Callback invoked when the Surface has been destroyed
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent msg) {   //	     * Standard override to get key-press events.
		return thread.doKeyDown(keyCode, msg);
	}


	@Override
	public boolean onKeyUp(int keyCode, KeyEvent msg) {   //	     * Standard override for key-up.
		return thread.doKeyUp(keyCode, msg);
	}


}//class
