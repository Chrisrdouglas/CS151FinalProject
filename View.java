import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicArrowButton;

public class View {
	//Variables--------------------------------------------------
	private Model m;
	private Controller c;
	private CalendarFrame cf;
	private TaskFrame tf;
	
	//Methods----------------------------------------------------
	public void addModel(Model m)
	{
		this.m = m;
	}
	
	public void addController(Controller c)
	{
		this.c = c;
	}
	
	public void addBoth(Model m, Controller c)
	{
		this.m = m;
		this.c = c;
	}
	
	public void drawCF()
	{
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		//panel that holds navigation/days of weeek
		JPanel monthDisplay = new JPanel();
		monthDisplay.setLayout(new GridLayout(2,1));
		
		//creates the month/year navigation 
		JPanel navigation = new JPanel();
		navigation.setLayout(new GridLayout(1,4));
		
		//arrow buttons
		BasicArrowButton leftArrow = new BasicArrowButton(BasicArrowButton.WEST);
		leftArrow.addActionListener(event -> {
			c.subMonth();
		});
		BasicArrowButton rightArrow = new BasicArrowButton(BasicArrowButton.EAST);
		rightArrow.addActionListener(event -> {
			c.addMonth();
		});
		
		//month and years
		JLabel month = new JLabel(c.selectedMonthToString());		//**STILL NEEDS TO CHANGED IN CONTROLLER
		JPanel yearPanel = new JPanel();
		yearPanel.setLayout(new FlowLayout());
		JTextField year = new JTextField(String.valueOf(m.getSelectedYear()));			//**maybe fixed
		year.setEnabled(false);
		yearPanel.add(year);
		BasicArrowButton upArrow = new BasicArrowButton(BasicArrowButton.NORTH);
		upArrow.addActionListener(event ->{
			c.addYear();
		});
		
		BasicArrowButton downArrow = new BasicArrowButton(BasicArrowButton.SOUTH);
		downArrow.addActionListener(event ->{
			c.subYear();
		});
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2,1));
		buttons.add(upArrow);
		buttons.add(downArrow);
		yearPanel.add(buttons);
		
	
		//add stuff to navigation panel
		navigation.add(leftArrow);
		navigation.add(month);
		navigation.add(yearPanel);
		navigation.add(rightArrow);
		
		//days of week panel
		JPanel week = new JPanel();
		week.setLayout(new GridLayout(1,7));
		week.add(new JLabel("Sun"));
		week.add(new JLabel("Mon"));
		week.add(new JLabel("Tue"));
		week.add(new JLabel("Wed"));
		week.add(new JLabel("Thu"));
		week.add(new JLabel("Fri"));
		week.add(new JLabel("Sat"));
		
		//add stuff to monthDisplay
		monthDisplay.add(navigation);
		monthDisplay.add(week);
		
		//display today's date
		String todayDate = "Today: " + c.formatToday();
		JLabel today = new JLabel(todayDate);
		
		//add stuff to frame
		frame.add(monthDisplay, BorderLayout.NORTH);
		//frame.add(dates, BorderLayout.CENTER);
		frame.add(today, BorderLayout.SOUTH);
		frame.setSize(375,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void drawDates() {
		GregorianCalendar selectedMonth = m.getSelectedMonth();
		while(selectedMonth.get(Calendar.DATE) != 1)
		{
			selectedMonth.add(Calendar.DATE, -1);
		}
		while(selectedMonth.get(Calendar.DAY_OF_WEEK) != 1)
		{
			selectedMonth.add(Calendar.DATE, -1);
		}
		while(selectedMonth.get(Calendar.DATE) != 1)
		{
			//**ADD GRAYED OUT DATES HERE
			selectedMonth.add(Calendar.DATE, 1);
		}
		while(selectedMonth.get(Calendar.DATE) != selectedMonth.getActualMaximum(Calendar.DAY_OF_MONTH))
		{
			//**PRINT BLACK DATE
			selectedMonth.add(Calendar.DATE, 1);
		}
		while(selectedMonth.get(Calendar.DAY_OF_WEEK) != 7)
		{
			//**PRINT GRAY DATE
		}
		//**PRINT GRAY SATURDAY
	}
	

	public void drawTF()
	{
		
	}
	
	//Constructors-----------------------------------------------
	public View()
	{
		m = null;
		c = null;
		cf = new CalendarFrame();
		tf = new TaskFrame();
	}
}
