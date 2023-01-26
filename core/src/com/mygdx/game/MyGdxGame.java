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
	GameObject menu;
	GameObject settings;
	GameObject highscores;
	GameObject start;
	GameObject exit;
	BatchDrawer batch;

	CustomerManager customerManager;
	Boolean Pause = true;
	BTexture pauseTexture;
	GameObject pauseMenu;
	GameObject closeMenu;
	GameObject muteMusic;
	GameObject unmuteMusic;

	GameObject menuControls;
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

		menuControls = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("controls.png", 250, 350));
		menuControls.transform.position.y = 4;
		menuControls.transform.position.x = 500;
		menuControls.transform.position.z = 25;

		settings = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("gear.png", 65, 70));
		settings.transform.position.y = 4;
		settings.transform.position.x = 75;
		settings.transform.position.z = 125;

		highscores = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("trophy-for-sports.png", 300, 70));
		highscores.transform.position.y = 4;
		highscores.transform.position.x = 75;
		highscores.transform.position.z = 200;

		exit = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("exitIcon.png", 65, 70));
		exit.transform.position.y = 4;
		exit.transform.position.x = 75;
		exit.transform.position.z = 50;

		start = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("play-button-arrowhead.png", 300, 70));
		start.transform.position.y = 4;
		start.transform.position.x = 75;
		start.transform.position.z = 275;
		
		menu = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("menu.png", 800, 415));
		menu.transform.position.y = 3;

		//	gameObjectHandler.Instantiate(obj);

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

	/**
	 * Function to change menu visibility when an action is taken from it
	 */
	public void changeMenuVisbility () {
		settings.negateVisibility();
		start.negateVisibility();
		highscores.negateVisibility();
		exit.negateVisibility();
		menuControls.negateVisibility();
		menu.negateVisibility();
	}

	@Override
	public void render () {


		

		if((Gdx.input.isKeyJustPressed(InputsDefaults.exit)) || (exit.isObjectTouched()) ){ // If the escape key is pressed or the text is clicked in the main menu the game will shut down
			Gdx.app.exit();
			System.exit(0);
		}

		if((Gdx.input.isKeyJustPressed(InputsDefaults.highscores)) || (highscores.isObjectTouched())){ // If the H button is pressed or the text is clicked in the main menu the game will display the high scores
			this.changeMenuVisbility();
			// code to display the highscores


		}

		if((Gdx.input.isKeyJustPressed(InputsDefaults.settings)) || (settings.isObjectTouched())){ // If the S button is pressed or the text is clicked in the main menu, the game will display settings over the menu
			// The main menu is at position y = 3 so that the settings menu can be rendered over it if needed in position y = 4
			this.changeMenuVisbility();
			// code to display the settings menu

		}

		if((Gdx.input.isKeyJustPressed(InputsDefaults.start)) || (start.isObjectTouched())){ // If ENTER is pressed or the test is clicked, the game will be unpaused and the menu will disappear
			// Music changes from main menu music to game music
			// Main menu disappears and becomes invisible
			this.changeMenuVisbility();
			Pause = !Pause;

		}

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
			//the time step is within accumulator
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
