package AudioMapperApp.TextFileHandler;

import java.io.FileWriter;

public class TextFileWritter 
{
	private FileWriter FStream;
	private Boolean ret;
	
	public TextFileWritter()
	{
		return;
	}
	
	public Boolean OpenFile(String FilePath, Boolean mode)
	{
		try
		{
			FStream = new FileWriter(FilePath, mode);
			ret = true;
		}
		catch(Exception Exc)
		{
			ret = false;
			System.out.println(Exc.getMessage());
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
			ret = false;
			System.out.println(Exc.getMessage());
		}
		return ret;
	}

	public Boolean ClearFile(String filePath) 
	{
		ret = OpenFile(filePath, false);
		if(true == ret)
		{
			ret = CloseFile();
		}
		return ret;
	}

	public Boolean WriteLine(String line) 
	{
		try
		{
			FStream.write(line+"\n");
			ret = true;
		}
		catch(Exception Exc)
		{
			ret = false;
			System.out.println(Exc.getMessage());
		}
		return ret;
	}
}
