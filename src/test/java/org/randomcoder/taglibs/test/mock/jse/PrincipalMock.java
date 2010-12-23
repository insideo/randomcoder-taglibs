package org.randomcoder.taglibs.test.mock.jse;

import java.security.Principal;

public class PrincipalMock implements Principal
{
	private String name;
	
	public PrincipalMock(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
}
