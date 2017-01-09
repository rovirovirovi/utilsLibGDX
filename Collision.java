package com.vali.lib;

import java.util.ArrayList;
import java.util.Stack;

public class Collision {
	
	
	public static boolean place_entity(float x, float y, float w, float h, String TAG, Stack<Entity> stack){
		
		for(int i = 0; i < stack.size(); i++){
			Entity e = stack.get(i);
			if(e.tag == TAG && e.alive && e.canCollide){
				if(OverlapAdvanced(x,y,w,h,e))
					return true;
			}
		}
		return false;
	}
	
	public static boolean place_entity(float x, float z, float w, float d, String TAG, ArrayList<LayeredEntity> list){
		
		for(int i = 0; i < list.size(); i++){
			LayeredEntity e = list.get(i);
			if(e.tag == TAG){
				if(OverlapAdvanced(x,z,w,d,e.x, e.z, e.width, e.depth))
					return true;
			}
		}
		return false;
	}
	
public static boolean place_entity3D(float x, float y, float z, float w, float h, float d, String TAG, ArrayList<LayeredEntity> list){
		
		for(int i = 0; i < list.size(); i++){
			LayeredEntity e2 = list.get(i);
			if(e2.tag == TAG){
				if(Overlap3D(x, y, z, w, h, d, e2.x, e2.y, e2.z, e2.width, e2.height, e2.depth))
					return true;
			}
		}
		return false;
	}
	
	public static Entity place_entity(Entity e1, String TAG, Stack<Entity> stack){
		
		for(int i = 0; i < stack.size(); i++){
			Entity e = stack.get(i);
			if(e.tag == TAG && e.alive && e.canCollide){
				if(OverlapAdvanced(e1, e))
					return e;
			}
		}
		return null;
	}
	
	public static boolean OverlapAdvanced(Entity e1, Entity e2){
		float dx = ((e1.x + e1.offsetX + e1.width / 2) - (e2.x + e2.offsetX + e2.width / 2));
		float dy = ((e1.y + e1.offsetY + e1.height / 2) - (e2.y + e2.offsetY + e2.height / 2));
		if(Math.abs(dx) <= e1.width / 2 + e2.width / 2 && Math.abs(dy) <= e1.height / 2 + e2.height / 2)
			return true;
		return false;
	}
	public static boolean OverlapAdvanced(float x, float y, float w, float h, Entity e2){
		float dx = ((x + w / 2) - (e2.x + e2.offsetX + e2.width / 2));
		float dy = ((y + h / 2) - (e2.y + e2.offsetY + e2.height / 2));
		if(Math.abs(dx) <= w / 2 + e2.width / 2 && Math.abs(dy) <= h / 2 + e2.height / 2)
			return true;
		return false;
	}
	
	public static boolean OverlapAdvanced(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2){
		float dx = ((x1 + w1 / 2) - (x2 + w2 / 2));
		float dy = ((y1 + h1 / 2) - (y2 + h2 / 2));
		if(Math.abs(dx) <= w1 / 2 + w2 / 2 && Math.abs(dy) <= h1 / 2 + h2 / 2)
			return true;
		return false;
	}
	
	public static boolean Overlap3D(float x1, float y1, float z1, float w1, float h1, float d1, float x2, float y2, float z2, float w2, float h2, float d2){
		float dx, dy, dz;
		dx = (x1 + w1 / 2) - (x2 + w2 / 2);
		dy = (y1 + h1 * LayeredEntity.LAYER_SIZE) - (y2 + h2 * LayeredEntity.LAYER_SIZE);
		dz = (z1 + d1 / 2) - (z2 + d2 / 2);
		if(Math.abs(dx) <= w1 / 2 + w2 / 2 &&
			Math.abs(dy) <= h1 / 2 + h2 / 2 &&
			Math.abs(dz) <= d1 / 2 + d2 / 2){
			return true;
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
			if(e.tag == TAG && e.alive && e.canCollide){
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
			if(e.tag == TAG && e.alive & e.canCollide){
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
