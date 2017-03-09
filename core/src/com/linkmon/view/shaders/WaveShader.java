package com.linkmon.view.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class WaveShader {
	
	String vertexShader;
    String fragmentShader;
    ShaderProgram shaderProgram;

	private float angleWave = 10;

	private float angleWaveSpeed = 120;

	private double PI2 = Math.PI*2;
	float time = 0;
	
	float isoX;
	float isoY;

	private float amplitudeWave = 15;
	
	public WaveShader() {
		vertexShader = Gdx.files.internal("Shaders/vertex.glsl").readString();
        fragmentShader = Gdx.files.internal("Shaders/fragment.glsl").readString();
        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
        //shaderProgram.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ShaderProgram.pedantic = false;
	}
	
	public void updateWave() {
		final float dt = Gdx.graphics.getRawDeltaTime();
		
		if (time>20)
			time = 0;
		
		time += Gdx.graphics.getDeltaTime();
		 
		angleWave += dt * angleWaveSpeed;
	    while(angleWave > PI2)
	        angleWave -= PI2;
	    
	    Vector2 reso = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    
	    //feed the shader with the new data
	    shaderProgram.begin();
	    shaderProgram.setUniformf("waveData", angleWave, amplitudeWave );
	    shaderProgram.setUniformf("time", time);
	    shaderProgram.setUniformf("speed", 0.05f);
	    shaderProgram.setUniformf("scale", 1f);
	    shaderProgram.setUniformf("resolution", reso);
	    shaderProgram.end();
	}
	
	public void setShader(Batch batch) {
		batch.setShader(shaderProgram);
	}

}
