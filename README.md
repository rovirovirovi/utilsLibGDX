# utilsLibGDX
Just some classes that help making stuff in LibGDX.
You have everything you need to create a whole game. 

##Features
-simple collision (using tags)  
-state manager  
-entities  
-animation (for entities)  
-ui  
-camera  
-callbacks  

##Usage
Just put all the files in a folder inside your src(where you have all of your code). Also you'll have to change the package in most of the files.

### States
In your main file, add a variable of type "StateManager", initialize it, load a state, and in the render function remove everything and just call the update function of the StateManager variable you created earlier.  
You should know that the StateManager's update function calls both the update and the render function of the state that's loaded.  
Example:  
```
StateManager sm;  
  
...  
  
sm = new StateManager();  
sm.loadState(new PlayState());  
  
...  
  
sm.update();  
```
  
Of course, you'll have to create a state to load first. That's fairly simple actually.  
  
Create a new class and call it whatever you want ( i'll be naming it PlayState for the sake of having a descriptive name) and have it extend the type State (you'll have to import it).  
  
In the constructor, call the function init(). What that does is initialize most of the stuff you'll be using like the camera, spriteBatch and the entity stack for you (there's more things it does but you get the idea).  
  
After that, add a update and render function ( they don't need any parameters ). These functions are going to be called by the state manager if you loaded the state and called update there.  
  
In the update function of the state, it's very important you call updateObjects(). That function is going to update everything in your state.  
```
void update(){
  ... (your code)  
    
  updateObjects();  
  cam.update();  
}  
```
  
In the render function, call glClearColor and glClear, like you usually do in the main file of your game.  Also, this is where you'll want to set the projection matrix of the spriteBatch. Be aware that there is already a spriteBatch and a camera created in the State file by default, which are both initialized in the init() function.
```
void render(){  
  Gdx.gl.glClearColor(20f/255f , 27f/255f, 36f/255f, 1);  
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  
	  	
	sb.setProjectionMatrix(cam.cam.combined);  
	sb.begin();  
  ... (your code)  
  renderObjects(sb);  
  sb.end();  
}  
```
The renderObjects() function I called there draws all the entities, gui objects like text and buttons, and particles (in the future) on the screen.  

###Cameras
The "cam" variable is already in the main State file, and is initialized when you call init(), so use it if you want. If you want to create another camera, just do  
```
Camera camera2;  
  
... 
  
camera2.update();  
  
...  
  
sb.setProjectionMatrix(camera2.cam.combined);  
```
If you want the camera to follow an entity, or just look at something, just change it's position using the setPosition() function.  
```
cam.setPosition(position x, position y);  
```
  
You can also restrict it's position within a rectangle using the restrictToBounds function.  
```
cam.restrictToBounds(bottom left x, bottom left y, top right x, top right y);
```
  
###Entities
Again, this stuff is fairly easy to use.  
First, create a class and call it whatever you want and make it extend the type Entity. I'll be naming it "Player".  
```
public class Player extends Entity {  
```
  
The constructor has to be made like this:  
```
public Player(float x, float y){  
  super(x,y, *string containing the path to the sprite of this entity*);  
  ... (your code)  
}  
```
You need those two parameters, which will give this entity's initial position, but after you can add whatever else you need after them ( or before).  
  
Every entity needs an update and a draw function ( yes, entities have draw functions not render functions, sorry).  
```
public void update(){
  ... (your code)  
  updateSelf();
}  

public void draw(SpriteBatch sb){  
  drawSelf(sb);  
}  
```
The updateSelf function calculates stuff like velocity and updates the current animation that the entity is using (if any). Likewise, the drawSelf function handles drawing either a single image or an animation if provided.  
  
Finally, to make the entity appear in a state, in your state file ( the one you created earlier if you followed what's written at the States section), call the add() function.  
```
(inside of your state file)  
add(new Player(position x, position y));  
```
I've used the Player class i created earlier, but make sure to use whatever entity you make. Also, if your entity doesn't show on screen, check if you called this function before anything else.  
  
###Animations
First of all, be aware that the only thing that has animation at the moment is the entity class, but it would be fairly simple to add it yourself to things like buttons and cursors.  
  
Alright, let's begin.  
  
First, create a new variable of type Animation in your entity class. Let's call it "anim_idle".  
  
```
Animation anim_idle;  
```
  
The fun part is the initialization of the variable, as that is where you'll specify things like the frames of the animation and the speed, whether it loops or not, and a callback to be called at the end of the animation.  
  
To initialize it, you won't be using the new Animation command. Instead, you'll be using the addAnimation() function.  
```
anim_idle = addAnimation(new int[]{0,1,2,3}, 8, true, 16, 16, null);  
```
  
Alright, let me explain every parameter in there.  
  
The first parameter (the int array) is used to tell the animation what frames you want it to play. These are numbered from 0 to whatever.  
  
The second and third parameters represent the speed of the animation and whether it should loop or not.  
  
Next, the two ints. Those two are the size of every sprite part of the animation. To keep it simple, just put the sprite's size in there.  
  
And lastly, the last parameter is the callback that's going to be called at the end of the animation. If you don't want a callback, just put null.  
  
Also, to make the entity play the animation you've just created, call playAnimation().  
```
playAnimation(anim_idle);  
```
The only parameter this function uses is an animation.  
  
