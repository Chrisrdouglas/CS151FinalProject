import java.awt.*;

import javax.swing.*;

public class TaskFrame{

	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		//Creates label area to print out date at top of the window
		//**STILL NEEDS TO OBTAIN INFO ON WHAT DATE NEEDS TO BE PRINTED OUT
		JLabel date = new JLabel("Day: ");

		
		//Creates the scrolling area that displays tasks as a list
		//**NEEDS TO OBTAIN TASK INFO TO DISPLAY
		JPanel taskList = new JPanel();
		taskList.setLayout(new GridLayout(20,1));		//**DUMMY VARIABLES FOR TESTING
		for(int i = 0; i<20; i++){
			createButton("a", taskList);
		}
		JScrollPane scroller = new JScrollPane(taskList);

		
		//Adds the user text entry box and task buttons
		//**NEED TO MAKE IT LOOK PRETTIER (it looks u g l y)
		JPanel taskPanel = new JPanel();					//Panel that formats textfield and buttons
		taskPanel.setLayout(new BorderLayout());
		JTextField userText = new JTextField(20);
		taskPanel.add(userText, BorderLayout.NORTH);
		JPanel buttons = new JPanel();						//Panel that formats button positions
		buttons.setLayout(new GridLayout(1,4));
		createButton("Delete Task", buttons);
		createButton("Edit Task", buttons);
		createButton("Add Task", buttons);
		createButton("Export", buttons);
		taskPanel.add(buttons,BorderLayout.SOUTH);

		
		//formats things into frame
		frame.add(date, BorderLayout.NORTH);
		frame.add(scroller, BorderLayout.CENTER);
		frame.add(taskPanel, BorderLayout.SOUTH);
		
		frame.setSize(300,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void createButton(String name, JComponent c){
		JButton button = new JButton(name);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		c.add(button);
		
	}
}
