package AudioMapperApp.AudioFileHandler;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import AudioMapperApp.Logger.Logger;

public class AudioFileHandler 
{
	private static long CurrentPosition;
	private static Clip Player;
	private static AudioInputStream AuStream;
	private static String FilePath;
	
	public AudioFileHandler(String FilePathIn)
	{
		FilePath = FilePathIn;
		try 
		{
			AuStream = AudioSystem.getAudioInputStream(new File(FilePath));
		} 
		catch (Exception Exc) 
		{
			Logger.LogMessage(1, "Audio Player Handler failed to be initialize");
			Logger.LogMessage(1, Exc.getStackTrace().toString());
		}
		try 
		{
			Player = AudioSystem.getClip();
		} 
		catch (Exception Exc) 
		{
			Logger.LogMessage(1, "Audio Stream line unsupported");
			Logger.LogMessage(1, Exc.getStackTrace().toString());
		}
		try 
		{
			Player.open(AuStream);
		} 
		catch (Exception Exc) 
		{
			Logger.LogMessage(1, "Clip player failed to read audio stream");
			Logger.LogMessage(1, Exc.getStackTrace().toString());
		}
		Player.loop(0);
		Stop();
		return;
	}
	
	public static void Play()
	{
		if(AudioPlayerState.State.Playing != AudioPlayerState.GetAudioPlayerState())
		{
			Seek(CurrentPosition);
		}
		else
		{
			Logger.LogMessage(3, "Audio player state is already playing");
		}
		return;
	}
	
	public static void Pause()
	{
		if(AudioPlayerState.State.Playing == AudioPlayerState.GetAudioPlayerState())
		{
			CurrentPosition = Player.getMicrosecondPosition();
			Player.stop();
			AudioPlayerState.SetAudioPlayerState(AudioPlayerState.State.Paused);
		}
		else
		{
			Logger.LogMessage(3, "Audio player state isn't pausable");
		}
		return;
	}
	
	public static void Stop()
	{
		CurrentPosition = 0L;
		Player.stop();
		Player.close();
		AudioPlayerState.SetAudioPlayerState(AudioPlayerState.State.Stopped);
		return;
	}
	
	public static void Seek(long position)
	{
		if(position >= 0 && position < Player.getMicrosecondLength())
		{
			Player.stop();
			Player.close();
			ResetAudioStream();
			CurrentPosition = position;
			Player.setMicrosecondPosition(CurrentPosition);
			Player.start();
			AudioPlayerState.SetAudioPlayerState(AudioPlayerState.State.Playing);
		}
		else
		{
			Logger.LogMessage(1, "Seek position out of bounds");
		}
		return;
	}
	
	private static void ResetAudioStream()
	{
		try 
		{
			AuStream = AudioSystem.getAudioInputStream(new File(FilePath));
		} 
		catch (Exception Exc) 
		{
			Logger.LogMessage(1, "Audio Player Handler failed to be initialize");
			Logger.LogMessage(1, Exc.getStackTrace().toString());
		}
		try 
		{
			Player.open(AuStream);
		} 
		catch (Exception Exc) 
		{
			Logger.LogMessage(1, "Clip player failed to read audio stream");
			Logger.LogMessage(1, Exc.getStackTrace().toString());
		}
		Player.loop(0);
		return;
	}
}
