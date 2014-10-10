package org.jakway.stringparams.exceptions;

@SuppressWarnings("serial")
public class TooManyParametersException extends RuntimeException
{
	public TooManyParametersException()
	{
		super();
	}

	public TooManyParametersException(String str)
	{
		super(str);
	}
}
