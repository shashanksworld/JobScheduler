package model;

import java.util.ArrayList;
import model.Job;
public class Machine {

	private int id;
	private int totalJobs;
	private int offset;		
	private ArrayList<Job> jobList;
	public Machine(int id, int totalJobs, int offset, ArrayList<Job> jobList) {
		super();
		this.id = id;
		this.totalJobs = totalJobs;
		this.offset = offset;
		this.jobList = jobList;
	}
	
}
