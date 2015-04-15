package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Processor {

	public static void main(String args[])
	{
		// Welcome Screen
		// Read Data
		Processor pro=new Processor();
		String jobString=pro.readJobData();
		System.out.println(jobString);
		JSONArray jobArray=(JSONArray) convertString2JSON(jobString,"Jobs");
		ArrayList<Job> jobList=pro.createJobListFromJSONObject(jobArray);
		
		for(Job jobs : jobList)
		{
			System.out.println(jobs);
		}
		//Create Java Structure from Data Source
		//Allocate Job
		
		showMsg("sorting jobs based on finish time");
		Collections.sort(jobList, new JobFinishComparator());
		
		
		for(Job jobs : jobList)
		{
			System.out.println(jobs);
		}
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
	
	public static Object convertString2JSON(String jString,String element)
	{
		JSONObject jobdata=null;
		try {
			jobdata=new JSONObject(jString);
			showMsg("#### Object Created###");
			//System.out.println(jobdata.get(element));
			Object jObj=jobdata.get(element);
			
			if(jObj instanceof org.json.JSONArray)
				return (org.json.JSONArray)jObj;
			else if(jObj instanceof org.json.JSONObject)
					return (org.json.JSONObject)jObj;
			
			/*String s=jobdata.get("Jobs").toString();
			showMsg(jobdata.get("Jobs").getClass().toString());*/
			
			
			//showMsg((new JSONObject(s)).get("count").toString());
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return jobdata;
	}
	public ArrayList<Job> createJobListFromJSONObject(JSONArray jobList)
	{
			int i=0;
			ArrayList<Job> jobArrayList=new ArrayList<Job>();
			try {
				
					
				while(i<jobList.length())
				{	
					
					int duration=Integer.parseInt(((JSONObject) jobList.get(i)).get("duration").toString());
					int start=Integer.parseInt(((JSONObject) jobList.get(i)).get("start").toString());
					int finish=Integer.parseInt(((JSONObject) jobList.get(i)).get("finish").toString());
					jobArrayList.add(new Job(i,start,finish,duration));
					i++;
					
				}
			} catch (NumberFormatException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			return jobArrayList;
	}
	public ArrayList<Machine> CreateMachineArray(int m)
	{
		ArrayList<Machine> machineList= new ArrayList<Machine>();
			for(int i=0;i<m;i++)
			{
				ArrayList<Job> machienList;
				machineList.add(new Machine(i, 0, 0, null));
			}
				
		return machineList;
	}
	
}
