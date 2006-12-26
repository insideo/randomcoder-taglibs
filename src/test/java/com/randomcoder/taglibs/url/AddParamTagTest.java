package com.randomcoder.taglibs.url;

import java.io.StringWriter;

import javax.servlet.jsp.*;

import org.springframework.mock.web.*;

import com.randomcoder.taglibs.test.mock.jee.JspWriterMock;

import junit.framework.TestCase;

public class AddParamTagTest extends TestCase
{
	private AddParamTag tag;
	private ModifyTag parent;
	private MockPageContext context;
	private MockHttpServletRequest request;
	private StringWriter writer;

	@Override
	protected void setUp() throws Exception
	{
		tag = new AddParamTag();
		parent = new ModifyTag();		
		tag.setParent(parent);
		writer = new StringWriter();
		final JspWriterMock jspWriter = new JspWriterMock(writer);
		request = new MockHttpServletRequest();		
		request.setProtocol("http");
		request.setServerPort(80);
		request.setServerName("www.example.com");
		request.setContextPath("/context");
		request.setServletPath("/servlet");
		request.setPathInfo("/index.jsp");
		request.setQueryString("?param1=test1");
		context = new MockPageContext(new MockServletContext(), request) 
		{
			@Override
			public JspWriter getOut() { return jspWriter; }
		};
		tag.setPageContext(context);
		parent.setPageContext(context);
	}

	@Override
	protected void tearDown() throws Exception
	{		
		tag.release();
		tag = null;
		parent = null;
		context = null;
		request = null;
		writer.close();
		writer = null;
	}

	public void testDoEndTagNoParent() throws Exception
	{
		try
		{
			tag.setParent(null);
			tag.doEndTag();
			fail("JspException expected");
		}
		catch (JspException e)
		{
			// pass
		}
	}

	public void testDoEndTag() throws Exception
	{
		parent.doStartTag();
		tag.setName("param2");
		tag.setValue("test2");
		tag.doStartTag();
		assertEquals(1, parent.getParams().size());
		tag.doEndTag();
		assertEquals(2, parent.getParams().size());
	}
}
