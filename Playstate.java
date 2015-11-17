package com.vali.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Playstate extends State{

	public ArrayList<Entity> entities;

	SpriteBatch sb;
	SpriteBatch guib;
	Player p;
	Camera cam;
	CustomCursor cursor;

	public Playstate(){
		sb = new SpriteBatch();
		guib = new SpriteBatch();
		entities = new ArrayList<Entity>();
		
		cursor = new CustomCursor();
		p = new Player(120,120);
		
		entities.add(new Slime(145,168));
		entities.add(p);
		//entities.add(cursor);
		
		cam = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void update(){
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			cam.rotate(5);
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			cam.rotate(-5);
		
		
		
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).update();
		}
		
		
		
		cam.follow(true, p,0,0);
		cam.update();
		
		cursor.update();
		cursor.x = cam.unproject(cursor.pos).x - cam.x;
		cursor.y = cam.unproject(cursor.pos).y;		

	}
	public void draw(){
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		sb.begin();
		sb.setProjectionMatrix(cam.cam.combined);
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).draw(sb);
		}
		
		sb.end();
		
		guib.begin();
			cursor.draw(guib);
		guib.end();
		
	}
	
}
