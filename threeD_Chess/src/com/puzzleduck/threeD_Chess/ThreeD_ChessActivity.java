package com.puzzleduck.threeD_Chess;

import java.util.Random;
import java.util.Stack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;



//Simplifying things (hopefully) by using basic android rendering instead of GL
// * Copyright (C) 2007 The Android Open Source Project
// * Licensed under the Apache License, Version 2.0 (the "License");


//import game renderer thread
//import com.example.android.lunarlander.LunarView.LunarThread;
//import com.puzzleduck.threeD_Chess.R;
import com.puzzleduck.threeD_Chess.ThreeD_Renderer.threeD_Thread;

public class ThreeD_ChessActivity extends Activity {


//	//	         * State-tracking constants
//	public static final int STATE_LOSE = 1;
//	public static final int STATE_PAUSE = 2;
//	public static final int STATE_READY = 3;
//	public static final int STATE_RUNNING = 4;
//	public static final int STATE_WIN = 5;
	
	//	stackList bestMoves = new stackList();
	private static Stack currentPossibleMoves = new Stack();
	private static Stack aiMoves = new Stack();
	private static Stack gameMoves = new Stack();

	private static final int MENU_HINT = 1;
	private static final int MENU_UNDO = 2;
	private static final int MENU_DIFFICULTY = 3;
	private static final int MENU_ABOUT = 4;
	private static final int MENU_HELP = 5;
	private static final int MENU_SETTINGS = 6;
	//    private static final int MENU_STOP = 7;

	public static final int UINT_MAX = 99;
	public static final int INT_MAX = 99;




	public int values[] = { 26, 42, 22, 10, 25, /* Royalty */
			45, 21, 12, 24, 15, /* Nobility */
			6 /* Pawn */
	};

	stackList bestMoves = new stackList();

	public int BEST_STACKS = 5;
	public int INT_MIN = -99;



	//
	//		/* Reasons */
	int XawCR_EXPOSE = 1;
	int XawCR_INPUT  = 2;
	int XawCR_MOTION = 3;
	int XawCR_RESIZE = 4;

	//extern int n3DcErr;

	static int E3DcSIMPLE	= 0;
	private int E3DcLEVEL	= 1;
	private int E3DcCHECK	= 2;
	private int E3DcDIST	= 3;
	static int E3DcINVIS	= 4;
	static int E3DcBLOCK	= 5;
	private int E3DcMOVED	= 6;



	//    /** A handle to the thread that's actually running the animation. */
	private threeD_Thread mthreeD_Thread;
	//    /** A handle to the View in which the game is running. */
	private ThreeD_Renderer mthreeD_Renderer;

	//     * Invoked during init to give the Activity a chance to set up its Menu.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, MENU_HINT, 0, "Hint");
		menu.add(0, MENU_UNDO, 0, "Undo");
		menu.add(0, MENU_DIFFICULTY, 0, "Difficulty");
		menu.add(0, MENU_ABOUT, 0, "About");
		menu.add(0, MENU_HELP, 0, "Help");
		menu.add(0, MENU_SETTINGS, 0, "Settings");
		//        menu.add(0, MENU_HARD, 0, R.string.menu_start);
		return true;
	}



	//     * Invoked when the user selects an item from the Menu.
	//     * @param item the Menu entry which was selected
	//     * @return true if the Menu item was legit (and we consumed it), false
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Context context = getApplicationContext();
		Toast toast;
		CharSequence text;
		switch (item.getItemId()) {
		case MENU_HINT:
			//                mLunarThread.doStart();
			return true;
		case MENU_UNDO:
			//                mLunarThread.setState(LunarThread.STATE_LOSE,
			//                        getText(R.string.message_stopped));
			return true;
		case MENU_DIFFICULTY:
			//                mLunarThread.pause();
			return true;
		case MENU_ABOUT:
			text = "3Dc, Copyright (C) 1995,1996 Paul Hicks\n "
				+ "3Dc comes with ABSOLUTELY NO WARRANTY: see the GPL for details \n"
				+ "This is free software: you are welcome to redistribute it under certain conditions (see the GPL).\n"
				+ "\nThis version is ported to Android by PuZZleDucK\n";
			toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
			toast.show();
			//                mLunarThread.unpause();
			return true;
		case MENU_HELP:
			//                Context context = getApplicationContext();
			text = "Sorry, cant help you that much yet...\n "
				+ "you see nothing works yet except: \n"
				+ "-This help message\n"
				+ "-About message\n";
			toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
			toast.show();
			//                mLunarThread.setDifficulty(LunarThread.DIFFICULTY_EASY);
			return true;
		case MENU_SETTINGS:
			//                mLunarThread.setDifficulty(LunarThread.DIFFICULTY_MEDIUM);
			return true;
			//            case MENU_HARD:
			//                mLunarThread.setDifficulty(LunarThread.DIFFICULTY_HARD);
			//                return true;
		}
		return false;
	}

	//     * Invoked when the Activity is created.
	//     * @param savedInstanceState a Bundle containing state saved from a previous
	//     *        execution, or null if this is a new execution
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

//		System.out.println("Create");
		
		
		//eventually replace this with android propperties/settings
		//Define Black and White as Human, AI or remote
		//                    if (!strcmp(argv[argNum], "-play"))
		//                                    "%s: -play requires a colour (black or white)\n",
		//                                    "%s: %s is not a colour (must be black or white)\n",
		//                      } /* End autoplay setup */

		//                    else if (!strcmp(argv[argNum], "-altdisplay") ||
		//                                    "%s: option %s requires a display name parameter\n",
		//                            Open2ndDisplay(argv[argNum]);
		//                      } /* End net setup */
		//I'll need to allow remote connection of game eventually :)

		//%s ; play 3Dc, two humans on one display\n\
		//%s -ad|-altdisplay [display] ; black plays on display `display'\n\
		//%s -play colour ; play against the computer, which plays colour\n",
		//                  } /* Finish parameters */


		//        // tell system to use the layout defined in our XML file
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//hide status
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//no full screen
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.threedc);
		
		 


		//        // get handles to the LunarView from XML, and its LunarThread
		mthreeD_Renderer = (ThreeD_Renderer) findViewById(R.layout.threedc);

		mthreeD_Renderer = new ThreeD_Renderer(getApplicationContext(), null);
		mthreeD_Thread = mthreeD_Renderer.getThread();

		//        // give the renderer a handle to the TextView used for messages
		//      mthreeD_Renderer.setTextView((TextView) findViewById(R.id.text)); //origional... debug text display
		//      setContentView(findViewById(R.id.basicGraphics));

		Init3Dc();

		if (savedInstanceState == null) {
			// we were just launched: set up a new game
//			mthreeD_Thread.setState(STATE_READY);
			Log.w(this.getClass().getName(), "SIS is null");
		} else {
			// we are being restored: resume a previous game
//			mthreeD_Thread.restoreState(savedInstanceState);
			Log.w(this.getClass().getName(), "SIS is nonnull");
		}
	}

	//     * Invoked when the Activity loses user focus.
	@Override
	protected void onPause() {
		super.onPause();
		//        mLunarView.getThread().pause(); // pause game when Activity pauses
	}

	//     * Notification that something is about to happen, to give the Activity a
	//     * chance to save state.
	//     * @param outState a Bundle into which this Activity should save its state
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// just have the View's thread save its state into our Bundle
		super.onSaveInstanceState(outState);
		//        mLunarThread.saveState(outState);
		Log.w(this.getClass().getName(), "SIS called");
	}


	public static int ABS(int x)
	{
		return (x > 0) ? x : -x;
	}

	public static int MAX(int x, int y)
	{
		return (x > y) ? x : y;
	}
	public static int MIN(int x, int y)
	{
		return (x > y) ? x : y;
	}


	//	/* Returns from pieceMayMove */
	private static final int CASTLE = 2;
	private static final int EnPASSANT = 3;
	private static final int PROMOTE = 4;

	static final int PIECES = 48;
	private static final int COLOURS = 2;

	public static final int FILES = 8;//[Level][Rank][File]
	public static final int RANKS = 8;
	public static final int LEVELS = 3;

	//	/* Directions */
