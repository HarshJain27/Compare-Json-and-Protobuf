package common;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SerializeJSON {
	public static Map <Integer, List<Object>> data = new HashMap<Integer, List<Object>>();
	public static List<Integer> rollNoList=new ArrayList<Integer>();
	public static void main(String args[])
	{
		String fileName = "src/common/input.txt";
		JSONArray root =new JSONArray();
		try{
			BufferedReader inputHandle = new BufferedReader(new FileReader(fileName));
			String nextRow=inputHandle.readLine();
			//System.out.println(nextRow);
			while(nextRow!=null)
			{
				//System.out.println(nextRow);
				JSONObject tempObj =new JSONObject();
				JSONArray tempArr=new JSONArray();
				//Map<String,Integer> temp=new HashMap<String,Integer>();
				List<Object> temp=new ArrayList<Object>();
				String name="";
				int roll=0;
				int firstFlag1=0;
				StringTokenizer st1 = new StringTokenizer(nextRow,":");
				while(st1.hasMoreElements())
				{
					String token = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(token,",");
					int firstFlag2=0;
					if(firstFlag1==0)
					{
						while(st2.hasMoreElements())
						{
							if(firstFlag2==0){name=st2.nextToken();firstFlag2=1;}
							else roll=Integer.parseInt(st2.nextToken());
						}
						temp.add(name);
						temp.add(roll);
						//temp.put(name, roll);
						rollNoList.add(roll);
						tempObj.put("RollNo",roll);
						firstFlag1=1;
					}
					else
					{
						JSONObject tempCourse =new JSONObject();
						String var1="";int var2=0;
						while(st2.hasMoreElements())
						{
							if(firstFlag2==0){var1=st2.nextToken();firstFlag2=1;}
							else var2=Integer.parseInt(st2.nextToken());
						}
						//temp.put(var1, var2);
						temp.add(var1);
						temp.add(var2);
						tempCourse.put("CourseScore", var2);
						tempCourse.put("CourseName", var1);
						tempArr.add(tempCourse);
						
					}
					//System.out.println(temp);
				}	
				data.put(roll, temp);
				tempObj.put("CourseMarks", tempArr);
				tempObj.put("Name",name);
				root.add(tempObj);
				nextRow=inputHandle.readLine();
				//System.out.println(data.get());
				//System.out.println("not caming  out");
			}
			//System.out.println(data);
		inputHandle.close();	
		}
			catch(IOException e)
			{
				e.getMessage();
				System.out.println("FNF");
			}
		
/*==================================================================PARSING ENDS=============================================================*/		
		//System.out.println(root.toJSONString());
		/*=================================================================Serialization =============================================================*/					
		File file=new File("result.json");
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try(PrintWriter writer=new PrintWriter(file))
		{
			writer.print(root.toJSONString());
		}
		catch(FileNotFoundException e)
		{
			e.toString();
		}

	}
}
