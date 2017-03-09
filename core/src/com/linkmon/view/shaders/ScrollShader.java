package com.linkmon.view.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class ScrollShader {
	
	String vertexShader;
    String fragmentShader;
    ShaderProgram shaderProgram;

	float time = 0;
	
	public ScrollShader() {
		vertexShader = Gdx.files.internal("Shaders/base-vert.glsl").readString();
        fragmentShader = Gdx.files.internal("Shaders/scrolling_texture-frag.glsl").readString();
        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
        //shaderProgram.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ShaderProgram.pedantic = false;
	}
	
	public void updateScroll() {		
		if (time>40)
			time = 0;
		
		time += Gdx.graphics.getDeltaTime();
	    
	    Vector2 reso = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    
	    //feed the shader with the new data
	    shaderProgram.begin();
	    shaderProgram.setUniformf("time", -time);
	    shaderProgram.setUniformf("speed", 0.025f);
	    shaderProgram.setUniformf("scale", 1f);
	    shaderProgram.setUniformf("resolution", reso);
	    shaderProgram.end();
	}
	
	public void setShader(Batch batch) {
		batch.setShader(shaderProgram);
	}

}
