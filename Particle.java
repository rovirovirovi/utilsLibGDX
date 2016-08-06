package com.vali.lib;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.vali.game.MyGdxGame;


public class Particle {

	public float lifeTime = 1f;
	float timer_destroy = 0;
	public boolean fade, scaleDown;
	float alpha = 1f;
	boolean alive = true;
	float initScale = 1;
	
	Texture tex;
	public float x, y, scale = 1, angularVelocity, angularDrag, rotation;
	public Vector2 velocity, drag;
	boolean flipX, flipY;
	public Particle(float x, float y, String path) {
		this.x = x;
		this.y = y;
		tex = MyGdxGame.assetManager.get(path, Texture.class);
		drag = new Vector2();
		velocity = new Vector2();
	}
	public Particle(float x, float y, String path, float velocityX, float velocityY) {
		this.x = x;
		this.y = y;
		tex = MyGdxGame.assetManager.get(path, Texture.class);
		drag = new Vector2();
		velocity = new Vector2();
		velocity.x = velocityX;
		velocity.y = velocityY;
	}
	public Particle(float x, float y, String path, float velocityX, float velocityY, float dragX, float dragY) {
		this.x = x;
		this.y = y;
		tex = MyGdxGame.assetManager.get(path, Texture.class);
		drag = new Vector2();
		velocity = new Vector2();
		velocity.x = velocityX;
		velocity.y = velocityY;
		drag.x = dragX;
		drag.y = dragY;
	}
	public void update(){
		updateVelocity();
		updateUtils();
	}
	public void setLifeTime(float time){
		lifeTime = time;
	}
	public void startFade(){
		fade = true;
	}
	public void startScale(){
		scaleDown = true;
	}
	public void setScale(float scale){
		this.scale = scale;
		initScale = scale;
	}
	void updateUtils(){
		timer_destroy += Gdx.graphics.getDeltaTime();
		
		if(fade){
			alpha = MathUtils.lerp(1f,0f,timer_destroy / lifeTime);
		}
		if(scaleDown){
			scale = MathUtils.lerp(initScale,0f,timer_destroy / lifeTime);
		}
		if(timer_destroy >= lifeTime)
			alive = false;
	}
	void updateVelocity(){
		float d = drag.x * Gdx.graphics.getDeltaTime();
		if(velocity.x - d > 0)
			velocity.x -= d;
		else if(velocity.x + d < 0)
			velocity.x += d;
		else
			velocity.x = 0;
		
		d = drag.y * Gdx.graphics.getDeltaTime();
		if(velocity.y - d > 0)
			velocity.y -= d;
		else if(velocity.y + d < 0)
			velocity.y += d;
		else
			velocity.y = 0;
		
		x += velocity.x * Gdx.graphics.getDeltaTime();
		y += velocity.y * Gdx.graphics.getDeltaTime();
		
		d = angularDrag * Gdx.graphics.getDeltaTime();
		if(angularVelocity - d > 0)
			angularVelocity -= d;
		else if(angularVelocity + d < 0)
			angularVelocity += d;
		else
			angularVelocity = 0;
		
		rotation += angularVelocity * Gdx.graphics.getDeltaTime();
	}
	public void draw(SpriteBatch sb){
		if(tex != null)
		{
			
			sb.setColor(1,1,1,alpha);
			sb.draw(tex, x, y, tex.getWidth() / 2, tex.getHeight() / 2, tex.getWidth(), tex.getHeight(), scale, scale, rotation, 0, 0, tex.getWidth(), tex.getHeight(), flipX, flipY);
			sb.setColor(1,1,1,1);
		}
	}
}
