package com.linkmon.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.libgdx.LibgdxRenderingComponent;

public class LibgdxWorldRenderer {
	
	private World world;
	
	public LibgdxWorldRenderer(World world) {
		this.world = world;
		
		if(world.getRenderer() == null)
			world.addRenderer(new LibgdxRenderingComponent());
	}
	
	public void render(Batch batch) {
		
		((LibgdxRenderingComponent)world.getRenderer()).draw(batch, null);
		
		for(GameObject object : world.getObjects()) {
			((LibgdxRenderingComponent)object.getRenderer()).draw(batch, object);
		}
	}

}
