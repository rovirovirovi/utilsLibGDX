package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

public class ButtonInputManager implements InputProcessor{

	Camera cam;
	Vector3 vec;
	float scaleX, scaleY, x, y, width, height;
	ButtonCallback callback;
	public void setValues(Vector3 vec3, float x, float y, float width, float height, float scaleX, float scaleY, ButtonCallback callback, Camera cam){
		vec = vec3;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.callback = callback;
		this.cam = cam;
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			cam.unproject(vec.set(Gdx.input.getX(0), Gdx.input.getY(0),0));
			if(vec.x >= x && vec.x <= x + width && vec.y >= y && vec.y <= y + height){
				scaleX = .75f;
				scaleY = .75f;
			}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		cam.unproject(vec.set(Gdx.input.getX(0), Gdx.input.getY(0),0));
		if(vec.x >= x && vec.x <= x + width && vec.y >= y && vec.y <= y + height){
			scaleX = 1;
			scaleY = 1;
			callback.callback();
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		cam.unproject(vec.set(Gdx.input.getX(0), Gdx.input.getY(0),0));
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
