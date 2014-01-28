package de.mrphilip313.roleplaycore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class TestHTTPHandler extends AbstractHandler{

	@Override
	public void handle(String arg0, Request arg1, HttpServletRequest arg2,
			HttpServletResponse arg3) throws IOException, ServletException {
		arg3.setContentType("text/html;charset=utf-8");
		arg3.setStatus(HttpServletResponse.SC_OK);
		arg1.setHandled(true);
		arg3.getWriter().println("<h1>Test</h1>");
		
		
	}

}
