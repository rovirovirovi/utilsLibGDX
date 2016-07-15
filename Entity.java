package com.vali.lib;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.vali.game.MyGdxGame;

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
	
	public float centerX; 
	public float centerY;
	
	public State currentState;
	
	protected Animation currentAnimation;
	protected boolean _flipX;
	protected boolean _flipY;
	private float _scaleX;
	private float _scaleY;
	
	public boolean solid;
	
	public String tag;
	public boolean visible = true;
	public boolean alive;
	public int health = 1;
	
	public float currentX;
	public float currentY;
	
	public Color tint;
	
	public String name = "";
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	
	public Entity(float XX,float YY, String path){
		x = XX;
		y = YY;
		currentX = x;
		currentY = y;
		
		tag = "untagged";
		
		if(path != null){
			
			//Makes the entity use the loaded texture
			tex = MyGdxGame.assetManager.get(path, Texture.class);
		}
		else
			tex = null;
		solid = true;
		acceleration = new Vector2();
		
		velocity = new Vector2();
		
		_scaleX = 1;
		_scaleY = 1;
		
		drag = new Vector2();
		
		maxVelocity = new Vector2();
		
		if(tex == null){
			width = 16;
			height = 16;
		}
		else {
			width = tex.getWidth();
			height = tex.getWidth();
		}
		
		alive = true;
		origin = new Vector2();
		origin.x = x + width / 2;
		origin.y = y + height / 2;
		
		centerX = 0;
		centerY = 0;
		
		
		_flipX = false;
		_flipY = false;
		
		tint = Color.WHITE;
		
	}
	public AssetManager getAssetManager(){
		if(currentState != null)
			return MyGdxGame.assetManager;
		return null;
	}
	public void update(){
		updateMotion();
		updateAnimation();
	}
	public void updateSelf(){
		updateMotion();
		updateAnimation();
	}
	public void updateHealth(){
		if(health <= 0){
			removeSelf();
		}
	}
	public void removeSelf(){
		currentState.remove(this);
	}
	public void takeDamage(int value){
		health -= value;
	}
	public void takeDamage(int value, int knockbackDirection){
		health -= value;
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
		
		origin.x = x + width / 2;
		origin.y = y + height / 2;
		
		x += velocity.x * Gdx.graphics.getDeltaTime();
		y += velocity.y * Gdx.graphics.getDeltaTime();
	}
	public void CollideEntity(String TAG, Stack<Entity> stack_to_check) {
		if(Collision.place_entity(x + velocity.x * Gdx.graphics.getDeltaTime(), y ,width,height, TAG, stack_to_check)){
			
			while(!Collision.place_entity(x + Math.signum(velocity.x), y,width,height, TAG, stack_to_check))
				x += Math.signum(velocity.x);
			velocity.x = 0;
		}
		if(Collision.place_entity(x, y + velocity.y * Gdx.graphics.getDeltaTime(), width, height,TAG, stack_to_check)){
			while(!Collision.place_entity(x, y + Math.signum(velocity.y) ,width, height , TAG, stack_to_check))
				y += Math.signum(velocity.y);
			velocity.y = 0;
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
			currentAnimation.currentFrame = 0;
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
		if(tex != null){
			if(currentAnimation != null)
				sb.draw(tex, x, y, (float)currentAnimation.getSpriteWidth() / 2, (float)currentAnimation.getSpriteHeight() / 2, (float)currentAnimation.getSpriteWidth(), (float)currentAnimation.getSpriteHeight(), _scaleX, _scaleY, rotation, currentAnimation.getSpriteFrame(), 0, (int)currentAnimation.getSpriteWidth(), (int)currentAnimation.getSpriteHeight(), _flipX, _flipY);
			else{
				sb.draw(tex,x,y,width / 2, height / 2,(float)tex.getWidth(), (float)tex.getHeight(),_scaleX,_scaleY,rotation,0,0,tex.getWidth(),tex.getHeight(),_flipX,_flipY);
			}
		}
	}
	public void draw(SpriteBatch sb){
		drawSelf(sb);
	}
	public Animation addAnimation( int[] FRAMES, int speed, boolean LOOP, int spriteWidth, int spriteHeight, Callback cb){
		return new Animation(this.tex,FRAMES,speed,LOOP,spriteWidth, spriteHeight,cb);
	}
	
}
