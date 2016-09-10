package common;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.json.simple.JSONObject;

import common.ResultProto.CourseMarks;
import common.ResultProto.Result;
import common.ResultProto.Student;

//import JSON;
//import ResultProto.*;
//import ResultProto.Result;
//import ResultProto.Student;

public class SerializeProtobuf {
	public static Map <Integer, List<Object>> data = new HashMap<Integer, List<Object>>();
	public static List<Integer> rollNoList=new ArrayList<Integer>();
	public static void main(String args[])
	{
		String fileName = "src/common/input.txt";
		try{
			BufferedReader inputHandle = new BufferedReader(new FileReader(fileName));
			String nextRow=inputHandle.readLine();
			
			while(nextRow!=null)
			{
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
						rollNoList.add(roll);
				
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
		
						temp.add(var1);
						temp.add(var2);
						tempCourse.put("CourseScore", var2);
						tempCourse.put("CourseName", var1);
				
						
					}
		
				}	
				data.put(roll, temp);
				
				
				nextRow=inputHandle.readLine();
			
			}
		
			}
			catch(IOException e)
			{
				e.getMessage();
				System.out.println("FNF");
			}
		

		Result.Builder studentRecord=Result.newBuilder();
		int k=0;
		while(k<data.size())
		{
			studentRecord.addStudent(parseStudent(data.get(rollNoList.get(k))));k++;
		}
		File fil=new File("result_protobuf");
		if(!fil.exists())
			try {
				fil.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			FileOutputStream output = new FileOutputStream(fil);
			studentRecord.build().writeTo(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


}
	static Student parseStudent(List<Object> list)
	{
		Student.Builder stud=Student.newBuilder();
		//stud.getMarksList()
		stud.setName((String) list.get(0));
		stud.setRollNum((int) list.get(1));
		int k=2;
		
		while(k<list.size())
		{
			
			CourseMarks.Builder course=CourseMarks.newBuilder();
			course.setName((String) list.get(k));k++;
			course.setScore((int) list.get(k));k++;
			stud.addMarks(course.build());
		}
		return stud.build();
	}
}