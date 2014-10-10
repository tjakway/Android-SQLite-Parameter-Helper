package org.jakway.stringparams.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

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
