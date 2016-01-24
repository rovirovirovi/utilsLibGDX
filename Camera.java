package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Camera {
	
	public OrthographicCamera cam;
	public float x;
	public float y;
	float offsetX;
	float offsetY;
	public float rotation = 0;
	float tarX, tarY, speed;
	public boolean canMove = true;
	
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
	
	public Camera(float w, float h, float scale){
		cam = new OrthographicCamera(w,h);
		cam.setToOrtho(false,w, h);
		x = 0;
		y = 0;
		cam.position.set(x, y, 0);
		setZoom(4);
		cam.update();
	}
	public void shake(float intensity){
		offsetX = MathUtils.random(-intensity,intensity);
		offsetY = MathUtils.random(-intensity,intensity);
	}
	
	public void follow(boolean simpleFollow, Entity target){
		x = target.origin.x + offsetX;
		y = target.origin.y + offsetY;
	}
	
	public Vector3 unproject(Vector3 input){
		return cam.unproject(input);
	}
	
	public void update(){
		cam.position.set(x,y,0);
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
		rotation += angle * Gdx.graphics.getDeltaTime();
	}
	
	public void setZoom(float ZOOM){
		cam.zoom = 1f/ZOOM;
	}
	public float getZoom(){
		return (float)Math.pow(cam.zoom, -1);
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
