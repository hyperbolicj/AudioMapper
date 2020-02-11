package AudioMapperApp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import AudioMapperApp.AudioFileHandler.AudioFileHandler;
import AudioMapperApp.Logger.Logger;

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
	private JTextArea SampleContext;
	private JButton TrainerBrowseSamples;
	private JButton TrainerBrowseKey;
	private JButton TrainerBrowseTranscript;
	private JButton TrainerExec;
	private JPanel PlayerSouth;
	private JTextArea PlayerContext;
	private JScrollPane PlayerContextScroller;
	private JButton PlayerBrowseTranscript;
	private JButton PlayerBrowseAudioFile;
	private JButton PlayerBrowseCustomModel;
	private JButton PlayerPlay;
	private JButton PlayerStop;
	private JButton PlayerSeek;
	//worker variables
	private int i;
	private int returnVal;
	private Boolean test;
	private String[] params; 
	private Process Proc;
	private AudioFileHandler PlayerHandler;
	private Boolean PlayerAudioLoaded;
	
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
		SampleContext = new JTextArea();
		TrainerBrowseSamples = new JButton("Browse Sample File");
		TrainerBrowseKey = new JButton("Browse Key File");
		TrainerBrowseTranscript = new JButton("Browse Transcript File");
		TrainerExec = new JButton("Train Model");
		params = new String[6];
		PlayerSouth = new JPanel();
		PlayerContext = new JTextArea();
		PlayerContextScroller = new JScrollPane(PlayerContext, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		PlayerBrowseTranscript = new JButton("Browse Transcript");
		PlayerBrowseAudioFile = new JButton("Browse Audio File");
		PlayerBrowseCustomModel = new JButton("Browse Custom Model");
		PlayerPlay = new JButton("Play");
		PlayerStop = new JButton("Stop");
		PlayerSeek = new JButton("Seek");
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
		//Setup properties to dynamic components
		SampleBrowse.addActionListener(this);
		SampleContext.setEditable(false);
		TrainerBrowseSamples.addActionListener(this);
		TrainerBrowseKey.addActionListener(this);
		TrainerBrowseTranscript.addActionListener(this);
		TrainerExec.addActionListener(this);
		PlayerContext.setEditable(false);
		PlayerContext.setLineWrap(true);
		PlayerSouth.setLayout(new GridLayout(2,3));
		PlayerBrowseTranscript.addActionListener(this);
		PlayerBrowseAudioFile.addActionListener(this);
		PlayerBrowseCustomModel.addActionListener(this);
		PlayerPlay.addActionListener(this);
		PlayerStop.addActionListener(this);
		PlayerSeek.addActionListener(this);
		PlayerSouth.add(PlayerBrowseTranscript);
		PlayerSouth.add(PlayerBrowseAudioFile);
		PlayerSouth.add(PlayerBrowseCustomModel);
		PlayerSouth.add(PlayerPlay);
		PlayerSouth.add(PlayerStop);
		PlayerSouth.add(PlayerSeek);
		return;
	}
	
	private void StriptComponents()
	{
		try
		{
			Content.removeAll();
			Container.remove(Content);
		}
		catch(Exception Exc)
		{
			//log any errors
		}
		//reset parameters
		for(i = 0; i < 6; i++)
		{
			params[i] = "";
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
		SampleContext.setText("Sampler ready. \n");
		Content.add(SampleContext, BorderLayout.CENTER);
		Content.add(SampleBrowse, BorderLayout.SOUTH);
		Container.add(Content, BorderLayout.CENTER);
		//validate the component hierarchy & repaint it
		Container.validate();
		Container.setSize(500, 550);
		Container.repaint();
		return;
	}
	
	private void DisplayTrainer()
	{
		StriptComponents();
		Content.setLayout(new GridLayout(4,1));
		Content.add(TrainerBrowseTranscript);
		Content.add(TrainerBrowseSamples);
		Content.add(TrainerBrowseKey);
		Content.add(TrainerExec);
		Container.add(Content, BorderLayout.CENTER);
		//validate the component hierarchy & repaint it
		Container.validate();
		Container.pack();
		Container.setSize(500, Container.getHeight());
		Container.repaint();
		return;
	}
	
	private void DisplayPlayer()
	{
		StriptComponents();
		PlayerAudioLoaded = false;
		Content.setLayout(new BorderLayout());
		Content.add(PlayerContextScroller, BorderLayout.CENTER);
		Content.add(PlayerSouth, BorderLayout.SOUTH);
		Container.add(Content, BorderLayout.CENTER);
		//validate the component hierarchy & repaint it
		Container.validate();
		Container.setSize(510, 550);
		Container.repaint();
		return;
	}
	
	private Boolean CheckForRunningTasks()
	{
		//NOTE: implement check for running tasks
		return false;
	}
	
	public static void main(String[] args)
	{
		Logger.SetSensitivity(0);
		Logger.LogInit();
		App myApp = new App();
		return;
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
		i = 0;
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
		        	//NOTE: set filepath to be relative to this project
		        	params[0] = "C:\\Sophie_Stehlik\\Job_Stuff\\SampleCode\\AudioSampler\\AudioSampler\\AudioSampler\\bin\\Debug\\netcoreapp3.1\\AudioSampler.exe";
		        	params[1] = "Sampler_Log.txt";
		        	params[2] = "0";
		        	params[3] = FileIn.getAbsolutePath();
		        	//get filepath to save samples file to
		        	returnVal = FC.showSaveDialog(this);
		        	if(returnVal == JFileChooser.APPROVE_OPTION)
		        	{
		        		FileIn = FC.getSelectedFile();
			        	params[4] = FileIn.getAbsolutePath();
			        	//this application only requires 4 arguments
			        	params[5] = "";
			        	try
			        	{
			        		SampleContext.setText(SampleContext.getText() + "Launching Sampler.\n");
							Proc = new ProcessBuilder(params).start();
							SampleContext.setText(SampleContext.getText() + "Sampling Complete.\n");
			        	}
			        	catch (Exception Exc)
			        	{
			        		SampleContext.setText(SampleContext.getText() + "Sampling Failed.\n");
			        		//NOTE: Log Exceptions
			        	}
		        	}
		        	else
		        	{
		        		//user canceled
		        	}
		        } 
		        else 
		        {
		            //user canceled
		        }
			}
			else if(TrainerBrowseSamples == e.getSource())
			{
				returnVal = FC.showOpenDialog(this);
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		        	FileIn = FC.getSelectedFile();
		        	params[3] = FileIn.getAbsolutePath();
		        }
		        else
		        {
		        	//user canceled
		        }
			}
			else if(TrainerBrowseKey == e.getSource())
			{
				returnVal = FC.showOpenDialog(this);
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		        	FileIn = FC.getSelectedFile();
		        	params[4] = FileIn.getAbsolutePath();
		        }
		        else
		        {
		        	//user canceled
		        }
			}
			else if(TrainerBrowseTranscript == e.getSource())
			{
				returnVal = FC.showOpenDialog(this);
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		        	FileIn = FC.getSelectedFile();
		        	params[5] = FileIn.getAbsolutePath();
		        }
		        else
		        {
		        	//user canceled
		        }
			}
			else if(TrainerExec == e.getSource())
			{
				params[0] = "";
				params[1] = "0";
				//get output filepath
				returnVal = FC.showSaveDialog(this);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					FileIn = FC.getSelectedFile();
					params[2] = FileIn.getAbsolutePath();
					//check all arguments have been initialized
					test = true;
					for(i = 0; i < 6 && true == test; i++)
					{
						if(params[i].equals(""))
						{
							test = false;
						}
					}
					if(true == test)
					{
						//NOTE: launch trainer application using the filepaths specified
					}
				}
				else
				{
					//user canceled
				}
			}
			else if(PlayerBrowseTranscript == e.getSource())
			{
				returnVal = FC.showOpenDialog(this);
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		        	FileIn = FC.getSelectedFile();
		        	//NOTE: Add text to player center window
		        }
		        else
		        {
		        	
		        }
			}
			else if(PlayerBrowseAudioFile == e.getSource())
			{
				returnVal = FC.showOpenDialog(this);
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		        	FileIn = FC.getSelectedFile();
		        	PlayerHandler = new AudioFileHandler(FileIn.getAbsolutePath());
		        	System.out.println(FileIn.getAbsolutePath() + " loaded");
		        	PlayerAudioLoaded = true;
		        }
		        else
		        {
		        	
		        }
			}
			else if(PlayerBrowseCustomModel == e.getSource())
			{
				returnVal = FC.showOpenDialog(this);
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		        	FileIn = FC.getSelectedFile();
		        	//NOTE: load the model
		        }
		        else
		        {
		        	
		        }
			}
			else if(PlayerPlay == e.getSource())
			{
				if(true == PlayerAudioLoaded)
				{
					if(PlayerPlay.getText().equals("Play"))
					{
						PlayerPlay.setText("Pause");
						PlayerHandler.Play();
					}
					else
					{
						PlayerPlay.setText("Play");
						PlayerHandler.Pause();
					}
				}
			}
			else if(PlayerStop == e.getSource())
			{
				if(true == PlayerAudioLoaded)
				{
					PlayerPlay.setText("Play");
					PlayerHandler.Stop();
				}
			}
			else if(PlayerSeek == e.getSource())
			{
				if(true == PlayerAudioLoaded)
				{
					//NOTE: Determine the word number and apply the model then seek the word
				}
			}
			else
			{
				//un-handled source
			}
		}
		return;
	}
}
