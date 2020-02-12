package AudioMapperApp.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import AudioMapperApp.TextFileHandler.TextFileWritter;

public class Logger 
{
	private static final TextFileWritter Writer = new TextFileWritter();
	private static SimpleDateFormat Formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
	private static Date D = new Date();  
	private static String FilePath;
	private static int Sensitivity;
	
	public static void SetSensitivity(int SensitivityIn)
	{
		Sensitivity = SensitivityIn;
		return;
	}
	
	public static void LogInit()
	{
		FilePath = "Log_" + Formatter.format(D) + ".txt";
		try 
		{
			Writer.ClearFile(FilePath);
		} 
		catch (Exception Exc) 
		{
			//failed to initialize logger
		}
		return;
	}
	
	// SHEMA:
	// 0 => Fatal errors (Critical errors that cause runtime crash)
    // 1 => Minor errors (which are recoverable)
    // 2 => Data structure messages
    // 3 => Control flow messages
    // 4 => Other miscellaneous messages
	public static void LogMessage(int severity, String message)
	{
		if(severity <= Sensitivity)
		{
			try 
			{
				Writer.OpenFile(FilePath, true);
				Writer.WriteLine(Formatter.format(D) + ": " + message + '\n');
				Writer.CloseFile();
			} 
			catch (Exception Exc) 
			{
				//failed to write log message
			}
		}
		return;
	}
}
