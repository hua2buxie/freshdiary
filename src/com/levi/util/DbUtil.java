package com.levi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil
{
	/*private String dburl="jdbc:mysql://localhost:3306/db_diary?seUnicode=true&characterEncoding=UTF8";
	private String dbUserName="root";
	private String dbPassword="root";
	private String jdbcName="com.mysql.jdbc.Driver";
	*/
	public Connection getCon() throws Exception
	{
		/*Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dburl,dbUserName,dbPassword);*/
		Class.forName(PropertiesUtil.getValue("jdbcName"));
		Connection con=DriverManager.getConnection(PropertiesUtil.getValue("dburl"),PropertiesUtil.getValue("dbUserName"),PropertiesUtil.getValue("dbPassword"));
		return con;
	}
	public void closeCon(Connection con) throws Exception
	{
		if(con!=null)
			con.close();
	}
	public static void main(String[] args)
	{
		DbUtil dbUtil=new DbUtil();
		try
		{
			dbUtil.getCon();
			System.out.println("数据库连接成功");
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			System.out.println("数据库连接失败"+e.getMessage());
		}
	}
}
