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

package org.jakway.stringparams.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jakway.stringparams.Params;
import org.jakway.stringparams.exceptions.NoBindArgsException;
import org.jakway.stringparams.exceptions.NoParametersException;
import org.jakway.stringparams.exceptions.NullBindArgsException;
import org.jakway.stringparams.exceptions.NullStatementException;
import org.jakway.stringparams.exceptions.TooManyBindArgsException;
import org.jakway.stringparams.exceptions.TooManyParametersException;
import org.junit.Test;

/**
 * see https://github.com/tjakway/Android-SQLite-Parameter-Helper
 * @author thomasjakway
 */
public class StringParameterTests
{	
	@Test
	public void testSingleElement()
	{
		int favNum = 2935893;
		String statement = "My favorite number=?",
				expectedResult = "My favorite number="+2935893;
		
		String result = Params.parameterizeString(statement, new Object[] { Integer.valueOf(favNum) });
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testTooShortArray()
	{
		//statement with 4 parameters
		String statement = "Aklwke ? asljfwe? t1lk23? wedk2?";
		
		//only 2 bindArgs
		Object[] bindArgs = { Integer.valueOf(40329), Integer.valueOf(35893) };
		
		boolean caughtException = false;
		try {
		Params.parameterizeString(statement, bindArgs);
		} catch(TooManyParametersException e)
		{
			caughtException = true;
		}
		assertTrue(caughtException);
	}
	
	@Test
	public void testNullArray()
	{
		String statement = "asdkf ? welkrwk?";
		
		boolean exceptionCaught = false;
		try {
			Params.parameterizeString(statement, null);
		} catch(NullBindArgsException e)
		{
			exceptionCaught = true;
		}
		
		assertTrue(exceptionCaught);
	}
	
	@Test
	public void testMultiplePlaceHolders()
	{
		String statement = "my_birthday:?__my_favorite_ice_cream:?  your favorite number:?__df",
				expected = "my_birthday:1-12-80__my_favorite_ice_cream:chocolate  your favorite number:2.39283__df";
		
		Object[] bindArgs = { "1-12-80", "chocolate", Double.valueOf(2.39283)};
		
		String result = Params.parameterizeString(statement, bindArgs);
		
		assertEquals(expected, result);
	}
	
	@Test
	public void testTooManyObjects()
	{ 
		String statement = "INSERT INTO ? VALUES(?);";
		Object[] bindArgs = { "test_table", Integer.valueOf(2948), "test_arg", Integer.valueOf(23) };
		
		boolean caught=false;
		
		try
		{
			Params.parameterizeString(statement, bindArgs);
		}
		catch(TooManyBindArgsException e)
		{
			caught = true;
		}
		
		assertTrue(caught);
	}
	
	@Test
	public void testNullStatement()
	{
		boolean caught=false;
		try
		{
			Params.parameterizeString(null, new Object[] { "test", "test2" });
		}
		catch(NullBindArgsException e)
		{
			fail("NullBindArgsException should not come before NullStatementException!");
		}
		catch(NullStatementException e)
		{
			caught = true;
		}
		
		assertTrue(caught);
	}
	
	@Test
	public void testNullStatementPrecedence()
	{
		boolean caught=false;
		try
		{
			Params.parameterizeString(null, null);
		}
		catch(NullBindArgsException e)
		{
			fail("NullBindArgsException should not come before NullStatementException!");
		}
		catch(NullStatementException e)
		{
			caught = true;
		}
		
		assertTrue(caught);
	}
	
	@Test
	public void testNoParameters()
	{
		boolean caught=false;
		try
		{
			//can't pass null for bindArgs or it will throw a NullBindArgsException
			//the null exceptions take precedence over the No*Exceptions
			Params.parameterizeString("k;jlkjlj", new Object[] { "one", "two" });
		}
		catch(NoParametersException e)
		{
			caught = true;
		}
		
		assertTrue(caught);
	}
	
	@Test
	public void testNoBindArgs()
	{
		boolean caught=false;
		try
		{
			//can't pass null for bindArgs or it will throw a NullBindArgsException
			//the null exceptions take precedence over the No*Exceptions
			Params.parameterizeString("sdfle?", new Object[] { });
		}
		catch(NoBindArgsException e)
		{
			caught = true;
		}
		
		assertTrue(caught);
	}
	
	@Test
	public void testOnlyParam()
	{
		String statement = "?",
				expected = "cake and stuff";
		
		//only passing the parameter--it should swap the ? with the entire expected string
		assertEquals(Params.parameterizeString(statement, new Object[] { expected }),
				expected);
	}
	
	@Test
	public void testSingleBeginningParam()
	{
		String statement = "?_kwelqsofpwer",
				expected = "grea_kwelqsofpwer",
				arg = "grea";
		
		String result = Params.parameterizeString(statement, new Object[] { arg });
		
		assertEquals(result, expected);
	}
	
	@Test
	public void testSingleEndingParam()
	{
		String statement = "_kwelqsofpwer?",
				expected = "_kwelqsofpwergrea",
				arg = "grea";
		
		String result = Params.parameterizeString(statement, new Object[] { arg });
		
		assertEquals(result, expected);
	}
	
	@Test
	public void testMultiBeginningParam()
	{
		String statement = "?wel;1-?-we;lkr24?__kl",
				expected = "3958wel;1-290394-we;lkr24wefa__kl";
		Object[] args = {"3958", "290394", "wefa" };
		
		//WARNING!  accidentally wrapping args in Object[] { args } will throw an exception!
		String result = Params.parameterizeString(statement, args);
		
		assertEquals(result, expected);
	}
	
	@Test
	public void testMultiEndingParam()
	{
		String statement = "?wel;1-?-we;lkr24__kl?",
				expected = "3958wel;1-290394-we;lkr24__klwefa";
		Object[] args = {"3958", "290394", "wefa" };
		
		String result = Params.parameterizeString(statement, args);
		
		assertEquals(result, expected);
	}
}
