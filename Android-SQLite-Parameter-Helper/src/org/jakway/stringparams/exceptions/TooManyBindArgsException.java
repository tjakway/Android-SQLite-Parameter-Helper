package org.jakway.stringparams.exceptions;

/**
 * thrown when DBUtils.parameterizeString receives too many bind args
 * @author thomas
 *
 */
@SuppressWarnings("serial")
public class TooManyBindArgsException extends RuntimeException
{
	public TooManyBindArgsException()
	{
		super();
	}
	
	public TooManyBindArgsException(String str)
	{
		super(str);
	}
}
