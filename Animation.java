package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	
	public int currentFrame;
	private int[] frames;
	private float speed;
	private int sprWidth;
	private int sprHeight;
	private boolean loop;
	
	public TextureRegion tr;
	
	private float timer;
	Callback callback;
	
	public Animation( Texture TEX, int[] FRAMES, float SPEED, boolean LOOP,int sw,int sh, Callback callback){
		currentFrame = 0;
		loop = LOOP;
		frames = FRAMES;
		speed = SPEED;
		sprWidth = sw;
		sprHeight = sh;
		this.callback = callback;
	}
	
	public void update(){
		timer += Gdx.graphics.getDeltaTime() * speed;
		if(timer >= 1f){
			timer = 0;
			if(currentFrame >= frames.length - 1)
			{
				if(loop == true)
					currentFrame = 0;
				if(callback != null)
					callback.callback();
			}
			else
				currentFrame++;
			
		}
	}
	
	public void setSpeed(float f){
		this.speed = f;
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
