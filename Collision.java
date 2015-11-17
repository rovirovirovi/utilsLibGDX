package com.vali.game;

public class Collision {
	
	public static boolean overlap(Entity e1, Entity e2){
		return e1.x < e2.x + e2.width
				&& e1.x + e1.width > e2.x
				&& e1.y < e2.y + e2.height
				&& e1.height + e1.y > e2.y;
	}
	
	public static void collide(Entity e1, Entity e2){
		if (overlap(e1, e2)) {
			float e1cx = e1.x + e1.width / 2.0f;
			float e2cx = e2.x + e2.width / 2.0f;
			float dx = 0;
			if (e1cx < e2cx)
			{
				dx = e2.x - (e1.x + e1.width);
			}
			else
			{
				dx = (e2.x + e2.width) - e1.x;
			}

			float e1cy = e1.y + e1.height / 2.0f;
			float e2cy = e2.y + e2.height / 2.0f;
			float dy = 0;
			if (e1cy < e2cy)
			{
				dy = e2.y - (e1.y + e1.height);
			}
			else
			{
				dy = (e2.y + e2.height) - e1.y;
			}
			
			e1.simpleCollision(e1, e2, dx, dy);
		}
	}
	
	
}
