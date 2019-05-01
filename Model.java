import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Comparator;



public class Model {
	//Variables--------------------------------------------------
	private ArrayList<Dates> days;			//ArrayList of (tasks + associated dates)
	private Dates selectedTask;				//the task that is selected in the task frame
	private Controller controller;
	private GregorianCalendar selectedMonth;//used for displaying the calendar
	private GregorianCalendar selectedDay;	//used for keeping track of the highlighted day
	private GregorianCalendar today;		//today's date
	
	//Methods----------------------------------------------------
	public void addController(Controller c)
	{
		controller = c;
	}
	
	public int getSelectedYear()
	{
		return selectedMonth.get(Calendar.YEAR);
	}
	
	public GregorianCalendar getSelectedMonth()
	{
		return (GregorianCalendar) selectedMonth.clone();
	}
	
	public void setSelectedMonth(GregorianCalendar g)
	{
		selectedMonth = g;
	}
	
	public GregorianCalendar getToday()
	{
		return (GregorianCalendar) today.clone();
	}
	
	public void setSelectedDay(GregorianCalendar g)
	{
		selectedDay = g; 
	}
	
	public GregorianCalendar getSelectedDay()
	{
		return (GregorianCalendar) selectedDay.clone();
	}
	
	public ArrayList<Dates> getTasks(){
		ArrayList<Dates> taskList = new ArrayList<Dates>();
		Dates task = new Dates("", selectedDay.get(Calendar.YEAR), selectedDay.get(Calendar.MONTH), selectedDay.get(Calendar.DATE));
		for(Dates d : days) {
			if(task.compareTo(d) == 0)
				taskList.add(d);
		}
		return taskList;
	}

	
	public void addTask(String name)
	{
		//Dates d = new Dates(name, year, month, day);
		Dates d = new Dates(name, selectedDay.get(Calendar.YEAR), (selectedDay.get(Calendar.MONTH)+1), selectedDay.get(Calendar.DAY_OF_MONTH));
		days.add(d);
		Comparator<Dates> byDate = (Dates o1, Dates o2)->{return o1.compareTo(o2);};
		days.sort(byDate);
		
		//THIS IS WHERE WE'RE GOING TO WANT TO ADD THE CODE TO NOTIFY THE CONTROLLER TO UPDATE THE TASKVIEW FRAME
	}	
	
	public void addTask(String name, int year, int month, int day)
	{
		Dates d = new Dates(name, year, month, day);
		//Dates today's date = new Dates("", selectedDay.get(Calendar.YEAR), (selectedDay.get(Calendar.MONTH)+1), selectedDay.get(Calendar.DAY_OF_MONTH));
		this.addTask(d);
	}
	
	public void addTask(Dates d)
	{
		//Dates d = new Dates(name, year, month, day);
		//Dates today's date = new Dates("", selectedDay.get(Calendar.YEAR), (selectedDay.get(Calendar.MONTH)+1), selectedDay.get(Calendar.DAY_OF_MONTH));
		days.add(d);
		Comparator<Dates> byDate = (Dates o1, Dates o2)->{return o1.compareTo(o2);};
		days.sort(byDate);
		
		//**THIS IS WHERE WE'RE GOING TO WANT TO ADD THE CODE TO NOTIFY THE CONTROLLER TO UPDATE THE TASKVIEW FRAME
	}
	
	public void removeTask()
	{
		//Dates d = new Dates(name, year, month, day);
		//search the ArrayList ------ we could make this faster
		if(selectedTask == null)
		{
			return;
		}
		for(int i = 0; i < days.size();i++)
		{
			if(days.get(i).equals(selectedTask))
			{
				days.remove(i);
				selectedTask = null;
				break;
			}
		}
		
		//**THIS IS WHERE WE'RE GOING TO WANT TO ADD THE CODE TO NOTIFY THE CONTROLLER TO UPDATE THE TASKVIEW FRAME
	}
	
	public void removeTask(String name, int year, int month, int day)
	{
		Dates d = new Dates(name, year, month, day);
		//search the arraylist ------ we could make this faster
		for(int i = 0; i < days.size();i++)
		{
			if(days.get(i).equals(d))
			{
				days.remove(i);
				break;
			}
		}
		
		//**THIS IS WHERE WE'RE GOING TO WANT TO ADD THE CODE TO NOTIFY THE CONTROLLER TO UPDATE THE TASKVIEW FRAME
	}
	
	public void editTask(String newName, String oldName, int year, int month, int day)
	{
		Dates d = new Dates(oldName, year, month, day);
		for(int i = 0; i < days.size(); i++)
		{
			if(days.get(i).equals(d))
			{
				days.get(i).setTask(newName);
				break;
			}
		}
		
		//**THIS IS WHERE WE'RE GOING TO WANT TO ADD THE CODE TO NOTIFY THE CONTROLLER TO UPDATE THE TASKVIEW FRAME
	}
	
	public void editTask(String newName)
	{
		selectedTask.setTask(newName);
		
		//**THIS IS WHERE WE'RE GOING TO WANT TO ADD THE CODE TO NOTIFY THE CONTROLLER TO UPDATE THE TASKVIEW FRAME
	}
	
	public ArrayList<String> exportList()	//export the tasks in the selected day's month
	{
		//get the first and last day of the months.
		//String fileName = fileFormatter.format(new Date(selectedDay.get(Calendar.YEAR)-1900, selectedDay.get(Calendar.MONTH), selectedDay.get(Calendar.DATE)));
		//Path p = Paths.get(fileName+".txt");
		//get the first and last day of the months.
		Dates startDate = new Dates("", selectedDay.get(Calendar.YEAR), (selectedDay.get(Calendar.MONTH)+1), 1);
		Dates endDate = new Dates("", selectedDay.get(Calendar.YEAR), (selectedDay.get(Calendar.MONTH)+1), selectedDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		ArrayList<String> s = new ArrayList<>();
		//speed could be improved by keeping track of the first task in a month and the last when a new date is selected in the calendar frame
		for(int i = 0; i < days.size(); i++)
		{
			Dates read = days.get(i);
			if(read.compareTo(startDate)>=0 && read.compareTo(endDate)<=0)
			{
				s.add(read.toString());
			}
		}
		return s;
	}
	
	//Constructors-----------------------------------------------
	public Model()
	{
		days = new ArrayList<>();
		controller = null;
		selectedMonth = new GregorianCalendar();
		selectedDay = new GregorianCalendar();
		today = new GregorianCalendar();
	}
}
