package com.linkmon.componentmodel.gameobject;

import java.util.List;

import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.components.IExtraComponents;
import com.linkmon.componentmodel.components.IInputComponent;
import com.linkmon.componentmodel.components.IPhysicsComponent;
import com.linkmon.componentmodel.components.IRenderingComponent;
import com.linkmon.componentmodel.linkmon.poop.PoopInputComponent;
import com.linkmon.eventmanager.input.InputListener;
import com.linkmon.model.gameobject.Direction;

public class GameObject {
	
	private int id;
	
	private int type;
	
	private World world;
	
	private IRenderingComponent renderingComponent;
	private IInputComponent inputComponent;
	private IPhysicsComponent physicsComponent;
	
	private IExtraComponents extraComponents;
	
	private float x;
	private float y;
	
	private float width = 0;
	private float height = 0;
	
	private AABB aabb;
	
	public int direction = Direction.LEFT;
	public boolean isMoving = false;
	
	private String name;
	
	public GameObject(int id, int type, IRenderingComponent renderingComponent, IInputComponent inputComponent,
			IPhysicsComponent physicsComponent, IExtraComponents extraComponents) {
		this.id = id;
		this.type = type;
		this.x = 0;
		this.y = 0;
		
		this.renderingComponent = renderingComponent;
		this.inputComponent = inputComponent;
		this.physicsComponent = physicsComponent;
		
		this.extraComponents = extraComponents;
	}
	
	public void update(List<GameObject> objects) {
		
//		if(width > 0 && height > 0) {
//			createAABB();
//			aabb.update(this);
//		}
		updateInput();
		updatePhysics(objects);
		updateRendering();
		updateExtras();
	}
	
	public void updateInput() {
		if(inputComponent != null)
			inputComponent.update(this);
	}
	
	public void updatePhysics(List<GameObject> objects) {
		if(physicsComponent != null) {
			physicsComponent.update(this, objects);
			if(aabb != null)
				aabb.update(this);
		}
	}
	
	public void updateRendering() {
		if(renderingComponent != null)
			renderingComponent.update(this);
	}
	
	public void updateExtras() {
		if(extraComponents != null)
			extraComponents.update(this);
	}
	
	public IRenderingComponent getRenderer() {
		return renderingComponent;
	}
	
	public void setRenderer(IRenderingComponent renderer) {
		renderingComponent = renderer;
	}
	
	public IExtraComponents getExtraComponents() {
		// TODO Auto-generated method stub
		return extraComponents;
	}
	
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDirection(int direction) {
		// TODO Auto-generated method stub
		this.direction = direction;
	}

	public IPhysicsComponent getPhysicsComponent() {
		// TODO Auto-generated method stub
		return physicsComponent;
	}

	public void addWorld(World world) {
		// TODO Auto-generated method stub
		this.world = world;
	}
	
	public World getWorld() {
		// TODO Auto-generated method stub
		return world;
	}

	public IInputComponent getInputComponent() {
		// TODO Auto-generated method stub
		return inputComponent;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public AABB getAabb() {
		createAABB();
		return aabb;
	}

	private void createAABB() {
		// TODO Auto-generated method stub
		if(aabb == null)
			aabb = new AABB(x, y, width, height);
	}

	public void addInputComponent(IInputComponent inputComponent) {
		// TODO Auto-generated method stub
		this.inputComponent = inputComponent;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
