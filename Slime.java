package com.vali.game;

public class Slime extends Entity{

	Animation idleAnim;
	
	public Slime(float XX, float YY) {
		super(XX, YY, "slime_red.png");
		
		idleAnim = addAnimation(tex, new int[]{0,1,2,1}, 12, true, 16, 16);
		playAnimation(idleAnim);
	}

}
