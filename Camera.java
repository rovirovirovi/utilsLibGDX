package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.vali.game.MyGdxGame;

public class Camera {
	
	public OrthographicCamera cam;
	public float x;
	public float y;
	
	Vector2 pos;
	Vector2 targetPos;
	
	float offsetX;
	float offsetY;
	public float rotation = 0;
	float tarX, tarY, speed;
	public boolean canMove = true;
	float shakeVelocity = 0;
	
	public boolean inLerp;
	private float lerpTargetX, lerpTargetY;
	Callback lerpCallback;
	float lerpTime;
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public void setX(float x){
		this.x = x;
		this.pos.x = x;
	}
	public void setY(float y){
		this.y = y;
		this.pos.y = y;
	}
	
	public Camera(float w, float h, float scale){
		cam = new OrthographicCamera(w,h);
		cam.setToOrtho(false,w, h);
		pos = new Vector2();
		targetPos = new Vector2();
		x = 0;
		y = 0;
		cam.position.set(x, y, 0);
		setZoom(4);
		cam.update();
	}
	public void shake(float intensity){
		shakeVelocity = intensity;
	}
	public void setOffsetX(float offset){
		offsetX = offset;
	}
	public void setOffsetY(float offset){
		offsetY = offset;
	}
	public void follow(boolean simpleFollow, Entity target){
		x = target.origin.x;
		y = target.origin.y;
	}
	public void followAdvanced(Entity target){
		targetPos.x = target.x + target.width/2;
		targetPos.y = target.y + target.height / 2;
		pos = pos.lerp(targetPos, Gdx.graphics.getDeltaTime() * 30);
	}
	public void lerpPos(float targetX, float targetY, float time, Callback cb){
		inLerp = true;
		lerpTargetX = targetX;
		lerpTargetY = targetY;
		lerpTime = time;
		lerpCallback = cb;
	}
	private void handleLerp(){
		if(inLerp){
			x = Utils.lerp(x, lerpTargetX, lerpTime * Gdx.graphics.getDeltaTime());
			y = Utils.lerp(y, lerpTargetY, lerpTime * Gdx.graphics.getDeltaTime());
			if(Math.abs(x) - Math.abs(lerpTargetX) < 5 && Math.abs(y) - Math.abs(lerpTargetY) < 5){
				inLerp = false;
				if(lerpCallback != null)
					lerpCallback.callback();
			}
		}
	}
	public void setPosition(float x, float y){
		setX(x);
		setY(y);
	}
	public void restrictToBounds(float x, float y, float x2, float y2){
		this.x = MathUtils.clamp(this.x, x + (MyGdxGame.VIRTUAL_WIDTH / 2) / getZoom(), x2 - (MyGdxGame.VIRTUAL_WIDTH / 2) / getZoom());
		this.y = MathUtils.clamp(this.y, y + (MyGdxGame.VIRTUAL_HEIGHT / 2) / getZoom(), y2 - (MyGdxGame.VIRTUAL_HEIGHT / 2) / getZoom());
	}
	public Vector3 unproject(Vector3 input){
		return cam.unproject(input);
	}
	
	public void update(){
		offsetX = MathUtils.random(-shakeVelocity,shakeVelocity);
		offsetY = MathUtils.random(-shakeVelocity,shakeVelocity);
		shakeVelocity -= Gdx.graphics.getDeltaTime() * 10;
		shakeVelocity = MathUtils.clamp(shakeVelocity, 0, 100);
		
		float decX = x - (int)x;
		float decY = y - (int)y;
		
			
			handleLerp();
			cam.position.set(pos.x + offsetX, pos.y + offsetY,0);
			
			x = pos.x;
			y = pos.y;
		cam.update();
	}
	
	
	public void setRotation(float angle){
		cam.up.set(0,1,0);
		cam.direction.set(0,0,-1);
		cam.rotate(angle);
		rotation = angle;
	}
	
	public void rotate(float angle){
		cam.rotate(angle);
		rotation += angle;
	}
	
	public void setZoom(float ZOOM){
		cam.zoom = 1f/ZOOM;
	}
	public float getZoom(){
		return (float)(1f/(float)cam.zoom);
	}
	public boolean inView(Entity e){
		return e.x >= x - cam.viewportWidth / 2 && e.x + e.width <= x + cam.viewportWidth / 2 &&
			   e.y >= y - cam.viewportHeight / 2 && e.y + e.height <= y + cam.viewportHeight / 2;
	}
	public void enableMovement(){
		canMove = true;
	}
	public void disableMovement(){
		canMove = false;
	}
}
