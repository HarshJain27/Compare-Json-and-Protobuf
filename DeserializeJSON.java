package common;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DeserializeJSON {
	public static void main(String args[])
	{
		File fil=new File("output_json.txt");
		if(!fil.exists())
			try {
				fil.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try(PrintWriter writer=new PrintWriter(fil))
		{
			
			Scanner sc;
			try {
				//System.out.println("AME");
				File file=new File("result.json");
				sc = new Scanner(file);
				StringBuilder tempString=new StringBuilder();
				//System.out.println(sc.hasNextLine());
				while(sc.hasNextLine())
				{
					tempString.append(sc.nextLine());
				}
				//System.out.println(tempString);
				JSONParser parseObject=new JSONParser();
				JSONArray deserializeObj=(JSONArray)parseObject.parse(tempString.toString());
				
				for(int i=0;i<deserializeObj.size();i++)
				{
					JSONObject object=(JSONObject) deserializeObj.get(i);
					String name=(String) object.get("Name");
					JSONArray marks=(JSONArray) object.get("CourseMarks");
					long rollNo=(long) object.get("RollNo");
					writer.print(name+","+rollNo);
					int k=0;
					while(k<marks.size())
					{
						JSONObject courseObject=(JSONObject) marks.get(k);
						long score=(long) courseObject.get("CourseScore");
						String cname=(String) courseObject.get("CourseName");
						writer.print(":"+cname+","+score);
						k++;
					}
					writer.print("\n");
					//System.out.println(name+""+rollNo+""+""+marks);
					//System.out.println("ok");
				}
				
				sc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writer.close();
			
		}
		catch(FileNotFoundException e)
		{
			e.toString();
		}

	}
	
}
