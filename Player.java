package com.vali.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity{

	Animation animWalk;
	Animation animIdle;
	
	public Player(float x,float y) {
		super(x, y, "player_sheet.png");
		
		drag = new Vector2(400, 0);
		maxVelocity = new Vector2(200, 9999);
		
		animIdle = addAnimation(tex, new int[]{0,1,2,3,4,5}, 12, true, 32, 32);
		animWalk = addAnimation(tex, new int[]{6,7,8,9,10,11,12,13}, 12, true, 32, 32);
		playAnimation(animIdle);
	}
	
	public void update(){
		
		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			playAnimation(animWalk);
			setFacing(true);
		}
		else
		{
			playAnimation(animIdle);
			setFacing(false);
		}
		
		handle_facing();
		
		updateMotion();
		updateAnimation();
	}
	void handle_facing(){
		
	}

	
	
}
