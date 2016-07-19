package com.levi.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.levi.dao.DiaryDao;
import com.levi.dao.DiaryTypeDao;
import com.levi.model.Diary;
import com.levi.model.DiaryType;
import com.levi.util.DbUtil;
import com.levi.util.StringUtil;

public class DiaryTypeServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	DiaryTypeDao diaryTypeDao=new DiaryTypeDao();
	DiaryDao diaryDao=new DiaryDao();
	
	
	@Override
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
		if("list".equals(action))
		{
			diaryTypeList(httpServletRequest, httpServletResponse);
		}
		else if("preSave".equals(action))
		{
			diaryTypePreSave(httpServletRequest,httpServletResponse);
		}
		else if("save".equals(action))
		{
			diaryTypeSave(httpServletRequest,httpServletResponse);
		}
		else if("delete".equals(action))
		{
			diaryTypeDelete(httpServletRequest,httpServletResponse);
		}
	}
	public void diaryTypeList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		Connection con=null;
		try
		{
			con=dbUtil.getCon();
			List<DiaryType> diaryTypeList=diaryTypeDao.diaryTypeList(con);
			httpServletRequest.setAttribute("diaryTypeList", diaryTypeList);
			httpServletRequest.setAttribute("mainPage", "diaryType/diaryTypeList.jsp");
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
	public void diaryTypePreSave(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		String diaryTypeId=httpServletRequest.getParameter("diaryTypeId");
		if(StringUtil.isNotEmpty(diaryTypeId))
		{
			Connection con=null;
			try
			{
				con=dbUtil.getCon();
				DiaryType diaryType=diaryTypeDao.diaryTypeShow(con,diaryTypeId);
				httpServletRequest.setAttribute("diaryType", diaryType);
				
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
		httpServletRequest.setAttribute("mainPage", "diaryType/diaryTypeSave.jsp");
		try
		{
			httpServletRequest.getRequestDispatcher("mainTemp.jsp").forward(httpServletRequest, httpServletResponse);
		} catch (ServletException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void diaryTypeSave(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
	{
		String diaryTypeId = httpServletRequest.getParameter("diaryTypeId");
		String typeName = httpServletRequest.getParameter("typeName");
		DiaryType diaryType = new DiaryType(typeName);
		if (StringUtil.isNotEmpty(diaryTypeId))
		{
			diaryType.setDiaryTypeId(Integer.parseInt(diaryTypeId));
		}
		Connection con = null;
		try
		{
			con = dbUtil.getCon();
			int saveNum = 0;
			if (StringUtil.isNotEmpty(diaryTypeId))
			{
				saveNum = diaryTypeDao.diaryTypeUpdate(con, diaryType);
			} else
			{
				saveNum = diaryTypeDao.diaryTypeAdd(con, diaryType);
			}
			if (saveNum > 0)
			{
				httpServletRequest
						.getRequestDispatcher("diaryType?action=list").forward(
								httpServletRequest, httpServletResponse);
			} else
			{
				httpServletRequest.setAttribute("diaryType", diaryType);
				httpServletRequest.setAttribute("error", "保存失败!");
				httpServletRequest.setAttribute("mainPage",
						"diaryType/diaryTypeSave.jsp");
				httpServletRequest.getRequestDispatcher("mainTemp.jsp").forward(httpServletRequest, httpServletResponse);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
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
	public void diaryTypeDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		String diaryTypeId=httpServletRequest.getParameter("diaryTypeId");
		Connection con=null;
		if(StringUtil.isNotEmpty(diaryTypeId))
		{
			
			try
			{
				con=dbUtil.getCon();
				if(diaryDao.existDiaryWithTypeId(con, diaryTypeId))
				{
					httpServletRequest.setAttribute("error", "日志类别下有日志，不能删除该类别!");
				}
				else
				{
					diaryTypeDao.diaryTypeDelete(con, diaryTypeId);
				}
				httpServletRequest.getRequestDispatcher("diaryType?action=list").forward(httpServletRequest, httpServletResponse);
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
}
