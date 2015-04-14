package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.*;
public class Processor {

	public static void main(String args[])
	{
		// Welcome Screen
		// Read Data
		Processor pro=new Processor();
		String jobString=pro.readJobData();
		System.out.println(jobString);
		try {
			JSONObject jobdata=new JSONObject(jobString);
			System.out.println("#### Object Created###");
			System.out.println(jobdata.get("Machine"));
			String s=jobdata.get("Machine").toString();
			showMsg((new JSONObject(s)).get("count").toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Create Java Structure from Data Source
		//Allocate Job
		
		
	}
	
	public String readJobData()
	{
		showMsg("Please Enter the path for Job and Machine Info in JSON as Shown above");
		
		Path path =FileSystems.getDefault().getPath("data.json");
		Charset charset = Charset.forName("US-ASCII");
		StringBuffer sb=new StringBuffer() ;
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        sb.append(line.trim());
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}		
		
		return sb.toString();
	}
	
	public static String getUserInput(String s)
	{
			Scanner sc= new Scanner(System.in);
			return sc.nextLine();
	}
	
	public static void showMsg(String s)
	{
		System.out.println(s);
	}
	
}
