package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vali.game.MyGdxGame;

public class Button {

	Texture tex;
	Callback callback;
	public boolean enabled = true;
	boolean pressed = false;
	public float x,y, scaleX = 1, scaleY = 1, rotation = 0;
	boolean flipX, flipY;
	private boolean inLerpPos = false, inLerpRot = false;
	private float lerpPosTargetX, lerpPosTargetY, lerpRotTarget, lerpPosTime, lerpRotTime;
	boolean isText;
	Text text;
	public Button(float x, float y, String imagePath, Callback cb){
		tex = MyGdxGame.assetManager.get(imagePath, Texture.class);
		this.x = x;
		this.y = y;
		callback = cb;
		text = null;
	}
	public Button(float x, float y, String imagePath, String text, Callback cb){
		tex = MyGdxGame.assetManager.get(imagePath, Texture.class);
		this.x = x;
		this.y = y;
		callback = cb;
		this.text = new Text(x + tex.getWidth() / 2,y + tex.getHeight() / 2, 11, text, "fonts/ARCADECLASSIC.TTF");
		this.text.SetText(text);
		this.text.center = true;
	}
	public void lerpPos(float targetX, float targetY, float time){
		inLerpPos = true;
		lerpPosTargetX = targetX;
		lerpPosTargetY = targetY;
		lerpPosTime = time;
	}
	public void lerpRot(float targetRot, float time){
		inLerpPos = true;
		lerpRotTarget = targetRot;
		lerpRotTime = time;
	}
	public void setCallback(Callback callback){
		this.callback = callback;
	}
	public void update(){
		if(!pressed)
		{
			scaleX = 1;
			scaleY = 1;
		}
		else{
			scaleX = .8f;
			scaleY = .8f;
		}
		if(text != null)
		{
			text.SetScale(scaleX);
		}
		handleLerp();
		text.x = x + tex.getWidth() / 2;
		text.y = y + tex.getHeight() / 2;
	}
	private void handleLerp(){
		if(inLerpPos){
			x = Utils.lerp(x, lerpPosTargetX, lerpPosTime * Gdx.graphics.getDeltaTime());
			y = Utils.lerp(y, lerpPosTargetY, lerpPosTime * Gdx.graphics.getDeltaTime());
			if(x == lerpPosTargetX && y == lerpPosTargetY){
				inLerpPos = false;
			}
		}
		if(inLerpRot){
			rotation = Utils.LerpDegrees(rotation, lerpRotTarget, lerpRotTime * Gdx.graphics.getDeltaTime());
			if(rotation == lerpRotTarget)
			{
				inLerpRot = false;
			}
		}
	}
	public void draw(SpriteBatch sb){
		if(enabled)
		{
			sb.draw(tex, x, y,tex.getWidth() / 2, tex.getHeight() / 2, tex.getWidth(), tex.getHeight(), scaleX, scaleY, rotation,0,0,tex.getWidth(),tex.getHeight(),flipX,flipY);
			if(text != null){
				text.draw(sb);
			}
		}
	}
	public void press(){
		pressed = true;
	}
	public void unpress(){
		pressed = false;
		if(callback != null)
			callback.callback();
	}
}
