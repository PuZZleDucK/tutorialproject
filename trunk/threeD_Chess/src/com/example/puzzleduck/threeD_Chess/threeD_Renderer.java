package com.example.puzzleduck.threeD_Chess;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//Simplifying things (hopefully) by using basic android rendering instead of GL
class threeD_Renderer extends SurfaceView implements SurfaceHolder.Callback {
	class threeD_Thread extends Thread {

		//	         * State-tracking constants
		public static final int STATE_LOSE = 1;
		public static final int STATE_PAUSE = 2;
		public static final int STATE_READY = 3;
		public static final int STATE_RUNNING = 4;
		public static final int STATE_WIN = 5;

		//	        /** The drawable to use as the background of the animation canvas */
		private Bitmap mBackgroundImage;

		//	         * Current height of the surface/canvas.
		private int mCanvasHeight = 0;

		//	         * Current width of the surface/canvas.
		private int mCanvasWidth = 0;

		//	        /** Message handler used by thread to interact with TextView */
		private Handler mHandler;

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
		private Drawable noneImage;

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
		/* R */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
		/* G */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
		/* B */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
		/* A */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
		};

		float[] blueMatrix = new float[] {
				//   R     G     B     A     X
			/* R */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			/* G */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			/* B */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
			/* A */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
			};

		float[] blackMatrix = new float[] {
				//   R     G     B     A     X
			/* R */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			/* G */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			/* B */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			/* A */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
			};
		float[] whiteMatrix = new float[] {
				//   R     G     B     A     X
			/* R */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
			/* G */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
			/* B */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
			/* A */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
			};
		float[] greenMatrix = new float[] {
				//   R     G     B     A     X
			/* R */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			/* G */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
			/* B */	0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			/* A */	0.0f, 0.0f, 0.0f, 5.0f, 0.0f,
			};


		//	        /** Used to figure out elapsed time between frames */
		//	        private long mLastTime;
		//
		//	        /** Paint to draw the lines on screen. */
		//	        private Paint mLinePaint;
		//
		//	        /** "Bad" speed-too-high variant of the line color. */
		//	        private Paint mLinePaintBad;
		//
		//	        /** The state of the game. One of READY, RUNNING, PAUSE, LOSE, or WIN */
		private int mMode;

		//	        /** Indicate whether the surface has been created & is ready to draw */
		private boolean mRun = false;
		//
		//	        /** Scratch rect object. */
		private RectF mScratchRect;
		//
		//	        /** Handle to the surface manager object we interact with */
		private SurfaceHolder mSurfaceHolder;

		public threeD_Thread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
			// get handles to some important objects
			mSurfaceHolder = surfaceHolder;
			mHandler = handler;
			mContext = context;
			

			Resources res = context.getResources();
			// cache handles to our key sprites & other drawables
			kingImage = context.getResources().getDrawable( R.drawable.k_king);
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
			noneImage = context.getResources().getDrawable( R.drawable.k_none);
			

			//	            // load background image as a Bitmap instead of a Drawable b/c
			//	            // we don't need to transform it and it's faster to draw this way
			mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.k_none);
			//
			//	            // Use the blank image as the model size for all sprites
