package com.vali.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class CustomCursor extends Entity{

	Vector3 pos;
	
	public CustomCursor() {
		super(0, 0, "crosshair.png");
		pos = new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
	}
	public void update(){
		pos.x = Gdx.input.getX() + Gdx.graphics.getWidth() + width / 2;
		pos.y = -Gdx.input.getY() + Gdx.graphics.getHeight() + height / 2;
		pos.z = 0;
	}

}
