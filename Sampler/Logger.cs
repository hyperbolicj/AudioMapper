using System;
using System.IO;

namespace AudioSampler
{
    internal static class Logger
    {
        private static StreamWriter OfStream;

        public static string LogFile { get; internal set; }
        public static int Sensitivity { get; internal set; }

        internal static void logInit()
        {
            OfStream = new StreamWriter(LogFile, false);
            OfStream.Close();
            return;
        }

        internal static void LogMessage(int Severity, String Message)
        {
            OfStream = new StreamWriter(LogFile, true);
            if(Severity <= Sensitivity)
            {
                OfStream.WriteLine(DateTime.Now + ":" + Message);
            }
            OfStream.Close();
            return;
        }
    }
}
