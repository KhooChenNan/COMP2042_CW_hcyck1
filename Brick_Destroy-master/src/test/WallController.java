package test;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class WallController {

	private Wall wall;
	private Ball ball;
	
	public WallController (Wall wallInput, Ball ballInput) {
		this.wall = wallInput;
		this.ball = ballInput;
	}
	
	private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
    	
    	/* It's expected that the no. of Bricks are uniformly distributed among the lines but if it's not divisible at first, they'll round down */
        brickCnt -= brickCnt % lineCnt; 
        // E.g. First level has 33 bricks and 3 lines.  Expected to be uniformly distributed but it's not
        // HOWEVER: If it's 31 bricks and 3 lines, it will then round down to 30 bricks

        /* Formula for no. of bricks on each lines */
        int brickOnLine = brickCnt / lineCnt; // E.g. If brick no. = 30 and lines = 3, each lines will have 10 bricks

        /* Attributes for the bricks (Length and Height) */
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

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
            tmp[i] = new ClayBrick(p,brickSize);
        }
        return tmp;

    }
	
	private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

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

	/* Construct the levels */
    public void makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
    	// Additions/Changes of levels made here
        Brick[][] tmp = new Brick[Wall.LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,Wall.CLAY); // First level
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,Wall.CLAY,Wall.CEMENT); // Second Level
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,Wall.CLAY,Wall.STEEL); // Third Level
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,Wall.STEEL,Wall.CEMENT); // Fourth Level
        wall.levelsSetter(tmp);
    }

    /* Movements for the player and ball */
    public void move(){
        wall.playerControllerGetter().move();
        wall.ballGetter().move();
        //ball.move();
    }

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

    /* Tests if the ball comes in contact/impact with the Wall */
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

    private boolean impactBorder(){
        Point2D p = wall.ballGetter().getPosition();
        return ((p.getX() < wall.areaGetter().getX()) ||(p.getX() > (wall.areaGetter().getX() + wall.areaGetter().getWidth())));
    }
    
    /* Constructs the brick */
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
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }
    
    /* Resets the coordinates of the ball to starting coordinates */
    public void ballReset(){
        wall.playerControllerGetter().moveTo(wall.startPointGetter());
        wall.ballGetter().moveTo(wall.startPointGetter());
        int speedX,speedY;
        do{
            speedX = wall.rndGetter().nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -wall.rndGetter().nextInt(3);
        }while(speedY == 0);

        /* Sets the speed of ball and resets the counter */
        wall.ballGetter().setSpeed(speedX,speedY);
        wall.ballLostSetter(false); // if (ballLost == true) means you lost a life, else you're still alive
    }
    
    /* Resets the bricks the moment you clear the level */
    public void wallReset(){
        for(Brick b : wall.bricksGetter())
            b.repair();
        wall.brickCountSetter(wall.bricksGetter().length);
        wall.ballCountSetter(3);
    }
    
    public boolean ballEnd(){
        return (wall.ballCountGetter() == 0);
    }

    public boolean isDone(){
    	return (wall.brickCountGetter() == 0);
    }

    /* Sets the ball's horizontal speed */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    /* Sets the ball's vertical speed */
    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    /* Resets the lives/Ball no. */
    public void resetBallCount(){
        wall.ballCountSetter(3);
    }
	
}
