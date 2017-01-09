package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.vali.game.MyGdxGame;

public class Utils {

	
	
	public static void prepareRender(float r, float g, float b, float a){
		Gdx.gl.glClearColor(r/255f , g/255f, b/255f, a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	public static void prepareRender3D(){
		Gdx.gl.glViewport(0, 0, MyGdxGame.VIRTUAL_WIDTH,  MyGdxGame.VIRTUAL_HEIGHT);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	public static float AngleClamp(float angle, float min, float max)
	{
	    if (angle > max && angle < 360 - min) {
	        if (angle > 180) {
	            angle = 360 - min;
	        } else {
	            angle = max;
	        }
	    }

	    return angle;
	}
	public static float lerp(float a, float b, float f){
	    return (a * (1.0f - f)) + (b * f);
	}
	public static float distance(float xa, float ya, float xb, float yb){
		return (float) Math.sqrt(((xb - xa) * (xb - xa) + (yb - ya) * (yb - ya)));
	}
	public static float LerpDegrees(float start, float end, float amount)
    {
        float difference = Math.abs(end - start);
        if (difference > 180)
        {
            // We need to add on to one of the values.
            if (end > start)
            {
                // We'll add it on to start...
                start += 360;
            }
            else
            {
                // Add it on to end.
                end += 360;
            }
        }

        // Interpolate it.
        float value = (start + ((end - start) * amount));

        // Wrap it..
        float rangeZero = 360;

        if (value >= 0 && value <= 360)
            return value;

        return (value % rangeZero);
    }
}
