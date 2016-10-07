package com.vali.lib;

import java.util.Stack;

import com.badlogic.gdx.math.MathUtils;

public class Collision {
	
	
	public static boolean place_entity(float x, float y, float w, float h, String TAG, Stack<Entity> stack){
		
		for(int i = 0; i < stack.size(); i++){
			Entity e = stack.get(i);
			if(e.tag == TAG && e.alive){
				if(x + w > e.x && x < e.x + e.width && y + h > e.y && y < e.y + e.height)
					return true;
			}
		}
		return false;
	}
	
	public static boolean overlap_entity(Entity e1, Entity e2){
		 return (e2.x + e2.width > e1.x && e2.x < e1.x + e1.width &&
			e2.y + e2.height > e1.y && e2.y < e1.y + e1.height);
	}
	
	public static Entity get_entity_at(float x, float y, String TAG, Stack<Entity> stack){
		for(int i = 0; i < stack.size(); i++){
			Entity e = stack.get(i);
			if(e.tag == TAG && e.alive){
				if(x  > e.x && x < e.x + e.width && y > e.y && y < e.y + e.height)
				{
					return e;
				}
			}
		}
		return null;
	}
	public static Stack<Entity> get_overlapping_entities(float x, float y, float w, float h, String TAG, Stack<Entity> stack){
		Stack<Entity> ens = new Stack<Entity>();
		for(int i = 0; i < stack.size(); i++){
			Entity e = stack.get(i);
			if(e.tag == TAG && e.alive){
				if((x  > e.x && x < e.x + e.width && y > e.y && y < e.y + e.height) ||
					(e.x + e.width > x && e.x  < x + w && e.y + e.height > y && e.y < y + h))
				{
					ens.add(e);
				}
			}
		}
		return ens;
	}
	
	

	
}
