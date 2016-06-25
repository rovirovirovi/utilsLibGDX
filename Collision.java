package com.vali.lib;

import java.util.Stack;

public class Collision {
	
	public static boolean overlap(Entity e1, Entity e2, CollisionCallback callback){
		if(e1.x + e1.offsetX  < e2.x + e2.offsetX + e2.width
				&& e1.x + e1.offsetX  + e1.width > e2.x + e2.offsetX 
				&& e1.y + e1.offsetY < e2.y + e2.offsetY + e2.height
				&& e1.height  + e1.y + e1.offsetY > e2.y + e2.offsetY){
			if(callback != null)
				callback.callback(e1, e2);
			return true;
		}
		return false;
		
	}
	
	public static boolean place_entity(float x, float y, float w, float h, String TAG, Stack<Entity> stack){
		
		for(int i = 0; i < stack.size(); i++){
			Entity e = stack.get(i);
			if(e.tag == TAG){
				if(x + w > e.x && x < e.x + e.width && y + h > e.y && y < e.y + e.height)
					return true;
			}
		}
		return false;
	}
	
	
	public static Entity get_entity_at(float x, float y, String TAG, Stack<Entity> stack){
		for(int i = 0; i < stack.size(); i++){
			Entity e = stack.get(i);
			if(e.tag == TAG){
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
			if(e.tag == TAG){
				if((x  > e.x && x < e.x + e.width && y > e.y && y < e.y + e.height) ||
					(e.x + e.width > x && e.x  < x + w && e.y + e.height > y && e.y < y + h))
				{
					ens.add(e);
				}
			}
		}
		return ens;
	}
	
	public static boolean overlap(Entity e1, Stack<Entity> e2, CollisionCallback callback){
		for(int i = 0; i < e2.size();i++)
			if(e1 != null)
				if(e2.get(i) != null)
					if(e1 != e2.get(i))
						return overlap(e1, e2.get(i), callback);
		return false;
	}
	public static void overlap(Stack<Entity> e1, Stack<Entity> e2, CollisionCallback callback) {
		for(int i = 0; i < e1.size(); i++){
			overlap(e1.get(i), e2, callback);
		}
	}

	
}
