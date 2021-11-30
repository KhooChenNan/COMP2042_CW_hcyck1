/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class Wall {

    private static final int LEVELS_COUNT = 4;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private Random rnd; // Random number generator 
    private Rectangle area;

    Brick[] bricks;
    Ball ball;
    Player player;

    private Brick[][] levels;
    private int level;
    private PlayerController playerController;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    /* Wall constructor */    
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

    	/* Coordinates of the starting point = the coordinates of the ball position */
        this.startPoint = new Point(ballPos);
        
        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;

        ballCount = 3; // Number of balls/lives
        ballLost = false;

        rnd = new Random(); // Initializes variable "rnd"

        makeBall(ballPos); // Calling the make ball function and makes the ball at the starting coordinates
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2; // Speed X randomly generated between 0-5 (inclusive) -  2
        }while(speedX == 0); // Makes sure that the horizontal speed of ball will never be 0 or else it'll keep randomly generating a number
        do{
            speedY = -rnd.nextInt(3); // Speed Y randomly generated between 0-3 (inclusive) and then multiplies by -1
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY); // Sets horizontal and vertical speed according to the randomly generated speed

        player = new Player((Point) ballPos.clone(),150,10, drawArea); // Initializes a new player

        playerController = new PlayerController(player);
        
        area = drawArea;


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

    /* Makes the ball */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    /* Construct the levels */
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
    	// Additions/Changes of levels made here
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY); // First level
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT); // Second Level
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL); // Third Level
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT); // Fourth Level
        return tmp;
    }

    /* Movements for the player and ball */
    public void move(){
        playerController.move();
        ball.move();
    }

    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY(); // If the player comes to contact/impact with the ball, it reverses speed Y
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
        }
        else if(impactBorder()) {
            ball.reverseX(); // Will not reverse speed Y as it's an oblique collision with the border
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }

    /* Tests if the ball comes in contact/impact with the Wall */
    private boolean impactWall(){
        for(Brick b : bricks){
            switch(b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.down, Brick.Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.up,Brick.Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.right,Brick.Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.left,Brick.Crack.LEFT);
            }
        }
        return false;
    }

    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /* Getter method for Brick no. */
    public int getBrickCount(){
        return brickCount;
    }

    /* Getter method for Ball no./Lives */
    public int getBallCount(){
        return ballCount;
    }

    /* Getter method to check whether Ball is lost */
    public boolean isBallLost(){
        return ballLost;
    }

    /* Resets the coordinates of the ball to starting coordinates */
    public void ballReset(){
        playerController.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        /* Sets the speed of ball and resets the counter */
        ball.setSpeed(speedX,speedY);
        ballLost = false; // if (ballLost == true) means you lost a life, else you're still alive
    }

    /* Resets the bricks the moment you clear the level */
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public boolean isDone(){
        return brickCount == 0;
    }

    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    public boolean hasLevel(){
        return level < levels.length;
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
        ballCount = 3;
    }

    /* Constructs the brick */
    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out; // Output of the brick
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }
    
    /* Getter methods */
    public PlayerController playerControllerGetter() {
    	return playerController;
    }

}
