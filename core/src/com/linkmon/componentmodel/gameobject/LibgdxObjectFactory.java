package com.linkmon.componentmodel.gameobject;

import com.linkmon.componentmodel.libgdx.LibgdxRenderingComponent;
import com.linkmon.componentmodel.libgdx.LinkmonAnimationComponent;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.LinkmonPhysicsComponent;

public class LibgdxObjectFactory implements IGameObjectFactory {
	
	public GameObject createLinkmon(int id) {
		GameObject linkmon = new GameObject(0, 1, new LibgdxRenderingComponent(), null,
				new LinkmonPhysicsComponent(), new LinkmonExtraComponents());
		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon));
		return linkmon;
	}
	
	public GameObject getObjectFromId(int id) {
		switch(id) {
			case 3 : {
				GameObject poop = new GameObject(id, 0, new LibgdxRenderingComponent(), null, null, null);
				((LibgdxRenderingComponent)poop.getRenderer()).setSprite(poop);
				return poop;
			}
		}
		return null;
	}
}
