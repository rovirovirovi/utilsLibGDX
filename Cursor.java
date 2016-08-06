package com.vali.lib;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.vali.game.MyGdxGame;
import com.vali.game.items.Item;

public class Cursor {

	public Texture tex;
	float tx,ty;
	public float x, y;
	boolean bind = false;
	Camera cam;
	public Entity draggedEntity;
	public boolean visible = true;
	public Cursor(Camera cam){
		tex = MyGdxGame.assetManager.get("cursor_icon.png", Texture.class);
		this.cam = cam;
		tx = ty = 0;
		Gdx.input.setCursorCatched(true);
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
	public Item eOverlapped( String tag, Stack<Item> items){
			for(int i = 0; i < items.size(); i++){
				if(x < items.get(i).x + 8
					&& x > items.get(i).x 
					&& y+2 < items.get(i).y + 8
					&& y + 6 > items.get(i).y){
						return items.get(i);
				}
			}
		
		return null;
	}
	public void bindToCamera(Camera cam){
		bind = true;
	}
	public void render(SpriteBatch sb){
		if(visible)
			sb.draw(tex, tx+cam.x, ty+cam.y);
	}
	
	
}
