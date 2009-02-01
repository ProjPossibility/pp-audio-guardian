
public class AAnalyzer {
	public static double getMaxAmplitude(byte[] stream){
		double max = 0;
		for(int i = 50 ; i < stream.length ; i+=2){
			if((double)stream[i] > max){
				max = (double)stream[i];
			}
		}
		return max;
	}
	
	public static double samplesAboveThreshold(byte[] stream, double thres){
		double samples = 0;
		for(int i = 50 ; i < stream.length ; i+=2){
			if((double)stream[i] >= thres){
				samples++;
			}
		}
		return samples;
	}
	
	public static double getAvgAmplitude(byte[] stream){
		double avg = 0;
		double num = (stream.length-50)/2;
		for(int i = 50 ; i < stream.length ; i+=2){
				avg += stream[i]/num;
		}
		return avg;
	}
}
