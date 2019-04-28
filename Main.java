
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model m = new Model();
		View v = new View();
		Controller c = new Controller();
		c.addBoth(m, v);
		m.addController(c);
		v.addBoth(m,c);
		
		print(c.formatToday());
		//m.export();
		
		m.addTask("Batman", 2019, 3, 20);
		m.addTask("Superman", 2019, 4, 1);
		m.addTask("Wonder Woman", 2019, 4, 20);
		m.addTask("Cyborg", 2019, 4, 30);
		m.addTask("Fanime", 2019, 5, 24);
		
		//print(d.toString());
		
		c.export();
		
	}

	static void print(String s)
	{
		System.out.println(s);
	}
}