
package blue.tmad40.stShards.utilities;

import java.util.concurrent.ThreadLocalRandom;


public class NumberHelper {

	// Picks a random int between the given min/max values
	public static int randomInRange(int min, int max) {

		return ThreadLocalRandom.current().nextInt(min, max + 1);

	}

}
