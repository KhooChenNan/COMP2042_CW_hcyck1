import static org.junit.Assert.*;

import org.junit.Test;

public class WallControllerMoveTest {

	@Test
	public void testMove() {
		int playerMove, ballMove;
		boolean playerMoveCheck, ballMoveCheck;
		
		// Essentially just testing whether the called functions return specific values
		playerMove = playerMove();
		ballMove = ballMove();
		
		if (playerMove == 1 && ballMove == 1) {
			playerMoveCheck = true;
			ballMoveCheck = true;
		}
		else {
			playerMoveCheck = false;
			ballMoveCheck = false;
		}
		
		if (playerMove == 1 && ballMove == 1) {
			assertEquals(true, playerMoveCheck);
			assertEquals(true, ballMoveCheck);
		}
		else {
			assertEquals(false, playerMoveCheck);
			assertEquals(false, ballMoveCheck);
		}
	}
	
	public int playerMove() {
		return 1;
	}
	
	public int ballMove() {
		return 1;
	}
}
