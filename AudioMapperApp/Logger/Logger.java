package AudioMapperApp.Logger;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger 
{
	private static FileWriter FStream;
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
			FStream = new FileWriter(FilePath, false);
			FStream.close();
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
				FStream = new FileWriter(FilePath, true);
				FStream.write(Formatter.format(D) + ": " + message + '\n');
				FStream.close();
			} 
			catch (Exception Exc) 
			{
				//failed to write log message
			}
		}
		return;
	}
}
