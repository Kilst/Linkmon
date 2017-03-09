package com.linkmon.model.aonevonebattle;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.linkmon.model.components.IExtraComponents;
import com.linkmon.model.components.IPhysicsComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;

public class OneVBattlePhysicsComponent implements IPhysicsComponent {
	
	private int homeX = -1;
	private int homeY = -1;
	
	private float velocity = 20;
	
	private float veloX = 0;
	private float veloY = 0;
	
	private float targetX = 0;
	private float targetY = 0;
	
	private float gravity = 0.9f;
	private boolean useGravity = false;
	private boolean updating = true;
	
	private int dashToX;
	private int dashToY;
	
	private boolean dashing = false;
	
	private GameObject object;
	
	private int directionX;
	private int directionY;
	
	public float angle;
	
	private double gradient;
	
	float distance;

//	@Override
//	public void update(GameObject object) {
//		// TODO Auto-generated method stub
//		checkDashPosition(object);
//	}
	
	public void setGravity(boolean useGravity) {
		this.useGravity = useGravity;	
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setAngle(float targetX, float targetY, GameObject move)
	{
		
		double dx;
		double dy;
		
	    dx = targetX - move.getX();
		// Minus to correct for coord re-mapping
	    dy = targetY - move.getY();

	    double inRads = Math.atan2(dy,dx);

	    // We need to map to coord system when 0 degree is at 3 O'clock, 270 at 12 O'clock
	    if (inRads < 0)
	        inRads = Math.abs(inRads);
	    else
	        inRads = 2*Math.PI - inRads;

	    angle = (float)Math.toDegrees(inRads);
	    
	    gradient = (dy/dx);
	    
	    double grad = Math.tan(inRads);
	    
		dx = Math.pow(move.getX() + move.getWidth()/2 - targetX, 2);

	    dy = Math.pow(move.getY()+move.getHeight()/2 - targetY, 2);
	    
	    distance = (float) Math.sqrt(dx-dy);
	    
	    distance = Vector2.dst(move.getX(), move.getY(), targetX, targetY);
	    
	    Gdx.app.log("OneVBattlePhysics", "Angle: " + angle + "  Gradient: " + gradient + "  Gradient: " + grad + " Distance: " + distance + " Target Y: " + targetY);
	}
	
	public void applyTrajectory(float distanceX, float trajectoryAngle) {
		
//		distanceX = Math.abs(distanceX);
		  Gdx.app.log("OneVBattlePhysics", "Distance: " + distanceX);
		angle = trajectoryAngle;
		
		velocity = (float) Math.sqrt((gravity*distanceX)/Math.sin(2*angle));
		
		veloX = (float) ((velocity)*Math.cos(Math.toRadians(angle)));
		veloY = (float) ((velocity*-1)*Math.sin(Math.toRadians(angle)));
	}
	
	public void setTarget(float x, float y) {
		targetX = x;
		targetY = y;
	}
	
	public void setHomePositions(GameObject object) {
//		if(homeX == -1)
			homeX = (int) object.getX();
//		if(homeY == -1)
			homeY = (int) object.getY();
	}
	
	private void checkDashPosition(GameObject object) {
		
		if(dashing) {
			// Move object
			object.setX((float) (object.getX()+((velocity)*Math.cos(Math.toRadians(angle)))));
			object.setY((float) (object.getY()+((velocity*-1)*Math.sin(Math.toRadians(angle)))));
			Gdx.app.log("BattleLinkmonRendering", "Angle: " + angle);
		
			// Check if xPos is >= dashToX. Multiply by direction to normalise since right = 1, left = -1
			if(object.getX()*directionX >= dashToX*directionX) {
				object.setX(dashToX);
			}
			if(object.getY()*directionY >= dashToY*directionY) {
				object.setY(dashToY);
			}
			
			if(object.getY() == dashToY && object.getX() == dashToX) {
				dashing = false;
				Gdx.app.log("BattleLinkmonRendering", "Ended");
			}
		}
	}
	
	public void dashTo(int x, int y, GameObject object, int velocity) {
		// Set end position
		this.velocity = velocity;
		dashToX = x;
		dashToY = y;
		dashing = true;
		// Check which direction based on x positions
		if(x < object.getX())
			directionX = Direction.LEFT;
		else
			directionX = Direction.RIGHT;
		
		if(y < object.getY())
			directionY = Direction.DOWN;
		else
			directionY = Direction.UP;
		
		//Set angle
		setAngle(dashToX, dashToY, object);
	}
	
	public int getDashToX() {
		return dashToX;
	}

	public int getHomeX() {
		return homeX;
	}

	public int getHomeY() {
		return homeY;
	}

	@Override
	public void update(GameObject object, List<GameObject> objects) {
		// TODO Auto-generated method stub
		this.object = object;
		checkDashPosition(object);
		if(useGravity)
			applyVelocity();
	}
	
	public void applyVelocity() {
		
		if(updating) {
			if(this.targetX == object.getX() || this.targetY >= object.getY())
				updating = false;
			
			object.setX((float) (object.getX()+(veloX)));
			object.setY((float) (object.getY()+(veloY)));
			
			if(useGravity) {
				veloY-=gravity;
			}
		}
	}

	@Override
	public void move(GameObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMoveTo(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMoveToX(float x) {
		// TODO Auto-generated method stub
		
	}

}
