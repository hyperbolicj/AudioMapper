using System;
using System.Collections.Generic;
using System.IO;

namespace AudioSampler
{
    internal class AudioData
    {
        internal static uint NormalizedSampleRate = 8000;
        internal static ushort AudioFormat { get; set; }
        internal static ushort BitsPerSample { get; set; }
        internal static uint SampleRate { get; set; }
        internal static ushort NumberOfChanels { get; set; }
        internal static long NumberOfSamples { get; set; }
        internal static int NormalizedSampleRateRatio { get; set; }
        internal static string Filepath { get; set; }

        private static StreamWriter OfStream;
        private static List<long> Samples = new List<long>();

        internal static void AddSample(long SampleIn)
        {
            Samples.Add(SampleIn);
            return;
        }

        internal static void WriteOutAudioSampleData(String FilePath)
        {
            try
            {
                OfStream = new StreamWriter(FilePath, false);
            }
            catch(Exception Exc)
            {
                Logger.LogMessage(0, "Failed to open Samples output file");
                Logger.LogMessage(0, Exc.StackTrace);
            }
            foreach(long sample in Samples)
            {
                try
                {
                    OfStream.Write(sample + "\n");
                }
                catch(Exception Exc)
                {
                    Logger.LogMessage(0, "Failed to write out sample");
                    Logger.LogMessage(0, Exc.StackTrace);
                }
            }
            try
            {
                OfStream.Close();
            }
            catch(Exception Exc)
            {
                Logger.LogMessage(0, "Failed to close Samples output file");
                Logger.LogMessage(0, Exc.StackTrace);
            }
            return;
        }
    }
}
