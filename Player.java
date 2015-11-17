package com.vali.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity{

	Animation animWalk;
	Animation animIdle;
	
	public Player(float x,float y) {
		super(x, y, "player_sheet.png");
		
		drag = new Vector2(8000, 8000);
		maxVelocity = new Vector2(400, 400);
		
		animIdle = addAnimation(tex, new int[]{0,1,2,3,4,5}, 12, true, 32, 32);
		animWalk = addAnimation(tex, new int[]{6,7,8,9,10,11,12,13}, 12, true, 32, 32);
		playAnimation(animIdle);
	}
	
	public void update(){
		
		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			playAnimation(animWalk);
			setFacingX(true);
		}
		else
		{
			playAnimation(animIdle);
			setFacingX(false);
		}
		
		handle_animation();
		handle_movement();
		updateMotion();
		updateAnimation();
	}
	void handle_animation(){
		if(Gdx.input.isKeyPressed(Keys.W) ||
		   Gdx.input.isKeyPressed(Keys.S) ||
		   Gdx.input.isKeyPressed(Keys.D) ||
		   Gdx.input.isKeyPressed(Keys.A))
			playAnimation(animWalk);
		else
			playAnimation(animIdle);
	}
	void handle_movement(){
		if(Gdx.input.isKeyPressed(Keys.D))
			velocity.x += 10000 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.A))
			velocity.x -= 10000 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.W))
			velocity.y += 10000 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.S))
			velocity.y -= 10000 * Gdx.graphics.getDeltaTime();
	}

	
	
}
