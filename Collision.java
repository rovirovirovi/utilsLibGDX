package com.vali.lib;

import java.util.Stack;

public class Collision {
	
	public static boolean overlap(Entity e1, Entity e2){
		return e1.x + e1.offsetX  < e2.x + e2.offsetX + e2.width
				&& e1.x + e1.offsetX  + e1.width > e2.x + e2.offsetX 
				&& e1.y + e1.offsetY < e2.y + e2.offsetY + e2.height
				&& e1.height  + e1.y + e1.offsetY > e2.y + e2.offsetY ;
	}
	
	public static void collide(Entity e1, Stack<Entity> e2){
		for(int i = 0; i < e2.size()-1;i++)
			if(e1 != null)
				if(e2.get(i) != null)
					if(e1 != e2.get(i))
						collide(e1, e2.get(i));
	}
	
	public static void collide(Entity e1, Entity e2){
		if (overlap(e1, e2)) {
			float e1cx = e1.x + e1.offsetX + e1.width / 2.0f;
			float e2cx = e2.x + e2.offsetX + e2.width / 2.0f;
			float dx = 0;
			if (e1cx < e2cx)
			{
				dx = e2.x + e2.offsetX - (e1.x + e1.offsetX + e1.width );
			}
			else
			{
				dx = (e2.x + e2.offsetX + e2.width) - e1.x + e1.offsetX;
			}

			float e1cy = e1.y + e1.offsetY + e1.height / 2.0f;
			float e2cy = e2.y + e2.offsetY + e2.height / 2.0f;
			float dy = 0;
			if (e1cy < e2cy)
			{
				dy = e2.y + e2.offsetY - (e1.y + e1.offsetY + e1.height);
			}
			else
			{
				dy = (e2.y + e2.offsetY + e2.height) - e1.y + e1.offsetY;
			}
			
			e1.simpleCollision(e1, e2, dx, dy);
		}
	}

	public static void collide(Stack<Entity> e1, Stack<Entity> e2) {
		for(int i = 0; i < e1.size() - 1; i++){
			collide(e1.get(i), e2);
		}
		
	}

	
}
