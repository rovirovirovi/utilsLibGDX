package com.vali.lib;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.vali.game.items.Item;

public class State {
	public Stack<Entity> entities;
	public Stack<Text> text;
	public Stack<Item> tiles;
	public Stack<Entity> particles;
	public Camera cam;
	public StateManager stateManager;
	public Cursor cursor;
	public void init(){
		entities = new Stack<Entity>();
		particles = new Stack<Entity>();
		text = new Stack<Text>();
		tiles = new Stack<Item>();
		cam = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1);
		cursor = new Cursor(cam);
	}
	public void setStateManager(StateManager sm){
		stateManager = sm;
	}
	public void update(){}
	public void updateObjects(){
		cursor.update();
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i) != null){
				entities.get(i).update();
			}
		}
		for(int i = 0; i < particles.size(); i++){
			if(particles.get(i) != null){
				particles.get(i).update();
			}
		}
	}
	public void loadState(State s){
		stateManager.loadState(s);
	}
	public void render(){}
	public void renderText(SpriteBatch sb){
		for(int i = 0; i < text.size(); i++){
			if(text.get(i) != null){
				text.get(i).render(sb);
			}
		}
	}
	public void renderObjects(SpriteBatch sb){
		
		for(int i = 0; i < particles.size(); i++){
			if(particles.get(i) != null){
				particles.get(i).draw(sb);
			}
		}
		for(int i = 0; i < tiles.size(); i++){
			if(tiles.get(i) != null){
				tiles.get(i).draw(sb);
			}
		}
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i) != null){
				if(inView(entities.get(i).x,entities.get(i).y,entities.get(i).width,entities.get(i).height))
					entities.get(i).draw(sb);
			}
		}
		

	}
	public void add(Entity e){
		if(entities == null){
			entities = new Stack<Entity>();
		}
		e.currentState = this;
		entities.add(e);
		
	}
	public boolean inView(float x, float y, float w, float h){
		if(x + w > cam.getX() - (Gdx.graphics.getWidth() / 2) / cam.getZoom() && 
			x < cam.getX() + (Gdx.graphics.getWidth() / 2) / cam.getZoom() &&
			y + h > cam.getY() - (Gdx.graphics.getHeight() / 2) / cam.getZoom() && 
			y < cam.getY() + (Gdx.graphics.getHeight() / 2) / cam.getZoom())
			return true;
		return false;
	}
	public void remove(Entity e){
		if(entities != null){
			if(entities.contains(e)){
				entities.remove(e);
				e = null;
			}
		}
	}
	public void resize(int width, int height) {}
	
	public void print(Object obj){
		System.out.println(obj);
	}
}
