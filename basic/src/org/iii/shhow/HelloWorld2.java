package org.iii.shhow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.io.PrintWriter;
//import java.io.File;
import java.util.*;
import java.io.*;

public class HelloWorld2
{
	static String SendGet(String url)
	{
		// 定义一个字符串用来存储网页内容
		String result = "";
		// 定义一个缓冲字符输入流
		BufferedReader in = null;
		try
		{
			// 将string转成url对象
			URL realUrl = new URL(url);
			// 初始化一个链接到那个url的连接
			URLConnection connection = realUrl.openConnection();
			// 开始实际的连接
			connection.connect();
			// 初始化 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			// 用来临时存储抓取到的每一行的数据
			String line;
			while ((line = in.readLine()) != null)
			{
				// 遍历抓取到的每一行并将其存储到result里面
				result += line;
			}
		} catch (Exception e)
		{
			System.out.println("GET request error！" + e);
			e.printStackTrace();
		}
		// 使用finally来关闭输入流
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		return result;
	}

	static String RegexString(String targetStr, String patternStr)
	{
		// 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
		// 相当于埋好了陷阱匹配的地方就会掉下去
		Pattern pattern = Pattern.compile(patternStr);
		// 定义一个matcher用来做匹配
		Matcher matcher = pattern.matcher(targetStr);
		// 如果找到了
		if (matcher.find())
		{
			// 打印出结果
			System.out.println(matcher.group(0));
			return matcher.group(1);
		}
		return "Nothing";
	}

	
	public static String TdParser(String s) {
		int mode=0;
		String resultData="";
		for(int i=3; i<s.length(); i++) {
			//System.out.println(result.charAt(i));
			if(mode==0) {
				if(s.charAt(i-3)=='<' && 
					s.charAt(i-2)=='t' && 
					s.charAt(i-1)=='d' && 
					s.charAt(i)=='>' && s.charAt(i+1)!='<') {
					mode=1;
				}
			}
			else if(mode==1){
				System.out.print(s.charAt(i));
				resultData+=s.charAt(i);
				if(s.charAt(i+1)=='<') {
					mode=0;
					System.out.print("\n");
					resultData+="\n";
				}
			}
		}
		return resultData;
	}
	
	public static ArrayList<String> Parser(String s){
		ArrayList<String> tokenArray = new ArrayList<String>();
		String buff="";
		int mode=0;
		//mode
		//0: 
		//1: 
		//
		//
		for(int i=3; i<s.length(); i++) {
			System.out.print(s.charAt(5));
			if(s.charAt(i-3)=='<' && 
					s.charAt(i-2)=='t' && 
					s.charAt(i-1)=='d' && 
					s.charAt(i)=='>') {
				mode=1;		//pass <td>
			}
			else if(mode==1) {
				char c = s.charAt(i);
				if(c!=0x20) {
					buff+=c;
				}
			}
			else if(mode==1 && s.charAt(i)=='<') {
				tokenArray.add(buff);
				buff="";
				mode=0;
			}
			else {mode=0;}
			

			
		}

		return tokenArray;
	}
	
	
	public static void main(String[] args)
	{
		String[] dateArray= {"20180327"};
		//String[] dateArray= {"20180327", "20180326", "20180323", "20180322", "20180321", "20180320"};
		
		
		// 定义即将访问的链接
		String url0 = "http://www.twse.com.tw/fund/TWT44U?response=html&date=";
		for(String date : dateArray) {
			String url = url0 + date;
			System.out.println(url);
			// 访问链接并获取页面内容
			String result = SendGet(url);
			//System.out.println(result);
			
			
			try (PrintWriter out = new PrintWriter(date + ".txt")) {
				out.println(result);
			} catch (Exception e3) {
				System.out.println(e3);
			}
			
			//show processing date
			//String imgSrc = RegexString(result, "<td>(.+?)</td>");
			//String imgSrc = RegexString(result, "<title>(.+?)</title>");
			//System.out.println(imgSrc);
			
			//String resultData=TdParser(result);
			ArrayList<String> resultData=Parser(result);

			try (PrintWriter out = new PrintWriter("test.txt")) {
				out.println(resultData.get(1));
			} catch (Exception e3) {
				System.out.println(e3);
			}
			
			/*
			try (PrintWriter out = new PrintWriter(date + "_resultData.txt")) {
				out.println(resultData);
			} catch (Exception e3) {
				System.out.println(e3);
			}
			*/
			
		}
		
		
		/*
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));
		    out.write("aString\nthis is a\nttest");  //Replace with the string 
		                                             //you are trying to write
		}
		catch (IOException e)
		{
		    System.out.println("Exception ");

		}
		finally
		{
		    //out.close();
		}
		*/
		//PrintWriter printWriter = new PrintWriter(file);

	}
}