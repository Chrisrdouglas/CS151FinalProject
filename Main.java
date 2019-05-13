
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model m = new Model();
		View v = new View();
		Controller c = new Controller();
		c.addBoth(m, v);
		m.addController(c);
		v.addBoth(m,c);
		
		//print(c.formatToday());
		//m.export();
		
		//m.addTask("Do homework", 2019, 5, 8);
		//m.addTask("Print handouts", 2019, 5, 8);
		//m.addTask("Cry", 2019, 5, 8);
		//m.addTask("Cyborg", 2019, 4, 30);
		//m.addTask("Fanime", 2019, 5, 24);
		
		//print(d.toString());
		
		//c.export();
		
		//c.importer("C:\\Users\\chris\\OneDrive\\workspaceO\\151 Final Project\\April_2019.txt");
		
		//for(String s : m.exportList())
		//{
		//	print(s);
		//}
		
		v.drawCF();
		v.drawTF();
		
	}

	//static void print(String s)
	//{
	//	System.out.println(s);
	//}
}
