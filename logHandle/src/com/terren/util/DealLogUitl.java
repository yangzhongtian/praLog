package com.terren.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.terren.NewsLog;

public class DealLogUitl {
	public static void  getLogList(String filestr){
		List<NewsLog> list =ReadFileLine("E:/FileWorkEcplise/.china.com.cn.full_webdig_201408121640.log");
	}
	
	//按行读取文件
			public static List<NewsLog> ReadFileLine(String  filePath){
				long befMem =Runtime.getRuntime().freeMemory();
				System.out.println("程序开始前的剩余内存"+befMem);
				File file = new File(filePath);
				BufferedReader  bReader =null;
				try {
					bReader = new BufferedReader(new FileReader(file));
					int line =0;
					long bef = System.currentTimeMillis();
					
					String tempString="";
					List<NewsLog> list = new ArrayList<NewsLog>();
					while((tempString = bReader.readLine())!=null){
						NewsLog newsLog1  = new  NewsLog();
						dealString(tempString,newsLog1);
						line++;
						list.add(newsLog1);
						
					}
					long aft  = System.currentTimeMillis();
					
					System.out.println("用时:"+(aft-bef)+"毫秒");
					
					System.out.println("总共条数:"+list.size()+"条");
					Map <String,String> map  = new HashMap<String, String>();
					Map <String,String> mapN  = new HashMap<String, String>();
					Map <String,String> mapO  = new HashMap<String, String>();
					int newR=0;
					int oldR=0;
					int otherTyp=0;
					
					for(int h =0;h<list.size();h++){
						NewsLog log = list.get(h);
						if((log.getVisitType()).equals("0")){
							mapN.put(log.getCookie(), "");
							newR++;
						}else if((log.getVisitType()).equals("1")){
							mapO.put(log.getCookie(), "");
							oldR++;
						}else if((log.getVisitType()).equals("-")) {
							otherTyp++;
						}
						
						map.put(list.get(h).getCookie(), "s");
					}
					System.out.println("uv数"+map.size()+"  pv数"+list.size());
					System.out.println("新访: uv数="+mapN.size()+" ,pv数="+newR);
					System.out.println("回访: uv数="+mapO.size()+" ,pv数="+oldR);
					System.out.println("访问项为  - 个数: "+otherTyp);
				/*****************新访和回访的Uv*************************/
					return list;
				}catch(FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
				
				
				
			}
			
			
			public static void  dealString(String temString,NewsLog newsLog){
				String [] arr  = new String[8];
			//	System.out.println(temString);
				StringTokenizer stringTokenizer = new StringTokenizer(temString);
				int i= 0;
				while(stringTokenizer.hasMoreElements()){
					if(i==1){
						arr[i] = stringTokenizer.nextToken("[]");
					}else {
						arr[i] = stringTokenizer.nextToken("[");
					}
					
					i++;
				}
				
				
					 dealBefsString(arr[0],newsLog);
					 dealAgent(arr[1],newsLog);
				
			}
			
			//对每行String 进行处理
			public static void dealBefsString(String str,NewsLog lo){
				StringTokenizer stringTokenizer = new StringTokenizer(str);
				int i=0;
				while(stringTokenizer.hasMoreElements()){
						i++;
						switch (i) {
						case 1:
							lo.setTime(stringTokenizer.nextToken());
							break;
						case 2:
							lo.setIp(stringTokenizer.nextToken());
							break;
						case 3:
							lo.setCookie(stringTokenizer.nextToken());
							break;
						case 4:
							lo.setVisitType(stringTokenizer.nextToken());
							break;
						case 5:
							lo.setUrl(stringTokenizer.nextToken().replace("\"",""));
							break;
						case 6:
							lo.setRefer(stringTokenizer.nextToken().replace("\"",""));
							break;
						case 7:
							lo.setLength(stringTokenizer.nextToken());
							break;
							
						case 8:
							lo.setTitle(stringTokenizer.nextToken());
							break;
						case 9:
							lo.setCodeing(stringTokenizer.nextToken());
							break;
						case 10:
							lo.setCollected(stringTokenizer.nextToken());
							break;
						
						default:
							stringTokenizer.nextToken();
							break;
						}
//						lo.setCookie("sd ");
//						lo.setVisitType((Math.random()>0.5?0:1)+"");
//						lo.setRefer((Math.random()>0.5?0:1)+"");
//						stringTokenizer.nextToken();
				}
				
				
				}
			
			//对userAgent处理
			public static void dealAgent(String str, NewsLog lo){
				StringTokenizer str2= new StringTokenizer(str);
//				System.out.println(str+"==========================================================");
				int z=0;
				while(str2.hasMoreTokens()){
					
					switch (z) {
					case 0:
						lo.setvTime(str2.nextToken("\"").trim());
						break;
					case 1:
						lo.setUa(str2.nextToken("\""));
						break;
					case 2:
						str2.nextToken("\"").trim();
						
						break;
					default:
						
						str2.nextToken(" ").replace("\"", "");
						break;
					}
					z++;
				}
			}
}
