package com.vali.lib;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.vali.game.MyGdxGame;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class State {
	public Stack<Entity> entities;
	public Stack<Text> text;
	public Stack<Entity> tiles;
	public Stack<Particle> particles;
	public Camera cam;
	public StateManager stateManager;
	public Cursor cursor;
	public SpriteBatch sb;
	public UI ui;
	public InputMultiplexer inputMultiplexer;
	
	com.badlogic.gdx.physics.box2d.World lightWorld;
	public RayHandler rayHandler;
	public Stack<PointLight> lights;
	
	public void init(){
		entities = new Stack<Entity>();
		particles = new Stack<Particle>();
		text = new Stack<Text>();
		tiles = new Stack<Entity>();
		lights = new Stack<PointLight>();
		cam = new Camera(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, 1);
		sb = new SpriteBatch();
		cursor = new Cursor(cam);
		ui = new UI(cursor);
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(ui);
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		RayHandler.useDiffuseLight(true);
		lightWorld = new World(new Vector2(0, 0), true);
		rayHandler = new RayHandler(lightWorld);
		rayHandler.setAmbientLight(.2f, .2f, .4f, .1f);
	}
	public void addInputManager(InputProcessor ip){
		inputMultiplexer.addProcessor(ip);
	}
	public void setStateManager(StateManager sm){
		stateManager = sm;
	}
	public void update(){}
	public void updateObjects(){
		if(Gdx.graphics.getDeltaTime() < 0.03f){
			cursor.update();
			ui.update();
			for(int i = 0; i < entities.size(); i++){
				if(entities.get(i) != null){
					if(entities.get(i).alive){
						entities.get(i).update();
					}
				}
			}
			for(int i = 0; i < particles.size(); i++){
				if(particles.get(i) != null){
					particles.get(i).update();
				}
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
				text.get(i).draw(sb);
			}
		}
	}
	
	public void drawLights()
	{
		rayHandler.setCombinedMatrix(cam.cam);
		rayHandler.updateAndRender();
	}
	
	public void renderObjects(SpriteBatch sb){
		
		
		for(int i = 0; i < tiles.size(); i++){
			if(tiles.get(i) != null){
				tiles.get(i).draw(sb);
			}
		}
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i) != null){
				if(inView(entities.get(i).x,entities.get(i).y,entities.get(i).width,entities.get(i).height))
					if(entities.get(i).alive)
						entities.get(i).draw(sb);
			}
		}
		for(int i = 0; i < particles.size(); i++){
			if(particles.get(i) != null){
				if(particles.get(i).alive)
					particles.get(i).draw(sb);
				else
				{
					particles.remove(i);
				}
			}
			
		}
		ui.render(sb);
		cursor.render(sb);
	}
	
	public void removeLights(){
		for(int i = 0; i < lights.size(); i++){
			if(lights.get(i) != null)
			{
				lights.get(i).remove(false);
				lights.get(i).dispose();
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
		if(x + w > cam.getX() - (MyGdxGame.VIRTUAL_WIDTH / 2) / cam.getZoom() && 
			x < cam.getX() + (MyGdxGame.VIRTUAL_WIDTH / 2) / cam.getZoom() &&
			y + h > cam.getY() - (MyGdxGame.VIRTUAL_HEIGHT / 2) / cam.getZoom() && 
			y < cam.getY() + (MyGdxGame.VIRTUAL_HEIGHT / 2) / cam.getZoom())
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
	public void resize(int width, int height) {
	}
	
	public void print(Object obj){
		System.out.println(obj);
	}
	
	public Entity getEntityWithTag(String tag){
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i).tag == tag)
				return entities.get(i);
		}
		return null;
	}
}
