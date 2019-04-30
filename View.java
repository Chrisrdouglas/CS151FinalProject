import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.plaf.basic.BasicArrowButton;

public class View{
	//Variables--------------------------------------------------
	private Model m;
	private Controller c;
	private JFrame cFrame;
	private JFrame vFrame;
	private ButtonGroup f;
	
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
		
		//panel that holds navigation/days of week
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
		JLabel month = new JLabel(c.selectedMonthToString());
		JPanel yearPanel = new JPanel();
		yearPanel.setLayout(new FlowLayout());
		JTextField year = new JTextField(String.valueOf(m.getSelectedYear()));
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
		ButtonGroup buttonGroup = this.drawDates();
		dates.setLayout(new GridLayout(buttonGroup.getButtonCount()/7,7));
		for(Enumeration<AbstractButton> e = buttonGroup.getElements(); e.hasMoreElements();)
		{
			dates.add(e.nextElement());
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
	
	
	public ButtonGroup drawDates() {
		ButtonGroup i = new ButtonGroup();
		JToggleButton b = null;
		GregorianCalendar selectedMonth = m.getSelectedMonth();
		while(selectedMonth.get(Calendar.DATE) != 1) //bring everything back to 1
		{
			selectedMonth.add(Calendar.DATE, -1);
		}
		while(selectedMonth.get(Calendar.DAY_OF_WEEK) != 1) //bring everything back to Saturday
		{
			selectedMonth.add(Calendar.DATE, -1);
		}
		while(selectedMonth.get(Calendar.DATE) != 1) //add previous months days
		{
			//**ADD GRAYED OUT DATES HERE
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			b.addActionListener(event -> 
			{
				c.subMonth();
			});
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}
		//while(selectedMonth.get(Calendar.DATE) != selectedMonth.getActualMaximum(Calendar.DAY_OF_MONTH)) //add up until end of month
		while(selectedMonth.get(Calendar.DATE) != selectedMonth.getActualMaximum(Calendar.DAY_OF_MONTH)) //add up until end of month
		{
			//**PRINT BLACK DATE
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			b.addActionListener(event -> 
			{
				c.changeSelectedDay(((AbstractButton) event.getSource()).getText());
			});
		//okay so you're not going to understand what i did here when you look at this x days from now...
		//basically we got the event source (the JToggleButton), casted it,
		//then got it's text, then told the controller to change the selected day
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}
		
		if(selectedMonth.get(Calendar.DATE) == 31)
		{
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			b.addActionListener(event -> 
			{
				c.changeSelectedDay(((AbstractButton) event.getSource()).getText());
			});
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}
		
		while(selectedMonth.get(Calendar.DAY_OF_WEEK) != 7) //add until saturday gets added
		{
			//**PRINT GRAY DATE
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			b.addActionListener(event -> 
			{
				c.addMonth();
			});
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}
		//**PRINT GRAY SATURDAY
		b = this.createDate(selectedMonth.get(Calendar.DATE));
		b.addActionListener(event -> 
		{
			c.addMonth();
		});
		i.add(b);
		this.f = i;
		return i;
	}
	
	public JToggleButton createDate(int date){
		String num = String.valueOf(date);
		JToggleButton day = new JToggleButton(num);
		day.setFocusPainted(false);
		//day.setContentAreaFilled(false);
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
	
	//Constructors-----------------------------------------------
	public View()
	{
		m = null;
		c = null;
		cFrame = new JFrame();
		vFrame = new JFrame();
	}


}
