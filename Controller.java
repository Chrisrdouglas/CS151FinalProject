import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Controller {
	//Variables--------------------------------------------------
	private Model m;
	private View v;
	
	private static SimpleDateFormat MonthDYYYY = new SimpleDateFormat("MMMM d, YYYY");	//printing out the date in "[Name of Month] [Date], [YYYY]"
	private static SimpleDateFormat DMonthDYYYY = new SimpleDateFormat("EEEE MM/d/YYYY");
	private static SimpleDateFormat fileFormatter = new SimpleDateFormat("MMMM_YYYY");
	private static SimpleDateFormat Month = new SimpleDateFormat("MMMM");	//printing out the date in "[Name of Month] [Date], [YYYY]"
	private static Pattern importer = Pattern.compile("\\{Date:\\s(\\d{8})\\t\\tTask:\\s(.*)\\}");
	
	
	//Methods----------------------------------------------------
	public void addModel(Model model)
	{
		this.m = model;
	}
	public void addView(View view)
	{
		this.v = view;
	}
	public void addBoth(Model model, View view)
	{
		this.m = model;
		this.v = view;
	}
	
	public void updateTF()
	{
		v.drawTF();
	}
	
	public void changeSelectedDay(String s)
	{
		GregorianCalendar selectedMonth = m.getSelectedMonth();
		selectedMonth.set(selectedMonth.get(Calendar.YEAR), selectedMonth.get(Calendar.MONTH), Integer.parseInt(s));
		m.setSelectedDay(selectedMonth);
																							//System.out.println(MonthDYYYY.format(new Date(selectedMonth.get(Calendar.YEAR)-1900, selectedMonth.get(Calendar.MONTH), selectedMonth.get(Calendar.DATE))));
		v.drawTF();
	}
	
	public void addMonth()
	{
		GregorianCalendar g = this.m.getSelectedMonth();
		g.add(Calendar.MONTH, 1);
		this.m.setSelectedMonth(g);
		this.v.drawCF();
	}
	
	public void subMonth()
	{
		GregorianCalendar g = this.m.getSelectedMonth();
		g.add(Calendar.MONTH, -1);
		this.m.setSelectedMonth(g);
		this.v.drawCF();
	}
	
	
	public void addYear()
	{
		GregorianCalendar g = this.m.getSelectedMonth();
		g.add(Calendar.YEAR, 1);
		this.m.setSelectedMonth(g);
		this.v.drawCF();
	}
	
	public void subYear()
	{
		GregorianCalendar g = this.m.getSelectedMonth();
		g.add(Calendar.YEAR, -1);
		this.m.setSelectedMonth(g);
		this.v.drawCF();
	}
	
	public String selectedMonthToString()
	{
		return Month.format(new Date(0,this.m.getSelectedMonth().get(Calendar.MONTH)+1,0));
	}
	
	public String formatToday()
	{
		GregorianCalendar today = m.getToday();
		return MonthDYYYY.format(new Date(today.get(Calendar.YEAR)-1900, today.get(Calendar.MONTH), today.get(Calendar.DATE)));
	}
	
	public String formatTaskDate() //used when updating the TaskView Frame
	{
		GregorianCalendar selectedDay = m.getSelectedDay();
		return DMonthDYYYY.format(new Date(selectedDay.get(Calendar.YEAR)-1900, selectedDay.get(Calendar.MONTH), selectedDay.get(Calendar.DATE)));
	}
	
	public void export()
	{
		GregorianCalendar selectedDay = m.getSelectedDay();
		String fileName = fileFormatter.format(new Date(selectedDay.get(Calendar.YEAR)-1900, selectedDay.get(Calendar.MONTH), selectedDay.get(Calendar.DATE)));
		Path p = Paths.get(fileName+".txt");
		ArrayList<String> s = m.exportList();
			
		try {
			Files.write(p, s, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void importer(String s)
	{
		if(this.m == null)
		{
			return;
		}
		try {
			Scanner reader = new Scanner(new File(s));
			while(reader.hasNext())
			{
				String toConvert = reader.nextLine();
				Matcher m = importer.matcher(toConvert);
				if(m.matches())
				{
					this.m.addTask(new Dates(m.group(2), Integer.parseInt(m.group(1))));
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return;
		}
	}
	
	
	//Constructors-----------------------------------------------
	public Controller()
	{
		m = null;
		v = null;
	}
}
