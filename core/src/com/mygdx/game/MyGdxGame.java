package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackCore.Pathfinding.*;
import com.mygdx.game.BlackScripts.*;

import com.mygdx.game.BlackScripts.CoreData.Inputs.InputsDefaults;
import com.badlogic.gdx.math.Rectangle;
import java.awt.*;
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
	RunInteract interact;
	MasterChef masterChef;

	GameObject obj;
	GameObject obj2;
	GameObject obj3;
	GameObject menu;

	LoadSounds soundLoader = new LoadSounds();


	BatchDrawer batch;

	public static PathfindingConfig pathfindingConfig;

	CustomerManager customerManager;
	public Boolean gameRestart = false;
	Boolean Pause = true;
	BTexture pauseTexture;
	GameObject pauseMenu;
	GameObject closeMenuText;
	GameObject playIcon;
	GameObject muteMusicIcon;
	GameObject muteMusicText;
	GameObject unmuteMusicIcon;
	GameObject unmuteMusicText;
	GameObject closeGameText;
	GameObject closeGameIcon;
	GameObject controlsText;
	boolean muteState = false;

	@Override
	public void create () {




		soundLoader.loadAllSounds(soundFrame);
		long id = soundFrame.playSound("Main Screen");
		soundFrame.setLooping(id, "Main Screen");

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

		GridSettings gridsets = new GridSettings();
		gridsets.scaleOfGrid = 25;
		gridsets.XSizeOfFloor = 1000;
		gridsets.ZSizeOfFloor = 600;

		GridPartition gPart = new GridPartition(gridsets);

		pathfindingConfig = new PathfindingConfig();
		pathfindingConfig.DiagonalCost = 1;
		pathfindingConfig.StepCost = 1;
		pathfindingConfig.DiagonalCost = 1;
		pathfindingConfig.PathMutliplier = 1;
		pathfindingConfig.maxIterations = 500;
		pathfindingConfig.DistanceCost = 1;


		// Makes a menu game object using the menu.png file as a texture and sets it to position y = 3, which brings it to the front
		menu = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("menu.png", 800, 415));
		menu.transform.position.y = 3;

		// Makes a pauseMenu game object using the pauseMenu.png file as a texture
		pauseMenu = new GameObject(new Rectangle(10,20,20,20),
				new BTexture("pauseMenu.png", 800, 415));
		pauseMenu.negateVisibility(); // makes it invisible initially, so it does not block the screen
		pauseMenu.transform.position.y = 3;

		// All the following game objects are used to display the pause menu options
		playIcon =  new GameObject(new Rectangle(10,20, 20, 20), new BTexture("play-button-arrowhead.png", 64, 64));
		playIcon.negateVisibility();
		playIcon.transform.position.x = 50;
		playIcon.transform.position.z = 275;
		playIcon.transform.position.y = 10;


		closeMenuText = new GameObject(new Rectangle(10,20, 20, 20), new BTexture("Resume.png", 180, 85));
		closeMenuText.negateVisibility();
		closeMenuText.transform.position.x = 140;
		closeMenuText.transform.position.z = 265;
		closeMenuText.transform.position.y = 10;

		muteMusicIcon =  new GameObject(new Rectangle(10,20, 20, 20), new BTexture("volume.png", 64, 64));
		muteMusicIcon.negateVisibility();
		muteMusicIcon.transform.position.x = 50;
		muteMusicIcon.transform.position.z = 155;
		muteMusicIcon.transform.position.y = 10;

		muteMusicText =  new GameObject(new Rectangle(10,20, 20, 20), new BTexture("muteSound.png", 190, 85));
		muteMusicText.negateVisibility();
		muteMusicText.transform.position.x = 140;
		muteMusicText.transform.position.z = 145;
		muteMusicText.transform.position.y = 10;

		unmuteMusicIcon =  new GameObject(new Rectangle(10,20, 20, 20), new BTexture("mute-speaker.png", 64, 64));
		unmuteMusicIcon.negateVisibility();
		unmuteMusicIcon.transform.position.x = 50;
		unmuteMusicIcon.transform.position.z = 155;
		unmuteMusicIcon.transform.position.y = 10;

		unmuteMusicText =  new GameObject(new Rectangle(10,20, 20, 20), new BTexture("unMuteSound.png", 230, 85));
		unmuteMusicText.negateVisibility();
		unmuteMusicText.transform.position.x = 140;
		unmuteMusicText.transform.position.z = 145;
		unmuteMusicText.transform.position.y = 10;

		closeGameText = new GameObject(new Rectangle(10,20, 20, 20), new BTexture("closeGame.png", 190, 85));
		closeGameText.negateVisibility();
		closeGameText.transform.position.x = 140;
		closeGameText.transform.position.z = 35;
		closeGameText.transform.position.y = 10;

		closeGameIcon = new GameObject(new Rectangle(10,20, 20, 20), new BTexture("cancel-button.png", 64, 64));
		closeGameIcon.negateVisibility();
		closeGameIcon.transform.position.x = 50;
		closeGameIcon.transform.position.z = 35;
		closeGameIcon.transform.position.y = 10;

		controlsText = new GameObject(new Rectangle(10,20, 20, 20), new BTexture("controls.png", 252, 350));
		controlsText.negateVisibility();
		controlsText.transform.position.x = 450;
		controlsText.transform.position.z = 15;
		controlsText.transform.position.y = 10;


		GameWorld.Instantiate(gPart);





		interact = new RunInteract(GameWorld);





		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,480);




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

		customerManager = new CustomerManager(GameWorld.Tables,gPart,GameWorld.TableRadius);
		CustomerManager.customermanager.setCustomerTexture(texture);

		CustomerManager.customermanager.WaitingPositions = GameWorld.CustomerWaitingLocations;
		CustomerManager.customermanager.spawningLocation = GameWorld.CustomerSpawnLocations;
		customerManager.BossTableSeats = GameWorld.BossSeats;



		ScriptManager.tryAppendLooseScript(customerManager);

	}

	/**
	 * This method negates the visibility of everything on the pause menu
	 * so that it can be shown or hidden
	 */
	public void negatePauseMenu(){
		pauseMenu.negateVisibility();
		closeMenuText.negateVisibility();
		playIcon.negateVisibility();
		closeGameText.negateVisibility();
		closeGameIcon.negateVisibility();
		controlsText.negateVisibility();
		if (!muteState){
			muteMusicIcon.negateVisibility();
			muteMusicText.negateVisibility();
		}else{
			unmuteMusicIcon.negateVisibility();
			unmuteMusicText.negateVisibility();
		}
	}

	@Override
	public void render () {

		if(Gdx.input.isKeyJustPressed(InputsDefaults.exit)){ // If the escape key is pressed in the main menu the game will shut down
			Gdx.app.exit();
			System.exit(0);
		}

		if(Gdx.input.isKeyJustPressed(InputsDefaults.highscores)){ // If the H button is pressed in the main menu the game will display the high scores
			;
			// code to display the highscores
		}

		if(Gdx.input.isKeyJustPressed(InputsDefaults.settings)){ // If the S button is pressed in the main menu, the game will display settings over the menu
			// The main menu is at position y = 3 so that the settings menu can be rendered over it if needed in position y = 4
			// code to display the settings menu
		}

		if(Gdx.input.isKeyJustPressed(InputsDefaults.start)&&menu.getVisibility()){ // If ENTER is pressed, the game will be unpaused and the menu will disappear
			// Music changes from main menu music to game music
			// Main menu disappears and becomes invisible
			//
			Pause = !Pause;
			menu.negateVisibility();
		}

		// If P is pressed while the game is running, the pause variable is negated and the menu will appear
		if (!menu.getVisibility() && Gdx.input.isKeyJustPressed(InputsDefaults.pause)){
			Pause = !Pause;
			negatePauseMenu();
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
			ScriptManager.RunUpdate(); // this is run when the game is not paused
		}else{
			// If the text is clicked, the game will be unpaused and the menu will disappear
			if (closeMenuText.isObjectTouched() || playIcon.isObjectTouched()){
				Pause = !Pause;
				negatePauseMenu();
			}
			// If the text is clicked or the X key is pressed the game will exit
			if (Gdx.input.isKeyJustPressed(InputsDefaults.exit) || closeGameText.isObjectTouched() || closeGameIcon.isObjectTouched()){
				this.dispose();
				Gdx.app.exit();
				System.exit(0);
			}
			// If the unmute/ mute icon or text is clicked, the music will be muted or unmuted
			if (Gdx.input.isKeyJustPressed(InputsDefaults.mute) || muteMusicIcon.isObjectTouched() || unmuteMusicIcon.isObjectTouched()
			|| muteMusicText.isObjectTouched() || unmuteMusicText.isObjectTouched()){
				if (muteState){
					soundFrame.SoundEngine.unMuteSound();
					unmuteMusicIcon.negateVisibility();
					muteMusicIcon.negateVisibility();
					unmuteMusicText.negateVisibility();
					muteMusicText.negateVisibility();
					muteState = !muteState;
				}
				else{
					soundFrame.SoundEngine.muteSound();
					unmuteMusicIcon.negateVisibility();
					muteMusicIcon.negateVisibility();
					unmuteMusicText.negateVisibility();
					muteMusicText.negateVisibility();
					muteState = !muteState;
				}
//				System.out.print(soundFrame.volume);

			}
		}
		camera.update();
		ScreenUtils.clear(1, 0, 0, 1);
		batch.RenderTextures(camera.combined);
	}

	public void FinishGame(float score){

	}

	@Override
	public void dispose () {
		gameObjectHandler.dispose();
		batch.dispose();
	}
}
