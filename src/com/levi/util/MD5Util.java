package com.levi.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5Util
{
	public static String EncoderPwdByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md5=MessageDigest.getInstance("MD5");
		BASE64Encoder base64en=new BASE64Encoder();
		String md52=base64en.encode(md5.digest(str.getBytes("utf-8")));
		return md52;
	}
	public static void main(String[] args)
	{
		try
		{
			System.out.println(EncoderPwdByMd5("1234"));
		} catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
