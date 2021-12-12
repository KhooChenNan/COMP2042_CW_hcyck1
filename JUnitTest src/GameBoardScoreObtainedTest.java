import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class GameBoardScoreObtainedTest {

	@Test
	public void testScoreObtained() {
		Random rnd = new Random();
		int ballCount, brickDestroyed, finalScore = 0;
		boolean answerCheck;
		
		do {
			ballCount = rnd.nextInt(3); // Randomly generated value from 0 to 3.
		} while (ballCount == 0); // lowest possible score = +10; highest possible score = +30
		
		do {
			brickDestroyed = rnd.nextInt(31); // Randomly generated value from 0 to 31.
		} while (brickDestroyed == 0 || brickDestroyed == 31); // lowest possible score = -60; highest possible score = -2
		
    	finalScore = (31*2) - (brickDestroyed*2) + (ballCount *10);
    	
    	if (finalScore >= 12 && finalScore <= 90) {
    		answerCheck = true;
    	}
    	else {
    		answerCheck = false;
    	}
    	
    	if (finalScore >= 12 && finalScore <= 90) {
    		assertEquals(true, answerCheck);
    	}
    	else {
    		assertEquals(false, answerCheck);
    	}
	}
}
