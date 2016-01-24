package com.vali.lib;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class State {
	public Stack<Entity> entities;
	public Stack<Entity> bullets;
	public Stack<Particle> particles;
	public void init(){
		entities = new Stack<Entity>();
		particles = new Stack<Particle>();
	}
	public void update(){}
	public void updateObjects(){
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
	public void draw(){}
	public void drawObjects(SpriteBatch sb){
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i) != null){
				entities.get(i).draw(sb);
			}
		}
		for(int i = 0; i < particles.size(); i++){
			if(particles.get(i) != null){
				particles.get(i).draw(sb);
			}
		}
	}
}
