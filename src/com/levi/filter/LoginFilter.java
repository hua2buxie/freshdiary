package com.levi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter
{

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		HttpServletResponse response=(HttpServletResponse)servletResponse;
		HttpSession session=request.getSession();
		Object object=session.getAttribute("currentUser");
		String path=request.getServletPath();
		System.out.println(path);
		if(object==null&&path.indexOf("login")<0&&path.indexOf("bootstrap")<0&&path.indexOf("images")<0&&path.indexOf("style")<0&&path.indexOf("login.jsp")<0&&path.indexOf("login")<0)
		{
			response.sendRedirect("login.jsp");
		}
		else
		{
			int a,b;
			
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		// TODO Auto-generated method stub
		
	}
	
}
