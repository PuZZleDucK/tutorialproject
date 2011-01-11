package com.example.puzzleduck.threeD_Chess;

public class Stack {

	
	
	  
	  //
	  //stack needs its own class
//Global stack *
//StackNew(void)
//{
//stack *s;
//
//s = (stack *)malloc(sizeof(stack));
//if (!CHECK( s != NULL ))
//  return NULL;
//s->top = NULL;
//s->nSize = 0;
//return s;
//}
//
//Global void
//StackDelete(stack *s)
//{
//while(StackPop(s) != NULL)
//  nop();
//
//free(s);
//return;
//}
//
//Global void
//StackPush(stack *s, const Move *newMove)
//public void StackPush(stack s, Move newMove)
//{
//struct stack_el *newEl;
//
//newEl = (struct stack_el *)malloc(sizeof(struct stack_el));
//if (!CHECK( newEl != NULL ))
//  return;
//newEl->mvt = (Move *)malloc(sizeof(Move));
//if (!CHECK( newEl->mvt != NULL ))
//  return;
//memcpy(newEl->mvt, newMove, sizeof(Move));
//newEl->below = s->top;
//s->top = newEl;
//s->nSize++;
//return;
}
//
//Global Move *
//StackPop(stack *s)
//{
//Move *oldMove;
//struct stack_el *oldEl;
//
//if (s->top == NULL)
//  return NULL;
//
//oldMove = s->top->mvt;
//oldEl = s->top;
//s->top = s->top->below;
//s->nSize--;
//free(oldEl);
//
//return oldMove;
//}
//
///* Don't delete returned value; it's still on the stack! */
//Global Move *
//StackPeek(stack *s, int numMoves)
//{
//struct stack_el *oldEl;
//
//if (numMoves >= s->nSize)
//  return NULL;
//
//for (oldEl = s->top; numMoves > 0; --numMoves)
//  oldEl = oldEl->below;
//
//return oldEl->mvt;
//}
//
//#ifdef DEBUG
//Global void
//StackDump( stack *s )
//{
//int i;
//struct stack_el *el;
//
//el = s->top;
//for (i=0; i<s->nSize; ++i)
//  {
//    printf("%i: %s at (%i,%i,%i) to (%i,%i,%i)\n",i,
//           Piece2String( Board[el->mvt->xyzBefore.zLevel][el->mvt->xyzBefore.yRank][ el->mvt->xyzBefore.xFile] ),
//           el->mvt->xyzBefore.xFile,
//           el->mvt->xyzBefore.yRank,
//           el->mvt->xyzBefore.zLevel,
//           el->mvt->xyzAfter.xFile,
//           el->mvt->xyzAfter.yRank,
//           el->mvt->xyzAfter.zLevel);
//    el = el->below;
//  }
//}
//#endif /* DEBUG */


	
	
}
