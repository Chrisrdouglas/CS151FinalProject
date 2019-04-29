import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

public class CalendarFrame {
	public static void main(String[] args){
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
		BasicArrowButton rightArrow = new BasicArrowButton(BasicArrowButton.EAST);
		
		//month and years
		JLabel month = new JLabel("January");		//**TEMP TEST VALUE
		JPanel yearPanel = new JPanel();
		yearPanel.setLayout(new FlowLayout());
		JTextField year = new JTextField("2014");			//**TEMP TEST VALUE
		year.setEnabled(false);
		yearPanel.add(year);
		BasicArrowButton upArrow = new BasicArrowButton(BasicArrowButton.NORTH);
		BasicArrowButton downArrow = new BasicArrowButton(BasicArrowButton.SOUTH);
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
		
		//dates panel
		JPanel dates = new JPanel();
		dates.setLayout(new GridLayout(6,7));
		for(int i = 0; i < 42; i++){			//**TEST VALUES
			createDate(i, dates);
		}
		
		//display today's date
		String todayDate = "Today: ";			//**ADD SOMETHING TO DISPLAY TODAY'S DATE
		JLabel today = new JLabel(todayDate);
		
		//add stuff to frame
		frame.add(monthDisplay, BorderLayout.NORTH);
		frame.add(dates, BorderLayout.CENTER);
		frame.add(today, BorderLayout.SOUTH);
		frame.setSize(375,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//creates dates with buttons
	public static void createDate(int date, JComponent c){
		String num = String.valueOf(date);
		JButton day = new JButton(num);
		//day.setFocusPainted(false);
		day.setContentAreaFilled(false);
		day.setBorderPainted(false);
		c.add(day);	
	}
}
