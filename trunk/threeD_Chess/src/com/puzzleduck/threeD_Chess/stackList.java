package com.puzzleduck.threeD_Chess;

import java.util.Stack;

public class stackList {

	//	Local struct stackList
	//	{
	//	  stack *stacks[BEST_STACKS];
	//	  int   ratings[BEST_STACKS];
	//	} 
	//
	//	#define BEST_STACKS 5

	public Stack stacks[] = {null,null,null,null,null};
	public int   ratings[] = {0,0,0,0,0};

	public stackList()
	{
		////		stacks = default values;
	}

}


//from stack el

//public class Stack_el {
//
//
//	//
//	//stack has its own class... but may only need wrappers???
//	//reappropriating for stack_el
//
//	//moving stack item to new class
//	//	struct stack_el
//	//	{
//	//	  Move *mvt;
//	//	  struct stack_el *below;
//	//	};
//
//	public Move mvt;
//	//	public Stack_el below;
//	public Stack_el below;
//
//	public Stack_el(){
//		mvt = null;
//		below = null;
//	}
//
//}







//	 * The move stack for 3Dc.
//	    Copyright (C) 1995  Paul Hicks
//	public Stack stack;		  

//stack has its own class... but may only need wrappers???
//StackNew(void)
//{
//stack *s;
//
//s = (stack *)malloc(sizeof(stack));
//if (!CHECK( s != NULL ))
//return NULL;
//s->top = NULL;
//s->nSize = 0;
//return s;
//}
//	public static Stack StackNew()
//	{
////		stack = new Stack();
//		return new Stack();
//	}



//		
//	//Global void
//		//StackPush(stack *s, const Move *newMove)
//		static void StackPush(Stack targetStack, Move newMove)
//		{
//			//struct stack_el *newEl;
//			Stack_el newEl = new Stack_el();
//	
//	//newEl = (struct stack_el *)malloc(sizeof(struct stack_el));
//	//if (!CHECK( newEl != NULL ))
//	//return;
//			//newEl->mvt = (Move *)malloc(sizeof(Move));
//	//if (!CHECK( newEl->mvt != NULL ))
//	//return;
//	//memcpy(newEl->mvt, newMove, sizeof(Move));
//			newEl.mvt = newMove;
//			//newEl->below = s->top;
//			newEl.below = (Stack_el)targetStack.firstElement();
//	//		newEl.below = stack.firstElement();
//	//s->top = newEl;
//	//s->nSize++;
//			targetStack.add(newEl);
//			//return;
//		}

//Global Move *
//StackPop(stack *s)
//	public Move StackPop()
//	{
//		Move oldMove;
////struct stack_el *oldEl;
//
////if (s->top == NULL)
////return NULL;
//		if(stack.empty())
//		{
//			return null;
//		}
//		
//		//oldMove = s->top->mvt;
//		oldMove = ((Stack_el)stack.pop()).mvt;
////oldEl = s->top;
////s->top = s->top->below;
////s->nSize--;
////free(oldEl);
////
//		return oldMove;
//	}



