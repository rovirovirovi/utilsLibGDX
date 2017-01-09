package com.vali.lib;

import java.util.ArrayList;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.vali.game.MyGdxGame;

public class LayeredEntity {

	public float x, y, z;
	public int width;
	public int height;
	public int depth;
	protected ArrayList<Decal> decalList;
	protected int numberOfLayers;
	public static final float LAYER_SIZE = 2;
	
	public Vector3 velocity;
	Vector3 maxVelocity;
	Vector3 drag;
	public String tag;
	State state;
	
	public LayeredEntity(float x, float y, float z, String texturePath, int numberOfLayers){
		setPosition(x,y,z);
		this.numberOfLayers = numberOfLayers;
		
		tag = "";
		
		decalList = new ArrayList<Decal>();
		Texture t = MyGdxGame.assetManager.get(texturePath, Texture.class);
		width = t.getWidth() / numberOfLayers;
		depth = t.getHeight();
		height = numberOfLayers;
		TextureRegion tr = new TextureRegion(t);
		for(int i = 0; i < numberOfLayers; i++){
			tr.setRegion(i * width, 0, width, depth);
			Decal decal = Decal.newDecal(tr, true);
			decal.setPosition(x, y + i * LAYER_SIZE, z);
			decal.rotateX(-90);
			decalList.add(decal);
		}
		velocity = new Vector3();
		maxVelocity = new Vector3();
		drag = new Vector3();
		
	}
	
	public LayeredEntity(){
		
	}
	
	public void setVelocity(float x, float y, float z){
		velocity.set(x,y,z);
	}
	public void setVelocity(Vector3 tmp){
		velocity.set(tmp);
	}
	
	public void setMaxVelocity(float x, float y, float z){
		maxVelocity.set(x,y,z);
	}
	public void setMaxVelocity(Vector3 tmp){
		maxVelocity.set(tmp);
	}
	
	public void setDrag(float x, float y, float z){
		drag.set(x,y,z);
	}
	public void setDrag(Vector3 tmp){
		drag.set(tmp);
	}
	
	public void update(){
		updateMotion();
	}
	
	public void translate(float x, float y, float z){
		this.x += x;
		this.y += y;
		this.z += z;
		setPosition(this.x, this.y, this.z);
	}
	public void translate(Vector3 tmp){
		translate(tmp.x, tmp.y, tmp.z);
	}
	
	public void setPosition(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		for(int i = 0; i < numberOfLayers; i++){
			decalList.get(i).setPosition(x + width / 2, y - height * LAYER_SIZE / 2 + i * LAYER_SIZE, z + depth / 2);
		}
	}
	public void setPosition(Vector3 tmp){
		setPosition(tmp.x, tmp.z, tmp.z);
	}
	
	public void rotate(float x, float y, float z){
		for(int i = 0; i < numberOfLayers; i++){
			decalList.get(i).rotateX(x);
			decalList.get(i).rotateY(y);
			decalList.get(i).rotateZ(z);
		}
	}
	public void rotate(Vector3 tmp){
		rotate(tmp.x, tmp.z, tmp.z);
	}
	
	public void draw(DecalBatch db){
		for(int i = 0; i < numberOfLayers; i++){
			db.add(decalList.get(i));
		}
	}
	
	protected void updateMotion(){
		velocity.x = MathUtils.clamp(velocity.x, -maxVelocity.x, maxVelocity.x);
		velocity.y = MathUtils.clamp(velocity.y, -maxVelocity.y, maxVelocity.y);
		velocity.z = MathUtils.clamp(velocity.z, -maxVelocity.z, maxVelocity.z);
		
		float d = 0;
		d = drag.x * Gdx.graphics.getDeltaTime();
		if(velocity.x - d > 0)
			velocity.x -= d;
		else if(velocity.x + d < 0)
			velocity.x += d;
		else
			velocity.x = 0;
		
		d = drag.y * Gdx.graphics.getDeltaTime();
		if(velocity.y - d > 0)
			velocity.y -= d;
		else if(velocity.y + d < 0)
			velocity.y += d;
		else
			velocity.y = 0;
		
		d = drag.z * Gdx.graphics.getDeltaTime();
		if(velocity.z - d > 0)
			velocity.z -= d;
		else if(velocity.z + d < 0)
			velocity.z += d;
		else
			velocity.z = 0;
		
		x += velocity.x * Gdx.graphics.getDeltaTime();
		y += velocity.y * Gdx.graphics.getDeltaTime();
		z += velocity.z * Gdx.graphics.getDeltaTime();
		
		setPosition(x,y,z);
	}
	
	public void CollideEntity(String TAG, ArrayList<LayeredEntity> stack_to_check) {
		if(Collision.place_entity(x + velocity.x * Gdx.graphics.getDeltaTime(), z ,width ,depth , TAG, stack_to_check)){
			while(!Collision.place_entity(x + Math.signum(velocity.x), z,width ,depth , TAG, stack_to_check))
				x += Math.signum(velocity.x);
			velocity.x = 0;
		}
		if(Collision.place_entity(x, z + velocity.z * Gdx.graphics.getDeltaTime(), width , depth ,TAG, stack_to_check)){
			while(!Collision.place_entity(x, z + Math.signum(velocity.z) ,width , depth  , TAG, stack_to_check))
				z += Math.signum(velocity.z);
			velocity.z = 0;
		}
		
		
	}
	
	public void CollideEntity3D(String TAG, ArrayList<LayeredEntity> stack_to_check){
		if(Collision.place_entity3D(x + velocity.x * Gdx.graphics.getDeltaTime(), y, z ,width ,height, depth , TAG, stack_to_check)){
			while(!Collision.place_entity3D(x + Math.signum(velocity.x), y, z ,width , height, depth, TAG, stack_to_check))
				x += Math.signum(velocity.x);
			velocity.x = 0;
		}
		if(Collision.place_entity3D(x , y+ velocity.y * Gdx.graphics.getDeltaTime(), z ,width ,height, depth , TAG, stack_to_check)){
			while(!Collision.place_entity3D(x, y + Math.signum(velocity.y), z ,width , height, depth, TAG, stack_to_check))
				y += Math.signum(velocity.y);
			velocity.y = 0;
		}
		if(Collision.place_entity3D(x , y, z+ velocity.z * Gdx.graphics.getDeltaTime() ,width ,height, depth , TAG, stack_to_check)){
			while(!Collision.place_entity3D(x, y, z  + Math.signum(velocity.z),width , height, depth, TAG, stack_to_check))
				z += Math.signum(velocity.z);
			velocity.z = 0;
		}
	}
	
	public static class YComparator implements Comparator<Decal>{
		@Override
		public int compare(Decal d1, Decal d2){
			return Float.compare(d1.getY(), d2.getY());
		}
	}
	
}
