package com.vali.game;

public class FlyingEnemy extends Entity{

	Animation idleAnim;
	
	public FlyingEnemy(float XX, float YY) {
		super(XX, YY, "alienFloatEnemy.png");
		
		idleAnim = addAnimation(tex, new int[]{0,1,0,1,2,3,2,3,2,4,5,4,5,2,3,2,3,0,1,0,1}, 24, true, 32, 32);
		playAnimation(idleAnim);
	}

}
