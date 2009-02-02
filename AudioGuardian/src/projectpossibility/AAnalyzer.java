package projectpossibility;

//Amplitude Analyzer
//Analyzes amplitude of the input audio stream

public class AAnalyzer {
	
	// Returns maximum amplitude at any point in the sample
	public static double getMaxAmplitude(byte[] stream){
		double max = 0;
		for(int i = 0 ; i < stream.length ; i+=2){
			if((double)stream[i] > max){
				max = (double)stream[i];
			}
		}
		return max;
	}
	
	// Returns number of samples above a certain threshold level in the sample
	public static double samplesAboveThreshold(byte[] stream, double thres){
		double samples = 0;
		for(int i = 0 ; i < stream.length ; i+=2){
			if((double)stream[i] >= thres){
				samples++;
			}
		}
		return samples;
	}
	
	// Returns average amplitude of entire sample
	public static double getAvgAmplitude(byte[] stream){
		double avg = 0;
		double num = (stream.length-50)/2;
		for(int i = 0 ; i < stream.length ; i+=2){
				avg += stream[i]/num;
		}
		return avg;
	}
}
