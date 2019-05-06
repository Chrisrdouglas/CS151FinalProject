import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

public class View{
	//Variables--------------------------------------------------
	private Model m;
	private Controller c;
	private JFrame cFrame;
	private JFrame tFrame;
	private ArrayList<Dates> taskList;

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
		JLabel month = new JLabel(c.selectedMonthToString(), SwingConstants.CENTER);
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

	//generates the grid of dates
	//returns a ButtonGroup
	public ButtonGroup drawDates() {
		ButtonGroup i = new ButtonGroup();
		JToggleButton b = null;
		GregorianCalendar selectedMonth = m.getSelectedMonth();

		//bring everything back to 1
		while(selectedMonth.get(Calendar.DATE) != 1) 
		{
			selectedMonth.add(Calendar.DATE, -1);
		}

		//bring everything back to Saturday
		while(selectedMonth.get(Calendar.DAY_OF_WEEK) != 1) 
		{
			selectedMonth.add(Calendar.DATE, -1);
		}

		//add previous months days
		while(selectedMonth.get(Calendar.DATE) != 1) 
		{
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			b.setForeground(Color.gray);
			b.addActionListener(event -> 
			{
				c.subMonth();
			});
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}

		//add up until end of month
		while(selectedMonth.get(Calendar.DATE) != selectedMonth.getActualMaximum(Calendar.DAY_OF_MONTH)) 
		{
			//prints today's date in red
			if(selectedMonth.get(Calendar.DATE) == m.getToday().get(Calendar.DATE) 
					&& selectedMonth.get(Calendar.MONTH) == m.getToday().get(Calendar.MONTH)
					&& selectedMonth.get(Calendar.YEAR) == m.getToday().get(Calendar.YEAR)) {
				b = this.createDate(selectedMonth.get(Calendar.DATE));
				b.setForeground(Color.red);
				b.addActionListener(event -> 
				{
					c.changeSelectedDay(((AbstractButton) event.getSource()).getText());
				});
			} else {
				b = this.createDate(selectedMonth.get(Calendar.DATE));
				b.addActionListener(event -> 
				{
					c.changeSelectedDay(((AbstractButton) event.getSource()).getText());
				});
			}


			//okay so you're not going to understand what i did here when you look at this x days from now...
			//basically we got the event source (the JToggleButton), casted it,
			//then got it's text, then told the controller to change the selected day
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}

		//adds and prints last day of month
		if(selectedMonth.get(Calendar.DATE) == 31 || selectedMonth.get(Calendar.DATE) == 30 ||selectedMonth.get(Calendar.DATE) == 29 ||selectedMonth.get(Calendar.DATE) == 28 )
		{
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			b.addActionListener(event -> 
			{
				c.changeSelectedDay(((AbstractButton) event.getSource()).getText());
			});
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}

		//add until saturday gets added
		while(selectedMonth.get(Calendar.DAY_OF_WEEK) != 7) 
		{
			b = this.createDate(selectedMonth.get(Calendar.DATE));
			b.setForeground(Color.gray);
			b.addActionListener(event -> 
			{
				c.addMonth();
			});
			i.add(b);
			selectedMonth.add(Calendar.DATE, 1);
		}

		//prints gray Saturday
		b = this.createDate(selectedMonth.get(Calendar.DATE));
		b.setForeground(Color.gray);
		b.addActionListener(event -> 
		{
			c.addMonth();
		});
		i.add(b);
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



	public void drawTF()
	{
		tFrame.getContentPane().removeAll();
		tFrame.setLayout(new BorderLayout());

		//Creates label area to print out date at top of the window
		JLabel date = new JLabel("Day: " + c.formatTaskDate());


		//Creates the scrolling area that displays tasks as a list
		JPanel taskList = new JPanel();
		ButtonGroup taskButtons = drawTasks();
		taskList.setLayout(new GridLayout(this.taskList.size(), 1));	
		for(Enumeration<AbstractButton> e = taskButtons.getElements(); e.hasMoreElements();)
		{
			taskList.add(e.nextElement());
		}
		JScrollPane scroller = new JScrollPane(taskList);


		//Adds the user text entry box and task buttons
		//**NEED TO MAKE IT LOOK PRETTIER (it looks u g l y) // no it's our beautiful baby. donut call it ugly
		JPanel taskPanel = new JPanel();					//Panel that formats textfield and buttons
		taskPanel.setLayout(new BorderLayout());
		JTextField userText = new JTextField(20);
		taskPanel.add(userText, BorderLayout.NORTH);


		JPanel buttons = new JPanel();						//Panel that formats button positions
		buttons.setLayout(new GridLayout(1,4));

		//delete boi
		JButton delete = new JButton("Delete Task");
		delete.addActionListener(event -> {
			m.removeTask();
		});

		//edit boi
		JButton edit = new JButton("Edit Task");
		edit.addActionListener(event -> {
			String newName = userText.getText();
			m.editTask(newName);
		});

		//add boi
		JButton add = new JButton("Add Task");
		add.addActionListener(event -> {
			String taskName = userText.getText();
			m.addTask(taskName);
		});

		//export boi
		JButton export = new JButton("Export");
		export.addActionListener(event -> {
			c.export();
		});

		//add buttons to button panel
		buttons.add(delete);
		buttons.add(edit);
		buttons.add(add);
		buttons.add(export);
		taskPanel.add(buttons,BorderLayout.SOUTH);


		//formats things into frame
		tFrame.add(date, BorderLayout.NORTH);
		tFrame.add(scroller, BorderLayout.CENTER);
		tFrame.add(taskPanel, BorderLayout.SOUTH);

		tFrame.setSize(300,300);
		tFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tFrame.setVisible(true);
	}


	public ButtonGroup drawTasks() {
		ButtonGroup tasks = new ButtonGroup();
		JToggleButton b = null;
		taskList = m.getTasks();
		for(Dates task : taskList) {
			b = new JToggleButton(task.getTask());
			b.addActionListener(event -> 
			{
				m.selectTask(((JToggleButton) event.getSource()).getText());
			});
			tasks.add(b);
		}
		return tasks;
	}

	//Constructors-----------------------------------------------
	public View()
	{
		m = null;
		c = null;
		cFrame = new JFrame();
		tFrame = new JFrame();
		taskList = null;
	}


}