//	private static final int LEFT  = -1;
//	private static final int RIGHT  = 1;
//	private static final int FORW   = 1;  /* if colour is black => -1 */
//	private static final int BACK  = -1;  /* if colour is black => +1 */
//	private static final int UP    = 1;
//	private static final int DOWN  = -1;
//	private static final int NODIR  = 0;

	private String winString = "";
	private Random rng = new Random();

	static int n3DcErr;

	public static Board Board = new Board(new Piece[LEVELS][RANKS][FILES], new Piece[COLOURS][PIECES], new int[] {1,1,2,2,2,2,2,4,4,4,24});
	public static Piece selectedSquare;


	private int RANDDIR()
	{
		return rng.nextInt(2);  // this is 0-1
	}

	static boolean HORZ(int x, int y)
	{
		return ((x)==0) ^ ((y)==0);
	}

	static boolean DIAG(int x, int y)
	{
		return (x)!=0 && (ABS(x)==ABS(y));
	}

	private boolean HORZ2D(int x, int y, int z)
	{
		return HORZ(x, y) && (z)==0;
	}

	private boolean DIAG2D(int x, int y, int z)
	{
		return DIAG(x, y) && (z)==0;
	}

	private boolean HORZ3D(int x, int y, int z)
	{
		return ((HORZ(x, y) && ((ABS(z)==ABS(x)) || (ABS(z)==ABS(y)) || ((z)==0))) || ((x)==0 && (y)==0 && (z)!=0));
	}

	private boolean DIAG3D(int x, int y, int z)
	{
		return DIAG(x, y) && ((z)==0 || ABS(z)==ABS(x));
	}

	//	/* This function interprets the result of TraverseDir(piece...) */
	//lost?

	/* This function sets up the board*/
	private void Init3Dc()
	{
		int thisFile;
		int thisRank;
		int thisLevel;
		int thisColor;
		int[][] count = {{0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0}};
		int thisTitle;
		selectedSquare = new Piece(-1,0,0,1,-1, this);

		/* This structure is mainly for "obviousness"; it is entirely trivial */
		int StartBoard[][][] =
		{ /* The boards */   //[Level][Rank][File]
				{ /* Bottom board */
					{ Piece.galley,  Piece.cannon, Piece.abbey, Piece.prince, Piece.princess, Piece.abbey, Piece.cannon, Piece.galley},
					{   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn},
					{ Piece.galley,  Piece.cannon, Piece.abbey, Piece.prince, Piece.princess, Piece.abbey, Piece.cannon, Piece.galley},
				},
				{ /* Middle board */
					{   Piece.rook, Piece.knight, Piece.bishop,   Piece.king,  Piece.queen, Piece.bishop, Piece.knight,   Piece.rook},
					{   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn},
					{   Piece.rook, Piece.knight, Piece.bishop,   Piece.king,  Piece.queen, Piece.bishop, Piece.knight,   Piece.rook}
				},
				{ /* Top board */
					{ Piece.galley,  Piece.cannon, Piece.abbey, Piece.prince, Piece.princess, Piece.abbey, Piece.cannon, Piece.galley},
					{   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none,   Piece.none},
					{   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn,   Piece.pawn},
					{ Piece.galley,  Piece.cannon, Piece.abbey, Piece.prince, Piece.princess, Piece.abbey, Piece.cannon, Piece.galley},
				}
		}; /* StartBoard */

		for (thisLevel = 0; thisLevel < LEVELS; ++thisLevel)//[Level][Rank][File]
		{
			thisColor = Piece.WHITE;
			for (thisRank = 0; thisRank < RANKS; ++thisRank)
			{
				/* From the 4th rank on is black's half of the board */
				if (thisRank == 4)
					thisColor = Piece.BLACK;
				Piece temp = null;
				for (thisFile = 0; thisFile < FILES; ++thisFile)
				{
					thisTitle = StartBoard[thisLevel][thisRank][thisFile];
					if(thisTitle != Piece.none)
					{
						temp = new Piece(thisTitle, thisLevel, thisRank, thisFile, thisColor, this);
//						Context context = getApplicationContext();

						Board.getMuster()[thisColor][Piece.MusterIdx(Board, thisTitle, count[thisColor][thisTitle])] = temp;
						Board.getBoard()[thisLevel][thisRank][thisFile] = temp;
						temp = null;
						(count[thisColor][thisTitle])++;
					}
				}
			}
		}
		n3DcErr = 0;

		Board.setSQUARE_INVALID(new Piece(0,0,0,0,0, this));
		Board.setSQUARE_EMPTY(new Piece(0,0,0,0,0, this));

	}

	private int MAX_RETRIES = 100; /* Number of times to guess a tricky move */

	//defining FALSE, TRUE and NULL as they are used heaps
	static boolean FALSE = false;
	static boolean TRUE = true;
	static Object NULL = null;

	private int computer = Piece.BLACK;//defaulting to ai as black
	private boolean gamePaused = FALSE;

	public boolean SetupAutoplay(String colourName)
	{
		if (colourName.equals("black") )
			computer = Piece.BLACK;
		else if (colourName.equals("white"))
			computer = Piece.WHITE;
		else
		{
			return FALSE;
		}
		return TRUE;
	}

	public void DoMain3DcLoop()
	{
		Move automove = new Move();
		//  XEvent event;
		boolean retry = FALSE;


		//Can't loop... lets try a once through and see what we can startup...
		//	//  while (firstGFX->mainWindow)
		//	  while (true)//?? game loop
		//	  {
		//		  	//      /* First thing to do: check for end of game! */
		//		  	//	      if (IsGameFinished() && !gamePaused)
		if (IsGameFinished() && !gamePaused)
		{
			FinishGame((Board.getBwToMove() == Piece.BLACK) ? Piece.WHITE : Piece.BLACK);
		}
		if ( (Board.getBwToMove() == computer) && !gamePaused)
		{    	  
			//        if (((retry == FALSE) &&    GenMove(computer, &automove) == TRUE) ||
			//        ((retry == TRUE)  && GenAltMove(computer, &automove) == TRUE))
			//dropping alt move for now... this is plenty messy enough :p
			// not sure i need retry, but i'll leave it for now
//			if (((retry == FALSE) &&    GenMove(computer, automove) == TRUE) ||
//					((retry == TRUE)  && GenAltMove(computer, automove) == TRUE))
			automove = GenMove(computer);
			if(automove != null)
			{
				if ( (Board.getBoard()[ automove.xyzBefore.thisLevel ]
				                             [ automove.xyzBefore.thisRank ]
				                               [ automove.xyzBefore.thisFile ] == NULL ) ||
				                               ( PieceMove( Board.getBoard()[ automove.xyzBefore.thisLevel ]
				                                                              [ automove.xyzBefore.thisRank ]
				                                                                [ automove.xyzBefore.thisFile ],
				                                                                automove.xyzAfter.thisFile,
				                                                                automove.xyzAfter.thisRank,
				                                                                automove.xyzAfter.thisLevel ) ) )
				{
					/* The move was illegal for some reason
					 * (in the future I plan to eliminate all
					 * possibility of getting in here) */
					//                  D( printf( "Can't move from (%i,%i,%i) to (%i,%i,%i)\n",
					//                            automove->xyzBefore.xFile,
					//                            automove->xyzBefore.yRank,
					//                            automove->xyzBefore.zLevel,
					//                            automove->xyzAfter.xFile,
					//                            automove->xyzAfter.yRank,
					//                            automove->xyzAfter.zLevel ) );
					//
					retry = TRUE;
				}
				else /* Move is legit: do it */
				{
					retry = FALSE;
					//                  PrintMove( automove );

					Board.setBwToMove(((computer == Piece.WHITE) ? Piece.BLACK : Piece.WHITE));
				} /* End 'found computer move' */
			} /* Still finding computer's move? */
			else
			{
//			 * Give up, it's too hard for me..
			}
		} /* End computer's move */

		//      if (XtAppPending(XtWidgetToApplicationContext(firstGFX->mainWindow)))
		//        {
		//          XtAppNextEvent(XtWidgetToApplicationContext(firstGFX->mainWindow),
		//                         &event);
		//          XtDispatchEvent(&event);
		//        }
		//
		//      if ((secondGFX != NULL) &&
		//          (XtAppPending(XtWidgetToApplicationContext(secondGFX->mainWindow))))
		//        {
		//          XtAppNextEvent(XtWidgetToApplicationContext(secondGFX->mainWindow),
		//                         &event);
		//          XtDispatchEvent(&event);
		//        }

		//	  } /* End game loop */
	}



	///* Utility functions */

	public String Piece2String( Piece piece )
	{
		String names[] =
		{
				"King",   "Queen",    "Bishop", "Knight", "Rook",
				"Prince", "Princess", "Abbey",  "Cannon", "Galley",
				"Pawn", ""
		};
		return names[piece.thisType];
	}

	public int Computer()
	{
		return computer;
	}

	public void PauseGame()
	{
		gamePaused = TRUE;
	}

	public void ResumeGame()
	{
		gamePaused = FALSE;
	}

	public boolean IsGamePaused()
	{
		return gamePaused;
	}


	private boolean IsGameFinished()
	{
		boolean blackKingVisible, whiteKingVisible,
		blackFirstPrinceVisible, whiteFirstPrinceVisible,
		blackSecondPrinceVisible, whiteSecondPrinceVisible;

		Context context = getApplicationContext();
		CharSequence text = "Black:" + Piece.BLACK 
		+ "\nWhite: " +  Piece.WHITE
		+ "\nKing: " +  Piece.king
		+ "\n...";
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

		blackKingVisible = Board.getMuster()[Piece.BLACK][Piece.MusterIdx(Board, Piece.king, 0)].bVisible;
		whiteKingVisible = Board.getMuster()[Piece.WHITE][Piece.MusterIdx(Board, Piece.king, 0)].bVisible;

		blackFirstPrinceVisible = Board.getMuster()[Piece.BLACK][Piece.MusterIdx(Board, Piece.prince, 0)].bVisible;
		whiteFirstPrinceVisible = Board.getMuster()[Piece.WHITE][Piece.MusterIdx(Board, Piece.prince, 0)].bVisible;

		blackSecondPrinceVisible = Board.getMuster()[Piece.BLACK][Piece.MusterIdx(Board, Piece.prince, 1)].bVisible;
		whiteSecondPrinceVisible = Board.getMuster()[Piece.WHITE][Piece.MusterIdx(Board, Piece.prince, 1)].bVisible;

		if ((!whiteKingVisible || 
				(!whiteFirstPrinceVisible && !whiteSecondPrinceVisible)) ||
				(!blackKingVisible ||
						(!blackFirstPrinceVisible && !blackSecondPrinceVisible)))
		{
			return TRUE;
		}
		return FALSE;
	}

	private void FinishGame(int bwWinner)
	{
		gamePaused = TRUE;
		winString = (bwWinner == Piece.BLACK) ? "Black wins!" : "White wins!";

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(winString)
		.setCancelable(false)
		.setNeutralButton("O.K.", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();

	}

	//	public void PrintMove( Move move )
	//{
	//	  String moveString = null;
	//	  String printMoveString;
	//
	//  if (move != null)
	//  {
	//	  Piece piece, enemy;
	//    Coord pos;
	//
	//  piece = Board.getBoard()[ move.xyzAfter.zLevel] [ move.xyzAfter.yRank ] [ move.xyzAfter.xFile ];
	//
	//if (piece != null)
	//{
	//
	//  enemy = Board.getMuster()[(piece.bwSide == Piece.WHITE) ? Piece.BLACK : Piece.WHITE] [ MusterIdx(Piece.king, 0) ];
	//  pos = enemy.xyzPos;
	//
	//  moveString = "Move: " + piece.nName + " [" 
	//  + move.xyzBefore.zLevel + ","
	//  + move.xyzBefore.yRank + ","
	//  + move.xyzBefore.xFile + "]->["
	//  + move.xyzAfter.zLevel + ","
	//  + move.xyzAfter.zLevel + ","
	//  + move.xyzAfter.zLevel + "]"
	//  + (IsKingChecked(Board,  piece.bwSide ) ? " check!" : "");
	//  
	////DEBUG:
	//mthreeD_Renderer.setDebugText(moveString);
	//  
	////  String checkString;
	////  if(IsKingChecked( piece.bwSide ))
	////	  {
	////	  checkString = " check!"; 
	////	  }else
	////	  {
	////		  checkString =  "";
	////	  }
	//
	//
	//  //no audio alerts, maybe vibe.
	////      /* Display the move: beep if
	////       *  1) the computer or
	////       *  2) the other player in a network game
	////       *     moved or if
	////       *  3) the move resulted in a check
	////       * This is now changed so that there's no beep when the computer
	////       * moves 'cos it's so fast. */
	////      Err3Dc( firstGFX, moveString,
	////             (/* (Computer() == bwToMove) || */
	////              ( (secondGFX != NULL) && (bwToMove == BLACK) ) ||
	////              ( IsKingChecked( piece->bwSide ))) ?
	////             TRUE : FALSE );
	//
	//Err3Dc( moveString,(/* (Computer() == bwToMove) || */
	// ( (Board.getBwToMove() == Piece.BLACK) ) ||
	// ( IsKingChecked( piece.bwSide ))) ? TRUE : FALSE );
	//
	//  
	//  //      if ( secondGFX != NULL )
	////        {
	////          Err3Dc( secondGFX, moveString, (bwToMove == WHITE) ?
	////                 TRUE : FALSE );
	////        }
	////
	////      /* I think that this isn't allowed becase the string is
	////       * still needed by the label widget but it hasn't caused
	////       * any problems so far.. */
	////      free(moveString);
	////    }
	////  else
	////    {
	////      /* Print something, even if out of memory.. */
	////      if ( (Computer() == bwToMove) ||
	////          ( (secondGFX != NULL) && (bwToMove == BLACK)))
	////        Err3Dc(firstGFX, "Opponent has moved", TRUE);
	////      else if ( (secondGFX != NULL) && (bwToMove == WHITE) )
	////        Err3Dc(secondGFX, "Opponent has moved", TRUE);
	//    }
	//  }
	//}
	//	

	
	//i think this might be a duplicate class...
//	public Piece PieceNew(int nType,
//			int x, int y, int z,
//			int col)
//	{
//		return new Piece(nType, x, y, z, col);
//	}


	public void PieceDelete(Piece piece)
	{
		if(Board.getPieceAt(piece.xyzPos.thisLevel, piece.xyzPos.thisRank, piece.xyzPos.thisFile) == piece)
		{//need to do setpieceas too
			Board.getBoard()[piece.xyzPos.thisLevel][piece.xyzPos.thisRank][piece.xyzPos.thisFile] = null;//[Level][Rank][File]
		}
		piece = null;
	}

	//		/*
	//		 * Here down are the specific piece-movement functions
	//		 *
	//		 * These all assume that piece is of the correct type.
	//		 * No check is made and things get very odd if this assumption
	//		 * is contradicted, so be careful.
	//		 */

	private boolean KingMayMove(Piece piece, int newLevel, int newRank, int newFile)//[Level][Rank][File]
	{
		int fileDiff, xCur, xInc;
		int rankDiff;
		int levelDiff;

		//			  xDiff = newLevel - piece.xyzPos.thisFile;
		//			  yDiff = newRank - piece.xyzPos.thisRank;
		//			  zDiff = newFile - piece.xyzPos.thisLevel;

		fileDiff = newFile - piece.xyzPos.thisFile;
		rankDiff = newRank - piece.xyzPos.thisRank;
		levelDiff = newLevel - piece.xyzPos.thisLevel;

		fileDiff = ABS(fileDiff);
		rankDiff = ABS(rankDiff);
		levelDiff = ABS(levelDiff);

		/* Not allowed move more than 1 except when castling */
		if ( (piece.bHasMoved && (fileDiff > 2)) ||
				(rankDiff > 1) || (levelDiff > 1) )
		{
			n3DcErr = E3DcDIST;
			return FALSE;
		}

		//		  /*
		//		   * At this stage, we have determined that, given an empty board,
		//		   * the move is legal.  Now take other pieces into account.
		//		   *///[Level][Rank][File]
		if (piece.FakeMoveAndIsKingChecked( Board, newLevel, newRank, newFile) ||
				( (fileDiff == 2) &&
						piece.FakeMoveAndIsKingChecked( Board, (newFile + piece.xyzPos.thisFile)/2,
								newRank, newLevel ) ))
		{
			n3DcErr = E3DcCHECK;
			return FALSE;
		}

		if (fileDiff == 2)
		{ /* Castling */
			int xRook;

			if (rankDiff > 0 || levelDiff > 0)
			{
				n3DcErr = E3DcSIMPLE;
				return FALSE;
			}

			/*
			 * Determine x-pos of castling rook
			 */
			if (newFile > piece.xyzPos.thisFile)
				xRook = FILES-1;//right edge
			else
				xRook = 0;//left edge

			if (piece.bHasMoved || Board.getPieceAt(1, newRank, xRook).bHasMoved)
			{
				n3DcErr = E3DcMOVED;
				return FALSE;
			}
			//else if (!Board[1][yNew][xRook]) //testing for null???
			//			  else if (!Board[1][yNew][xRook])
			//		        {
			//		          n3DcErr = E3DcSIMPLE;
			//		          return FALSE;
			//		        }

			xInc = ( xRook == 0 ) ? -1 : 1 ;

			for (xCur = piece.xyzPos.thisFile + xInc; xCur != xRook; xCur += xInc)
			{  /* Is the castle blocked? */
				//		          if (Board[1][yNew][xCur])
				if (Board.getBoard()[1][newRank][xCur] != null)
				{
					n3DcErr = E3DcBLOCK;
					return FALSE;
				}
			}
			//
			//		      return CASTLE;
			return true;
		}
		//
		return TRUE;
	}
	//int newLevel, int newRank, int newFile
	private boolean QueenMayMove(Piece piece, int newLevel, int newRank, int newFile)//[Level][Rank][File]
	{	
		//		{
		int fileDiff;
		int rankDiff;
		int levelDiff;

		Piece pDestSquare;

		fileDiff = newFile - piece.xyzPos.thisFile;
		rankDiff = newRank - piece.xyzPos.thisRank;
		levelDiff = newLevel - piece.xyzPos.thisLevel;

		if ((fileDiff > 0 && rankDiff > 0 && (ABS(fileDiff) != ABS(rankDiff))) ||
				(fileDiff > 0 && levelDiff > 0 && (ABS(fileDiff) != ABS(levelDiff))) ||
				(rankDiff > 0 && levelDiff > 0 && (ABS(rankDiff) != ABS(levelDiff))))
		{
			n3DcErr = E3DcSIMPLE;
			return false;
		}
		//
		//		  /*
		//		   * At this stage, we have determined that, given an empty board,
		//		   * the move is legal.  Now take other pieces into account.
		//		   */
		pDestSquare = piece.TraverseDir(Board, fileDiff, rankDiff, levelDiff,
				MAX(ABS(fileDiff), MAX(ABS(rankDiff), ABS(levelDiff))));
		return piece.IsMoveLegal(Board, pDestSquare);
	}

	//int newLevel, int newRank, int newFile
	private boolean BishopMayMove(Piece piece, int newLevel, int newRank, int newFile)
	{

		int fileDiff;
		int rankDiff;
		int levelDiff;
		Piece pDestSquare;

		fileDiff = 
			rankDiff = newRank - piece.xyzPos.thisRank;
		levelDiff = newLevel - piece.xyzPos.thisLevel;

		if (!DIAG3D(fileDiff, rankDiff, levelDiff))
		{
			n3DcErr = E3DcSIMPLE;
			return FALSE;
		}

		//		  /*
		//		   * At this stage, we have determined that, given an empty board,
		//		   * the move is legal.  Now take other pieces into account.
		//		   */
		pDestSquare = piece.TraverseDir(Board, fileDiff, rankDiff, levelDiff, MAX(ABS(fileDiff), ABS(rankDiff)));
		return piece.IsMoveLegal(Board, pDestSquare);
	}

	//int newLevel, int newRank, int newFile
	private boolean KnightMayMove(Piece piece, int newLevel, int newRank, int newFile)
	{	

		int fileDiff;
		int rankDiff;

		if (newLevel != piece.xyzPos.thisLevel)
		{
			n3DcErr = E3DcLEVEL;
			return FALSE; /* Knights may not change level */
		}

		fileDiff = newFile - piece.xyzPos.thisFile;
		rankDiff = newRank - piece.xyzPos.thisRank;

		fileDiff = ABS(fileDiff);
		rankDiff = ABS(rankDiff);

		if ((fileDiff == 0) || (rankDiff == 0) || ((fileDiff + rankDiff) != 3))
			return FALSE;

		return TRUE;
	}

	//int newLevel, int newRank, int newFile
	private boolean RookMayMove(Piece piece, int newLevel, int newRank, int newFile)
	{

		int fileDiff;
		int rankDiff;
		int levelDiff;
		Piece pDestSquare;

		fileDiff = newFile - piece.xyzPos.thisFile;
		rankDiff = newRank - piece.xyzPos.thisRank;
		levelDiff = newLevel - piece.xyzPos.thisLevel;

		if (!HORZ3D(fileDiff, rankDiff, levelDiff))
		{// leaving static helper methods in f-r-l format
			n3DcErr = E3DcSIMPLE;
			return FALSE;
		}
		//
		//		  /*
		//		   * At this stage, we have determined that, given an empty board,
		//		   * the move is legal.  Now take other pieces into account.
		//		   */
		pDestSquare = piece.TraverseDir(Board, levelDiff, rankDiff, fileDiff,
				MAX(ABS(levelDiff), MAX(ABS(rankDiff), ABS(fileDiff))));
		return piece.IsMoveLegal(Board, pDestSquare);
	}

	//int newLevel, int newRank, int newFile
	private boolean PrinceMayMove(Piece piece, int newLevel, int newRank, int newFile)
	{

		int fileDiff;
		int rankDiff;

		if (newFile != piece.xyzPos.thisLevel)
		{
			n3DcErr = E3DcLEVEL;
			return FALSE; /* Princes may not change level */
		}

		fileDiff = newFile - piece.xyzPos.thisFile;
		rankDiff = newRank - piece.xyzPos.thisRank;

		fileDiff = ABS(fileDiff);
		rankDiff = ABS(rankDiff);

		if (fileDiff > 1 || rankDiff > 1) /* Not allowed move more than 1 */
		{
			n3DcErr = E3DcDIST;
			return FALSE;
		}

		return TRUE;
	}

	//int newLevel, int newRank, int newFile
	private boolean PrincessMayMove(Piece piece, int newLevel, int newRank, int newFile)
	{

		int fileDiff;
		int rankDiff;
		Piece pDestSquare;

		if (newLevel != piece.xyzPos.thisLevel)
		{
			n3DcErr = E3DcLEVEL;
			return FALSE; /* Princesses may not change level */
		}

		fileDiff = newFile - piece.xyzPos.thisFile;
		rankDiff = newRank - piece.xyzPos.thisRank;

		if (fileDiff > 0 && rankDiff > 0 && (ABS(fileDiff) != ABS(rankDiff)))
		{
			n3DcErr = E3DcSIMPLE;
			return FALSE;
		}

		//		  /*
		//		   * At this stage, we have determined that, given an empty board,
		//		   * the move is legal.  Now take other pieces into account.
		//		   */
		pDestSquare = piece.TraverseDir(Board, fileDiff, rankDiff, 0,
				MAX(ABS(fileDiff), ABS(rankDiff)));
		return piece.IsMoveLegal(Board, pDestSquare);
	}

	//int newLevel, int newRank, int newFile
	private boolean AbbeyMayMove(Piece piece, int newLevel, int newRank, int newFile)
	{

		int fileDiff;
		int rankDiff;
		Piece pDestSquare;

		if (newLevel != piece.xyzPos.thisLevel)
		{
			n3DcErr = E3DcLEVEL;
			return FALSE; /* Abbies may not change level */
		}

		fileDiff = newFile - piece.xyzPos.thisFile;
		rankDiff = newRank - piece.xyzPos.thisRank;

		if (!DIAG(fileDiff, rankDiff))
		{
			n3DcErr = E3DcSIMPLE;
			return FALSE;
		}

		//		  /*
		//		   * At this stage, we have determined that, given an empty board,
		//		   * the move is legal.  Now take other pieces into account.
		//		   */
		pDestSquare = piece.TraverseDir(Board, fileDiff, rankDiff, 0, MAX(ABS(fileDiff), ABS(rankDiff)));
		return piece.IsMoveLegal(Board, pDestSquare);
	}

	//int newLevel, int newRank, int newFile
	private boolean CannonMayMove(Piece piece, int newLevel, int newRank, int newFile)
	{

		int fileDiff;
		int rankDiff;
		int levelDiff;

		fileDiff = newFile - piece.xyzPos.thisFile;
		rankDiff = newRank - piece.xyzPos.thisRank;
		levelDiff = newLevel - piece.xyzPos.thisLevel;

		fileDiff = ABS(fileDiff);
		rankDiff = ABS(rankDiff);
		levelDiff = ABS(levelDiff);

		if (((fileDiff + rankDiff + levelDiff) != 6) || ((fileDiff != 3) && (rankDiff != 3)) || ((fileDiff != 2) && (rankDiff != 2) && (levelDiff != 2)))
		{
			n3DcErr = E3DcSIMPLE;
			return FALSE;
		}

		return TRUE;
	}

	//int newLevel, int newRank, int newFile
	private boolean GalleyMayMove(Piece piece, int newLevel, int newRank, int newFile)
	{

		int fileDiff;
		int rankDiff;
		Piece pDestSquare;

		if (newLevel != piece.xyzPos.thisLevel)
		{
			n3DcErr = E3DcLEVEL;
			return FALSE; /* Gallies may not change level */
		}

		fileDiff = newFile - piece.xyzPos.thisFile;
		rankDiff = newRank - piece.xyzPos.thisRank;

		if (!HORZ(fileDiff, rankDiff))
		{
			n3DcErr = E3DcSIMPLE;
			return FALSE;
		}

		//		  /*
		//		   * At this stage, we have determined that, given an empty board,
		//		   * the move is legal.  Now take other pieces into account.
		//		   */
		pDestSquare = piece.TraverseDir(Board, fileDiff, rankDiff, 0, MAX(ABS(fileDiff), ABS(rankDiff)));
		return piece.IsMoveLegal(Board, pDestSquare);
	}

	//int newLevel, int newRank, int newFile
	private boolean PawnMayMove(Piece piece, int newLevel, int newRank, int newFile)
	{

		int fileDiff;
		int rankDiff, rankInc;

		if (newLevel != piece.xyzPos.thisLevel)
		{
			n3DcErr = E3DcLEVEL;
			return FALSE; /* Pawns may not change level */
		}

		fileDiff = newFile - piece.xyzPos.thisFile;
		rankInc = rankDiff = newRank - piece.xyzPos.thisRank;

		fileDiff = ABS(fileDiff);
		rankDiff = ABS(rankDiff);

		//		  /*
		//		   * Pawns must move at least 1 forward
		//		   */
		if ((rankDiff == 0) || ((rankInc < 0) && (piece.thisSide == Piece.WHITE)) || ((rankInc > 0) && (piece.thisSide == Piece.BLACK))) /* Moving backwards */
		{
			n3DcErr = E3DcSIMPLE;
			return FALSE;
		}

		//		  /* Check the definitely-illegal moves first.. */
		if (fileDiff > 1 || (fileDiff == 1 && rankDiff != 1))
		{
			n3DcErr = E3DcSIMPLE;
			return FALSE;
		}

		//		  /*
		//		   * It is difficult to cater for 'en passant' in the middle of a
		//		   * conditional.  So, against all convention laid out in other
		//		   * rules functions, I am checking a move and returning true if it
		//		   * is valid, rather than returning FALSE if it is invalid.
		//		   */
		//		#if 0
		//		#endif /* 0 */
		if (fileDiff == 1 && rankDiff == 1 && Board.getPieceAt(newFile,newRank,newLevel) != null)
		{ /* En passant? */
			//		        if (Board[zNew][yNew - yInc][xNew] && /* 'Takable' piece */
			//		            Board[zNew][yNew - yInc][xNew]->nName == pawn && /* Is pawn */
			//		            Board[zNew][yNew - yInc][xNew]->bwSide != piece->bwSide && /* Is enemy */
			//		            1) /* Dummy line to reduce no. of changes */

			if (Board.getPieceAt(newLevel,newRank - rankInc,newFile) != null && /* 'Takable' piece */
					(Board.getPieceAt(newLevel,newRank - rankInc,newFile).thisType == Piece.pawn) && /* Is pawn */
					(Board.getPieceAt(newLevel,newRank - rankInc,newFile).thisSide != piece.thisSide) ) /* Dummy line to reduce no. of changes */
			{
				//		            return EnPASSANT;
				return true;
			}
			else
			{
				n3DcErr = E3DcSIMPLE;
				return FALSE;
			}
		}

		//		  /*
		//		   * Pawns can not move forward under these conditions:
		//		   *  They move more than 2
		//		   *  They move more than 1 and they have already moved
		//		   *  They attempt to take any piece (catered for in next conditional)
		//		   */
		if (rankDiff > 2 || /* Move too far */
				(piece.bHasMoved && rankDiff == 2)) /* Move too far */
		{
			n3DcErr = E3DcDIST;
			return FALSE;
		}

		//		  /*
		//		   * Pawns may not take anything under these conditions:
		//		   *  They do not move diagonally forward one space
		//		   *  The victim is an ally
		//		   */
		if (Board.getPieceAt(newLevel,newRank,newFile) != null  && /* Taking something */
				(!(fileDiff == 1 && rankDiff == 1) || /* Not moving diagonally */
						Board.getPieceAt(newLevel,newRank,newFile).thisSide == piece.thisSide))
		{
			n3DcErr = E3DcSIMPLE;
			return FALSE;
		}

		//		  /* Check for possible promotion */
		//		  if ((yNew == FILES-1 && piece->bwSide == WHITE) ||
		//		      (yNew == 0 && piece->bwSide == BLACK))
		//		    return PROMOTE;
		if ((newRank == FILES-1 && piece.thisSide == Piece.WHITE) ||
				(newRank == 0 && piece.thisSide == Piece.BLACK))
			return true;

		//
		return TRUE;
	}


	// TODO: promotion code	  
	//	  switch (piece.nName)
	//	    {
	//	    case king:
	//	      retval = KingMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    case queen:
	//	      retval = QueenMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    case bishop:
	//	      retval = BishopMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    case knight:
	//	      retval = KnightMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    case rook:
	//	      retval = RookMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    case prince:
	//	      retval = PrinceMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    case princess:
	//	      retval = PrincessMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    case abbey:
	//	      retval = AbbeyMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    case cannon:
	//	      retval = CannonMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    case galley:
	//	      retval = GalleyMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    case pawn:
	//	      retval = PawnMayMove(piece, xNew, yNew, zNew);
	//	      break;
	//	    default:
	//	      retval = FALSE;
	//	      n3DcErr = E3DcSIMPLE;
	//	    }
	//   //TODO: not sure what is happening here yet... check for check after promotion of pawn
	//	  if ( retval != FALSE )
	//	    {
	//	      if ( FakeMoveAndIsKingChecked(piece, xNew, yNew, zNew) == TRUE )
	//	        {
	//	          n3DcErr = E3DcCHECK;
	//	          return FALSE;
	//	        }
	//	    }
	//
	//	  return retval;
	//	}




	//	 * Execute the move
	//int newLevel, int newRank, int newFile
	private boolean PieceMove(Piece piece, int newLevel, int newRank, int newFile)
	{
		Move thisMove = new Move();
		//	  Boolean moveType; /* Not quite Boolean... */
//		boolean moveType; /* Not quite Boolean... */

		if (!piece.PieceMayMove(Board, newLevel, newRank, newFile))
			return FALSE;

		//	   * Keep record of move
		thisMove.xyzBefore.thisFile = piece.xyzPos.thisFile;
		thisMove.xyzBefore.thisRank = piece.xyzPos.thisRank;
		thisMove.xyzBefore.thisLevel = piece.xyzPos.thisLevel;

		thisMove.xyzAfter.thisFile = newFile;
		thisMove.xyzAfter.thisRank = newRank;
		thisMove.xyzAfter.thisLevel = newLevel;

		//TODO: EnPassant handling
		//	  if (moveType == EnPASSANT)
		//	    {
		//	      thisMove.nHadMoved = EnPASSANT;
		//	      thisMove.pVictim = Board[zNew][yNew + (piece->bwSide == WHITE ?
		//	                                             -1 : 1)][xNew];
		//	    }
		//	  else if (moveType == CASTLE)
		//	    {
		//	      thisMove.nHadMoved = CASTLE;
		//	      thisMove.pVictim = Board[1][yNew][xNew < ((FILES-1)/2) ? 0 : FILES-1];
		//	    }
		//	  else if (moveType == PROMOTE)
		//	    {
		//	      thisMove.nHadMoved = PROMOTE;
		//	      thisMove.pVictim = Board[zNew][yNew][xNew];
		//	    }
		//	  else
		//	    {
		//      thisMove.nHadMoved = piece->bHasMoved;
		//      thisMove.pVictim = Board[zNew][yNew][xNew];
		thisMove.nHadMoved = piece.bHasMoved;
		thisMove.pVictim = Board.getPieceAt(newLevel,newRank,newFile);
		//	    }

		//	  StackPush(MoveStack, thisMove);
		gameMoves.push(thisMove);
		//	  currentPossibleMoves.push(thisMove); .... oops wrong stack

		//        ...O.k. time to impliment stack
		//	  piece->bHasMoved = TRUE;
		//	  PieceDisplay(piece, FALSE);
		//	  Board[piece->xyzPos.zLevel][piece->xyzPos.yRank][piece->xyzPos.xFile] = NULL;

		piece.bHasMoved = TRUE;
		//	  PieceDisplay(piece, FALSE);
		Board.setPieceAt(thisMove.xyzBefore.thisLevel,thisMove.xyzBefore.thisRank,thisMove.xyzBefore.thisFile, null);

		//	  if (Board[zNew][yNew][xNew]) /* Kill victim */
		if (thisMove.pVictim != null) /* Kill victim */
		{
			Board.getPieceAt(newLevel,newRank,newFile).bVisible = FALSE;
			Board.setPieceAt(newLevel,newRank,newFile, null);
			//		      PieceDisplay(Board[zNew][yNew][xNew], FALSE);
			//		      Board[zNew][yNew][xNew]->bVisible = FALSE;
			//		      Board[zNew][yNew][xNew] = NULL;
		}

		//	  Board[zNew][yNew][xNew] = piece;
		//	  piece->xyzPos.xFile = xNew;
		//	  piece->xyzPos.yRank = yNew;
		//	  piece->xyzPos.zLevel = zNew;
		//	  PieceDisplay(piece, TRUE);
		Board.setPieceAt(newLevel,newRank,newFile, piece);
		piece.xyzPos = new Coord(newLevel,newRank,newFile);

		//still skipping special rules for now
		//	  /* Now move any special pieces */
		//	  if (moveType == CASTLE)
		//	    {
		//	      int xRookSrc, xRookDest;
		//
		//	      /* If xNew on right of board then move to left
		//	       * else move to right */
		//	      if (xNew > (FILES/2))
		//	        {
		//	          xRookSrc = FILES -1;
		//	          xRookDest = xNew -1;
		//	        }
		//	      else
		//	        {
		//	          xRookSrc = 0;
		//	          xRookDest = xNew +1;
		//	        }
		//
		//	      PieceDisplay(Board[1][yNew][xRookSrc], FALSE);
		//
		//	      Board[1][yNew][xRookDest] = Board[1][yNew][xRookSrc];
		//	      Board[1][yNew][xRookSrc] = NULL;
		//
		//	      (Board[1][yNew][xRookDest])->xyzPos.xFile = xRookDest;
		//	      (Board[1][yNew][xRookDest])->bHasMoved = TRUE;
		//
		//	      PieceDisplay(Board[1][yNew][xRookDest], TRUE);
		//	    }
		//	  else if (moveType == EnPASSANT)
		//	    {
		//	      int yPawnSrc;
		//
		//	      /* If yNew is forward of half-way then victim is back one
		//	       * else it is forward one */
		//	      yPawnSrc = (yNew > (RANKS/2) ? yNew-1 : yNew+1);
		//	      PieceDisplay(Board[zNew][yPawnSrc][xNew], FALSE);
		//	      Board[zNew][yPawnSrc][xNew]->bVisible = FALSE;
		//	      Board[zNew][yPawnSrc][xNew] = NULL;
		//	    }
		//
		//	#if 0
		//	  /* I think that this code is obsolete.. */
		//	  /* Check that the king isn't in check */
		//	  if (IsKingChecked( piece->bwSide ))
		//	    {
		//	      /* Oops, this move puts the king in check;
		//	       * it's illegal, so undo it */
		//	      PieceUndo();
		//	      n3DcErr = E3DcCHECK;
		//	      return FALSE;
		//	    }
		//	#endif
		//
		//	  /* If this bit is up with EnPASSANT and CASTLE, then the
		//	   * promotion dialog pops up even though the promotion is
		//	   * illegal.  A promotion doesn't affect whether or not
		//	   * the opponent checks your king (even if you promote to
		//	   * cannon or something, you're still in the same place as
		//	   * the dissappearing pawn..) so it works out better all
		//	   * around if we just do it here. */
		//	  if (moveType == PROMOTE)
		//	    {
		//	      PieceDisplay(piece, FALSE);
		//	      PiecePromote(piece); /* This function asks for promotion type, etc. */
		//	    }
		//
		return TRUE;
	}
	//
	//	/*
	//	 * Undo the move
	//	 */
	//	Global Boolean
	//	PieceUndo(void)
	//	{
	//	  Move *move;
	//	  Colour bwMoved, bwTaken;
	//	  Coord src, dest;
	//
	//	  move = StackPop(MoveStack);
	//	  if (move == NULL)
	//	    return FALSE;
	//
	//	  src = move->xyzAfter;
	//	  dest = move->xyzBefore;
	//
	//	  bwMoved = Board[src.zLevel][src.yRank][src.xFile]->bwSide;
	//	  bwTaken = (bwMoved == WHITE ? BLACK : WHITE);
	//
	//	  /* Clear the "moved-to" square */
	//	  PieceDisplay(Board[src.zLevel][src.yRank][src.xFile], FALSE);
	//
	//	  /* Move the "moved" piece back */
	//	  Board[dest.zLevel][dest.yRank][dest.xFile] =
	//	    Board[src.zLevel][src.yRank][src.xFile];
	//	  (Board[dest.zLevel][dest.yRank][dest.xFile])->xyzPos = dest;
	//
	//	  Board[src.zLevel][src.yRank][src.xFile] = NULL;
	//
	//	  switch (move->nHadMoved)
	//	    {
	//	    case PROMOTE:
	//	      /* This piece was promoted from a pawn: demote it */
	//	      Board[dest.zLevel][dest.yRank][dest.xFile]->nName = pawn;
	//	      (Board[dest.zLevel][dest.yRank][dest.xFile])->bHasMoved = TRUE;
	//	      break;
	//
	//	    case CASTLE:
	//	      {
	//	        int xRookSrc, xRookDest; /* xRookDest is beside edge */
	//
	//	        /* The move undone was a castle */
	//	        /* The king is back in the right place; now
	//	         * fix the rook */
	//	        if (src.xFile < dest.xFile)
	//	          {
	//	          /* Castled to a smaller-id square (Queen's side for white,
	//	           * King's side for black) */
	//	            xRookSrc = dest.xFile -1;
	//	            xRookDest = 0;
	//	          }
	//	        else
	//	          {
	//	          /* Castled to a larger-id square (Queen's side for black,
	//	           * King's side for white) */
	//	            xRookSrc = dest.xFile +1;
	//	            xRookDest = FILES -1;
	//	          }
	//	            
	//	        PieceDisplay(Board[1][dest.yRank][xRookSrc], FALSE);
	//	        Board[1][dest.yRank][xRookDest] = Board[1][dest.yRank][xRookSrc];
	//	        Board[1][dest.yRank][xRookSrc] = NULL;
	//	        (Board[1][dest.yRank][xRookDest])->xyzPos.xFile = xRookDest;
	//	        (Board[1][dest.yRank][xRookDest])->xyzPos.yRank = dest.yRank;
	//	        (Board[1][dest.yRank][xRookDest])->xyzPos.zLevel = 1;
	//	        PieceDisplay(Board[1][dest.yRank][xRookDest], TRUE);
	//
	//	        /* And finally---reset the bHasMoved flags */
	//	        (Board[1][dest.yRank][dest.xFile])->bHasMoved = FALSE;
	//	        (Board[1][dest.yRank][xRookDest])->bHasMoved = FALSE;
	//	        }
	//	      break;
	//
	//	    case EnPASSANT:
	//	      FallThrough();
	//
	//	    default:
	//	      (Board[dest.zLevel][dest.yRank][dest.xFile])->bHasMoved =
	//	        move->nHadMoved;
	//	    }
	//
	//	  /* Draw the piece in its original space */
	//	  PieceDisplay(Board[dest.zLevel][dest.yRank][dest.xFile], TRUE);
	//
	//	  /* Put any taken piece back */
	//	  if (move->pVictim)
	//	    {
	//	      Coord srcPos;
	//
	//	      /* Don't use src as the victim's square, as it could have
	//	       * been en passant */
	//	      srcPos = move->pVictim->xyzPos;
	//
	//	      Board[srcPos.zLevel][srcPos.yRank][srcPos.xFile] = move->pVictim;
	//	      Board[srcPos.zLevel][srcPos.yRank][srcPos.xFile]->bVisible = TRUE;
	//
	//	      PieceDisplay(Board[srcPos.zLevel][srcPos.yRank][srcPos.xFile], TRUE);
	//	    }
	//
	//	  free(move);
	//
	//	  return TRUE;
	//	}
	//



	//Global void
	//StackDelete(stack *s)
	public void StackDelete(Stack s)
	{
		//while(StackPop(s) != NULL)
		//nop();
		//
		//free(s);
		aiMoves = null;
		//		return;
	}



	///* Don't delete returned value; it's still on the stack! */
	//Global Move *
	//StackPeek(stack *s, int numMoves)
	public Move StackPeek(int numMoves)
	{
		//struct stack_el *oldEl;

		//if (numMoves >= s->nSize)
		//		//return NULL;
		//		if (numMoves >= stack.size())
		if (numMoves >= aiMoves.size())
		{
			return null;
		}

		//for (oldEl = s->top; numMoves > 0; --numMoves)
		//oldEl = oldEl->below;

		return (Move)aiMoves.elementAt(numMoves);
		//return oldEl->mvt;
	}


	private Vibrator vibe;

	public int Err3Dc( String pszLeader, Boolean beep )
	{
		//		  char *err;
//		String err = "";

		/*
		 * All strings are designed to be printed thus:
		 *      printf("That piece %s.\n");
		 */
		//		  error_t ERRORS[] = {
		error_t ERRORS[] = {
				new error_t(E3DcSIMPLE, "may not move thus"),
				new error_t(E3DcLEVEL, "may not move vertically"),
				new error_t(E3DcCHECK, "would place your king in check"),
				new error_t(E3DcDIST, "may not move that far"),
				new error_t(E3DcINVIS, "is not currently on the board"),
				new error_t(E3DcBLOCK, "is blocked from moving there"),
				new error_t(E3DcMOVED, "has already moved")
		};

		if (beep)
		{
			vibe.vibrate(300);
		}
		//	    XBell(XtDisplay(gfx->mainWindow), 0);

		//	  if (pszLeader == NULL)
		if (pszLeader == null)
		{
			//	      XtVaSetValues(gfx->remark,
			//	                    XtNlabel, "",
			//	                    NULL);
			return 0;
		}

		//	  err = (char *)malloc(strlen(pszLeader) + 40);
		//	  if (!err)
		//	    return 1;

		//	  sprintf(err, pszLeader, ERRORS[n3DcErr].pszErrStr);
		//	  Context context = getApplicationContext();
		//	  CharSequence text = "ERR: " + err + "\nPSZ: " + pszLeader + "\nTXT: " + ERRORS[n3DcErr].pszErrStr;
		//	  int duration = Toast.LENGTH_LONG;
		//	  Toast toast = Toast.makeText(context, text, duration);
		//	  toast.show();
		//Haha, funny our debug lines converged here :O

		//	  XtVaSetValues(gfx->remark,
		//	                XtNlabel, err,
		//	                NULL);

		//	  free(err);
		return 0;
	}

	//	/* Prompt for piece type to which to promote the pawn */
	public void PiecePromote(Piece piece)
	{
		//	  Widget dialog, list;
		//	  GfxInfo *gfx;
		//
		//	#define PROMOTABLES 8
		//	  static char *types[] =
		//	    {
		//	      "Queen", "Rook", "Bishop", "Knight",
		//	      "Princess", "Galley", "Abbey", "Cannon",
		//	      NULL
		//	    };
		//
		//	  if ( Computer() == piece->bwSide )
		//	    {
		//	      piece->nName = queen; /* This saves many headaches */
		//	      PieceDisplay( piece, TRUE );
		//	      return;
		//	    }
		//
		//	  PauseGame(); /* Suspend thinking */
		//
		//	  gfx = firstGFX;
		//	  if ( (secondGFX != NULL) &&
		//	       (piece->bwSide == BLACK) )
		//	    gfx = secondGFX;
		//
		//	  dialog = XtCreatePopupShell("Promotion", transientShellWidgetClass,
		//	                              gfx->mainWindow, NULL, 0);
		//
		//	  list = XtVaCreateManagedWidget("list", listWidgetClass, dialog,
		//	                                 XtNlist, types,
		//	                                 XtNnumberStrings, PROMOTABLES,
		//	                                 XtNverticalList, TRUE,
		//	                                 XtNforceColumns, TRUE,
		//	                                 XtNdefaultColumns, 1,
		//	                                 NULL);
		//
		//	  XtAddCallback(list, XtNcallback, PromotePiece, (XtPointer)piece);
		//	  XtManageChild(dialog);
		//
		return;
	}

	//	/* Update the muster count for the given type/colour in the muster window */
	//	Global void
	//	UpdateMuster(Colour bwSide, Title nType, Bool redisplay)
//	public int UpdateMuster(int bwSide, int nType, Boolean redisplay)
//	{
//		int count, i, curX, curY;
//		//	  Dimension mWidth, mHeight;
//		//	  char cnt[2] = {' ', '0'};
//		//	  GfxInfo *gfx;
//		//
//		//	  gfx = firstGFX;
//		//	  while ( gfx != NULL )
//		//	    {
//		//	      XSetClipMask(XtDisplay(gfx->muster), gfx->gc, None);
//		//	      XSetForeground(XtDisplay(gfx->muster), gfx->gc, gfx->blackPixel);
//		//
//		count = 0;
//		for (i = 0; i < Board.getTitleCount()[nType]; ++i)
//		{
//			if (Board.getMuster()[bwSide][Piece.MusterIdx(Board, nType, i)].bVisible)
//			{
//				++count;
//			}
//		}
//		
//		return count;
//		//
//		//	      cnt[0] = '0' + (count / 10);
//		//	      cnt[1] = '0' + (count % 10);
//		//	      if (cnt[0] == '0')
//		//	        cnt[0] = ' ';
//		//
//		//	      XtVaGetValues(gfx->muster,
//		//	                    XtNheight, &mHeight,
//		//	                    XtNwidth, &mWidth,
//		//	                    NULL);
//		//
//		//	      curY = (((mHeight / 5) - XPM_SIZE) / 2) + ((mHeight % 5) / 2) + 2;
//		//	      if (nType == pawn)
//		//	        {
//		//	          curX = (((mWidth / 2) - XPM_SIZE) / 2) + ((mWidth % 2) / 2) + 10;
//		//	          if (bwSide == BLACK)
//		//	            curX += mWidth / 2;
//		//	          curY += (2 * (mHeight / 5));
//		//	        }
//		//	      else if (bwSide == WHITE)
//		//	        {
//		//	          curX = (((mWidth / 5) - XPM_SIZE) / 2) + ((mWidth % 5) / 2) + 10;
//		//	          curX += (nType % 5) * (mWidth / 5);
//		//	          curY += ((mHeight / 5) * (nType / 5));
//		//	        }
//		//	      else /* bwSide == BLACK */
//		//	        {
//		//	          curX = (((mWidth / 5) - XPM_SIZE) / 2) + ((mWidth % 5) / 2) + 10;
//		//	          curX += (nType % 5) * (mWidth / 5);
//		//	          curY += (4 * bwSide * (mHeight / 5)) - ((mHeight / 5) * (nType / 5));
//		//	        }
//		//
//		//	      XClearArea(XtDisplay(gfx->muster), XtWindow(gfx->muster),
//		//	                 curX, curY - gfx->font->ascent,
//		//	                 XTextWidth(gfx->font, "MM", 2),
//		//	                 gfx->font->ascent + gfx->font->descent,
//		//	                 redisplay);
//		//	      XDrawString(XtDisplay(gfx->muster), XtWindow(gfx->muster),
//		//	                  gfx->gc, curX, curY,
//		//	                  cnt, 2);
//		//
//		//	      if ( gfx == firstGFX )
//		//	        gfx = secondGFX;
//		//	      else
//		//	        gfx = NULL;
//		//	    }
//		//
//		//	  return;
//	}

	//	
	//	/*
	//	 * The TCP interface for 3Dc
	//	 */
	//	/*
	//
	//	    3Dc, a game of 3-Dimensional Chess
	//	    Copyright (C) 1995  Paul Hicks
	//	    E-Mail: paulh@euristix.ie
	//	*/
	//
	//	#include "machine.h"
	//
	//	/* AutoInclude all necessary files */
	//	#include "3Dc.h"
	//
	//	Local GfxInfo GFX2;
	//
	//	Global Boolean
	//	Open2ndDisplay(const char *displayName)
	//	{
	//	  XtAppContext app;
	//	  XColor col, dummy;
	//	  int x;
	//	  Display *display;
	//
	//	  secondGFX = &GFX2;
	//
	//	  app = XtCreateApplicationContext();
	//	  x = 0;
	//	  display = XtOpenDisplay(app, displayName, "3Dc", "3Dc", NULL, 0, &x, NULL);
	//	  secondGFX->mainWindow =
	//	    XtAppCreateShell("3Dc", "3Dc", applicationShellWidgetClass, display,
	//	                     NULL, 0);
	//
	//	#ifdef FONTCURSOR
	//	  secondGFX->monoGC = NULL;
	//	#endif /* FONTCURSOR */
	//	  secondGFX->gc = XDefaultGCOfScreen(XtScreen(secondGFX->mainWindow));
	//	  XSetFunction(display, secondGFX->gc, GXcopy);
	//
	//	  secondGFX->whitePixel = WhitePixelOfScreen(XtScreen(secondGFX->mainWindow));
	//	  secondGFX->blackPixel = BlackPixelOfScreen(XtScreen(secondGFX->mainWindow));
	//
	//	  /* Make two attempts at getting a grey colour. */
	//	  if (!XAllocNamedColor(display,
	//	                    DefaultColormapOfScreen(XtScreen(secondGFX->mainWindow)),
	//	                        "grey", &col, &dummy))
	//	    {
	//	      if (!XAllocNamedColor(display,
	//	                 DefaultColormapOfScreen(XtScreen(secondGFX->mainWindow)),
	//	                            "light grey", &col, &dummy))
	//	        secondGFX->greyPixel = secondGFX->blackPixel;
	//	      else
	//	        secondGFX->greyPixel = col.pixel;
	//	    }
	//	  else
	//	    secondGFX->greyPixel = col.pixel;
	//
	//	  if (InitPixmaps( secondGFX ))
	//	    return 1;
	//	  if (InitMainWindow( secondGFX ))
	//	    return 1;
	//	  if (InitBoardWindows( secondGFX ))
	//	    return 1;
	//
	//	  XtRealizeWidget(secondGFX->mainWindow);
	//
	//	  return 0;
	//	}	


	//	 * engine.c
	//	 * The rules engine for 3Dc.
	//	    Copyright (C) 1995  Paul Hicks
	//	    E-Mail: paulh@euristix.ie



	//	/* Go dist in given direction.  Direction is positive, negative, 0,
	//	 * with obvious meanings (think of the axes).
	//	 * Return SQUARE_EMPTY, SQUARE_INVALID, or a pointer to the piece
	//	 * first encountered (even if it is before the "destination"
	//	 * location).
	//	 *
	//	 * SQUARE_EMPTY and SQUARE_INVALID are of type (Piece *); the only
	//	 * legitimate value in them is xyzPos which is the coord of the
	//	 * square in question (SQUARE_EMPTY) or the coord of the last
	//	 * legitimate square on the route (SQAURE_INVALID).  If any or all
	//	 * members of the xyzPos struct are equal to UINT_MAX then there was
	//	 * error which utterly precludes moving (e.g. dist == 0).
	//	 */







	//	/* DrawingA.c: The DrawingArea Widget Methods */
	//	static char defaultTranslations[] = "<BtnDown>: input() \n <BtnUp>: input() \n <KeyDown>: input() \n <KeyUp>: input() \n <Motion>: motion() \n <Configure>: resize()";
	//	static XtActionsRec actionsList[] = {
	//	  { "input",  (XtActionProc)input_draw },
	//	  { "motion", (XtActionProc)motion_draw },
	//	  { "resize", (XtActionProc)resize_draw },
	//	};
	//
	//	/* Default instance record values */
	//	static XtResource resources[] = {
	//	  {XtNexposeCallback, XtCCallback, XtRCallback, sizeof(caddr_t),
	//	     XtOffset(DrawingAreaWidget, drawing_area.expose_callback), 
	//	     XtRCallback, NULL },
	//	  {XtNinputCallback, XtCCallback, XtRCallback, sizeof(caddr_t),
	//	     XtOffset(DrawingAreaWidget, drawing_area.input_callback), 
	//	     XtRCallback, NULL },
	//	  {XtNmotionCallback, XtCCallback, XtRCallback, sizeof(caddr_t),
	//	     XtOffset(DrawingAreaWidget, drawing_area.motion_callback), 
	//	     XtRCallback, NULL },
	//	  {XtNresizeCallback, XtCCallback, XtRCallback, sizeof(caddr_t),
	//	     XtOffset(DrawingAreaWidget, drawing_area.resize_callback), 
	//	     XtRCallback, NULL },
	//	};
	//
	//
	//	DrawingAreaClassRec drawingAreaClassRec = {
	//	  /* CoreClassPart */
	//	{
	//	  (WidgetClass) &simpleClassRec,	/* superclass		  */	
	//	    "DrawingArea",			/* class_name		  */
	//	    sizeof(DrawingAreaRec),		/* size			  */
	//	    NULL,				/* class_initialize	  */
	//	    NULL,				/* class_part_initialize  */
	//	    FALSE,				/* class_inited		  */
	//	    Initialize,				/* initialize		  */
	//	    NULL,				/* initialize_hook	  */
	//	    XtInheritRealize,			/* realize		  */
	//	    actionsList,			/* actions		  */
	//	    XtNumber(actionsList),		/* num_actions		  */
	//	    resources,				/* resources		  */
	//	    XtNumber(resources),		/* resource_count	  */
	//	    NULLQUARK,				/* xrm_class		  */
	//	    FALSE,				/* compress_motion	  */
	//	    FALSE,				/* compress_exposure	  */
	//	    TRUE,				/* compress_enterleave    */
	//	    FALSE,				/* visible_interest	  */
	//	    Destroy,				/* destroy		  */
	//	    NULL,				/* resize		  */
	//	    Redisplay,				/* expose		  */
	//	    NULL,				/* set_values		  */
	//	    NULL,				/* set_values_hook	  */
	//	    XtInheritSetValuesAlmost,		/* set_values_almost	  */
	//	    NULL,				/* get_values_hook	  */
	//	    NULL,				/* accept_focus		  */
	//	    XtVersion,				/* version		  */
	//	    NULL,				/* callback_private	  */
	//	    defaultTranslations,		/* tm_table		  */
	//	    XtInheritQueryGeometry,		/* query_geometry	  */
	//	    XtInheritDisplayAccelerator,	/* display_accelerator	  */
	//	    NULL				/* extension		  */
	//	  },  /* CoreClass fields initialization */
	//	  {
	//	    /* change_sensitive		*/	XtInheritChangeSensitive
	//	  },  /* SimpleClass fields initialization */
	//	  {
	//	    0,                                     /* field not used    */
	//	  },  /* DrawingAreaClass fields initialization */
	//	};
	//
	//	  
	//	WidgetClass drawingAreaWidgetClass = (WidgetClass)&drawingAreaClassRec;
	//
	//
	//	static void Initialize( request, new)
	//	DrawingAreaWidget request, new;
	//	{
	//	  if (request->core.width == 0)
	//	    new->core.width = 100;
	//	  if (request->core.height == 0)
	//	    new->core.height = 100;
	//	}
	//
	//
	//	static void Destroy( w)
	//	DrawingAreaWidget w;
	//	{
	//	  XtRemoveAllCallbacks((Widget)w, XtNexposeCallback);
	//	  XtRemoveAllCallbacks((Widget)w, XtNinputCallback);
	//	  XtRemoveAllCallbacks((Widget)w, XtNmotionCallback);
	//	  XtRemoveAllCallbacks((Widget)w, XtNresizeCallback);
	//	}
	//
	//
	//	/* Invoke expose callbacks */
	//	static void Redisplay(w, event, region)
	//	DrawingAreaWidget w;
	//	XEvent		 *event;
	//	Region		  region;
	//	{
	//	  XawDrawingAreaCallbackStruct cb;
	//
	//	  cb.reason = XawCR_EXPOSE;
	//	  cb.event  = event;
	//	  cb.window = XtWindow(w);
	//	  XtCallCallbacks((Widget)w, XtNexposeCallback, (char *)&cb);
	//	}
	//
	//	/* Invoke resize callbacks */
	//	static void resize_draw(w, event, args, n_args)
	//	DrawingAreaWidget w;
	//	XEvent		 *event;
	//	char		 *args[];
	//	int		  n_args;
	//	{
	//	  XawDrawingAreaCallbackStruct cb;
	//
	//	  cb.reason = XawCR_RESIZE;
	//	  cb.event  = event;
	//	  cb.window = XtWindow(w);
	//	  XtCallCallbacks((Widget)w, XtNresizeCallback, (char *)&cb);
	//	}
	//
	//	/* Invoke input callbacks */
	//	static void input_draw(w, event, args, n_args)
	//	DrawingAreaWidget w;
	//	XEvent		 *event;
	//	char		 *args[];
	//	int		  n_args;
	//	{
	//	  XawDrawingAreaCallbackStruct cb;
	//
	//	  cb.reason = XawCR_INPUT;
	//	  cb.event  = event;
	//	  cb.window = XtWindow(w);
	//	  XtCallCallbacks((Widget)w, XtNinputCallback, (char *)&cb);
	//	}
	//
	//	/* Invoke motion callbacks */
	//	static void motion_draw(w, event, args, n_args)
	//	DrawingAreaWidget w;
	//	XEvent		 *event;
	//	char		 *args[];
	//	int		  n_args;
	//	{
	//	  XawDrawingAreaCallbackStruct cb;
	//
	//	  cb.reason = XawCR_MOTION;
	//	  cb.event  = event;
	//	  cb.window = XtWindow(w);
	//	  XtCallCallbacks((Widget)w, XtNmotionCallback, (char *)&cb);
	//	}

	//	
	//	/*
	//	 * The callbacks for the X interface for 3Dc
	//	 */
	//	/*
	//
	//	    3Dc, a game of 3-Dimensional Chess
	//	    Copyright (C) 1995  Paul Hicks
	//
	//	    This program is free software; you can redistribute it and/or modify
	//	    it under the terms of the GNU General Public License as published by
	//	    the Free Software Foundation; either version 2 of the License, or
	//	    (at your option) any later version.
	//	    E-Mail: paulh@euristix.ie
	//	*/
	//	#include "DrawingA.h"
	//
	//	#include "local.h"
	//	#include "machine.h"
	//	#include "3Dc.h"
	//
	//	Local GfxInfo *
	//	Widget2GfxInfo(Widget w)
	//	{
	//	  if ((secondGFX != NULL) &&
	//	      (XtDisplay(secondGFX->mainWindow) == XtDisplay(w)))
	//	    return secondGFX;
	//
	//	  return firstGFX;
	//	}
	//
	//	/* Draw one level of the board */
	//	Global void
	//	DrawBoard(Widget w, XtPointer client, XtPointer call)
	//	{
	//	  unsigned
	//	    curX, curY,
	//	    minX = 0, minY = 0,
	//	    maxX = 7, maxY = 7;
	//	  Level boardNum;
	//	  XEvent *event;
	//	  GfxInfo *gfx;
	//	  Dimension width, height;
	//
	//	  gfx = Widget2GfxInfo(w);
	//
	//	  for (boardNum = 0; boardNum < LEVELS; ++boardNum)
	//	    {
	//	      if (w == gfx->board[boardNum])
	//	        break;
	//	    }
	//
	//	  if (!CHECK(boardNum != LEVELS))
	//	    return;
	//
	//	  width  = gfx->width [boardNum] / FILES;
	//	  height = gfx->height[boardNum] / RANKS;
	//
	//	  /* call is NULL if called manually */
	//	  if ( call != NULL )
	//	    {
	//	      event = ((XawDrawingAreaCallbackStruct *)call)->event;
	//	      if ( event->type == ConfigureNotify )
	//	        {
	//	          gfx->width [boardNum] = event->xconfigure.width;
	//	          gfx->height[boardNum] = event->xconfigure.height;
	//	        }
	//
	//	      width  = gfx->width [boardNum] / FILES;
	//	      height = gfx->height[boardNum] / RANKS;
	//
	//	      /* If call is null, then the function is called by us
	//	       * and the whole board (ignoring expose-rects) is to be redrawn.
	//	       * Similarly, the whole board is redrawn when resizing. */
	//	      if (event->type == Expose)
	//	        {
	//	          minX = event->xexpose.x / width;
	//	          minY = event->xexpose.y / height;
	//	          maxX = MIN(FILES-1, 1+ minX +
	//	                     (event->xexpose.width / width));
	//	          maxY = MIN(RANKS-1, 1+ minY +
	//	                     (event->xexpose.height / height));
	//	        }
	//	    }
	//
	//	  XSetForeground(XtDisplay(w), gfx->gc, gfx->greyPixel);
	//
	//	  for (curY = minY; curY <= maxY; ++curY)
	//	    {
	//	      for (curX = minX; curX <= maxX; ++curX)
	//	        {
	//	          if ( Board[boardNum][curY][curX] == NULL )
	//	            {
	//	              XRectangle rect = {0, 0, XPM_SIZE, XPM_SIZE};
	//
	//	              rect.width =  width;
	//	              rect.height = height;
	//
	//	              XSetClipRectangles(XtDisplay(gfx->mainWindow), gfx->gc,
	//	                                 SQ_POS_X(gfx, boardNum, curX),
	//	                                 SQ_POS_Y(gfx, boardNum, curY),
	//	                                 &rect, 1, Unsorted);
	//
	//	              /* White in the bottom right... */
	//	              if ( ((curX + curY) %2) == 1 )
	//	                XFillRectangle(XtDisplay(w), XtWindow(w), gfx->gc,
	//	                               SQ_POS_X(gfx, boardNum, curX),
	//	                               SQ_POS_Y(gfx, boardNum, curY),
	//	                               width, height);
	//	              else
	//	                XClearArea(XtDisplay(w), XtWindow(w),
	//	                           SQ_POS_X(gfx, boardNum, curX),
	//	                           SQ_POS_Y(gfx, boardNum, curY),
	//	                           width, height, FALSE);
	//	            }
	//	          else /* if (Board[boardNum][curY][curX] != NULL) */
	//	            {
	//	              PieceDisplay(Board[boardNum][curY][curX], TRUE);
	//	            }
	//	        }
	//	    }
	//
	//	  return;
	//	}
	//
	//	Global void
	//	MouseInput(Widget w, XtPointer client, XtPointer call)
	//	{
	//	  XADCS *data;
	//	  Piece *piece;
	//	  int moved;
	//	  Level boardNum;
	//	  int
	//	    xWinRel, yWinRel,
	//	    dummy1, dummy2, dummy3;
	//	  Window win, dummy;
	//	  XColor fore, back;
	//	  Local Cursor cursor = 0;
	//	  Pixmap pixmap;
	//	  GfxInfo *gfx;
	//
	//	  Local Coord src;
	//
	//	#ifdef FONTCURSOR
	//	  if (!cursor)
	//	    cursor = XCreateFontCursor(XtDisplay(w), XC_exchange);
	//	#endif /* FONTCURSOR */
	//
	//	  gfx = Widget2GfxInfo(w);
	//
	//	  data = (XADCS *)call;
	//
	//	  if (data->event->type == ButtonPress)
	//	    {
	//	      /* Getting boardNum is easy for ButtonPress */
	//	      for (boardNum = 0;
	//	           (w != gfx->board[boardNum]) && (boardNum < LEVELS);
	//	           ++boardNum);
	//
	//	      if (boardNum == LEVELS)
	//	        return; /* Error! */
	//
	//	      src.xFile = data->event->xbutton.x / (gfx->width [boardNum]/FILES);
	//	      src.yRank = data->event->xbutton.y / (gfx->height[boardNum]/RANKS);
	//	      src.zLevel = boardNum;
	//
	//	      if (Board[src.zLevel][src.yRank][src.xFile] == NULL)
	//	        {
	//	          src.zLevel = LEVELS;
	//	          return;
	//	        }
	//
	//	#ifndef FONTCURSOR
	//	      /* Define the colours */
	//	      if (Board[src.zLevel][src.yRank][src.xFile]->bwSide == BLACK)
	//	        {
	//	          fore.pixel = gfx->whitePixel;
	//	          back.pixel = gfx->blackPixel;
	//	        }
	//	      else
	//	        {
	//	          fore.pixel = gfx->blackPixel;
	//	          back.pixel = gfx->whitePixel;
	//	        }
	//	      XQueryColor(XtDisplay(w), DefaultColormapOfScreen(XtScreen(w)), &fore);
	//	      XQueryColor(XtDisplay(w), DefaultColormapOfScreen(XtScreen(w)), &back);
	//
	//	      /* Create the pixmap */
	//	      pixmap = XCreatePixmap(XtDisplay(w), XtWindow(w),
	//	                             XPM_SIZE, XPM_SIZE, 1);
	//
	//	      /* Create the GC */
	//	      if (gfx->monoGC == NULL)
	//	        {
	//	          gfx->monoGC = XCreateGC(XtDisplay(w), pixmap, 0, NULL);
	//	          XSetFunction(XtDisplay(w), gfx->monoGC, GXcopy);
	//	        }
	//
	//	      XCopyPlane(XtDisplay(w),
	//	                 gfx->face[WHITE]
	//	                          [Board[src.zLevel]
	//	                                [src.yRank]
	//	                                [src.xFile]->nName],
	//	                 pixmap, gfx->monoGC, 0, 0,
	//	                 XPM_SIZE, XPM_SIZE, 0, 0, 1);
	//
	//	      /* Now we can create the cursor */
	//	      cursor =
	//	        XCreatePixmapCursor(XtDisplay(w),
	//	                            pixmap,
	//	                            gfx->mask[Board[src.zLevel]
	//	                                               [src.yRank]
	//	                                               [src.xFile]->nName],
	//	                            &fore, &back, 15, 15);
	//	      XFreePixmap(XtDisplay(w), pixmap);
	//	#endif /* FONTCURSOR */
	//
	//	      /* Now let's change the cursor */
	//	      for (boardNum = 0; boardNum < LEVELS; ++boardNum)
	//	        {
	//	          XDefineCursor(XtDisplay(w), XtWindow(w), cursor);
	//	        }
	//	    }
	//	  else if (data->event->type == ButtonRelease)
	//	    {
	//	      /* First of all - if we didn't pick up a piece, there's no
	//	       * point in continuing */
	//	      if (src.zLevel == LEVELS)
	//	        return;
	//
	//	      for (boardNum = 0; boardNum < LEVELS; ++boardNum)
	//	        {
	//	          /* Now we have to restore the cursor */
	//	          XUndefineCursor(XtDisplay(w), XtWindow(w));
	//	        }
	//
	//	      /* For ButtonRelease, we have to take care of the release being
	//	       * in another board */
	//	      for (boardNum = 0; boardNum < LEVELS; ++boardNum)
	//	        {
	//	          /* Before I added in pixmap cursors, I needed only run
	//	           * XQueryPointer once, but now I must do it once per
	//	           * board.  Even if I define FONTCURSOR.  Presumably
	//	           * I changed something about XQueryPointer and searching
	//	           * through many levels of children, but I can't find what
	//	           * it was.  O well. */
	//	          XQueryPointer(XtDisplay(w),
	//	                        XtWindow(XtParent(gfx->board[boardNum])),
	//	                        &dummy, &win,
	//	                        &dummy1, &dummy2,
	//	                        &xWinRel, &yWinRel,
	//	                        &dummy3);
	//
	//	          if (win == XtWindow(gfx->board[boardNum]))
	//	            break;
	//	        }
	//
	//	#ifndef FONTCURSOR
	//	      XFreeCursor(XtDisplay(w), cursor);
	//	      cursor = 0;
	//	#endif
	//
	//	      if (boardNum == LEVELS)
	//	        {
	//	          Err3Dc(gfx, "Can't move outside boards", TRUE);
	//	          return;
	//	        }
	//
	//	     /* First - ignore clicks on the one square */
	//	     if ((src.xFile == (xWinRel / (gfx->width [boardNum]/FILES))) &&
	//	         (src.yRank == (yWinRel / (gfx->height[boardNum]/RANKS))) &&
	//	         (src.zLevel == boardNum))
	//	       {
	//	         Err3Dc(gfx, NULL, FALSE);
	//	         return;
	//	       }
	//
	//	      piece = Board[src.zLevel][src.yRank][src.xFile];
	//
	//	      if ((!piece) || (!piece->bVisible))
	//	        {
	//	          Err3Dc(gfx, "There's no piece there!", FALSE);
	//	          return;
	//	        }
	//	      else if ( (piece->bwSide == Computer()) ||
	//	               ( (secondGFX != NULL) &&
	//	                 ( ((gfx == firstGFX)  && (piece->bwSide == BLACK)) ||
	//	                  (((gfx == secondGFX) && (piece->bwSide == WHITE))) ) ) )
	//	        {
	//	          Err3Dc(gfx, "That's not your piece!", FALSE);
	//	          return;
	//	        }
	//	      else if (piece->bwSide != bwToMove)
	//	        {
	//	          Err3Dc(gfx, "It's not your go yet!", FALSE);
	//	          return;
	//	        }
	//
	//	      moved = PieceMove(piece,
	//	                        xWinRel / (gfx->width [boardNum]/FILES),
	//	                        yWinRel / (gfx->height[boardNum]/RANKS),
	//	                        boardNum);
	//
	//	      if (moved)
	//	        {
	//	          PieceDisplay(piece, TRUE);
	//	          UpdateMuster(piece->bwSide, piece->nName, TRUE);
	//
	//	          PrintMove( StackPeek( MoveStack, 0 ) );
	//
	//	          bwToMove = (bwToMove == WHITE ? BLACK : WHITE);
	//	        } /* End "this was a legal move" */
	//	      else
	//	        Err3Dc(gfx, "That piece %s.", TRUE);
	//	    } /* End "this was a buttonrelease" */
	//	}
	//
	//	Global void
	//	ResignGame(Widget w, XtPointer client, XtPointer call)
	//	{
	//	  Widget dialog, form, cont, restart, quit;
	//	  XtPopdownID popMeDown;
	//	  Dimension x, y;
	//	  GfxInfo *gfx;
	//
	//	  PauseGame(); /* Suspend thinking */
	//
	//	  gfx = Widget2GfxInfo(w);
	//
	//	  /* This is one smart function.  XtCreatePopupShell will create
	//	   * an unmanaged widget if it doesn't already exist, or do nothing
	//	   * otherwise.  In combination with XtCallbackPopdown, this makes
	//	   * dialogs a doddle (kudos to the Xt people - everything else handy
	//	   * about programming in X seems to be from Motif, this is nice for
	//	   * a change).
	//	   *
	//	   * Addendum:
	//	   * Hmm.  It appears that I spoke hastily.  In my version of Xt,
	//	   * XtCallbackPopdown appears to do nothing; at least, I can't get
	//	   * it to do anything.  So I've written cancelDialog, which does
	//	   * exactly the same thing (well, actually it works, you know,
	//	   * pops the dialog down :).
	//	   */
	//	  XtVaGetValues(gfx->mainWindow,
	//	                XtNx, &x,
	//	                XtNy, &y,
	//	                NULL);
	//	  dialog = XtVaCreatePopupShell("Resign Game?", transientShellWidgetClass,
	//	                                gfx->mainWindow,
	//	                                XtNx, x + 50,
	//	                                XtNy, y + 50,
	//	                                NULL);
	//
	//	  form = XtVaCreateManagedWidget("form", formWidgetClass, dialog,
	//	                                 NULL);
	//
	//	  cont = XtVaCreateManagedWidget("Continue", commandWidgetClass, form,
	//	                                 XtNlabel, "Continue",
	//	                                 NULL);
	//
	//	  restart = XtVaCreateManagedWidget("Restart", commandWidgetClass, form,
	//	                                    XtNlabel, "Restart",
	//	                                    XtNfromHoriz, cont,
	//	                                    NULL);
	//
	//	  quit = XtVaCreateManagedWidget("Quit", commandWidgetClass, form,
	//	                                 XtNlabel, "Quit",
	//	                                 XtNfromHoriz, restart,
	//	                                 NULL);
	//
	//	  popMeDown = (XtPopdownID)XtMalloc(sizeof(XtPopdownIDRec));
	//	  popMeDown->shell_widget = dialog;
	//	  popMeDown->enable_widget = gfx->mainWindow;
	//
	//	  XtAddCallback(cont, XtNcallback, CancelDialog, popMeDown);
	//	  XtAddCallback(restart, XtNcallback, Restart3Dc, popMeDown);
	//	  XtAddCallback(quit, XtNcallback, Quit3Dc, NULL);
	//
	//	  XtManageChild(dialog);
	//
	//	  return;
	//	}
	//
	//	Global void
	//	UndoMove(Widget w, XtPointer client, XtPointer call)
	//	{
	//	  GfxInfo *gfx;
	//
	//	  gfx = Widget2GfxInfo(w);
	//
	//	  /* This fearsome conditional disallows undoing any but your
	//	   * own moves.  I don't know if it should be in or not;
	//	   * thus it is conditionally compiled */
	//	#ifndef UNDO_ANY_MOVE
	//	  if ( ((Computer() != NOCOL) && (bwToMove != Computer())) ||
	//	      ( (secondGFX != NULL) &&
	//	       ( ((gfx == secondGFX) && (bwToMove == BLACK)) ||
	//	         ((gfx == firstGFX)  && (bwToMove == WHITE)) ) ) )
	//	    {
	//	      Err3Dc( gfx, "You can only undo your own moves!", FALSE );
	//	      return;
	//	    }
	//	#endif /* UNDO_ANY_MOVE */
	//
	//	  if (PieceUndo() == FALSE)
	//	    {
	//	      Err3Dc(gfx, "Nothing to undo!", FALSE);
	//	      return;
	//	    }
	//
	//	  /* Because we don't know what type was done, update them all */
	//	  DrawMuster(gfx->muster, NULL, NULL);
	//	  if (secondGFX != NULL)
	//	    DrawMuster(secondGFX->muster, NULL, NULL);
	//
	//	  /* TODO:
	//	   * pop up dialog asking for permission to undo
	//	   * in multi-display games
	//	   */
	//
	//	  /* Undo means that the same guy goes again... */
	//	  bwToMove = (bwToMove == WHITE ? BLACK : WHITE);
	//
	//	  return;
	//	}
	//
	//	/* Draw the muster window */
	//	Global void
	//	DrawMuster(Widget w, XtPointer client, XtPointer call)
	//	{
	//	  Dimension musterWidth, musterHeight;
	//	  int leftX, curX, curY, x;
	//	  GfxInfo *gfx;
	//
	//	  /* On which display are we working? */
	//	  gfx = Widget2GfxInfo( w );
	//
	//	  XtVaGetValues(w,
	//	                XtNheight, &musterHeight,
	//	                XtNwidth, &musterWidth,
	//	                NULL);
	//
	//	  /* Resize the remark label */
	//	  XtVaSetValues(gfx->remark,
	//	                XtNwidth, musterWidth,
	//	                NULL);
	//
	//	  /* Factor in centring */
	//	  leftX = curX = (musterWidth % 5) / 2 + ((musterWidth / 5) - XPM_SIZE) / 2;
	//	  curY = (musterHeight % 5) / 2 + ((musterHeight / 5) - XPM_SIZE) / 2;
	//
	//	  XSetForeground(XtDisplay(w), gfx->gc, gfx->blackPixel);
	//
	//	  /* Draw white royalty */
	//	  for (x = 0; x < 5; ++x)
	//	    {
	//	      XSetClipOrigin(XtDisplay(w), gfx->gc, curX, curY);
	//	      XSetClipMask(XtDisplay(w), gfx->gc, gfx->mask[x]);
	//	      XCopyArea(XtDisplay(w),
	//	                gfx->face[WHITE][x], XtWindow(w), gfx->gc,
	//	                0, 0, XPM_SIZE, XPM_SIZE, curX, curY);
	//
	//	      UpdateMuster(WHITE, x, FALSE);
	//
	//	      curX += musterWidth / 5;
	//	    }
	//
	//	  curX = leftX;
	//	  curY += musterHeight / 5;
	//
	//	  /* Draw white nobility */
	//	  for (x = 0; x < 5; ++x)
	//	    {
	//	      XSetClipOrigin(XtDisplay(w), gfx->gc, curX, curY);
	//	      XSetClipMask(XtDisplay(w), gfx->gc, gfx->mask[x+5]);
	//	      XCopyArea(XtDisplay(w),
	//	                gfx->face[WHITE][x+5], XtWindow(w), gfx->gc,
	//	                0, 0, XPM_SIZE, XPM_SIZE, curX, curY);
	//
	//	      UpdateMuster(WHITE, x+5, FALSE);
	//
	//	      curX += musterWidth / 5;
	//	    }
	//
	//	  curX = ((musterWidth % 2) / 2) + (((musterWidth / 2) - XPM_SIZE) / 2);
	//	  curY += musterHeight / 5;
	//
	//	  /* Draw pawns */
	//	  for (x = 0; x < 2; ++x)
	//	    {
	//	      if (curX < 0) curX = 0;
	//
	//	      XSetClipOrigin(XtDisplay(w), gfx->gc, curX, curY);
	//	      XSetClipMask(XtDisplay(w), gfx->gc, gfx->mask[pawn]);
	//	      XCopyArea(XtDisplay(w),
	//	                gfx->face[x][pawn], XtWindow(w), gfx->gc,
	//	                0, 0, XPM_SIZE, XPM_SIZE, curX, curY);
	//
	//	      UpdateMuster(x, pawn, FALSE);
	//
	//	      curX += musterWidth / 2;
	//	    }
	//
	//	  curX = leftX;
	//	  curY += musterHeight / 5;
	//
	//	  /* Draw black nobility */
	//	  for (x = 0; x < 5; ++x)
	//	    {
	//	      XSetClipOrigin(XtDisplay(w), gfx->gc, curX, curY);
	//	      XSetClipMask(XtDisplay(w), gfx->gc, gfx->mask[x+5]);
	//	      XCopyArea(XtDisplay(w),
	//	                gfx->face[BLACK][x+5], XtWindow(w), gfx->gc,
	//	                0, 0, XPM_SIZE, XPM_SIZE, curX, curY);
	//
	//	      UpdateMuster(BLACK, x+5, FALSE);
	//
	//	      curX += musterWidth / 5;
	//	    }
	//
	//	  curX = leftX;
	//	  curY += musterHeight / 5;
	//
	//	  /* Draw BLACK royalty */
	//	  for (x = 0; x < 5; ++x)
	//	    {
	//	      XSetClipOrigin(XtDisplay(w), gfx->gc, curX, curY);
	//	      XSetClipMask(XtDisplay(w), gfx->gc, gfx->mask[x]);
	//	      XCopyArea(XtDisplay(w),
	//	                gfx->face[BLACK][x], XtWindow(w), gfx->gc,
	//	                0, 0, XPM_SIZE, XPM_SIZE, curX, curY);
	//
	//	      UpdateMuster(BLACK, x, FALSE);
	//
	//	      curX += musterWidth / 5;
	//	    }
	//
	//	  return;
	//	}
	//
	//	/* The promotion callback */
	//	Global void
	//	PromotePiece(Widget w, XtPointer client, XtPointer call)
	//	{
	//	  Piece *piece;
	//
	//	  piece = (Piece *)client;
	//	  PieceDisplay(piece, FALSE);
	//
	//	  switch (((XawListReturnStruct *)call)->list_index)
	//	    {
	//	    case 0:
	//	      piece->nName = queen;
	//	      break;
	//	    case 1:
	//	      piece->nName = rook;
	//	      break;
	//	    case 2:
	//	      piece->nName = bishop;
	//	      break;
	//	    case 3:
	//	      piece->nName = knight;
	//	      break;
	//	    case 4:
	//	      piece->nName = princess;
	//	      break;
	//	    case 5:
	//	      piece->nName = galley;
	//	      break;
	//	    case 6:
	//	      piece->nName = abbey;
	//	      break;
	//	    case 7:
	//	      piece->nName = cannon;
	//	      break;
	//	    }
	//	  
	//	  PieceDisplay(piece, TRUE);
	//	  UpdateMuster(piece->bwSide, piece->nName, TRUE);
	//
	//	  XtPopdown(XtParent(w));
	//	  XtDestroyWidget(XtParent(w));
	//
	//	  ResumeGame(); /* Resume thinking */
	//
	//	  return;
	//	}
	//
	//	Global void
	//	Restart3Dc(Widget w, XtPointer client, XtPointer call)
	//	{
	//	  int i;
	//
	//	  if (w)
	//	    {
	//	      XtPopdownID popMeDown;
	//	      popMeDown = (XtPopdownID)client;
	//	      XtPopdown(popMeDown->shell_widget);
	//	      XtDestroyWidget(popMeDown->shell_widget);
	//	      XtSetSensitive(popMeDown->enable_widget, TRUE);
	//	      XtFree((char *)popMeDown);
	//	    }
	//
	//	  XtSetSensitive(firstGFX->undo, TRUE);
	//	  if ( secondGFX != NULL )
	//	    XtSetSensitive(secondGFX->undo, TRUE);
	//
	//	  /* Destroy all the pieces */
	//	  for (i = 0; i < PIECES; ++i)
	//	    {
	//	      PieceDelete(Muster[WHITE][i]);
	//	      PieceDelete(Muster[BLACK][i]);
	//	    }
	//
	//	  /* Free the move stack */
	//	  StackDelete(MoveStack);
	//
	//	  bwToMove = WHITE;
	//	  ResumeGame();
	//
	//	  /* Refresh the screen */
	//	  Draw3DcBoard();
	//	  for (i = 0; i < TITLES; ++i)
	//	    {
	//	      UpdateMuster(WHITE, i, TRUE);
	//	      UpdateMuster(BLACK, i, TRUE);
	//	    }
	//
	//	  return;
	//	}
	//
	//	Global void
	//	CancelDialog(Widget w, XtPointer client, XtPointer call)
	//	{
	//	  XtPopdownID popMeDown;
	//
	//	  popMeDown = (XtPopdownID)client;
	//
	//	  XtPopdown(popMeDown->shell_widget);
	//	  XtDestroyWidget(popMeDown->shell_widget);
	//	  XtSetSensitive(popMeDown->enable_widget, TRUE);
	//	  XtFree((char *)popMeDown);
	//	  ResumeGame(); /* Most dialogs are modal, requiring the use of this line */
	//
	//	  return;
	//	}
	//

	//	 * ai.c
	//	 * An implementation of computer intelligence (you wot?) for 3Dc.
	//	    Copyright (C) 1995,1996  Paul Hicks
	//	    E-Mail: paulh@euristix.ie


	public void PushMove(Move move, int value)
	{
		int i;

		if ( value == INT_MIN )
			return;

		for ( i = 0; 
		(bestMoves.stacks [i] != NULL) && (bestMoves.ratings[i] > value) && (i < BEST_STACKS);
		++i )
		{
			//	    nop();
		}



		if ( i == BEST_STACKS )
		{
			move = null;
			return;
		}

		if ( bestMoves.ratings[i] == value )
		{
			//	      StackPush( Board.getMoveStack(), move );
			//different stack I'm quite sure... this one for AI
			aiMoves.push(move);


			move = null;
		}
		else
		{
			int j;

			j = BEST_STACKS-1;
			//	      /* Get rid of the lowest level (if it falls off the end) */
			if (bestMoves.stacks[j] != NULL)
				StackDelete( bestMoves.stacks[j] );

			/* Move all the lower levels down */
			for (; j > i; --j)
			{
				bestMoves.stacks [j] = bestMoves.stacks [j-1];
				bestMoves.ratings[j] = bestMoves.ratings[j-1];
			}

			//	      /* Create the new stack */
			//	      bestMoves.stacks[i] = StackNew();
			bestMoves.stacks[i] = new Stack();
			//	      StackPush( Board.getMoveStack(), move );
			aiMoves.push(move);
			bestMoves.ratings[i] = value;
		}
	}

	//	/* This function circumvents the problem of negative ratings
	//	 * by adding to all ratings enough to make the smallest rating == 0 */
	public void FixMoves( )
	{
		int i, add;

		for ( i = (BEST_STACKS -1); (bestMoves.stacks[i] == NULL) && (i >= 0); --i )
		{
			//	    nop();
		}
		if ((i < 0) || (bestMoves.ratings[i] >= 0))
			return;

		add = -(bestMoves.ratings[i]);
		for (; i >= 0; --i)
			bestMoves.ratings[i] += add;
	}

	public Move PopMove()
	{
		Move ret;
		int stacks, randStack, randMove, randBase, randNum;

		if ( bestMoves.stacks[0] == null )
			return null;
		/*
		 * This algorithm works by generating a number randBase on which to base a
		 * call to random(): think of it as a die with randBase sides.  randBase
		 * it dependant on the rating of the moves at the current level and
		 * on the level itself.  It may be that the number of moves at the current
		 * level will also be a factor in later version.  (A "level" is all moves
		 * with equal rating.  Only the top BEST_STACKS levels are remembered).
		 * Then the generated random number is reverse engineered into a level.
		 * Finally a purely random choice of move within that level is made.
		 */
		if ( bestMoves.ratings[0] == INT_MAX )
		{
			/* If this move wins the game then do it unconditionally */
			randMove = 0;
			randStack = 0;
		}
		else
		{
			for ( stacks = 0, randBase = 0;
			( bestMoves.stacks[stacks] != NULL ) &&
			( stacks < BEST_STACKS ) ;
			++stacks )
				randBase += ((BEST_STACKS-stacks) * bestMoves.ratings[stacks]);

			randNum = rng.nextInt()%randBase;

			for ( randStack = stacks-1;
			(randStack > 0) && (randNum > 0); --randStack )
			{
				randNum -= (( BEST_STACKS - randStack ) *
						bestMoves.ratings[randStack] );
			}

			randMove = rng.nextInt()%bestMoves.stacks[randStack].size();
			//DEBUG... may impliment later...
			//	      D( printf( "Choosing move %i from a stack of size %i\n",
			//	                randMove+1, bestMoves.stacks[randStack]->nSize ) );
		}

		ret = StackPeek( randMove );
		//	  CHECK((ret->xyzBefore.zLevel != 3) && (ret->xyzAfter.zLevel != 3));
		//can't ffind definition for check???

		//	  /* This defeats the opaqueness of stack.c but it's easy */
		//	  /* It just clears the one move we've just made from the move stack */
		//	  /* If the chosen move was the top of the stack.. */
		if (randMove == 0)
		{
			//	      /* Don't free the move: it's "ret" and still in use! */
			//	      StackPop( );
			aiMoves.pop();
			//	      /* If there is only the one move then we remove the stack */
			if ( bestMoves.stacks[randStack].size() == 0 )
			{
				StackDelete( bestMoves.stacks[randStack] );

				while ( ((randStack+1) < BEST_STACKS) &&
						(bestMoves.stacks[randStack+1] != NULL) )
				{
					bestMoves.stacks [randStack] = bestMoves.stacks [randStack+1];
					bestMoves.ratings[randStack] = bestMoves.ratings[randStack+1];
					++randStack;
				}

				bestMoves.stacks[randStack] = null;
				bestMoves.ratings[randStack] = INT_MIN;
			}
		}
		/* We used a second or later move */
		else
		{
			//removing for now... i think this was the ionly place stack_el was used... still
//			int i;
//			Stack_el el = new Stack_el();
//			Stack_el temp = new Stack_el();

//			el = (Stack_el)bestMoves.stacks[randStack].firstElement();
			stackList prefferedMoves = (stackList)bestMoves.stacks[randStack].firstElement();

			//	      for (i = 1; (i < randMove) && (el->below != NULL); ++i)
//			for (i = 1; (i < randMove) && (el.below != NULL); ++i)
//			{
//				el = el.below;
//			}

			//	      if (!CHECK((el->below != NULL) && (el->below->mvt == ret)))
			//	        {
			//	          /* Hopefully this can never happen */
			// :) you think you hope it can never happen... I'm terrified it's important.
			//	        }

//			temp = el.below; 			// !!! Is this legal... I think I should use a remove from index sorta
//			el.below = temp.below;	// !!! thing, but i need to re-refference .below anyhow, see how it goes for now
			//          bestMoves.stacks[randStack]->nSize--;
			//	          free( temp );
		}

		return ret;
	}

	public int RateMove( Move move, int bwSide )
	{
		int rating = 0;
		int bwEnemy;
		Coord xyzPos;
		Piece moving;
		Piece storing;
		bwEnemy = ( bwSide == Piece.WHITE ) ? Piece.BLACK : Piece.WHITE;

		/* Rate taking king */
		if (move.pVictim == Board.getMuster()[ bwEnemy ][ Piece.MusterIdx(Board, Piece.king, 0 )])
			return INT_MAX;

		//	  /* Fake the move for simple lookahead */
		moving  = Board.getBoard()[ move.xyzBefore.thisLevel ] [ move.xyzBefore.thisRank ] [ move.xyzBefore.thisFile ];
		storing = Board.getBoard()[ move.xyzAfter.thisLevel ] [ move.xyzAfter.thisRank ] [ move.xyzAfter.thisFile ];

		//realy hope this CHECK thing isn't CHECKing anything impotant
		//	  if (!CHECK( moving != NULL ))
		//	    return INT_MIN;

		//	  /* Rate saving king */
		//	  /* It might be more efficient to put the IsKingChecked() call
		//	   * inside the move faked below but that would mean nasty code
		//	   * duplication re: finding king coords and so on.  This code
		//	   * is easier to maintain. */
		if ( Board.getMuster()[ bwSide ][ Piece.MusterIdx(Board, Piece.king, 0 )].FakeMoveAndIsKingChecked( Board,
				move.xyzAfter.thisLevel,
				move.xyzAfter.thisRank,
				move.xyzAfter.thisFile ))
			return INT_MIN;

		/* Fake the move */
		Board.getBoard()[ move.xyzBefore.thisLevel ] [ move.xyzBefore.thisRank ] [ move.xyzBefore.thisFile ] = null;
		Board.getBoard()[ move.xyzAfter.thisLevel ][ move.xyzAfter.thisRank ] [ move.xyzAfter.thisFile ] = moving;
		if ( storing != null )
			storing.bVisible = FALSE;

		/* Rate check */
		xyzPos = (Board.getMuster()[ bwEnemy ][ Piece.MusterIdx(Board, Piece.king, 0 )]).xyzPos;
		if ( move.pVictim.SquareThreatened(Board, bwSide, xyzPos.thisLevel, xyzPos.thisRank, xyzPos.thisFile) != null )
			rating += values[ Piece.king ];

		/* Rate danger: if there's a chance of being captured in the new pos. */
		xyzPos = move.xyzAfter;
		if ( move.pVictim.SquareThreatened(Board, bwEnemy, xyzPos.thisLevel, xyzPos.thisRank, xyzPos.thisFile) != null )
			rating -= (values[ moving.thisType ] /2);

		/* Undo the fake */
		Board.getBoard()[ move.xyzBefore.thisLevel ] [ move.xyzBefore.thisRank ] [ move.xyzBefore.thisFile ] = moving;
		Board.getBoard()[ move.xyzAfter.thisLevel ] [ move.xyzAfter.thisRank ] [ move.xyzAfter.thisFile ] = storing;
		if ( storing != null )
			storing.bVisible = TRUE;

		/* Rate capture */
		if (( move.pVictim != NULL ) && ( move.pVictim.thisSide == bwEnemy ))
		{
			int i;
			rating += values[ move.pVictim.thisType ];

			/* Rate evasion: if there's a chance of any friendly piece
			 * being captured in the old pos but not in the new (this isn't
			 * an authorative check and needs to be enhanced). */
			for ( i = 0; i < PIECES; ++i )
			{
				if ( Board.getMuster()[ bwSide ][ i ].bVisible && move.pVictim.SquareThreatened(Board, bwEnemy,
						Board.getMuster()[ bwSide ][ i ].xyzPos.thisFile,
						Board.getMuster()[ bwSide ][ i ].xyzPos.thisRank,
						Board.getMuster()[ bwSide ][ i ].xyzPos.thisLevel ) ==  move.pVictim )
					rating += (values[ Board.getMuster()[ bwSide ][ i ].thisType ] /2);
			}
		}

		//	  /* Rate special moves */
		//	  /* En passant and castling not yet possible for computer */
		if (( move.pVictim != null ) && ( move.pVictim.thisSide == bwSide))
			rating += 10; /* Castling */
		if (( move.xyzAfter.thisRank == (bwSide == Piece.WHITE ? RANKS-1 : 0) ) && ( moving.thisType == Piece.pawn ))
			rating += values[Piece.queen]; /* Promotion */
		/* Note the horrible magic numbers below */
		if ( (ABS( move.xyzAfter.thisRank - move.xyzBefore.thisRank ) == 2) && ( moving.thisType == Piece.pawn ))
			rating += 1; /* Two-forward by pawn: the computer doesn't
			 * usually like opening its attack routes otherwise */

		/* Rate position; distance forward (should be proximity to
		 * enemy king except for pawns */
		if ( bwSide == Piece.WHITE )
			rating += move.xyzAfter.thisRank - move.xyzBefore.thisRank;
		else
			rating += 7 - move.xyzAfter.thisRank + move.xyzBefore.thisRank;
		return rating;
	}

	//time to java'ize
	public Move GenMove( int bwSide)
	{
		Stack moves;
		int pieceIdx = 0;
		Move thisMove;

		/* First clear out any old moves */
//		if (pieceIdx == 0)
//		{
			int i;

			for ( i = 0; (bestMoves.stacks[i] != NULL) && (i < BEST_STACKS); ++i)
			{
				StackDelete(bestMoves.stacks[i]);
				bestMoves.stacks[i] = null;
				bestMoves.ratings[i] = INT_MIN;
			}

//		}

		if ( Board.getMuster()[bwSide][pieceIdx].bVisible )
		{
			//	      moves = FindAllMoves( Board.getMuster()[bwSide][pieceIdx] );
			moves = Board.getMuster()[bwSide][pieceIdx].FindAllMoves(Board);
			if (moves != null)
			{
				//	          while ( (thisMove = StackPop( )) != null )
				while ( (thisMove = (Move)aiMoves.pop()) != null )
				{
					PushMove( thisMove, RateMove( thisMove, bwSide ) );
				}
				StackDelete(moves);
			}
		}

		if (++pieceIdx == PIECES)
		{
			FixMoves();
//			ret = PopMove();
			pieceIdx = 0;
			return PopMove();
		}
		return null;
	}
//	public boolean GenMove( int bwSide, Move ret)
//	{
//		Stack moves;
//		int pieceIdx = 0;
//		Move thisMove;
//
//		/* First clear out any old moves */
//		if (pieceIdx == 0)
//		{
//			int i;
//
//			for ( i = 0; (bestMoves.stacks[i] != NULL) && (i < BEST_STACKS); ++i)
//			{
//				StackDelete(bestMoves.stacks[i]);
//				bestMoves.stacks[i] = null;
//				bestMoves.ratings[i] = INT_MIN;
//			}
//		}
//
//		if ( Board.getMuster()[bwSide][pieceIdx].bVisible )
//		{
//			//	      moves = FindAllMoves( Board.getMuster()[bwSide][pieceIdx] );
//			moves = Board.getMuster()[bwSide][pieceIdx].FindAllMoves(Board);
//			if (moves != null)
//			{
//				//	#         ifdef NOTDEF
//				//	          StackDump( moves );
//				//	#         endif /* DEBUG */
//
//				//	          while ( (thisMove = StackPop( )) != null )
//				while ( (thisMove = (Move)aiMoves.pop()) != null )
//				{
//					PushMove( thisMove, RateMove( thisMove, bwSide ) );
//				}
//				StackDelete(moves);
//			}
//		}
//
//		if (++pieceIdx == PIECES)
//		{
//			FixMoves();
//			ret = PopMove();
//			pieceIdx = 0;
//			return TRUE;
//		}
//
//		ret = null;
//		return FALSE;
//	}


}
