package org.jakway.stringparams.exceptions;

/**
 * NullStatementException takes precedence over this exception
 * If both could be thrown, only NullStatementException should be thrown.
 * @author thomas
 *
 */
@SuppressWarnings("serial")
public class NullBindArgsException extends RuntimeException
{
	public NullBindArgsException()
	{
		super();
	}
	
	public NullBindArgsException(String str)
	{
		super(str);
	}
}
