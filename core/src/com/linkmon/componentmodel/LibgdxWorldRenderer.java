package com.linkmon.componentmodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.libgdx.LibgdxAnimationComponent;
import com.linkmon.componentmodel.libgdx.LibgdxRenderingComponent;
import com.linkmon.componentmodel.libgdx.LinkmonAnimationComponent;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;

public class LibgdxWorldRenderer {
	
	private World world;
	
	public LibgdxWorldRenderer(World world) {
		this.world = world;
	}
	
	public void render(Batch batch) {
		
		world.update();
		
		if(world.renderer == null)
			world.renderer = new LibgdxRenderingComponent();
		
		((LibgdxRenderingComponent)world.renderer).draw(batch, null);
		
		for(GameObject object : world.getObjects()) {
			((LibgdxRenderingComponent)object.getRenderer()).draw(batch, object);
		}
	}

}