//			int width = noneImage.getIntrinsicWidth();
//			int height = noneImage.getIntrinsicHeight();

			mScratchRect = new RectF(0, 0, 0, 0);
		}

		//	         * Starts the game, setting parameters for the current difficulty.
		public void doStart() {
			synchronized (mSurfaceHolder) {
				//	                // First set the game for Medium difficulty
				//	                // Adjust difficulty params for EASY/HARD
				//	                // pick a convenient initial location for the lander sprite
				//	                // start with a little random motion
				//	                // Figure initial spot for landing, not too near center
				//	                mLastTime = System.currentTimeMillis() + 100;
				setState(STATE_RUNNING);
			}
		}

		//	         * Pauses the physics update & animation.
		public void pause() {
			synchronized (mSurfaceHolder) {
				if (mMode == STATE_RUNNING) setState(STATE_PAUSE);
			}
		}

		//	         * Restores game state from the indicated Bundle. Typically called when
		//	         * the Activity is being restored after having been previously
		//	         * destroyed.
		public synchronized void restoreState(Bundle savedState) {
			synchronized (mSurfaceHolder) {
				setState(STATE_PAUSE);
				//	                mRotating = 0;
				//	                mDifficulty = savedState.getInt(KEY_DIFFICULTY);
			}
		}

		//	        @Override
		public void run() {
			while (mRun) {
				Canvas c = null;
				try {
					c = mSurfaceHolder.lockCanvas(null);
					synchronized (mSurfaceHolder) {
//						if (mMode == STATE_RUNNING) updatePhysics();
						//if running do main game loop
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
		//	         * Sets the game mode. That is, whether we are running, paused, in the
		//	         * failure state, in the victory state, etc.
		public void setState(int mode) {
			synchronized (mSurfaceHolder) {
				setState(mode, null);
			}
		}

		//	         * Sets the game mode. That is, whether we are running, paused, in the
		//	         * failure state, in the victory state, etc.
		public void setState(int mode, CharSequence message) {
			//	             * This method optionally can cause a text message to be displayed
			//	             * to the user when the mode changes. Since the View that actually
			//	             * renders that text is part of the main View hierarchy and not
			//	             * owned by this thread, we can't touch the state of that View.
			//	             * Instead we use a Message + Handler to relay commands to the main
			//	             * thread, which updates the user-text View.
			synchronized (mSurfaceHolder) {
				mMode = mode;

				if (mMode == STATE_RUNNING) {
					Message msg = mHandler.obtainMessage();
					Bundle b = new Bundle();
					b.putString("text", "");
					//	                    b.putInt("viz", View.INVISIBLE);
					msg.setData(b);
					mHandler.sendMessage(msg);
				} else {
					Resources res = mContext.getResources();
					CharSequence str = "";
					//	                    if (mMode == STATE_READY)
					//	                        str = res.getText(R.string.mode_ready);
					//	                    else if (mMode == STATE_PAUSE)
					//	                        str = res.getText(R.string.mode_pause);
					//	                    else if (mMode == STATE_LOSE)
					//	                        str = res.getText(R.string.mode_lose);
					//	                    else if (mMode == STATE_WIN)
					//	                        str = res.getString(R.string.mode_win_prefix)
					//	                                + mWinsInARow + " "
					//	                                + res.getString(R.string.mode_win_suffix);
					//
					//	                    if (message != null) {
					//	                        str = message + "\n" + str;
					//	                    }
					Message msg = mHandler.obtainMessage();
					Bundle b = new Bundle();
					b.putString("text", str.toString());
					//	                    b.putInt("viz", View.VISIBLE);
					msg.setData(b);
					mHandler.sendMessage(msg);
				}
			}
		}
		//
		//	        /* Callback invoked when the surface dimensions change. */
		public void setSurfaceSize(int width, int height) {
			// synchronized to make sure these all change atomically
			synchronized (mSurfaceHolder) {
				mCanvasWidth = width;
				mCanvasHeight = height;

				// don't forget to resize the background image
				mBackgroundImage = mBackgroundImage.createScaledBitmap( mBackgroundImage, width, height, true);
			}
		}

		//	         * Resumes from a pause.
		public void unpause() {
			//	            // Move the real time clock up to now
			synchronized (mSurfaceHolder) {
				//	                mLastTime = System.currentTimeMillis() + 100;
			}
			setState(STATE_RUNNING);
		}

		
		
		
		
		
		
		//	         * Handles a key-down event.
		boolean doKeyDown(int keyCode, KeyEvent msg) {
			synchronized (mSurfaceHolder) {
				
				//	                boolean okStart = false;
				//	                if (keyCode == KeyEvent.KEYCODE_DPAD_UP) okStart = true;
				//	                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) okStart = true;
				//	                if (keyCode == KeyEvent.KEYCODE_S) okStart = true;
				//
				//	                boolean center = (keyCode == KeyEvent.KEYCODE_DPAD_UP);
				//
//					                if (okStart
//					                        && (mMode == STATE_READY || mMode == STATE_LOSE || mMode == STATE_WIN)) {
//					                    // ready-to-start -> start
//					                    doStart();
//					                    return true;
//					                } else if (mMode == STATE_PAUSE && okStart) {
//					                    // paused -> running
//					                    unpause();
//					                    return true;
//					                } else if (mMode == STATE_RUNNING) {
//					                    // center/space -> fire
//					                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
//					                            || keyCode == KeyEvent.KEYCODE_SPACE) {
//					                        setFiring(true);
//					                        return true;
//					                        // left/q -> left
//					                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
//					                            || keyCode == KeyEvent.KEYCODE_Q) {
//					                        mRotating = -1;
//					                        return true;
//					                        // right/w -> right
//					                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
//					                            || keyCode == KeyEvent.KEYCODE_W) {
//					                        mRotating = 1;
//					                        return true;
//					                        // up -> pause
//					                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//					                        pause();
//					                        return true;
//					                    }
//					                }
				
				
				
				
				
				
				
				
				
				
				
				
				
				

					                Toast toast;
					                CharSequence text;
					        		
					                switch(keyCode)
					        		{
					        			case(20): // 20 down...
					        				threeD_Chess.selectedSquare.moveDown();
					        			break;

					        			case(19): // 19 up...
					        				threeD_Chess.selectedSquare.moveUp();
					        			break;

					        			case(21): // 21 left...
					        				threeD_Chess.selectedSquare.moveLeft();
					        			break;

					        			case(22): // 22 right....
					        				threeD_Chess.selectedSquare.moveRight();
					        			break;

//					        			case(82): // . 82 menu...
//					        			break;
					        			
					        			
					        			
					        		}
					        		
					        //
//					        		this.LEVEL_OFFSETx += 0;
//					        		this.LEVEL_OFFSETy += 2*8;
////					        		this.LEVEL_SPLIT += 2;
//					        		this.RANK_OFFSETx += 0;
//					        		this.RANK_OFFSETy += 2;
//					        		this.FILE_OFFSETx += 0;
//					        		this.FILE_OFFSETy += 2;		
					        		

					        		
					        		
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				return false;
			}
		}

		//	         * Handles a key-up event.
		boolean doKeyUp(int keyCode, KeyEvent msg) {
			boolean handled = false;

			synchronized (mSurfaceHolder) {
				if (mMode == STATE_RUNNING) {
					if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
							|| keyCode == KeyEvent.KEYCODE_SPACE) {
						//	                        setFiring(false);
						handled = true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
							|| keyCode == KeyEvent.KEYCODE_Q
							|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
							|| keyCode == KeyEvent.KEYCODE_W) {
						//	                        mRotating = 0;
						handled = true;
					}
				}
			}
			//
			return handled;
		}

		private void doDraw(Canvas canvas) {
			//	            // so this is like clearing the screen.
			canvas.drawBitmap(mBackgroundImage, 0, 0, null);
			//	            canvas.save(); //before rotation/transform
			int x = 16;
			int y = 16;
			int totalXOffset = 0;
			int totalYOffset = 0; 
			//	            canvas.restore();
			for (int thisLevel = 0; thisLevel < threeD_Chess.LEVELS; ++thisLevel)
			{
				for (int thisRank = 0; thisRank < threeD_Chess.RANKS; ++thisRank)
				{
					for (int thisFile = 0; thisFile < threeD_Chess.FILES; ++thisFile)
					{
						totalXOffset = thisFile*FILE_OFFSETx + thisLevel*LEVEL_OFFSETx;
						totalYOffset = thisRank*RANK_OFFSETx + thisLevel*LEVEL_OFFSETy + thisLevel*LEVEL_SPLIT;
						noneImage.setBounds(
								totalXOffset,
								totalYOffset, 
								x+totalXOffset, 
								y+totalYOffset);								

						//background
							if( (thisRank + thisFile) % 2 == 0 )
							{
								noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
							}else
							{
								noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
							}
							//selection
							//check for selected square and highlight "green"
							if(thisLevel == threeD_Chess.selectedSquare.xyzPos.zLevel && 
									thisRank == threeD_Chess.selectedSquare.xyzPos.yRank &&
									thisFile == threeD_Chess.selectedSquare.xyzPos.xFile)
							{
								noneImage.setColorFilter(new ColorMatrixColorFilter(greenMatrix));
							}
							
						noneImage.draw(canvas);

						if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile] != null)
						{
																			
							switch(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].nName)
							{
							case(Piece.c_pawn):
								pawnImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
							if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								pawnImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								pawnImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}								
//							if( (thisRank + thisFile) % 2 == 0 )
//							{
//								noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//							}else
//							{
//								noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//							}
//								noneImage.draw(canvas);
								pawnImage.draw(canvas);
								break;
							case(Piece.c_galley):
								galleyImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
								if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								galleyImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								galleyImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
									galleyImage.draw(canvas);
								break;
							case(Piece.c_cannon):
								cannonImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
								if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								cannonImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								cannonImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
								cannonImage.draw(canvas);
								break;
							case(Piece.c_abbey):
								abbeyImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
								if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								abbeyImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								abbeyImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
								abbeyImage.draw(canvas);
								break;
							case(Piece.c_princess):
								princessImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
								if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								princessImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								princessImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
								princessImage.draw(canvas);
								break;
							case(Piece.c_prince):
								princeImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
								if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								princeImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								princeImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
								princeImage.draw(canvas);
								break;
							case(Piece.c_rook):
								rookImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
								if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								rookImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								rookImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
								rookImage.draw(canvas);
								break;
							case(Piece.c_knight):
								knightImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
								if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								knightImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								knightImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
								knightImage.draw(canvas);
								break;
							case(Piece.c_bishop):
								bishopImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
								if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								bishopImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								bishopImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
								bishopImage.draw(canvas);
								break;
							case(Piece.c_queen):
								queenImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
								if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								queenImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								queenImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
								queenImage.draw(canvas);
								break;
							case(Piece.c_king):
								kingImage.setBounds(
										totalXOffset,
										totalYOffset, 
										x+totalXOffset, 
										y+totalYOffset);
								if(threeD_Chess.Board.getBoard()[thisLevel][thisRank][thisFile].getColor() == Piece.BLACK)
							{
								kingImage.setColorFilter(new ColorMatrixColorFilter(redMatrix));
							}else
							{
								kingImage.setColorFilter(new ColorMatrixColorFilter(blueMatrix));
							}
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
								kingImage.draw(canvas);
								break;
							default:
//								if( (thisRank + thisFile) % 2 == 0 )
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(whiteMatrix));
//								}else
//								{
//									noneImage.setColorFilter(new ColorMatrixColorFilter(blackMatrix));
//								}
//									noneImage.draw(canvas);
									break;
									}
						}else{
							
						}
					}
				}
			}
			
		}

		//	         * Figures the lander state (x, y, fuel, ...) based on the passage of
		//	         * realtime. Does not invalidate(). Called at the start of draw().
		//	         * Detects the end-of-game and sets the UI to the next state.
