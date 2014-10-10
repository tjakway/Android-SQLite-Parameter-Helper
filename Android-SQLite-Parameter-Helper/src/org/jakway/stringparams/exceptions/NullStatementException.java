package org.jakway.stringparams.exceptions;

@SuppressWarnings("serial")
public class NullStatementException extends RuntimeException
{
	public NullStatementException()
	{
		super();
	}
	
	public NullStatementException(String str)
	{
		super(str);
	}
}
