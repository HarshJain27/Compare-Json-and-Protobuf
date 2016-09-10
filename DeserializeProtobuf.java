package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import common.ResultProto.CourseMarks;
import common.ResultProto.Result;
import common.ResultProto.Student;

public class DeserializeProtobuf {
	public static void main(String args[])
	{
		try {
			File fil=new File("result_protobuf");
			Result result=Result.parseFrom(new FileInputStream(fil));
			
			File file=new File("output_protobuf.txt");
			if(!file.exists())
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//FileOutputStream output = new FileOutputStream(file);
			PrintWriter writer=new PrintWriter(file);
			
			for(Student stud:result.getStudentList())
			{
				writer.print(stud.getName()+","+stud.getRollNum());
				for(CourseMarks course: stud.getMarksList())
				{
					writer.print(":"+course.getName()+","+course.getScore());
				}
				writer.print("\n");
			}
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
