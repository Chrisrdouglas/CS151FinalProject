import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicArrowButton;

public class View implements ActionListener {
	//Variables--------------------------------------------------
	private Model m;
	private Controller c;
	private JFrame cFrame;
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
		
		cFrame.getContentPane().removeAll();
		
		cFrame.setLayout(new BorderLayout());
		
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
		week.add(new JLabel("    Sun"));
		week.add(new JLabel("    Mon"));
		week.add(new JLabel("    Tue"));
		week.add(new JLabel("    Wed"));
		week.add(new JLabel("    Thu"));
		week.add(new JLabel("    Fri"));
		week.add(new JLabel("    Sat"));
		
		//add stuff to monthDisplay
		monthDisplay.add(navigation);
		monthDisplay.add(week);
		
		//add the numbers
		JPanel dates = new JPanel();
		//dates.setLayout(new GridLayout(5,7));
		ArrayList<JButton> dat = this.drawDates();
		dates.setLayout(new GridLayout(dat.size()/7,7));
		for(JButton i : dat)
		{
			dates.add(i);
		}
		
		
		
		//display today's date
		String todayDate = "Today: " + c.formatToday();
		JLabel today = new JLabel(todayDate);
		
		//add stuff to frame
		cFrame.add(monthDisplay, BorderLayout.NORTH);
		cFrame.add(dates, BorderLayout.CENTER);
		cFrame.add(today, BorderLayout.SOUTH);
		cFrame.repaint();
		cFrame.setSize(375,300);
		cFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cFrame.setVisible(true);
	}
	
	public ArrayList<JButton> drawDates() {
		ArrayList<JButton> i = new ArrayList<>();
		JButton b = null;
		GregorianCalendar selectedMonth = m.getSelectedMonth();
		while(selectedMonth.get(Calendar.DATE) != 1) //bring everything back to 1
		{
			selectedMonth.add(Calendar.DATE, -1);
		}
		while(selectedMonth.get(Calendar.DAY_OF_WEEK) != 1) //bring everything back to saturday
		{
			selectedMonth.add(Calendar.DATE, -1);
		}
		while(selectedMonth.get(Calendar.DATE) != 1) //add previous months days
		{
			//**ADD GRAYED OUT DATES HERE
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}
		//while(selectedMonth.get(Calendar.DATE) != selectedMonth.getActualMaximum(Calendar.DAY_OF_MONTH)) //add up until end of month
		while(selectedMonth.get(Calendar.DATE) != selectedMonth.getActualMaximum(Calendar.DAY_OF_MONTH)) //add up until end of month
		{
			//**PRINT BLACK DATE
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}
		
		if(selectedMonth.get(Calendar.DATE) == 31)
		{
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}
		
		while(selectedMonth.get(Calendar.DAY_OF_WEEK) != 7) //add until saturday gets added
		{
			//**PRINT GRAY DATE
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}
		//**PRINT GRAY SATURDAY
		b = this.createDate(selectedMonth.get(Calendar.DATE));
		i.add(b);
		
		return i;
	}
	
	public JButton createDate(int date){
		String num = String.valueOf(date);
		JButton day = new JButton(num);
		day.setFocusPainted(false);
		day.setContentAreaFilled(false);
		//day.setBorderPainted(false);
		return day;
		}
	
	public static void createDate(int date, JComponent c){
		String num = String.valueOf(date);
		JButton day = new JButton(num);
		//day.setFocusPainted(false);
		day.setContentAreaFilled(false);
		//day.setBorderPainted(false);
		c.add(day);
		}	
	

	public void drawTF()
	{
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//Constructors-----------------------------------------------
	public View()
	{
		m = null;
		c = null;
		cFrame = new JFrame();
		tf = new TaskFrame();
	}


}
