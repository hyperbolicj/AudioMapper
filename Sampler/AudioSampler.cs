using System;
using System.IO;

namespace AudioSampler
{
    internal class AudioSampler
    {
        private static BinaryReader Reader;
        private static bool ret;
        private static bool test;
        private static int i, j;
        private static byte[] Data;
        private static byte[] Sample;
        private static int sampleSize;
        private static long instance;

        internal AudioSampler()
        {
            Data = new byte[4];
            return;
        }

        internal static bool OpenAudioSampler(String FilePath)
        {
            try
            {
                Reader = new BinaryReader(File.Open(FilePath, FileMode.Open));
                ret = true;
            }
            catch (Exception Exc)
            {
                Logger.LogMessage(0, "Failed to open audio reader");
                Logger.LogMessage(0, Exc.StackTrace);
                ret = false;
            }
            return ret;
        }

        internal static bool ParseFile()
        {
            try
            {
                //code to parse file
                ret = true;
                //Read chunk ID 'RIFF'
                Data = Reader.ReadBytes(4);
                if (false == Test(Data, TestVariables.ChunkID))
                {
                    ret = false;
                    Logger.LogMessage(1, "Unexpected ChunkID in audio file");
                }
                //Read chunk size
                Data = Reader.ReadBytes(4); //total remainder of the audio file
                //Read format 'WAVE'
                Data = Reader.ReadBytes(4);
                if (false == Test(Data, TestVariables.Format))
                {
                    ret = false;
                    Logger.LogMessage(1, "Unsupported audio format in audio file");
                }
                //Read subchunk ID 'fmt '
                Data = Reader.ReadBytes(4);
                if (false == Test(Data, TestVariables.SubChunkID1))
                {
                    ret = false;
                    Logger.LogMessage(1, "Unexpected subchunk ID");
                }
                //Read subchunk size 
                Data = Reader.ReadBytes(4);
                if (false == Test(Data, TestVariables.SubChunk1Size))
                {
                    ret = false;
                    Logger.LogMessage(1, "Unexpected Subchunk size");
                }
                //Read Audio Format (Expected 1)
                AudioData.AudioFormat = Reader.ReadUInt16();
                if (1 != AudioData.AudioFormat)
                {
                    ret = false;
                    Logger.LogMessage(1, "Unexpected audio file compression");
                }
                //Read number of channels (1 or 2)
                AudioData.NumberOfChanels = Reader.ReadUInt16();
                //Read sample rate (expressed in Hz)
                AudioData.SampleRate = Reader.ReadUInt32();
                //Read Byterate (Sample rate * Number of channels * bits per sample / 8)
                Reader.ReadUInt32();
                //Read Block allign (Number of channels * bits per sample / 8)
                Reader.ReadUInt16();
                //Read Bits per sample
                AudioData.BitsPerSample = Reader.ReadUInt16();
                //Read subchunk ID 'DATA'
                Data = Reader.ReadBytes(4);
                if (false == Test(Data, TestVariables.SubChunkID2))
                {
                    ret = false;
                    Logger.LogMessage(1, "Unexpected Subchunk size");
                }
                //Read subchunk size (number of samples * number of channels * bytes per sample)
                AudioData.NumberOfSamples = Reader.ReadUInt32() / AudioData.NumberOfChanels / (AudioData.BitsPerSample / 8);
                //normalize the sample rate to (8000Hz)
                AudioData.NormalizedSampleRateRatio = (int)(AudioData.SampleRate / AudioData.NormalizedSampleRate);
                //read the data section
                sampleSize = AudioData.NumberOfChanels * (AudioData.BitsPerSample / 8);
                Sample = new byte[sampleSize];
                for (i = 0; i < AudioData.NumberOfSamples; i++)
                {
                    Sample = Reader.ReadBytes(sampleSize);
                    if (0 == i % AudioData.NormalizedSampleRateRatio)
                    {
                        AudioData.AddSample(ConvertSample(Sample));
                    }
                }
            }
            catch (Exception Exc)
            {
                Logger.LogMessage(0, "Failed to parse audio reader");
                Logger.LogMessage(0, Exc.StackTrace);
                ret = false;
            }
            return ret;
        }

        internal static bool CloseAudioSampler()
        {
            try
            {
                Reader.Close();
                ret = true;
            }
            catch (Exception Exc)
            {
                Logger.LogMessage(0, "Failed to close audio reader");
                Logger.LogMessage(0, Exc.StackTrace);
                ret = false;
            }
            return ret;
        }

        private static bool Test(byte[] TestArray, byte[] Key)
        {
            test = true;
            i = Math.Min(TestArray.Length, Key.Length);
            for (j = 0; j < i && test; j++)
            {
                if (TestArray[j] != Key[j])
                {
                    test = false;
                }
            }
            return test;
        }

        private static long ConvertSample(byte[] SamplesIn)
        {
            if (16 == AudioData.BitsPerSample)
            {
                instance = (long)(BitConverter.ToInt16(SamplesIn, 0));
            }
            else if (32 == AudioData.BitsPerSample)
            {
                instance = (long)(BitConverter.ToInt32(SamplesIn, 0));
            }
            else if (64 == AudioData.BitsPerSample)
            {
                instance = (long)(BitConverter.ToInt64(SamplesIn, 0));
            }
            else
            {
                instance = 0;
            }
            return instance;
        }
    }
}
