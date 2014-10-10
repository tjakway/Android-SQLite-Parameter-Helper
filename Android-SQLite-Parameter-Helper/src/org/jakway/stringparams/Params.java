/*
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org>
 */

package org.jakway.stringparams;

import org.jakway.stringparams.exceptions.NoBindArgsException;
import org.jakway.stringparams.exceptions.NoParametersException;
import org.jakway.stringparams.exceptions.NullBindArgsException;
import org.jakway.stringparams.exceptions.NullStatementException;
import org.jakway.stringparams.exceptions.TooManyBindArgsException;
import org.jakway.stringparams.exceptions.TooManyParametersException;

/**
 * see https://github.com/tjakway/Android-SQLite-Parameter-Helper
 * @author thomasjakway
 */
public class Params
{
	public static final String PARAMETER = "?";
	
	/**
	 * see http://stackoverflow.com/questions/275944/how-do-i-count-the-number-of-occurrences-of-a-char-in-a-string
	 * @param mainString
	 * @param toCount - returns the number of times this occurs in mainString
	 * @return
	 */
	public static final int countOccurrences(String mainString, String toCount)
	{
		return mainString.length() - mainString.replace(toCount, "").length();
	}
	
	/**
	 * This method presumes that you are extremely exacting
	 * It will throw exceptions for the slightest thing off
	 * For instance, a bindArgs[] with a length greater than the number of parameters
	 * causes a TooManyBindArgsException
	 * This behavior exists to catch bugs
	 * @param statement
	 * @param bindArgs
	 * @return
	 */
	public static final String parameterizeString(String statement, Object[] bindArgs)
	{
		if(statement == null)
			throw new NullStatementException();
		if(bindArgs == null)
			throw new NullBindArgsException();
		
		if(Params.countOccurrences(statement, PARAMETER) < 1)
		{
			//PARAMETER doesnt appear in the string
			//throw NoParametersException instead of TooManyBindArgsException because
			//this is the more obvious error
			throw new NoParametersException("No parameters were found in the passed String." +
					"  DBUtils.parameterizeString only uses "+PARAMETER+" as the parameter.  Did you use the wrong parameter?");
		}
		
		if(bindArgs.length < 1)
			throw new NoBindArgsException();
		
		//throw the TooMany*Exceptions after checking that there are >0 parameters and bindArgs
		//the reasoning is that having 0 parameters is the salient feature of that problem,
		//rather than having more bindArgs than parameters
		if(Params.countOccurrences(statement, PARAMETER) > bindArgs.length)
			throw new TooManyParametersException();
		else if(Params.countOccurrences(statement, PARAMETER) < bindArgs.length)
			throw new TooManyBindArgsException();
		
		String paramStr = new String(statement);
		
		int bindArgIndex=0;
		while(paramStr.indexOf(PARAMETER) > -1)
		{
			//is it length or length - 1?
			if(paramStr.indexOf(PARAMETER) == paramStr.length() -1)
			{
				//if it's on the very end of the string, truncate the parameter and add the 
				//last bindarg
				paramStr = paramStr.substring(0, paramStr.indexOf(PARAMETER)) + bindArgs[bindArgIndex].toString();
				break;
			}
			
			//is it length or length - 1?
			//this wont cause an IndexOutOfBoundsException because to do so the parameter would have to be on the
			//end and we checked for that
			//length -1 because the ending of substring is exclusive
			String remaining = paramStr.substring(paramStr.indexOf(PARAMETER)+1, paramStr.length());
			String alreadyProcessed = paramStr.substring(0, paramStr.indexOf(PARAMETER));
			paramStr = alreadyProcessed + bindArgs[bindArgIndex] + remaining;
			
			bindArgIndex++;
		}
		
		return paramStr;
	}
}
