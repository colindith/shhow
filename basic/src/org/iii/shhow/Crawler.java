package org.iii.shhow;

import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.io.PrintWriter;
//import java.io.File;
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Crawler {

	static String SendGet(String url)
	{
		//定義一個字串來抓取網頁內容
		String result = "";
		// 摰��銝芰�摮泵颲瘚�
		BufferedReader in = null;
		try
		{
			// 撠tring頧祆�rl撖寡情
			URL realUrl = new URL(url);
			// �����銝芷���銝沿rl���
			URLConnection connection = realUrl.openConnection();
			// 撘�憪����
			connection.connect();
			// ����� BufferedReader颲瘚霂餃�RL�����
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			// ��銝湔摮�������銵��
			String line;
			while ((line = in.readLine()) != null)
			{
				// ���������銵僎撠摮�result��
				result += line;
			}
		} catch (Exception e)
		{
			System.out.println("GET request error嚗�" + e);
			e.printStackTrace();
		}
		// 雿輻finally���颲瘚�
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
		// 摰��銝芣撘芋�嚗迨銝凋蝙�甇���”颲曉���銝剜閬���捆
		// �敶��末鈭������撠曹���
		Pattern pattern = Pattern.compile(patternStr);
		// 摰��銝沸atcher�������
		Matcher matcher = pattern.matcher(targetStr);
		// 憒��鈭�
		if (matcher.find())
		{
			// ���蝏��
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
		//mode 0:not recording, 1: recording
		for(int i=3; i<s.length(); i++) {
		//	System.out.print("-i="+i+"-");
		//	System.out.print(s.charAt(i));
			if(s.charAt(i-3)=='<' && 
				s.charAt(i-2)=='t' && 
				s.charAt(i-1)=='d' && 
				s.charAt(i)=='>') {
				mode=1;		//pass <td>
			}
			else if(mode==1 && s.charAt(i)=='<') {
				tokenArray.add(buff);
			//	System.out.print("+buff="+buff+"+");
				buff="";
				mode=0;
			}
			else if(mode==1) {
				char c = s.charAt(i);
				if(c!=0x20 && c!=',') {
					buff+=c;
				}
			}
			else {mode=0;}
			

			
		}

		return tokenArray;
	}
	
	public static void ListTable(ArrayList<String> tokenArray, int colCount) {
		int i=0;
		for(String s: tokenArray) {
			if(!s.isEmpty()) {
				System.out.printf("%12s", s);
				i++;
				if(i%colCount==0) System.out.print("\n");
			}
		}
	}
	
	public static void ListTableFile(ArrayList<String> tokenArray, int colCount, String fileName) {
		int i=0;
		try (PrintWriter writer = new PrintWriter(fileName)) {
			for(String s: tokenArray) {
				if(!s.isEmpty()) {
					writer.printf("%10s", s+",");
					i++;
					if(i%colCount==0) writer.print("\n");
				}
			}
		} catch (Exception e3) {
			System.out.println("write test file error: "+e3);
		}

	}
	
	public static void ListTableFileTrust(ArrayList<String> tokenArray, String fileName) {
		int i=0, j=1;
		
		try (PrintWriter writer = new PrintWriter(fileName)) {
			writer.printf("%10s", "����"+",");
			for(String s: tokenArray) {
				if(!s.isEmpty()) {
					writer.printf("%10s", s+",");
					i++;
					if(i%5==0) {
						writer.print("\n");
						writer.printf("%10s", j+",");
						j++;
					}
					
				}
			}
			writer.close();
		} catch (Exception e3) {
			System.out.println("write test file error: "+e3);
		}

	}
	public static void ListTableFileStockDay(ArrayList<String> tokenArray, String fileName){
		int i=0;
		byte[] BOM_UTF8 = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		try (FileOutputStream writerByte = new FileOutputStream(fileName,false)){
			writerByte.write(BOM_UTF8);

			for(String s: tokenArray) {
				if(!s.isEmpty()) {
					writerByte.write((s+",").getBytes("utf8"));
					i++;
					if(i%9==0) {
						writerByte.write("\n".getBytes("utf8"));
					}
				}
			}
		} catch (Exception e3) {
			System.out.println("write byte error: "+e3);
		}
		/*
		try (PrintWriter writer = new PrintWriter(fileName)) {
			
			for(String s: tokenArray) {
				if(!s.isEmpty()) {
					writer.append(s+",");
					i++;
					if(i%9==0) {
						writer.append("\n");
					}
				}
			}
			writer.close();
		} catch (Exception e3) {
			System.out.println("write test file error: "+e3);
		}
		*/
		System.out.println("done!");
		
	}
	public static void Trust() {
		String[] dateArray= {"20180327"};
	/*	String[] dateArray= {
				"20180327", 
				"20180326", 
				"20180323", 
				"20180322", 
				"20180321", 
				"20180320"
				};
	*/	
		//define the urls visits
		String url0 = "http://www.twse.com.tw/fund/TWT44U?response=html&date=";
		for(String date : dateArray) {
			String url = url0 + date;
			System.out.println(url);
			//visit and receive content
			String result = SendGet(url);
		//	System.out.println(result);
			
			try (PrintWriter writer = new PrintWriter(date + ".txt")) {
				writer.println(result);
				writer.close();
			} catch (Exception e3) {
				System.out.println(e3);
			}
			
			//search certain pattern in text
		//	String imgSrc = RegexString(result, "<td>(.+?)</td>");
		//	String imgSrc = RegexString(result, "<title>(.+?)</title>");
		//	System.out.println(imgSrc);
			
		//	String resultData=TdParser(result);
			ArrayList<String> resultData=Parser(result);
		//	ListTable(resultData, 5);
		//	ListTableFile(resultData, 5, "test.csv");
			ListTableFileTrust(resultData, "test.csv");
		}
	}
	public static void StockDay() {
	//	String[] dateArray = {"20180301","20180201","20180101", };
		String[] dateArray = {
				"20180301",
				"20180201", 
				"20180101", 
				"20171201", 
				"20171101", 
				"20171001", 
				"20170901",
				"20171201",
				"20171101",
				"20171001",
				"20170901",
				"20170801",
				"20170701",
				"20170601",
				"20170501",
				"20170401",
				"20170301",
				"20170201",
				"20170101",
				"20161201",
				"20161101",
				"20161001",
				"20160901",
				"20160801",
				"20160701",
				"20160601",
				"20160501",
				"20160401",
				"20160301",
				"20160201",
				"20160101",
				"20151201",
				"20151101",
				"20151001",
				"20150901",
				"20150801",
				"20150701",
				"20150601",
				"20150501",
				"20150401",
				"20150301",
				"20150201",
				"20150101"
			/*	"20141201",
				"20141101",
				"20141001",
				"20140901",
				"20140801",
				"20140701",
				"20140601",
				"20140501",
				"20140401",
				"20140301",
				"20140201",
				"20140101",
				"20131201",
				"20131101",
				"20131001",
				"20130901",
				"20130801",
				"20130701",
				"20130601",
				"20130501",
				"20130401",
				"20130301",
				"20130201",
				"20130101",
				"20121201",
				"20121101",
				"20121001",
				"20120901",
				"20120801",
				"20120701",
				"20120601",
				"20120501",
				"20120401",
				"20120301",
				"20120201",
				"20120101",
				"20111201",
				"20111101",
				"20111001",
				"20110901",
				"20110801",
				"20110701",
				"20110601",
				"20110501",
				"20110401",
				"20110301",
				"20110201",
				"20110101",
				"20101201",
				"20101101",
				"20101001",
				"20100901",
				"20100801",
				"20100701",
				"20100601",
				"20100501",
				"20100401",
				"20100301",
				"20100201",
				"20100101"*/
				};
		
		String[] stockNoArray = {
				"1101",
				"1102",
				"1103",
				"1104",
				"1108",
				"1109",
				"1110",
				"1201",
				"1203",
				"1210",
				"1213",
				"1215",
				"1216",
				"1217",
				"1218",
				"1219",
				"1220",
				"1225",
				"1227",
				"1229",
				"1231",
				"1232",
				"1233",
				"1234",
				"1235",
				"1236",
				"1256",
				"1262",
				"1301",
				"1303",
				"1304",
				"1305",
				"1307",
				"1308",
				"1309",
				"1310",
				"1312",
				"1312A",
				"1313",
				"1314",
				"1315",
				"1316",
				"1319",
				"1321",
				"1323",
				"1324",
				"1325",
				"1326",
				"1337",
				"1338",
				"1339",
				"1340",
				"1402",
				"1409",
				"1410",
				"1413",
				"1414",
				"1416",
				"1417",
				"1418",
				"1419",
				"1423",
				"1432",
				"1434",
				"1435",
				"1436",
				"1437",
				"1438",
				"1439",
				"1440",
				"1441",
				"1442",
				"1443",
				"1444",
				"1445",
				"1446",
				"1447",
				"1449",
				"1451",
				"1452",
				"1453",
				"1454",
				"1455",
				"1456",
				"1457",
				"1459",
				"1460",
				"1463",
				"1464",
				"1465",
				"1466",
				"1467",
				"1468",
				"1469",
				"1470",
				"1471",
				"1472",
				"1473",
				"1474",
				"1475",
				"1476",
				"1477",
				"1503",
				"1504",
				"1506",
				"1507",
				"1512",
				"1513",
				"1514",
				"1515",
				"1516",
				"1517",
				"1519",
				"1521",
				"1522",
				"1524",
				"1525",
				"1526",
				"1527",
				"1528",
				"1529",
				"1530",
				"1531",
				"1532",
				"1533",
				"1535",
				"1536",
				"1537",
				"1538",
				"1539",
				"1540",
				"1541",
				"1558",
				"1560",
				"1568",
				"1582",
				"1583",
				"1589",
				"1590",
				"1592",
				"1598",
				"1603",
				"1604",
				"1605",
				"1608",
				"1609",
				"1611",
				"1612",
				"1614",
				"1615",
				"1616",
				"1617",
				"1618",
				"1626",
				"1701",
				"1702",
				"1704",
				"1707",
				"1708",
				"1709",
				"1710",
				"1711",
				"1712",
				"1713",
				"1714",
				"1717",
				"1718",
				"1720",
				"1721",
				"1722",
				"1723",
				"1724",
				"1725",
				"1726",
				"1727",
				"1730",
				"1731",
				"1732",
				"1733",
				"1734",
				"1735",
				"1736",
				"1737",
				"1760",
				"1762",
				"1773",
				"1776",
				"1783",
				"1786",
				"1789",
				"1802",
				"1805",
				"1806",
				"1808",
				"1809",
				"1810",
				"1817",
				"1902",
				"1903",
				"1904",
				"1905",
				"1906",
				"1907",
				"1909",
				"2002",
				"2002A",
				"2006",
				"2007",
				"2008",
				"2009",
				"2010",
				"2012",
				"2013",
				"2014",
				"2015",
				"2017",
				"2020",
				"2022",
				"2023",
				"2024",
				"2025",
				"2027",
				"2028",
				"2029",
				"2030",
				"2031",
				"2032",
				"2033",
				"2034",
				"2038",
				"2049",
				"2059",
				"2062",
				"2069",
				"2101",
				"2102",
				"2103",
				"2104",
				"2105",
				"2106",
				"2107",
				"2108",
				"2109",
				"2114",
				"2115",
				"2201",
				"2204",
				"2206",
				"2207",
				"2208",
				"2227",
				"2228",
				"2231",
				"2236",
				"2239",
				"2243",
				"2301",
				"2302",
				"2303",
				"2305",
				"2308",
				"2311",
				"2312",
				"2313",
				"2314",
				"2316",
				"2317",
				"2321",
				"2323",
				"2324",
				"2325",
				"2327",
				"2328",
				"2329",
				"2330",
				"2331",
				"2332",
				"2337",
				"2338",
				"2340",
				"2342",
				"2344",
				"2345",
				"2347",
				"2348",
				"2349",
				"2351",
				"2352",
				"2353",
				"2354",
				"2355",
				"2356",
				"2357",
				"2358",
				"2359",
				"2360",
				"2362",
				"2363",
				"2364",
				"2365",
				"2367",
				"2368",
				"2369",
				"2371",
				"2373",
				"2374",
				"2375",
				"2376",
				"2377",
				"2379",
				"2380",
				"2382",
				"2383",
				"2385",
				"2387",
				"2388",
				"2390",
				"2392",
				"2393",
				"2395",
				"2397",
				"2399",
				"2401",
				"2402",
				"2404",
				"2405",
				"2406",
				"2408",
				"2409",
				"2412",
				"2413",
				"2414",
				"2415",
				"2417",
				"2419",
				"2420",
				"2421",
				"2423",
				"2424",
				"2425",
				"2426",
				"2427",
				"2428",
				"2429",
				"2430",
				"2431",
				"2433",
				"2434",
				"2436",
				"2438",
				"2439",
				"2440",
				"2441",
				"2442",
				"2443",
				"2444",
				"2448",
				"2449",
				"2450",
				"2451",
				"2453",
				"2454",
				"2455",
				"2456",
				"2457",
				"2458",
				"2459",
				"2460",
				"2461",
				"2462",
				"2464",
				"2465",
				"2466",
				"2467",
				"2468",
				"2471",
				"2472",
				"2474",
				"2475",
				"2476",
				"2477",
				"2478",
				"2480",
				"2481",
				"2482",
				"2483",
				"2484",
				"2485",
				"2486",
				"2488",
				"2489",
				"2491",
				"2492",
				"2493",
				"2495",
				"2496",
				"2497",
				"2498",
				"2499",
				"2501",
				"2504",
				"2505",
				"2506",
				"2509",
				"2511",
				"2514",
				"2515",
				"2516",
				"2520",
				"2524",
				"2527",
				"2528",
				"2530",
				"2534",
				"2535",
				"2536",
				"2537",
				"2538",
				"2539",
				"2540",
				"2542",
				"2543",
				"2545",
				"2546",
				"2547",
				"2548",
				"2597",
				"2601",
				"2603",
				"2605",
				"2606",
				"2607",
				"2608",
				"2609",
				"2610",
				"2611",
				"2612",
				"2613",
				"2614",
				"2615",
				"2616",
				"2617",
				"2618",
				"2630",
				"2633",
				"2634",
				"2636",
				"2637",
				"2642",
				"2701",
				"2702",
				"2704",
				"2705",
				"2706",
				"2707",
				"2712",
				"2722",
				"2723",
				"2727",
				"2731",
				"2739",
				"2748",
				"2801",
				"2809",
				"2812",
				"2816",
				"2820",
				"2823",
				"2832",
				"2834",
				"2836",
				"2838",
				"2838A",
				"2841",
				"2845",
				"2849",
				"2850",
				"2851",
				"2852",
				"2855",
				"2856",
				"2867",
				"2880",
				"2881",
				"2881A",
				"2882",
				"2882A",
				"2883",
				"2884",
				"2885",
				"2886",
				"2887",
				"2887E",
				"2888",
				"2889",
				"2890",
				"2891",
				"2891B",
				"2892",
				"2897",
				"2901",
				"2903",
				"2904",
				"2905",
				"2906",
				"2908",
				"2910",
				"2911",
				"2912",
				"2913",
				"2915",
				"2923",
				"2929",
				"2936",
				"2939",
				"3002",
				"3003",
				"3004",
				"3005",
				"3006",
				"3008",
				"3010",
				"3011",
				"3013",
				"3014",
				"3015",
				"3016",
				"3017",
				"3018",
				"3019",
				"3021",
				"3022",
				"3023",
				"3024",
				"3025",
				"3026",
				"3027",
				"3028",
				"3029",
				"3030",
				"3031",
				"3032",
				"3033",
				"3034",
				"3035",
				"3036",
				"3037",
				"3038",
				"3040",
				"3041",
				"3042",
				"3043",
				"3044",
				"3045",
				"3046",
				"3047",
				"3048",
				"3049",
				"3050",
				"3051",
				"3052",
				"3054",
				"3055",
				"3056",
				"3057",
				"3058",
				"3059",
				"3060",
				"3062",
				"3090",
				"3094",
				"3130",
				"3149",
				"3164",
				"3167",
				"3189",
				"3209",
				"3229",
				"3231",
				"3257",
				"3266",
				"3296",
				"3305",
				"3308",
				"3311",
				"3321",
				"3338",
				"3346",
				"3356",
				"3376",
				"3380",
				"3383",
				"3406",
				"3413",
				"3416",
				"3419",
				"3432",
				"3437",
				"3443",
				"3450",
				"3454",
				"3481",
				"3494",
				"3501",
				"3504",
				"3514",
				"3515",
				"3518",
				"3519",
				"3528",
				"3532",
				"3533",
				"3535",
				"3536",
				"3545",
				"3550",
				"3557",
				"3561",
				"3576",
				"3579",
				"3583",
				"3588",
				"3591",
				"3593",
				"3596",
				"3605",
				"3607",
				"3617",
				"3622",
				"3645",
				"3653",
				"3661",
				"3665",
				"3669",
				"3673",
				"3679",
				"3682",
				"3686",
				"3694",
				"3698",
				"3701",
				"3702",
				"3703",
				"3704",
				"3705",
				"3706",
				"3708",
				"4104",
				"4106",
				"4108",
				"4119",
				"4133",
				"4137",
				"4141",
				"4142",
				"4144",
				"4148",
				"4155",
				"4164",
				"4190",
				"4306",
				"4414",
				"4426",
				"4438",
				"4526",
				"4532",
				"4536",
				"4545",
				"4551",
				"4552",
				"4555",
				"4557",
				"4560",
				"4562",
				"4566",
				"4720",
				"4722",
				"4725",
				"4737",
				"4739",
				"4746",
				"4755",
				"4763",
				"4764",
				"4807",
				"4904",
				"4906",
				"4912",
				"4915",
				"4916",
				"4919",
				"4927",
				"4930",
				"4934",
				"4935",
				"4938",
				"4942",
				"4943",
				"4952",
				"4956",
				"4958",
				"4960",
				"4968",
				"4976",
				"4977",
				"4984",
				"4994",
				"4999",
				"5007",
				"5203",
				"5215",
				"5225",
				"5234",
				"5243",
				"5258",
				"5259",
				"5264",
				"5269",
				"5284",
				"5285",
				"5288",
				"5305",
				"5388",
				"5434",
				"5469",
				"5471",
				"5484",
				"5515",
				"5519",
				"5521",
				"5522",
				"5525",
				"5531",
				"5533",
				"5534",
				"5538",
				"5607",
				"5608",
				"5706",
				"5871",
				"5880",
				"5906",
				"5907",
				"6005",
				"6024",
				"6108",
				"6112",
				"6115",
				"6116",
				"6117",
				"6120",
				"6128",
				"6131",
				"6133",
				"6136",
				"6139",
				"6141",
				"6142",
				"6145",
				"6152",
				"6153",
				"6155",
				"6164",
				"6165",
				"6166",
				"6168",
				"6172",
				"6176",
				"6177",
				"6183",
				"6184",
				"6189",
				"6191",
				"6192",
				"6196",
				"6197",
				"6201",
				"6202",
				"6205",
				"6206",
				"6209",
				"6213",
				"6214",
				"6215",
				"6216",
				"6224",
				"6225",
				"6226",
				"6230",
				"6235",
				"6239",
				"6243",
				"6251",
				"6257",
				"6269",
				"6271",
				"6277",
				"6278",
				"6281",
				"6282",
				"6283",
				"6285",
				"6289",
				"6405",
				"6409",
				"6412",
				"6414",
				"6415",
				"6422",
				"6431",
				"6442",
				"6443",
				"6449",
				"6451",
				"6452",
				"6456",
				"6464",
				"6477",
				"6504",
				"6505",
				"6525",
				"6531",
				"6533",
				"6541",
				"6552",
				"6573",
				"6579",
				"6581",
				"6582",
				"6591",
				"6605",
				"6625",
				"8011",
				"8016",
				"8021",
				"8033",
				"8039",
				"8046",
				"8070",
				"8072",
				"8081",
				"8101",
				"8103",
				"8105",
				"8110",
				"8112",
				"8114",
				"8131",
				"8150",
				"8163",
				"8201",
				"8210",
				"8213",
				"8215",
				"8222",
				"8249",
				"8261",
				"8271",
				"8341",
				"8374",
				"8404",
				"8411",
				"8422",
				"8427",
				"8429",
				"8442",
				"8443",
				"8454",
				"8463",
				"8464",
				"8466",
				"8467",
				"8473",
				"8478",
				"8480",
				"8481",
				"8488",
				"8497",
				"8499",
				"8926",
				"8940",
				"8996",
				"9103",
				"910322",
				"910482",
				"9105",
				"910708",
				"910861",
				"9110",
				"911608",
				"911616",
				"911619",
				"911622",
				"911868",
				"912000",
				"912398",
				"9136",
				"9157",
				"9188",
				"9802",
				"9902",
				"9904",
				"9905",
				"9906",
				"9907",
				"9908",
				"9910",
				"9911",
				"9912",
				"9914",
				"9917",
				"9918",
				"9919",
				"9921",
				"9924",
				"9925",
				"9926",
				"9927",
				"9928",
				"9929",
				"9930",
				"9931",
				"9933",
				"9934",
				"9935",
				"9937",
				"9938",
				"9939",
				"9940",
				"9941",
				"9942",
				"9943",
				"9944",
				"9945",
				"9946",
				"9955",
				"9958"
				};
	/*	
		String[] stockNoArray = {"2330"};
	*/	
		
		boolean columnName = true;
		//define the urls visits
		String url0 = "http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=html";
		for(String stockNo : stockNoArray) {
			File f = new File("StDay_"+stockNo+".csv");
			if(f.exists() && !f.isDirectory()) { 
				System.out.println("StDay_"+stockNo+".csv" + " already exists. Do nothing!");
				continue;
			}
			ArrayList<String> resultData = new ArrayList<String>();
			for(String date : dateArray) {
				
				String result="";
				String url = url0 + "&date=" + date + "&stockNo=" + stockNo;
				System.out.println(url);
				//visit and receive content
				result = SendGet(url);
			//	result+=monthResult;
				ArrayList<String> resultDataRaw=Parser(result);
				int i=0;
				if(columnName==true) {
					for(String s : resultDataRaw) {
						resultData.add(s);
						i++;
					}
					columnName=false;
				}
				else {
					for(String s : resultDataRaw) {
						if(i>8) resultData.add(s);
						i++;
					}
				}
				try{TimeUnit.SECONDS.sleep(3);}catch(InterruptedException e){e.printStackTrace();}
			}

			
			ListTableFileStockDay(resultData, "StDay_"+stockNo+".csv");
		}
	}
	private static ArrayList<String> DeleteStockDayTitle(ArrayList<String> inputArray){
		int i=0;
		for(String s : inputArray) {
			
			if(s.equals("����")||s.equals("��漱��")||s.equals("��漱����")||s.equals("���")||
					s.equals("��擃")||s.equals("��雿")||s.equals("���")||s.equals("瞍脰�撌�")||
					s.equals("��漱蝑")&&i>8) {
				s="";
			}
			i++;
		}
		return inputArray;
	}
//	private static enum ParserTitle{KEEPTITLE, REMOVETITLE}

	public static void main(String[] args)
	{
	//	Trust();
		StockDay();


	}


}
