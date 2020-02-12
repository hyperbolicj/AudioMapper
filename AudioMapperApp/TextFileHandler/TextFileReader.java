package AudioMapperApp.TextFileHandler;

import java.io.FileReader;
import AudioMapperApp.Logger.Logger;

public class TextFileReader 
{
	private FileReader FStream;
	private Boolean ret;
	private int value;
	private Boolean HasValue;
	
	public TextFileReader()
	{
		return;
	}

	public Boolean OpenFile(String FilePath) 
	{
		try
		{
			FStream = new FileReader(FilePath);
			HasValue = false;
			ret = true;
		}
		catch(Exception Exc)
		{
			Logger.LogMessage(0, "Open file:" + FilePath + " failed");
			Logger.LogMessage(0, Exc.getStackTrace().toString());
			ret = false;
		}
		return ret;
	}

	public Boolean CloseFile() 
	{
		try
		{
			FStream.close();
			ret = true;
		}
		catch(Exception Exc)
		{
			Logger.LogMessage(0, "Fetal error in closing text file");
			Logger.LogMessage(0, Exc.getStackTrace().toString());
			ret = false;
		}
		return ret;
	}

	public Boolean EOF() 
	{
		try
		{
			value = FStream.read();
			HasValue = true;
			if(-1 == value)
			{
				ret = true;
			}
			else
			{
				ret = false;
			}
			
		}
		catch(Exception Exc)
		{
			Logger.LogMessage(0, "Read peek failed");
			Logger.LogMessage(0, Exc.getStackTrace().toString());
		}
		return ret;
	}

	public int Read() 
	{
		try
		{
			if(false == HasValue)
			{
				value = FStream.read();
			}
		}
		catch(Exception Exc)
		{
			Logger.LogMessage(0, "Text file reader failed to get next character");
			Logger.LogMessage(0, Exc.getStackTrace().toString());
		}
		return value;
	}
}
