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



public class Controller {
	//Variables--------------------------------------------------
	private Model m;
	private View v;
	
	private static SimpleDateFormat MonthDYYYY = new SimpleDateFormat("MMMM d, YYYY");	//printing out the date in "[Name of Month] [Date], [YYYY]"
	private static SimpleDateFormat DMonthDYYYY = new SimpleDateFormat("EEEE MM/d/YYYY");
	private static SimpleDateFormat fileFormatter = new SimpleDateFormat("MMMM_YYYY");
	private static SimpleDateFormat Month = new SimpleDateFormat("MMMM");	//printing out the date in "[Name of Month] [Date], [YYYY]"
	
	
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
	
	
	//Constructors-----------------------------------------------
	public Controller()
	{
		m = null;
		v = null;
	}
}
