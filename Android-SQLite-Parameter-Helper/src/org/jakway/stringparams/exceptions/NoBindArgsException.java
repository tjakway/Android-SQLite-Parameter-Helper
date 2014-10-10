package org.jakway.stringparams.exceptions;

@SuppressWarnings("serial")
public class NoBindArgsException extends RuntimeException
{
	public NoBindArgsException()
	{
		super();
	}
	
	public NoBindArgsException(String str)
	{
		super(str);
	}
}
