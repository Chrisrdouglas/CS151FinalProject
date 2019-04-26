import javax.swing.JFrame;

public class View {
	//Variables--------------------------------------------------
	private Model m;
	private Controller c;
	private JFrame cf;
	private JFrame tf;
	
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
		
	}
	
	public void drawTF()
	{
		
	}
	
	//Constructors-----------------------------------------------
	public View()
	{
		m = null;
		c = null;
		cf = new JFrame();
		tf = new JFrame();
	}
}
