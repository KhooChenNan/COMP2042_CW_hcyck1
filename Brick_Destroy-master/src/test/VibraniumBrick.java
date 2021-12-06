package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.Random;

public class VibraniumBrick extends Brick {

	/* Never cracks and has 3 lives. Probability of reducing life is 30%. */
	
	private static final String NAME = "Vibranium Brick";
	private static final Color DEF_INNER = new Color(102, 78, 174);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int VIBRANIUM_STRENGTH = 3;
    private static final double VIBRANIUM_PROBABILITY = 0.3;

    private Random rnd;
    private Shape brickFace;	
	
	public VibraniumBrick(Point point, Dimension size) {
		super(NAME, point, size, DEF_BORDER, DEF_INNER, VIBRANIUM_STRENGTH);
		rnd = new Random();
		brickFace = super.brickFace;
	}

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    public Shape getBrick() {
        return brickFace;
    }

    public  boolean setImpact(Point2D point , int dir){
        if(super.isBroken()) {
        	return false;
        }
        impact();
        return  super.isBroken();
    }

    public void impact(){
        if(rnd.nextDouble() < VIBRANIUM_PROBABILITY){
            super.impact(); 
        }
    }

	
}
