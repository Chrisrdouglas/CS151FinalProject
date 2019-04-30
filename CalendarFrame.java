import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

public class CalendarFrame {
	
	public CalendarFrame(){
		
		
		//dates panel
		JPanel dates = new JPanel();
		dates.setLayout(new GridLayout(6,7));
		for(int i = 0; i < 42; i++){			//**TEST VALUES
			createDate(i, dates);
		}
		
	
	}
	
	//creates dates with buttons
	public static void createDate(int date, JComponent c){
		String num = String.valueOf(date);
		JButton day = new JButton(num);
		//day.setFocusPainted(false);
		day.setContentAreaFilled(false);
		//day.setBorderPainted(false);
		c.add(day);	
	}

	
	public static void main(String[] args) {
		CalendarFrame c = new CalendarFrame();
		
	}
}
