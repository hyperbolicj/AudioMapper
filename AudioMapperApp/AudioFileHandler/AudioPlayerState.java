package AudioMapperApp.AudioFileHandler;

public class AudioPlayerState 
{
	public static enum State
	{
		Playing,
		Paused,
		Stopped;
	}
	
	private static State AudioPlayerState;

	public static State GetAudioPlayerState() 
	{
		return AudioPlayerState;
	}

	public static void SetAudioPlayerState(State audioPlayerStateIn) 
	{
		AudioPlayerState = audioPlayerStateIn;
	}
}
