package model;

import java.util.ArrayList;
import java.util.Comparator;
public class Machine implements Comparable{

	private int id;
	private int totalJobs;
	private int offset;		
	private ArrayList<Job> jobList;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotalJobs() {
		return totalJobs;
	}

	public void setTotalJobs(int totalJobs) {
		this.totalJobs = totalJobs;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public ArrayList<Job> getJobList() {
		return jobList;
	}

	public void setJobList(ArrayList<Job> jobList) {
		this.jobList = jobList;
	}

	public Machine(int id, int totalJobs, int offset, ArrayList<Job> jobList) {
		super();
		this.id = id;
		this.totalJobs = totalJobs;
		this.offset = offset;
		this.jobList = jobList;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(((Machine)o).getOffset()>this.offset)
		return -1;
		else if (((Machine)o).getOffset()<this.offset)
			return 1; 
		else return 
				0;
			
	}
	
	public String toString()
	{
		return "id:"+this.getId()+";offset:"+this.offset+";Total No of jobs :"+(this.getJobList()!=null?this.getJobList().size():null);
		//return "id:"+this.getId()+";offset:"+this.offset+";Total No of jobs :";
	}
}


