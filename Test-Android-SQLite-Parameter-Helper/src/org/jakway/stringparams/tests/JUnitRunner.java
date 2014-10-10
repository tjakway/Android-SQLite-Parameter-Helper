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

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * see https://github.com/tjakway/Android-SQLite-Parameter-Helper
 * @author thomasjakway
 */
public class JUnitRunner
{
	public static void main(String[] args) 
	{
		Result result = JUnitCore.runClasses(StringParameterTests.class);
		if(result.getFailureCount() < 1)
		{
			System.out.println("All tests successful!");
			System.out.println(result.getRunCount()+" tests run.");
		}
		else
		{
			System.err.println(result.getFailureCount()+" test failed.");
		}
		
		for (Failure failure : result.getFailures()) 
		{
			System.err.println("TEST FAILURE");
			System.err.println(failure.getTestHeader());
			System.err.println(failure.getMessage());
			System.err.println(failure.getDescription());
			System.err.println(failure.getTrace());
			if(failure.getException() != null)
				System.err.println(failure.getException());
		}
	}

}
