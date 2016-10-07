package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.vali.game.MyGdxGame;

public class Cursor {

	public Texture tex;
	float tx,ty;
	public float x, y;
	boolean bind = false;
	Camera cam;
	public Entity draggedEntity;
	public boolean visible = true;
	Color color;
	public Cursor(Camera cam){
		tex = MyGdxGame.assetManager.get("images/cursor_icon.png", Texture.class);
		this.cam = cam;
		tx = ty = 0;
		Gdx.input.setCursorCatched(true);
		color = new Color(1, 1, 1, 1);
	}
	public void update(){
		tx += Gdx.input.getDeltaX() / cam.getZoom();
		ty -= Gdx.input.getDeltaY() / cam.getZoom();
		
		tx = MathUtils.clamp(tx, -Gdx.graphics.getWidth() / cam.getZoom() / 2, Gdx.graphics.getWidth() / cam.getZoom() / 2 - tex.getWidth());
		ty = MathUtils.clamp(ty, -Gdx.graphics.getHeight() / cam.getZoom() / 2, Gdx.graphics.getHeight() / cam.getZoom() / 2 - tex.getHeight());
		x = cam.x + tx;
		y = cam.y + ty;
		
		if(draggedEntity != null){
			draggedEntity.x = x-4;
			draggedEntity.y = y;
		}
		
	}
	public void bindToCamera(Camera cam){
		bind = true;
	}
	public void render(SpriteBatch sb){
		sb.setColor(color);
		if(visible)
			sb.draw(tex, tx+cam.x, ty+cam.y);
		sb.setColor(Color.WHITE);
	}
	public void setColor(float r, float g, float b, float a){
		color.r = r;
		color.g = g;
		color.b = b;
		color.a = a;
	}
	
	
}
