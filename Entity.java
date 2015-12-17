package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Entity {
	
	public Texture tex;
	public float x;
	public float y;
	public Vector2 acceleration;
	public Vector2 velocity;
	public Vector2 drag;
	public Vector2 maxVelocity;
	public Vector2 origin;
	public float width;
	public float height;
	public float rotation;
	public float offsetX;
	public float offsetY;
	
	// NOT TO BE CONFUSED WITH ORIGIN.Y / ORIGIN.Y
	public float centerX; 
	public float centerY;
	
	public boolean col_top;
	public boolean col_bot;
	public boolean col_left;
	public boolean col_right;
	
	// DONT MODIFY THESE //
	public static int UP = 0;
	public static int RIGHT = 1;
	public static int DOWN = 2;
	public static int LEFT = 3;
	// END OF RESTRICTION //
	
	
	Animation currentAnimation;
	private boolean _flipX;
	private boolean _flipY;
	private float _scaleX;
	private float _scaleY;
	String name;
	public String tag;
	
	public Entity(float XX,float YY, String path, float spriteWidth, float spriteHeight){
		x = XX;
		y = YY;
		
		tex = new Texture(Gdx.files.internal(path));
		
		acceleration = new Vector2();
		
		velocity = new Vector2();
		
		_scaleX = 1;
		_scaleY = 1;
		
		drag = new Vector2();
		
		maxVelocity = new Vector2();
		
		width = spriteWidth;
		height = spriteHeight;
		
		
		origin = new Vector2();
		origin.x = x + width / 2;
		origin.y = y + height / 2;
		
		centerX = 0;
		centerY = 0;
		
		col_bot = false;
		col_top = false;
		col_right = false;
		col_left = false;
		
		_flipX = true;
		_flipY = false;
		
	}
	public void update(){
		updateMotion();
		updateAnimation();
	}
	public void updateSelf(){
		updateMotion();
		updateAnimation();
	}
	protected void updateAnimation(){
		if(currentAnimation != null){
			currentAnimation.update();
		}
	}
	protected void updateMotion(){
		velocity.x = MathUtils.clamp(velocity.x, -maxVelocity.x, maxVelocity.x);
		velocity.y = MathUtils.clamp(velocity.y, -maxVelocity.y, maxVelocity.y);
		
		float d = 0;
		d = drag.x * Gdx.graphics.getDeltaTime();
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
		
		if(velocity.y != 0)
		{
			col_bot = false;
		}
		origin.x = x + width / 2;
		origin.y = y + height / 2;
	}
	public void simpleCollision(Entity a, Entity b, float dx, float dy) {
		
		if(Math.abs(dy) < Math.abs(dx) && dy != 0){
			velocity.y = 0;
			y += dy;
				if(dy < 0)
				{
					col_top = true;
					col_bot = false;
					
				}
				else
				{
					col_top = false;
					col_bot = true;
				}
		}
		
		
		if(Math.abs(dx) < Math.abs(dy) && dx != 0){
			velocity.x = 0;
			x += dx;
				if(dx < 0)
				{
					col_right = true;
					col_left = false;
				}
				else
				{
					col_right = false;
					col_left = true;
				}
		}
		
	}
	public void setFacingX(boolean FLIP){
		_flipX = FLIP;
	}
	public void setFacingY(boolean FLIP){
		_flipY = FLIP;
	}
	public void playAnimation(Animation anim){
		if(currentAnimation != anim)
		{
			currentAnimation = anim;
		}
	}
	public void setScaleX(float scale){
		_scaleX = scale;
	}
	public void setScaleY(float scale){
		_scaleY = scale;
	}
	public float getScaleX(){
		return _scaleX;
	}
	public float getScaleY(){
		return _scaleY;
	}
	
	public void drawSelf(SpriteBatch sb){
		if(currentAnimation != null)
			sb.draw(tex, x, y, (float)currentAnimation.getSpriteWidth() / 2, (float)currentAnimation.getSpriteHeight() / 2, (float)currentAnimation.getSpriteWidth(), (float)currentAnimation.getSpriteHeight(), _scaleX, _scaleY, rotation, currentAnimation.getSpriteFrame(), 0, (int)currentAnimation.getSpriteWidth(), (int)currentAnimation.getSpriteHeight(), _flipX, _flipY);
		else{
			sb.draw(tex,x,y,centerX, centerY,(float)tex.getWidth(), (float)tex.getHeight(),_scaleX,_scaleY,rotation,0,0,tex.getWidth(),tex.getHeight(),_flipX,_flipY);
		}
	}
	public void draw(SpriteBatch sb){
		drawSelf(sb);
	}
	public static Animation addAnimation(Texture tex, int[] FRAMES, int speed, boolean LOOP, int spriteWidth, int spriteHeight){
		return new Animation(tex,FRAMES,speed,LOOP,spriteWidth, spriteHeight,null);
	}
}
