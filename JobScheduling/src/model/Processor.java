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

	static ArrayList<Job> jList=null;									//ArrayList of Job Objects
	static ArrayList<Machine> mList=new ArrayList<Machine>();			//ArrayList of Machine Objects
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	
	public static void main(String args[])
	{
		// Welcome Screen
		// Read Data
		Processor pro=new Processor();
		
		
		//Get User Input {Machine:{count:},jobs[{job1:{}},{job2:{}}}
		String jobString=pro.readJobData();
		
		
		//Create Job Data
		try {
			JSONArray jobArray=(JSONArray) convertString2JSON(jobString,"Jobs");
			JSONObject machineArray=(JSONObject) convertString2JSON(jobString,"Machine");
			mList=pro.CreateMachineArray(Integer.parseInt(machineArray.get("count").toString()));
			jList=pro.createJobListFromJSONObject(jobArray);
			
			//Validate Job and Machine Data
			if(mList.size()>=jList.size())
			{
				showMsg("  Invalid Job Scheduling Condition... # of Machine should be less than # of Jobs to be allocated");
				showMsg("System is going to exit");
				System.exit(0);
			}else
			{	//Sort Jobs		
				showMsg("\n");
				showMsg("###################User Input###################");
				showMsg("Total # of Jobs:"+jList.size());
				showMsg("Total # of Machines:"+mList.size());
				showMsg("\n");
				showMsg("###############Sorting Jobs by Offset###############");
				Collections.sort(jList, new JobFinishComparator());
				showMsg("\n");
				showMsg("###################Allocating Jobs###################");
				ProcessJobs();
				showMsg("###################Allcation Complete###################");
				showMsg("\n");
				showMsg("###################Machine Status###################");
				printMachineStatus();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	/*
	 * Print Jobs Allocated on Machines
	 * */
	public static void printMachineStatus()
	{
		
		for(Machine m: mList)
		{
			System.out.print("Machine:"+m.getId()+" MaxJob:"+m.getJobList().size() +" : ");
			for(Job j :m.getJobList())
			{

				System.out.print(" "+j);
			}
			
			System.out.println();
		}
	}
	
	/*
	 * Iterates over available machines and allocates the compatible jobs
	 *  to the next available machine
	 * */
	public static void ProcessJobs()
	{	ArrayList<Job> tList=null;
		int i=0;
		while(jList.size()>0)
		{
			
			Machine m=Collections.min(mList);							//find the machine with minimum value of finish time for last job added.
			
			if(m.getJobList()==null)
			{
				int mIndex=m.getId();
				tList=new ArrayList<Job>();
				
				Job j=jList.get(0);													//current Job
				tList.add(j);														//add job to active job list
				mList.get(mIndex).setOffset(j.getOffset());
				mList.get(mIndex).setJobList(tList);
				jList.remove(0);
				
			}
			else if(m.getOffset()<=jList.get(0).getInset())
			{	
				int mIndex=m.getId();
				tList=m.getJobList();
				//current Job
				Job j=jList.get(0);
				tList.add(j);
				mList.get(mIndex).setOffset(j.getOffset());
				mList.get(mIndex).setJobList(tList);
				jList.remove(0);
				
			}else
			{
				showMsg("Incompatible job: "+jList.get(0)+ "  skipping to next job");
				jList.remove(0);		
			}
			
		}
	}

	/*	#Ask you to enter path of the data file  
	 *  #Reads JOb data provided by user in json format
	 *  
	 *   * */
	public String readJobData()
	{	
		showMsg("Please Enter the path for Job and Machine Info in JSON format as {Machine:{count:..},Job:[{job1}, {job2}.....]}");
		Path path =FileSystems.getDefault().getPath(getUserInput("Enter Path for data file"));
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
	
	/*
	 * Custom function for getting user input through System.in
	 * */
	public static String getUserInput(String s)
	{
			Scanner sc= new Scanner(System.in);
			return sc.nextLine();
	}
	
	/*
	 * Static Logger
	 * */
	public static void showMsg(String s)
	{
		System.out.println(s);
	}
	
	/*
	 * Extract JsonArray or JsonObject based on type input 
	 * */
	public static Object convertString2JSON(String jString,String element)
	{
		JSONObject jobdata=null;
		try {
			jobdata=new JSONObject(jString);
			showMsg("#### Object Created### "+ element);
			
			Object jObj=jobdata.get(element);
			
			if(jObj instanceof org.json.JSONArray)
				return (org.json.JSONArray)jObj;
			else if(jObj instanceof org.json.JSONObject)
				return (org.json.JSONObject)jObj;
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return jobdata;
	}
	/*
	 * Create ArrayList<Job> from list of jobs obtained from JSON object
	 * and set default values of inset and offset to 0
	 * */
	public ArrayList<Job> createJobListFromJSONObject(JSONArray jobList)
	{
			int i=0;
			ArrayList<Job> jobArrayList=new ArrayList<Job>();
			try {
					
				while(i<jobList.length())
				{	
					int start=Integer.parseInt(((JSONObject) jobList.get(i)).get("start").toString());
					int finish=Integer.parseInt(((JSONObject) jobList.get(i)).get("finish").toString());
					int id=Integer.parseInt(((JSONObject) jobList.get(i)).get("id").toString());
					jobArrayList.add(new Job(id,start,finish,finish-start));
					i++;
					
				}
			} catch (NumberFormatException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			return jobArrayList;
	}
	//Set machines to unallocated state with offset to 0 and jobList as null
	public ArrayList<Machine> CreateMachineArray(int m)
	{
		ArrayList<Machine> machineList= new ArrayList<Machine>();
			for(int i=0;i<m;i++)
			{	
				machineList.add(new Machine(i, 0, 0, null));
			}
				
		return machineList;
	}
	
	
}
