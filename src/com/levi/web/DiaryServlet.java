package com.levi.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.levi.dao.DiaryDao;
import com.levi.model.Diary;
import com.levi.util.DbUtil;
import com.levi.util.StringUtil;

public class DiaryServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	DiaryDao diaryDao=new DiaryDao();
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ServletException, IOException
	{
		this.doPost(httpServletRequest, httpServletResponse);
	}
	@Override
	protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ServletException, IOException
	{
		httpServletRequest.setCharacterEncoding("utf-8");
		String action=httpServletRequest.getParameter("action");
		if("show".equals(action))
		{
			diaryShow(httpServletRequest,httpServletResponse);
			
		}
		else if("preSave".equals(action))
		{
			diaryPreSave(httpServletRequest, httpServletResponse);
		}
		else if("save".equals(action))
		{
			diarySave(httpServletRequest, httpServletResponse);
		}
		else if("delete".equals(action))
		{
			diaryDelete(httpServletRequest, httpServletResponse);
		}
		
	}
	public void diaryShow(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		String diaryId=httpServletRequest.getParameter("diaryId");
		Connection con=null;
		try
		{
			con=dbUtil.getCon();
			Diary diary=diaryDao.diaryShow(con, diaryId);
			httpServletRequest.setAttribute("diary", diary);
			httpServletRequest.setAttribute("mainPage", "diary/diaryShow.jsp");
			httpServletRequest.getRequestDispatcher("mainTemp.jsp").forward(httpServletRequest, httpServletResponse);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				dbUtil.closeCon(con);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void diaryPreSave(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		String diaryId=httpServletRequest.getParameter("diaryId");
		Connection con=null;
		try
		{
			if(StringUtil.isNotEmpty(diaryId))
			{
				con=dbUtil.getCon();
				Diary diary=diaryDao.diaryShow(con, diaryId);
				httpServletRequest.setAttribute("diary", diary);
				
			}
			httpServletRequest.setAttribute("mainPage", "diary/diarySave.jsp");
			httpServletRequest.getRequestDispatcher("mainTemp.jsp").forward(httpServletRequest, httpServletResponse);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				dbUtil.closeCon(con);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
	public void diarySave(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		String title=httpServletRequest.getParameter("title");
		String content=httpServletRequest.getParameter("content");
		String typeId=httpServletRequest.getParameter("typeId");
		String diaryId=httpServletRequest.getParameter("diaryId");
		Diary diary=new Diary(title,content,Integer.parseInt(typeId));
		Connection con=null;
		try
		{
			if(StringUtil.isNotEmpty(diaryId)){
				diary.setDiaryId(Integer.parseInt(diaryId));
			}
			
			con=dbUtil.getCon();
			int saveNums;
			if(StringUtil.isNotEmpty(diaryId)){
				saveNums=diaryDao.diaryUpdate(con, diary);	
			}else{
				saveNums=diaryDao.diaryAdd(con, diary);				
			}
			if(saveNums>0)
			{
				httpServletRequest.getRequestDispatcher("main?all=true").forward(httpServletRequest, httpServletResponse);
			}
			else
			{
				httpServletRequest.setAttribute("diary", diary);
				httpServletRequest.setAttribute("error", "保存失败");
				httpServletRequest.setAttribute("mainPage", "diary/diarySave.jsp");
				httpServletRequest.getRequestDispatcher("mainTemp.jsp").forward(httpServletRequest, httpServletResponse);
			}
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			try
			{
				dbUtil.closeCon(con);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void diaryDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		String diaryId=httpServletRequest.getParameter("diaryId");
		Connection con=null;
		try
		{
			con=dbUtil.getCon();
			int t=diaryDao.diaryDelete(con, diaryId);
			httpServletRequest.getRequestDispatcher("main?all=true").forward(httpServletRequest, httpServletResponse);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				dbUtil.closeCon(con);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
