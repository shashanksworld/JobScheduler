package model;

import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * The Class Job.
 */
class Job  {
	
	/** The id. for Job */
	private int id;
	
	/** The offset.  end time for interval*/
	private int offset;
	
	/** The inset.  start time for interval*/
	private int inset;
	
	/** The duration. */
	private int duration;
	
	/**
	 * Instantiates a new job.
	 *
	 * @param id the jobId
	 * @param offset the end time for a job
	 * @param inset the start time for job
	 * @param duration of a job element [offset-inset]
	 */
	public Job(int id,  int inset,int offset, int duration) {
		super();
		this.id = id;
		this.offset = offset;
		this.inset = inset;
		this.duration = duration;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * Over loading toString for Job Description
	 */
	
	public String toString()
	{
		return "[id:"+this.id+",St:"+this.inset+",Ft:"+this.offset+"]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getInset() {
		return inset;
	}

	public void setInset(int inset) {
		this.inset = inset;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

		
}

class JobFinishComparator implements Comparator<Job>
{
	@Override
	public 	int compare(Job j1, Job  j2) {
	        return j1.getOffset() - j2.getOffset();
	    }
}
class JobStartComparator implements Comparator<Job>
{
	@Override
	public 	int compare(Job j1, Job  j2) {
	        return j1.getInset() - j2.getInset();
	    }
}

