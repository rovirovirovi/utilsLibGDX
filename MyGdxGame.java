package com.vali.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
	
	StateManager sm;
	
	@Override
	public void create () {
		sm = new StateManager();
		sm.loadState(new Playstate());
	}

	@Override
	public void render () {
		sm.update();
	
	}
}
