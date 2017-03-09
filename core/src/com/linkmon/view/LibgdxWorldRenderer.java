package com.linkmon.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.linkmon.model.World;
import com.linkmon.model.WorldRenderingComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.view.particles.ParticleLoader;

public class LibgdxWorldRenderer {
	
	// Only 1 world at a time can loaded to be rendered
	
	// The world is updated in the gameLoop via service.update();
	
	private World world;
	private ParticleLoader pLoader;
	
	public LibgdxWorldRenderer(World world) {
		this.world = world;
	}
	
	public void render(Batch batch) {
		
		// Only render the world if it is active (gets set to inactive in the full screen UI menus)
		if(world.getActive()) {
			
			// Draw the world first
			((WorldRenderingComponent)world.getRenderer()).draw(batch, null);
			// Loop through all the objects in the world
			for(GameObject object : world.getObjects()) {
				if(((LibgdxRenderingComponent)object.getRenderer()) != null) // Check the object has a rendering component
					((LibgdxRenderingComponent)object.getRenderer()).draw(batch, object); // Draw the object
			}
			// Draw all the particles
			pLoader.render(batch);
			// Draw darkness overlay
			((WorldRenderingComponent)world.getRenderer()).drawLight(batch, null);
		}
	}

	public void addParticleLoader(ParticleLoader pLoader) {
		// TODO Auto-generated method stub
		this.pLoader = pLoader;
	}

	public void setWorld(World world) {
		// TODO Auto-generated method stub
		this.world = world;
	}

	public World getWorld() {
		// TODO Auto-generated method stub
		return world;
	}

}
