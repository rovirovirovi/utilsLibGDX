package com.vali.game;

import com.badlogic.gdx.ApplicationAdapter;

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
