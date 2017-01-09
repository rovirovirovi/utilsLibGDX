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
	public boolean alive = true;
	float initScale = 1;
	
	Texture tex;
	public float x, y, scale = 1, angularVelocity, angularDrag, rotation;
	public Vector2 velocity, drag;
 	public boolean flipX, flipY;
	int srcX, srcWidth;
	
	boolean gravityEnabled = false;
	protected Animation currentAnimation;
	
	public Particle(float x, float y, String path) {
		this.x = x;
		this.y = y;
		tex = MyGdxGame.assetManager.get(path, Texture.class);
		drag = new Vector2();
		velocity = new Vector2();
		srcX = 0;
		srcWidth = tex.getWidth();
	}
	public Particle(float x, float y, String path, float velocityX, float velocityY) {
		this.x = x;
		this.y = y;
		tex = MyGdxGame.assetManager.get(path, Texture.class);
		drag = new Vector2();
		velocity = new Vector2();
		velocity.x = velocityX;
		velocity.y = velocityY;
		srcX = 0;
		srcWidth = tex.getWidth();
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
		srcX = 0;
		srcWidth = tex.getWidth();
	}
	public void setSrcX(int srcX){
		this.srcX = srcX;
	}
	public void setSrcWidth(int srcWidth){
		this.srcWidth = srcWidth;
	}
	public void update(){
		updateUtils();
		updateVelocity();
		updateAnimation();
	}
	public void enableGravity(boolean enabled){
		gravityEnabled = enabled;
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
		
		if(gravityEnabled)
			velocity.y -= 440 * Gdx.graphics.getDeltaTime();
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
	
	public void setFlipX(boolean flip){
		flipX = flip;
	}
	
	public void playAnimation(Animation anim){
		if(currentAnimation != anim)
		{
			currentAnimation = anim;
			currentAnimation.currentFrame = 0;
		}
	}
	public void draw(SpriteBatch sb){
		if(tex != null)
		{
			sb.setColor(1,1,1,alpha);
			if(currentAnimation != null)
				sb.draw(tex, x, y, (float)currentAnimation.getSpriteWidth() / 2, (float)currentAnimation.getSpriteHeight() / 2, (float)currentAnimation.getSpriteWidth(), (float)currentAnimation.getSpriteHeight(), scale, scale, rotation, currentAnimation.getSpriteFrame(), 0, (int)currentAnimation.getSpriteWidth(), (int)currentAnimation.getSpriteHeight(), flipX, flipY);
			else
				sb.draw(tex, x, y, srcWidth / 2, tex.getHeight() / 2, srcWidth, tex.getHeight(), scale, scale, rotation, srcX, 0, srcWidth, tex.getHeight(), flipX, flipY);
			sb.setColor(1,1,1,1);
		}
	}
	
	protected void updateAnimation(){
		if(currentAnimation != null){
			currentAnimation.update();
		}
	}
	
	public Animation addAnimation( int[] FRAMES, int speed, boolean LOOP, int spriteWidth, int spriteHeight, Callback cb){
		return new Animation(this.tex,FRAMES,speed,LOOP,spriteWidth, spriteHeight,cb);
	}
}
