import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class VibraniumBrickImpactTest {

	@Test
	public void testImpact() {
		Random rnd = new Random();
		double probability = rnd.nextDouble();
		int strength = 3;
		
	    if(probability < 0.3){
	        strength--;
	    }
	    if (probability >= 0.3){
	    	assertEquals(3, strength);
	    }
	    else {
	    	assertEquals(2, strength);
	    }
	}
}

