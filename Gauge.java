package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vali.game.MyGdxGame;

public class Gauge {

	Texture texBg, texFg, texMark;
	public float x, y, value = 1, maxValue = 1, width, height, lerpTime = 1f, targetValue = 1, lerpSpeed;
	
	private int srcWidth, clipWidth, clipSrcWidth;
	
	public Gauge(float x, float y, float width, float height, String bgPath, String fgPath){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		if(MyGdxGame.assetManager.isLoaded(bgPath)){
			texBg = MyGdxGame.assetManager.get(bgPath, Texture.class);
		}
		else{
			MyGdxGame.assetManager.load(bgPath, Texture.class);
			MyGdxGame.assetManager.finishLoading();
			texBg = MyGdxGame.assetManager.get(bgPath, Texture.class);
		}
		if(MyGdxGame.assetManager.isLoaded(fgPath)){
			texFg = MyGdxGame.assetManager.get(fgPath, Texture.class);
		}
		else{
			MyGdxGame.assetManager.load(fgPath, Texture.class);
			MyGdxGame.assetManager.finishLoading();
			texFg = MyGdxGame.assetManager.get(fgPath, Texture.class);
		}
		srcWidth = texFg.getWidth();
		
	}
	public void update(){
		value = Utils.lerp(value, targetValue, lerpTime * Gdx.graphics.getDeltaTime());
	}
	public void setValue(float value){
		targetValue = value;
		
	}
	public void draw(SpriteBatch sb){
		sb.draw(texBg, x, y, texBg.getWidth(), texBg.getHeight(), texBg.getWidth(), texBg.getHeight(), 1, 1, 0, 0, 0, texBg.getWidth(), texBg.getHeight(), false, false);
		
		float targetRatio = value / maxValue;
		clipSrcWidth = (int) (value * srcWidth);	
		clipWidth = (int) (srcWidth * targetRatio);
		sb.draw(texFg, x, y, value * texFg.getWidth() / 2, texFg.getHeight() / 2, clipWidth, texFg.getHeight(), 1, 1, 0, 0, 0, clipSrcWidth, texFg.getHeight(), false, false);
		
	}
	
}
