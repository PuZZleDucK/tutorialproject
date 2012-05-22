package com.puzzleduck.threeD_Chess;


//	typedef struct
//{
//  Error nErrNum;
//  char *pszErrStr;
//} error_t;


public class error_t {


	public int nErrNum;
	public String pszErrStr;

	public error_t(int newnErrNum, String newpszErrStr)
	{
		nErrNum = newnErrNum;
		pszErrStr = newpszErrStr;
	}


}
