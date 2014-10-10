package org.jakway.stringparams.exceptions;

@SuppressWarnings("serial")
public class NoParametersException extends RuntimeException
{
	public NoParametersException()
	{
		super();
	}
	
	public NoParametersException(String str)
	{
		super(str);
	}
}