//		private void updatePhysics() {
			//	            long now = System.currentTimeMillis();

			//	            // Do nothing if mLastTime is in the future.
			//	            // This allows the game-start to delay the start of the physics
			//	            // by 100ms or whatever.
			//	            if (mLastTime > now) return;
			//
			//	            double elapsed = (now - mLastTime) / 1000.0;
			//
			//	            // mRotating -- update heading
			//	            // Base accelerations -- 0 for x, gravity for y
			//	            // figure speeds for the end of the period
			//	            mDX += ddx;
			//	            mDY += ddy;
			//	            // figure position based on average speed during the period
			//	            mX += elapsed * (mDX + dxOld) / 2;
			//	            mY += elapsed * (mDY + dyOld) / 2;
			//
			//	            mLastTime = now;

			//	            // Evaluate if we have landed ... stop the game
			//	            double yLowerBound = TARGET_PAD_HEIGHT + mLanderHeight / 2
			//	                    - TARGET_BOTTOM_PADDING;
			//	            if (mY <= yLowerBound) {
			//	                mY = yLowerBound;
			//
			//	                int result = STATE_LOSE;
			//	                CharSequence message = "";
			//	                Resources res = mContext.getResources();
			//	                double speed = Math.sqrt(mDX * mDX + mDY * mDY);
			//	                boolean onGoal = (mGoalX <= mX - mLanderWidth / 2 && mX
			//	                        + mLanderWidth / 2 <= mGoalX + mGoalWidth);
			//
			//	                // "Hyperspace" win -- upside down, going fast,
			//	                // puts you back at the top.
			//	                if (onGoal && Math.abs(mHeading - 180) < mGoalAngle
			//	                        && speed > PHYS_SPEED_HYPERSPACE) {
			//	                    result = STATE_WIN;
			//	                    mWinsInARow++;
			//	                    doStart();
			//
			//	                    return;
			//	                    // Oddball case: this case does a return, all other cases
			//	                    // fall through to setMode() below.
			//	                } else if (!onGoal) {
			//	                    message = res.getText(R.string.message_off_pad);
			//	                } else if (!(mHeading <= mGoalAngle || mHeading >= 360 - mGoalAngle)) {
			//	                    message = res.getText(R.string.message_bad_angle);
			//	                } else if (speed > mGoalSpeed) {
			//	                    message = res.getText(R.string.message_too_fast);
			//	                } else {
			//	                    result = STATE_WIN;
			//	                    mWinsInARow++;
			//	                }
			//
			//	                setState(result, message);
			//	            }
