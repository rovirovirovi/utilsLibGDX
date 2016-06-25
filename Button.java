package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.vali.game.MyGdxGame;

public class Button implements InputProcessor{

	public float x, y, width, height;
	Texture tex;
	Camera cam;
	public Vector3 vec;
	ButtonCallback callback;
	float scaleX = 1f, scaleY = 1f, rotation = 0f;
	public boolean enabled = true;
	
	public Button(float x, float y, String path, Camera cam, ButtonCallback cb){
		tex = MyGdxGame.assetManager.get(path,Texture.class);
		this.x = x;
		this.y = y;
		width = tex.getWidth();
		height = tex.getHeight();
		this.cam = cam;
		callback = cb;
		Gdx.input.setInputProcessor(this);
		vec = new Vector3(0,0,0);
	}
	public void setCamera(Camera cam){
		this.cam = cam;
	}
	public void update(){
		
	}
	public void draw(SpriteBatch sb){
		if(enabled)
			sb.draw(tex,x,y,width / 2, height / 2,(float)tex.getWidth(), (float)tex.getHeight(),scaleX,scaleY,rotation,0,0,tex.getWidth(),tex.getHeight(),false,false);
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
				scaleX = 1f;
				scaleY = 1f;
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
