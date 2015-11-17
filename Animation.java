package com.vali.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	
	public int currentFrame;
	private int[] frames;
	private int speed;
	private int sprWidth;
	private int sprHeight;
	
	public TextureRegion tr;
	
	private float timer;
	
	public Animation( Texture TEX, int[] FRAMES, int SPEED, boolean LOOP,int sw,int sh){
		currentFrame = 0;
		frames = FRAMES;
		speed = SPEED;
		sprWidth = sw;
		sprHeight = sh;
	}
	
	public void update(){
		timer += Gdx.graphics.getDeltaTime() * speed;
		if(timer >= 1f){
			timer = 0;
			if(currentFrame >= frames.length - 1)
				currentFrame = 0;
			else
				currentFrame++;
			
		}
		
		
	}
	public int getSpriteFrame(){
		return frames[currentFrame] * sprWidth;
	}
	public float getSpriteWidth(){
		return sprWidth;
	}
	public float getSpriteHeight(){
		return sprHeight;
	}
	
}
