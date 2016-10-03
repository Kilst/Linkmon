package com.linkmon.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.linkmon.model.World;
import com.linkmon.model.WorldRenderingComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.view.particles.ParticleLoader;

public class LibgdxWorldRenderer {
	
	private World world;
	private ParticleLoader pLoader;
	
	public LibgdxWorldRenderer(World world) {
		this.world = world;
		
		world.addRenderer(new WorldRenderingComponent(world));
	}
	
	public void render(Batch batch) {
		
		((WorldRenderingComponent)world.getRenderer()).draw(batch, null);
		
		for(GameObject object : world.getObjects()) {
			((LibgdxRenderingComponent)object.getRenderer()).draw(batch, object);
		}
		
		pLoader.render(batch);
		
		((WorldRenderingComponent)world.getRenderer()).drawLight(batch, null);
	}

	public void addParticleLoader(ParticleLoader pLoader) {
		// TODO Auto-generated method stub
		this.pLoader = pLoader;
	}

}
