package com.vali.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Playstate extends State{

	public ArrayList<Entity> entities;

	SpriteBatch sb;
	Player p;

	public Playstate(){
		sb = new SpriteBatch();
		entities = new ArrayList<Entity>();
		
		p = new Player(120,120);
		entities.add(p);
	}
	
	public void update(){
		
				
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).update();
		}

	}
	public void draw(){
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.begin();
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).draw(sb);
		}
		sb.end();
		
	}
	
}