//		}
	}

	//	    /** Handle to the application context, used to e.g. fetch Drawables. */
	private Context mContext;

	//	    /** Pointer to the text view to display "Paused.." etc. */
	private TextView mStatusText;

	//	    /** The thread that actually draws the animation */
	private threeD_Thread thread;

	private int LEVEL_OFFSETx = 0;
	private int LEVEL_OFFSETy = 16*8;
	private int LEVEL_SPLIT = 16;
	private int RANK_OFFSETx = 16;
	private int RANK_OFFSETy = 16;
	private int FILE_OFFSETx = 16;
	private int FILE_OFFSETy = 16;

	public threeD_Renderer(Context context, AttributeSet attrs) {
		super(context, attrs);

		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		//	        // create thread only; it's started in surfaceCreated()
		thread = new threeD_Thread(holder, context, new Handler() {
			@Override
			public void handleMessage(Message m) {
			}
		});
		//
		setFocusable(true); // make sure we get key events
	}

	//	     * Fetches the animation thread corresponding to this LunarView.
	public threeD_Thread getThread() {
		return thread;
	}

	//	     * Standard override to get key-press events.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent msg) {
		//moving to new key handler above.
//        Context context = getApplicationContext();

		return thread.doKeyDown(keyCode, msg);
	}


	//	     * Standard override for key-up. We actually care about these, so we can
	//	     * turn off the engine or stop rotating.
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent msg) {
		return thread.doKeyUp(keyCode, msg);
	}
	
//
//	public boolean onTouch(View v, MotionEvent event) {
/////does not seem to be attached
//
//	    return true;
//	}



	//threeD_Chess.DoMain3DcLoop();
	//	     * Standard window-focus override. Notice focus lost so we can pause on
	//	     * focus lost. e.g. user switches to take a call.
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus) thread.pause();
	}

	//	     * Installs a pointer to the text view used for messages.
	public void setTextView(TextView textView) {
		mStatusText = textView;
	}

	public void setDebugText(String debugText) {
		mStatusText.setText(debugText);
	}

	/* Callback invoked when the surface dimensions change. */
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);
	}

	//	     * Callback invoked when the Surface has been created and is ready to be
	//	     * used.
	public void surfaceCreated(SurfaceHolder holder) {
		// start the thread here so that we don't busy-wait in run()
		// waiting for the surface to be created
		thread.setRunning(true);
		thread.start();
	}

	//	     * Callback invoked when the Surface has been destroyed and must no longer
	//	     * be touched. WARNING: after this method returns, the Surface/Canvas must
	//	     * never be touched again!
	public void surfaceDestroyed(SurfaceHolder holder) {
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

}
