package AudioMapperAdd;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class App extends JFrame implements ActionListener, WindowListener
{
	//components that are always present
	private JFrame Container;
	private JMenuBar MenuBar;
	private JMenu Menu;
	private JMenuItem i1, i2, i3, i4;
	private JOptionPane Dialogue;
	private File FileIn;
	private final JFileChooser FC = new JFileChooser();
	//components used by specific display layouts
	private JPanel Content;
	private JButton SampleBrowse;
	
	//worker variables
	private int i;
	private int returnVal;
	private Boolean test;
	private String[] params; 
	private Process Proc;
	
	public App()
	{
		//Instantiate all components that will be used
		Container = new JFrame("Audio Mapper");
		Container.setLayout(new BorderLayout());
		MenuBar = new JMenuBar();
		Menu = new JMenu("Menu");
		Dialogue = new JOptionPane();
		Content = new JPanel();
		SampleBrowse = new JButton("Browse Audio File");
		//Setup static components that will always be displayed
		i1 = new JMenuItem("View Player");
		i1.addActionListener(this);
		i2 = new JMenuItem("View Trainer");
		i2.addActionListener(this);
		i3 = new JMenuItem("View Sampler");
		i3.addActionListener(this);
		i4 = new JMenuItem("Exit");
		i4.addActionListener(this);
		Menu.add(i1);
		Menu.add(i2);
		Menu.add(i3);
		Menu.add(i4);
		MenuBar.add(Menu);
		//Add Static components
		Container.setJMenuBar(MenuBar);
		Container.addWindowListener(this);
		Container.setSize(500, 550);
		Container.setVisible(true);
		//Setup Listeners to dynamic components
		SampleBrowse.addActionListener(this);
		return;
	}
	
	private void StriptComponents()
	{
		try
		{
			Container.remove(Content);
		}
		catch(Exception Exc)
		{
			//log any errors
		}
		//validate the component hierarchy & repaint it
		Container.validate();
		Container.repaint();
		return;
	}
	
	private void DisplaySampler()
	{
		StriptComponents();
		Content.setLayout(new BorderLayout());
		Content.add(SampleBrowse, BorderLayout.SOUTH);
		Container.add(Content, BorderLayout.CENTER);
		//validate the component hierarchy & repaint it
		Container.validate();
		Container.repaint();
		return;
	}
	
	private void DisplayTrainer()
	{
		StriptComponents();
		Container.validate();
		return;
	}
	
	private void DisplayPlayer()
	{
		StriptComponents();
		Container.validate();
		return;
	}
	
	private Boolean CheckForRunningTasks()
	{
		//NOTE: implement check for running tasks
		return false;
	}
	
	public static void main(String[] args)
	{
		App myApp = new App();
	}
	
	@Override
	public void windowOpened(WindowEvent e) 
	{
		return;
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		dispose();
		System.exit(0);
		return;
	}

	@Override
	public void windowClosed(WindowEvent e) 
	{
		return;
	}

	@Override
	public void windowIconified(WindowEvent e) 
	{
		return;
	}

	@Override
	public void windowDeiconified(WindowEvent e) 
	{
		return;
	}

	@Override
	public void windowActivated(WindowEvent e) 
	{
		return;
	}

	@Override
	public void windowDeactivated(WindowEvent e) 
	{
		return;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//check for tasks running
		test = CheckForRunningTasks();
		if(true == test)
		{
			i = Dialogue.showConfirmDialog(this, "There are tasks still running.\nDo you wish to terminate their execution?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		//if none, or user oked abandoning running task
		if(0 == i)
		{
			if(i1 == e.getSource())
			{
				DisplayPlayer();
			}
			else if(i2 == e.getSource())
			{
				DisplayTrainer();
			}
			else if(i3 == e.getSource())
			{
				DisplaySampler();
			}
			else if(i4 == e.getSource())
			{
				Container.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			}
			else if(SampleBrowse == e.getSource())
			{
				returnVal = FC.showOpenDialog(this);
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		        	FileIn = FC.getSelectedFile();
		            //Sample file
		        	params = new String[5];
		        	//NOTE: set filepath to be relative to this project
		        	params[0] = "C:\\Sophie_Stehlik\\Job_Stuff\\SampleCode\\AudioSampler\\AudioSampler\\AudioSampler\\bin\\Debug\\netcoreapp3.1\\AudioSampler.exe";
		        	params[1] = "Sampler_Log.txt";
		        	params[2] = "0";
		        	params[3] = FileIn.getAbsolutePath();
		        	params[4] = "OutputSamples.txt";
		        	try
		        	{
						Proc = new ProcessBuilder(params).start();
					}
		        	catch (Exception Exc)
		        	{
		        		//NOTE: Log Exceptions
					}
		        } else 
		        {
		            //user canceled
		        }
			}
			else
			{
				//unhandled source
			}
		}
		return;
	}
}
