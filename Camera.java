package com.vali.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class Camera {
	
	OrthographicCamera cam;
	float x;
	float y;
	public float rotation = 0;
	
	public Camera(float w, float h){
		cam = new OrthographicCamera(w, h);
		cam.setToOrtho(false);
		x = 0;
		y = 0;
		cam.position.set(x, y, 0);
		setZoom(1);
		cam.update();
	}
	
	public void follow(boolean simpleFollow, Entity target, float offsetX, float offsetY){
		/*
		x = (float) Math.floor((double)(target.origin.x)) - cam.viewportWidth / 4 + offsetX;
		y = (float) Math.floor((double)(target.origin.y));
		*/
		x = target.origin.x - cam.viewportWidth / 4 + offsetX;
		y = target.origin.y + offsetX;
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
	
	
	
}
