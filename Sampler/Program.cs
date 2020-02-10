using System;

namespace AudioSampler
{
    class Program
    {
        //arg[0] log filepath
        //arg[1] loger sensitivity
        //arg[2] audio filepath
        //arg[3] samples filepath
        static void Main(string[] args)
        {
            //establish logger file
            Logger.LogFile = args[0];
            //establish logger sensitivity
            Logger.Sensitivity = Int32.Parse(args[1]);
            //initialize logger
            Logger.logInit();
            //attempt to parse the audio file
            AudioSampler.OpenAudioSampler(args[2]);
            AudioSampler.ParseFile();
            AudioSampler.CloseAudioSampler();
            //attempt to write out the results
            AudioData.WriteOutAudioSampleData(args[3]);
            return;
        }
    }
}
