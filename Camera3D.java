package com.vali.lib;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Camera3D extends PerspectiveCamera{
	
	float orbitAngle;
	public Camera3D(float fov, float viewportWidth, float viewportHeight){
		super(fov,viewportWidth, viewportHeight);
		orbitAngle = -90;
		near = 0.001f;
		far = 1000;
	}
	
	public void orbitAroundPoint(float angle, float distance, float x, float y, float z){
		position.x = distance * MathUtils.cosDeg(angle) + x;
		position.z = distance * MathUtils.sinDeg(angle) + z;
		orbitAngle = angle;
	}
	
	public void orbitAroundPoint(float angle, float distance, Vector3 tmp){
		orbitAroundPoint(angle, distance, tmp.x, tmp.y, tmp.z);
	}
	
}
