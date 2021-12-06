package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

import test.Brick.Crack;

public class GraphiteBrick extends Brick {

	/* Has 2 lives and a probability of 0.5 chance to reduce it by 1 */
	
    private static final String NAME = "Graphite Brick";
    private static final Color DEF_INNER = new Color(153, 153, 0);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int GRAPHITE_STRENGTH = 2;
    private static final double GRAPHITE_PROBABILITY = 0.5;

    private Random rnd;
    private Crack crack;
    private Shape brickFace;
	
	public GraphiteBrick(Point point, Dimension size) {
		super(NAME, point, size, DEF_BORDER, DEF_INNER, GRAPHITE_STRENGTH);
		crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS);
		rnd = new Random();
		brickFace = super.brickFace;
	}

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    public boolean setImpact(Point2D point, int dir) {
    	if (super.isBroken()) {
    		return false;
    	}
    	impact(); // .5 chance to reduce the life by 1
    	if(!super.isBroken()) {
    		crack.makeCrack(point, dir);
    		updateBrick();
    		return super.isBroken();
    	}
    	return true;
    }

    @Override
    public Shape getBrick() {
        return brickFace;
    }

    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }
    
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
    
    public void impact(){
        if(rnd.nextDouble() < GRAPHITE_PROBABILITY){
            super.impact(); // reduces the life
        }
    }
}
