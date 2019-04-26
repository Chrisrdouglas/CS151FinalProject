
public class Dates implements Comparable{ 
	//Variables--------------------------------------------------------
	private String task; 
	private int date;
	
	
	//Methods----------------------------------------------------------
	public int getDate()
	{
		return date;
	}
	
	
	public String getTask()
	{
		return new String(task);
	}
	
	public void setTask(String s)
	{
		this.task = s;
	}
	
	public String toString()
	{
		//return (new StringBuffer("{Task: ").append(task).append("\tDate: ").append(Integer.toString(date)).append("}")).toString();
		
		//Changed for readability in the export file
		return (new StringBuffer("{Date: ").append(Integer.toString(date)).append("\t\tTask: ").append(task).append("}")).toString();
	}
	
	
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		
		if(o instanceof Dates)
		{
			Dates d = (Dates) o;
			//compare
			if(this.date == d.date)
			{
				return true;
			}
		}
		return false;
	}
	
	
	public Dates clone()
	{
		return new Dates(new String(task), date);
	}
	
	public int compareTo(Object o)
	{
		if(this == o)
		{
			return 0;
		}
		
		if(o instanceof Dates)
		{
			Dates d = (Dates) o;
			return this.date - d.date;
			//return d.date - this.date;
		}
		else
		{
			throw new ClassCastException();
		}
	}

	//Constructors--------------------------------------------------------
	public Dates()
	{
		task = "";
		date = 0;
	}
	
	public Dates(String t, int d)
	{
		task = t;
		date = d;
	}
	
	public Dates(String t, int y, int m, int d)
	{
		task = t;
		date = d + m*100 + y*10000;
	}
}
