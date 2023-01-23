package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.dongbat.jbump.Grid;
import com.dongbat.jbump.Item;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackCore.Pathfinding.*;
import com.mygdx.game.BlackScripts.*;
import com.mygdx.game.BlackScripts.CoreData.Inputs.InputsDefaults;
import com.mygdx.game.CoreData.Items.Items;
import jdk.javadoc.internal.doclets.formats.html.markup.Script;

import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	public static OrthographicCamera camera;
	BTexture texture;
	GameObjectHandler gameObjectHandler;

	BlackScriptManager ScriptManager;
	FixedTimeController fixedTime;
	CollisionDetection collisionDetection;
	PhysicsSuperController physicsController;

	SoundFrame soundFrame = new SoundFrame();

	CreateGameWorld GameWorld;
	MasterChef masterChef;
	GameObject obj;
	GameObject obj2;
	GameObject obj3;
	BatchDrawer batch;

	CustomerManager customerManager;
	Boolean Pause = false;
	BTexture pauseTexture;
	GameObject pauseMenu;
	GameObject closeMenu;
	GameObject muteMusic;
	GameObject unmuteMusic;
	boolean muteState = false;

	@Override
	public void create () {
		collisionDetection = new CollisionDetection();
		physicsController = new PhysicsSuperController();
		batch = new BatchDrawer();
//		Gdx.graphics.setContinuousRendering(false);
//		Gdx.graphics.requestRendering();
		try {
			ScriptManager = new BlackScriptManager(); // this exception shouldnt happen but just incase
		} catch (Exception e) {
			throw new RuntimeException(e);
		}



		batch = new BatchDrawer();
		gameObjectHandler = new GameObjectHandler(batch);

		fixedTime = new FixedTimeController();


		texture = new BTexture("badlogic.jpg",20,20);
		texture.setWrap(Texture.TextureWrap.MirroredRepeat);

		GameWorld = new CreateGameWorld();
		GameWorld.Instantiate();

		//obj = new GameObject(new Rectangle(10,10,20,20),texture);
		//obj2 = new GameObject(new Rectangle(10,10,20,20),texture);
//		obj2.transform.position = new Vector3(700,0,0);
//		obj.addDynamicCollider();
//		obj2.addDynamicCollider();

		// Makes a pauseMenu game object using the pauseMenu.png file as a texture
		pauseMenu = new GameObject(new Rectangle(10,20,20,20),
				new BTexture("pauseMenu.png", 800, 415));
		// makes it invisible initially, so it does not block the screen
		pauseMenu.negateVisibility();
		pauseMenu.transform.position.y = 3;

		closeMenu = new GameObject(new Rectangle(10,20, 20, 20), new BTexture("Resume.png", 300, 70));
		closeMenu.negateVisibility();
		closeMenu.transform.position.x = 50;
		closeMenu.transform.position.z = 325;
		closeMenu.transform.position.y = 10;

		muteMusic =  new GameObject(new Rectangle(10,20, 20, 20), new BTexture("muteSound.png", 300, 70));
		muteMusic.negateVisibility();
		muteMusic.transform.position.x = 50;
		muteMusic.transform.position.z = 205;
		muteMusic.transform.position.y = 10;

		unmuteMusic =  new GameObject(new Rectangle(10,20, 20, 20), new BTexture("unMuteSound.png", 300, 70));
		unmuteMusic.negateVisibility();
		unmuteMusic.transform.position.x = 50;
		unmuteMusic.transform.position.z = 205;
		unmuteMusic.transform.position.y = 10;
		

		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,400);




		//Try out script manager

		BlackScriptTest testScript = new BlackScriptTest();

		ScriptManager.tryAppendLooseScript(testScript);
		ScriptManager.tryAppendLooseScript(physicsController);


		BasicCharacterController characterController = new BasicCharacterController();
		characterController.camera = camera;

		//obj.AppendScript(characterController);

		ItemFactory factory = new ItemFactory();
		ScriptManager.tryAppendLooseScript(factory);


		ItemFactory.factory.implementItems();

		//System.out.println(factory.produceItem(Items.Lettuce).name);

		GridSettings gridsets = new GridSettings();
		gridsets.scaleOfGrid = 25;
		gridsets.XSizeOfFloor = 1000;
		gridsets.ZSizeOfFloor = 600;

		GridPartition gPart = new GridPartition(gridsets);

		PathfindingConfig config = new PathfindingConfig();
		config.DiagonalCost = 1;
		config.StepCost = 1;
		config.DiagonalCost = 1;
		config.PathMutliplier = 1;
		config.maxIterations = 500;
		config.DistanceCost = 1;
	//	List<Vector2> a = gPart.pathfindFrom(0,0,3,3,config);
	//	System.out.println(a.size());
	//	for (Vector2 v2: a
	//		 ) {
	///		System.out.print(a);
		//}

		masterChef = new MasterChef();
		masterChef.camera = camera;
		masterChef.chefTex = texture;
		masterChef.KitchenPartition = gPart;

		ScriptManager.tryAppendLooseScript(masterChef);

		customerManager = new CustomerManager();
		CustomerManager.customermanager.setCustomerTexture(texture);

		ScriptManager.tryAppendLooseScript(customerManager);

	}

	@Override
	public void render () {


		if (Gdx.input.isKeyJustPressed(InputsDefaults.pause)){
			Pause = !Pause;
			pauseMenu.negateVisibility(); // makes the pause menu visible
			closeMenu.negateVisibility();
			if (!muteState){
				muteMusic.negateVisibility();
			}else{
				unmuteMusic.negateVisibility();
			}

		}
		if(fixedTime.doTimeStep()){
			//the time step is within accumilator
			while (fixedTime.accumulator> fixedTime.dt){
				if (! Pause){ // pauses the game
					ScriptManager.RunFixedUpdate((float)fixedTime.dt);
				}
				fixedTime.accumulator-= fixedTime.dt;
				}
			}

		if (!Pause){
			ScriptManager.RunUpdate();
		}else{
			if (closeMenu.isObjectTouched()){
				pauseMenu.negateVisibility();
				closeMenu.negateVisibility();
				Pause = !Pause;
			}
			if (Gdx.input.isKeyJustPressed(InputsDefaults.exit)){
				Gdx.app.exit(); //Todo change this to restart the game if possible
				System.exit(0);
			}
			if (Gdx.input.isKeyJustPressed(InputsDefaults.mute) || muteMusic.isObjectTouched() || unmuteMusic.isObjectTouched()){
				if (muteState){
					soundFrame.SoundEngine.unMuteSound();
					unmuteMusic.negateVisibility();
					muteMusic.negateVisibility();
				}
				else{
					soundFrame.SoundEngine.muteSound();
					unmuteMusic.negateVisibility();
					muteMusic.negateVisibility();
				}
				muteState = !muteState;
				System.out.print(soundFrame.volume);

			}
		}
		camera.update();
		ScreenUtils.clear(1, 0, 0, 1);
		batch.RenderTextures(camera.combined);
	}
	
	@Override
	public void dispose () {
		gameObjectHandler.dispose();
		batch.dispose();
	}
}
