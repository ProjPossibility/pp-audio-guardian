package projectpossibility;

/**Amplitude Analyzer - 
 * performs calculations related to amplitude of the audio data\n
 * (finding max amplitude, finding number of samples above threshold,finding average amplitude)
 */
public class AAnalyzer {
	/**
	 * find maximum amplitude of the audio stream
	 * @param stream captured audio data
	 * @return maximum amplitude at any point in the sample
	 */
	public static double getMaxAmplitude(byte[] stream){
		double max = 0;
		for(int i = 0 ; i < stream.length ; i+=2){
			if((double)stream[i] > max){
				max = (double)stream[i];
			}
		}
		return max;
	}
	
	/**
	 * find number of samples with level above the supplied threshold value
	 * @param stream captured audio data
	 * @param thres threshold value
	 * @return number of samples
	 */
	public static double samplesAboveThreshold(byte[] stream, double thres){
		double samples = 0;
		for(int i = 0 ; i < stream.length ; i+=2){
			if((double)stream[i] >= thres){
				samples++;
			}
		}
		return samples;
	}
	
	/**
	 * find average amplitude of audio stream
	 * @param stream captured audio data
	 * @return average amplitude of entire sample
	 */
	public static double getAvgAmplitude(byte[] stream){
		double avg = 0;
		double num = (stream.length-50)/2;
		for(int i = 0 ; i < stream.length ; i+=2){
				avg += stream[i]/num;
		}
		return avg;
	}
}
