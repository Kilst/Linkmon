package com.linkmon.view.particles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class ParticleLoader {
	
	private ParticlePool starPool;
	private ParticlePool greenOrangeStarPool;
	private ParticlePool greenStarPool;
	private ParticlePool orangeStarPool;
	private ParticlePool flamePool;
	private ParticlePool rockPool;
	private ParticlePool meatPool;
	private ParticlePool electricAttackPool;
	private ParticlePool fireAttackPool;
	private ParticlePool attackPool;
	private ParticlePool greenAttackPool;
	private ParticlePool healPool;
	private ParticlePool deathPool;
	private ParticlePool tapPool;
	private ParticlePool bubblesPool;
	private ParticlePool fireBallPool;
	private ParticlePool tornadoPool;
	private ParticlePool fastIcePool;
	private ParticlePool waterJetPool;
	private ParticlePool leafBomb;
	private ParticlePool seedCannonPool;
	private ParticlePool bouncePool;
	private ParticlePool sleepPool;
	private ParticlePool darknessPool;
	
	public ParticleLoader() {
		starPool = new ParticlePool(ParticleIds.STAR);
		greenOrangeStarPool = new ParticlePool(ParticleIds.GREEN_ORANGE_STAR);
		greenStarPool = new ParticlePool(ParticleIds.GREEN_STAR);
		orangeStarPool = new ParticlePool(ParticleIds.ORANGE_STAR);
		flamePool = new ParticlePool(ParticleIds.FLAME);
		rockPool = new ParticlePool(ParticleIds.ROCK);
		meatPool = new ParticlePool(ParticleIds.MEAT);
		electricAttackPool = new ParticlePool(ParticleIds.ELECTRIC_ATTACK);
		fireAttackPool = new ParticlePool(ParticleIds.FIRE_ATTACK);
		attackPool = new ParticlePool(ParticleIds.DAMAGED);
		greenAttackPool = new ParticlePool(ParticleIds.GREEN_ATTACK);
		healPool = new ParticlePool(ParticleIds.HEAL);
		deathPool = new ParticlePool(ParticleIds.DEATH);
		tapPool = new ParticlePool(ParticleIds.TAP);
		bubblesPool = new ParticlePool(ParticleIds.BUBBLES);
		fireBallPool = new ParticlePool(ParticleIds.FIREBALL);
		tornadoPool = new ParticlePool(ParticleIds.TORNADO);
		fastIcePool = new ParticlePool(ParticleIds.FAST_ICE);
		waterJetPool = new ParticlePool(ParticleIds.WATER_JET);
		leafBomb = new ParticlePool(ParticleIds.LEAF_BOMB);
		seedCannonPool = new ParticlePool(ParticleIds.SEED_CANNON);
		bouncePool = new ParticlePool(ParticleIds.BOUNCE);
		sleepPool = new ParticlePool(ParticleIds.SLEEP);
		darknessPool = new ParticlePool(ParticleIds.DARKNESS);
	}
	
	private ParticlePool getPoolFromId(int id) {
		switch(id) {
			case(ParticleIds.TAP) : {
				return tapPool;
			}
			case(ParticleIds.SLEEP) : {
				return sleepPool;
			}
			case(ParticleIds.BOUNCE) : {
				return bouncePool;
			}
			case(ParticleIds.LEAF_BOMB) : {
				return leafBomb;
			}
			case(ParticleIds.SEED_CANNON) : {
				return seedCannonPool;
			}
			case(ParticleIds.WATER_JET) : {
				return waterJetPool;
			}
			case(ParticleIds.BUBBLES) : {
				return bubblesPool;
			}
			case(ParticleIds.FIREBALL) : {
				return fireBallPool;
			}
			case(ParticleIds.FAST_ICE) : {
				return fastIcePool;
			}
			case(ParticleIds.STAR) : {
				return starPool;
			}
			case(ParticleIds.GREEN_ORANGE_STAR) : {
				return greenOrangeStarPool;
			}
			case(ParticleIds.GREEN_STAR) : {
				return greenStarPool;
			}
			case(ParticleIds.ORANGE_STAR) : {
				return orangeStarPool;
			}
			case(ParticleIds.FLAME) : {
				return flamePool;
			}
			case(ParticleIds.ROCK) : {
				return rockPool;
			}
			case(ParticleIds.MEAT) : {
				return meatPool;
			}
			case(ParticleIds.ELECTRIC_ATTACK) : {
				return electricAttackPool;
			}
			case(ParticleIds.FIRE_ATTACK) : {
				return fireAttackPool;
			}
			case(ParticleIds.DAMAGED) : {
				return attackPool;
			}
			case(ParticleIds.GREEN_ATTACK) : {
				return greenAttackPool;
			}
			case(ParticleIds.HEAL) : {
				return healPool;
			}
			case(ParticleIds.DEATH) : {
				return deathPool;
			}
			case(ParticleIds.TORNADO) : {
				return tornadoPool;
			}
		}
		
		return null;
	}
	
	public void addParticle(int particleId, float x, float y) {
		
		ParticlePool pool = getPoolFromId(particleId);
		
		ParticleEffect particleEffect;
		particleEffect = pool.particlePool.obtain();
//        ParticleFactory.getParticleFromId(particleId, particleEffect);
        particleEffect.setPosition(x, y);
        particleEffect.start();
        pool.activeParticles.add(particleEffect);
	}
	
	public void render(Batch batch) {
		starPool.render(batch);
		greenOrangeStarPool.render(batch);
		greenStarPool.render(batch);
		orangeStarPool.render(batch);
		flamePool.render(batch);
		rockPool.render(batch);
		meatPool.render(batch);
		electricAttackPool.render(batch);
		fireAttackPool.render(batch);
		attackPool.render(batch);
		deathPool.render(batch);
		greenAttackPool.render(batch);
		healPool.render(batch);
		tapPool.render(batch);
		bubblesPool.render(batch);
		fireBallPool.render(batch);
		tornadoPool.render(batch);
		fastIcePool.render(batch);
		waterJetPool.render(batch);
		leafBomb.render(batch);
		seedCannonPool.render(batch);
		bouncePool.render(batch);
		sleepPool.render(batch);
		darknessPool.render(batch);
	}
}
