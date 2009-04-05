package projectpossibility;

/**
 * Stores data for the floor level for microphone recording.
 * This data is used to adjust the gain of the input.
 */
public class FloorLevel {
/**
 * Constructor
 * @param level The desired level to initialize the floor.
 */
	public FloorLevel(double level) {
		super();
		this.level = level;
	}

	private double level;
/**
 * Getter
 * @return The stored floor level.
 */
	public double getLevel() {
		return level;
	}
/**
 * Sets the level to a desired value.
 * @param level Desired level to be set.
 */
	public void setLevel(double level) {
		this.level = level;
	}
	
	
}
