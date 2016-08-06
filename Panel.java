package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vali.game.MyGdxGame;

public class Panel {

	public Texture tex;
	public float x,y, scaleX = 1, scaleY = 1, rotation = 0;
	boolean flipX, flipY;
	private float colR = 1f, colG = 1f, colB = 1f, colA = 1f;
	private boolean inLerpPos = false, inLerpRot = false, inLerpCol = false;
	private float lerpPosTargetX, lerpPosTargetY, lerpRotTarget, lerpPosTime, lerpRotTime, lerpColTargetR, lerpColTargetG, lerpColTargetB, lerpColTargetA, lerpColTime;
	private Callback colCallback, posCallback, rotCallback;
	public Panel(float x, float y, String imagePath){
		if(MyGdxGame.assetManager.isLoaded(imagePath)){
			tex = MyGdxGame.assetManager.get(imagePath, Texture.class);
		}
		else{
			MyGdxGame.assetManager.load(imagePath, Texture.class);
			MyGdxGame.assetManager.finishLoading();
			tex = MyGdxGame.assetManager.get(imagePath, Texture.class);
		}
		this.x = x;
		this.y = y;
	}
	public void lerpPos(float targetX, float targetY, float time, Callback cb){
		inLerpPos = true;
		lerpPosTargetX = targetX;
		lerpPosTargetY = targetY;
		lerpPosTime = time;
		posCallback = cb;
	}
	public void lerpRot(float targetRot, float time, Callback cb){
		inLerpPos = true;
		lerpRotTarget = targetRot;
		lerpRotTime = time;
		rotCallback = cb;
	}
	public void lerpColor(float tr, float tg, float tb, float ta, float time, Callback cb){
		this.lerpColTargetR = tr;
		this.lerpColTargetG = tg;
		this.lerpColTargetB = tb;
		this.lerpColTargetA = ta;
		this.lerpColTime = time;
		inLerpCol = true;
		colCallback = cb;
	}
	public void update(){
		handleLerp();
	}
	private void handleLerp(){
		if(inLerpPos){
			x = Utils.lerp(x, lerpPosTargetX, lerpPosTime * Gdx.graphics.getDeltaTime());
			y = Utils.lerp(y, lerpPosTargetY, lerpPosTime * Gdx.graphics.getDeltaTime());
			if(x == lerpPosTargetX && y == lerpPosTargetY){
				inLerpPos = false;
				if(posCallback != null)
					posCallback.callback();
			}
		}
		if(inLerpRot){
			rotation = Utils.LerpDegrees(rotation, lerpRotTarget, lerpRotTime * Gdx.graphics.getDeltaTime());
			if(rotation == lerpRotTarget)
			{
				inLerpRot = false;
				if(rotCallback != null)
					rotCallback.callback();
			}
		}
		if(inLerpCol){
			colR = Utils.lerp(colR, lerpColTargetR, lerpColTime * Gdx.graphics.getDeltaTime());
			colG = Utils.lerp(colG, lerpColTargetG, lerpColTime * Gdx.graphics.getDeltaTime());
			colB = Utils.lerp(colB, lerpColTargetB, lerpColTime * Gdx.graphics.getDeltaTime());
			colA = Utils.lerp(colA, lerpColTargetA, lerpColTime * Gdx.graphics.getDeltaTime());
			
			if(colR == lerpColTargetR && colG == lerpColTargetG && colB == lerpColTargetB && ((lerpColTargetA > colA && colA > lerpColTargetA - .005f) || (lerpColTargetA < colA && colA < lerpColTargetA + .005f))){
				inLerpCol = false;
				if(colCallback != null)
					colCallback.callback();
			}
		}
	}
	public void setPosCallback(Callback cb){
		this.posCallback = cb;
	}
	public void setRotCallback(Callback cb){
		this.rotCallback = cb;
	}
	public void setColCallback(Callback cb){
		this.colCallback = cb;
	}
	public void setColor(float r, float g, float b, float a){
		colR = r;
		colG = g;
		colB = b;
		colA = a;
	}
	public void draw(SpriteBatch sb){
		sb.setColor(colR, colG, colB, colA);
		sb.draw(tex, x, y,tex.getWidth() / 2, tex.getHeight() / 2, tex.getWidth(), tex.getHeight(), scaleX, scaleY, rotation,0,0,tex.getWidth(),tex.getHeight(),flipX,flipY);
		sb.setColor(Color.WHITE);
	}
	
}
