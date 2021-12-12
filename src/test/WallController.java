package test;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * A controller class for Wall object to adhere according to the MVC (Model-View-Controller) design pattern.
 * @author KhooChenNan
 *
 */
public class WallController {

	/**
	 * A wall object.
	 */
	private Wall wall;
	/**
	 * A ball object.
	 */
	private Ball ball;
	
	/**
	 * Constructor for the "wallController.Java" class which sets the wall and ball attributes.
	 * @param wallInput The input parameter wall to set the wall controller's wall attribute.
	 * @param ballInput The input parameter ball to set the wall controller's ball attribute.
	 */
	public WallController (Wall wallInput, Ball ballInput) {
		this.wall = wallInput;
		this.ball = ballInput;
	}
	
	/**
	 * Constructs the level in such a way that it lines up the bricks in a monotonous way (consists of only 1 type of brick). Creates the bricks 
	 * and then put them inside the array.
	 * @param drawArea The brick area.
	 * @param brickCount The number of bricks.
	 * @param lineCnt The number of lines of bricks.
	 * @param brickSizeRatio The brick to size ratio.
	 * @param type The type of brick to be used.
	 * @return The array of brick objects. (consists of multiple bricks)
	 */
	private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCount, int lineCnt, double brickSizeRatio, int type){
        /*
         * if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
          It's expected that the no. of Bricks are uniformly distributed among the lines but 
          if it's not divisible at first, they'll round down
         */
        brickCount -= brickCount % lineCnt; 
        // E.g. First level has 33 bricks and 3 lines.  Expected to be uniformly distributed but it's not
        // HOWEVER: If it's 31 bricks and 3 lines, it will then round down to 30 bricks

        /* Formula for no. of bricks on each lines */
        int brickOnLine = brickCount / lineCnt; // E.g. If brick no. = 30 and lines = 3, each lines will have 10 bricks

        /* Attributes for the bricks (Length and Height) */
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCount += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCount];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            /* Since there will be an extra brick, it will be built based on the first brick input. */
            tmp[i] = makeBrick(p,brickSize,type); // More Dynamic
            //tmp[i] = new SteelBrick(p,brickSize); this is for hard coding
        }
        return tmp;
    }
	
	/**
	 * Constructs the level in such a way that it lines up the bricks in a chess board way.  Creates the bricks and then put them inside
	 * the array.
	 * @param drawArea The brick area.
	 * @param brickCount The number of bricks.
	 * @param lineCnt The number of lines of bricks.
	 * @param brickSizeRatio The brick to size ratio.
	 * @param typeA The first type of brick to be used.
	 * @param typeB The second type of brick to be used.
	 * @return The array of brick objects. (consists of multiple bricks)
	 */
	private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCount, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCount -= brickCount % lineCnt;

        int brickOnLine = brickCount / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCount += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCount];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

	/**
	 * Construct the levels of the game.  Calls functions such as makeSingleTypeLevel and makeChessboardLevel for construction.
	 * @param drawArea The brick area.
	 * @param brickCount The number of bricks.
	 * @param lineCount The number of lines of bricks.
	 * @param brickDimensionRatio The brick to dimension ratio.
	 */
    public void makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
    	// Additions/Changes of levels made here
        Brick[][] tmp = new Brick[Wall.LEVELS_COUNT][];
        
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,Wall.CLAY); // First level
        tmp[1] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,Wall.CEMENT); // Second Level
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,Wall.STEEL,Wall.GRAPHITE); // Third Level
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,Wall.VIBRANIUM,Wall.GRAPHITE); // Fourth Level
        wall.levelsSetter(tmp);
    }
    
    /**
     * Movements for the player and the ball
     */
    public void move(){
        wall.playerControllerGetter().move();
        wall.ballGetter().move();
    }

    /**
     * To determine whether the ball has impact with the brick or the player.  (In short, to determine the type of impact)
     */
    public void findImpacts(){
        if(wall.playerGetter().impact(wall.ballGetter())){
        	wall.ballGetter().reverseY(); // If the player comes to contact/impact with the ball, it reverses speed Y
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            wall.brickCountSetter(wall.brickCountGetter()-1);
        }
        else if(impactBorder()) {
        	wall.ballGetter().reverseX(); // Will not reverse speed Y as it's an oblique collision with the border
        }
        else if(wall.ballGetter().getPosition().getY() < wall.areaGetter().getY()){
        	wall.ballGetter().reverseY();
        }
        else if(wall.ballGetter().getPosition().getY() > wall.areaGetter().getY() + wall.areaGetter().getHeight()){
            wall.ballCountSetter(wall.ballCountGetter()-1);
            wall.ballLostSetter(true);
        }
    }

    /**
     * A flag to tests if the ball comes in contact/impact with the Wall.
     * @return Return a boolean to indicate whether the ball has impact with the brick.
     */
    private boolean impactWall(){
        for(Brick b : wall.bricksGetter()){
            switch(b.findImpact(wall.ballGetter())) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                	wall.ballGetter().reverseY();
                    return b.setImpact(wall.ballGetter().down, Brick.Crack.UP);
                case Brick.DOWN_IMPACT:
                	wall.ballGetter().reverseY();
                    return b.setImpact(wall.ballGetter().up,Brick.Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                	wall.ballGetter().reverseX();
                    return b.setImpact(wall.ballGetter().right,Brick.Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                	wall.ballGetter().reverseX();
                    return b.setImpact(wall.ballGetter().left,Brick.Crack.LEFT);
            }
        }
        return false;
    }

    /**
     * A function to check whether the ball has made impact with the border or not.
     * @return A boolean to indicate whether the ball has made impact with the border.
     */
    private boolean impactBorder(){
        Point2D p = wall.ballGetter().getPosition();
        return ((p.getX() < wall.areaGetter().getX()) ||(p.getX() > (wall.areaGetter().getX() + wall.areaGetter().getWidth())));
    }
    
    /**
     * Constructs the brick by taking in 3 input parameters: the coordinates, the size, and the type of bricks.
     * @param point The coordinates of the brick.
     * @param size The size of the brick.
     * @param type The type of brick.
     * @return Returns the type of brick based on the input brick type.
     */
    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out; // Output of the brick
        switch(type){
            case Wall.CLAY:
                out = new ClayBrick(point,size);
                break;
            case Wall.STEEL:
                out = new SteelBrick(point,size);
                break;
            case Wall.CEMENT:
                out = new CementBrick(point, size);
                break;
            case Wall.GRAPHITE:
                out = new GraphiteBrick(point, size);
                break;
            case Wall.VIBRANIUM:
            	out = new VibraniumBrick(point, size);
            	break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }
    
    /**
     * A function that resets the ball's coordinate back to the original position if the player dies. (BallCount > 0)
     */
    public void ballReset(){
        wall.playerControllerGetter().moveTo(wall.startPointGetter());
        wall.ballGetter().moveTo(wall.startPointGetter());
        int speedX,speedY;
        do{
        	speedX = wall.rndGetter().nextInt(5) - 2;
        }while(speedX == 0);
        do{
        	speedY = -5;
        }while(speedY == 0);

        /* Sets the speed of ball and resets the counter */
        wall.ballGetter().setSpeed(speedX,speedY);
        wall.ballLostSetter(false); // if (ballLost == true) means you lost a life, else you're still alive
    }
    
    /**
     * A function that resets the bricks the moment the level has been cleared.
     */
    public void wallReset(){
        for(Brick b : wall.bricksGetter())
            b.repair();
        wall.brickCountSetter(wall.bricksGetter().length);
        wall.ballCountSetter(3);
    }
    
    /**
     * A function to check whether the ball count is 0 or not.
     * @return A boolean that returns true if the number of ball is 0.
     */
    public boolean ballEnd(){
        return (wall.ballCountGetter() == 0);
    }

    /**
     * A function to check whether the brick count is 0 or not.
     * @return A boolean that returns true if the number of brick is 0.
     */
    public boolean isDone(){
    	return (wall.brickCountGetter() == 0);
    }

    /**
     * Setter method for the ball's horizontal speed.
     * @param s The horizontal speed of the ball to be set.
     */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    /**
     * Setter method for the ball's vertical speed.
     * @param s The vertical speed of the ball to be set.
     */
    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    /**
     * A function that resets the number of ball count back to 3. (maximum life count)
     */
    public void resetBallCount(){
        wall.ballCountSetter(3);
    }
	
}
