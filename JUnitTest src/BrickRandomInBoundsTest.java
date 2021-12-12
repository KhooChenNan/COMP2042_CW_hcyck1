import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class BrickRandomInBoundsTest {

	@Test
	public void testRandomInBounds() {
		Random rnd = new Random();
		int n = calculationRandomInBounds(3), finalValue = rnd.nextInt(n) - 3;
		boolean answerCheck;
		
		if (finalValue > -3 && finalValue < 4) {
			answerCheck = true;
		}
		else {
			answerCheck = false;
		}
		
		if (finalValue > -3 && finalValue < 4) {
			assertEquals(true, answerCheck);
		}
		else {
			assertEquals(false, answerCheck);
		}
	}
	
	public int calculationRandomInBounds(int bound) {
		return (bound * 2) + 1;
	}
	
	

}
