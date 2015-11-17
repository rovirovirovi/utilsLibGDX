package com.vali.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class MyGdxGame extends ApplicationAdapter {
	
	StateManager sm;
	
	@Override
	public void create () {
		Gdx.input.setCursorCatched(true);
		sm = new StateManager();
		sm.loadState(new Playstate());
	}

	@Override
	public void render () {
		sm.update();
	
	}
}
