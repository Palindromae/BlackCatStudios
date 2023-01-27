package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackCore.Pathfinding.*;
import com.mygdx.game.BlackScripts.*;

import com.mygdx.game.BlackScripts.CoreData.Inputs.InputsDefaults;
import com.mygdx.game.CoreData.Items.Items;
import jdk.javadoc.internal.doclets.formats.html.markup.Script;

import java.awt.*;
import java.util.List;
import com.badlogic.gdx.math.Rectangle;


public class MyGdxGame extends ApplicationAdapter {
	public static OrthographicCamera camera;
	BTexture texture;
	GameObjectHandler gameObjectHandler;

	BlackScriptManager ScriptManager;
	FixedTimeController fixedTime;
	CollisionDetection collisionDetection;
	PhysicsSuperController physicsController;

	CreateGameWorld GameWorld;
	RunInteract interact;
	MasterChef masterChef;

	GameObject obj;
	GameObject obj2;
	GameObject obj3;
	GameObject menu;
	LoadSounds soundLoader = new LoadSounds();
	SoundFrame soundFrame = new SoundFrame();
	GameObject settings;
	GameObject highscores;
	GameObject start;
	GameObject exit;
	BatchDrawer batch;

	public static PathfindingConfig pathfindingConfig;

	CustomerManager customerManager;
	public Boolean gameRestart = false;
	Boolean Pause = true;
	BTexture pauseTexture;
	GameObject pauseMenu;
	GameObject closePauseMenuText;
	GameObject playIcon;
	GameObject muteMusicIcon;
	GameObject muteMusicText;
	GameObject unmuteMusicIcon;
	GameObject unmuteMusicText;
	GameObject closeGameText;
	GameObject closeGameIcon;
	GameObject controlsText;
	GameObject closeMenu;
	GameObject muteMusic;
	GameObject unmuteMusic;
	GameObject menuControls;
	GameObject pauseButton;
	boolean muteState = false;
	Boolean isGameRunning = false;
    HighScore highScore;

	@Override
	public void create () {

		soundFrame = new SoundFrame();
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
		menu = new GameObject((Shape2D) new Rectangle(10, 20, 20, 20), new BTexture("menu.png", 800, 480));
		menu.transform.position.y = 4;

		// Makes a pauseMenu game object using the pauseMenu.png file as a texture
		pauseMenu = new GameObject((Shape2D) new Rectangle(10,20,20,20),
				new BTexture("pauseMenu.png", 800, 480));
		pauseMenu.negateVisibility(); // makes it invisible initially, so it does not block the screen
		pauseMenu.transform.position.y = 4;

		pauseButton = new GameObject((Shape2D) new Rectangle(10,20,20,20),
				new BTexture("pause.png", 64, 46));
		pauseButton.transform.position.x = 735;
		pauseButton.transform.position.z = 415;
		pauseButton.transform.position.y = 3;

		// All the following game objects are used to display the pause menu options
		playIcon =  new GameObject((Shape2D) new Rectangle(10,20, 20, 20), new BTexture("play-button-arrowhead.png", 64, 64));
		playIcon.negateVisibility();
		playIcon.transform.position.x = 50;
		playIcon.transform.position.z = 325;
		playIcon.transform.position.y = 10;


		closePauseMenuText = new GameObject((Shape2D) new Rectangle(10,20, 20, 20), new BTexture("Resume.png", 180, 85));
		closePauseMenuText.negateVisibility();
		closePauseMenuText.transform.position.x = 140;
		closePauseMenuText.transform.position.z = 315;
		closePauseMenuText.transform.position.y = 10;

		muteMusicIcon =  new GameObject((Shape2D) new Rectangle(10,20, 20, 20), new BTexture("volume.png", 64, 64));
		muteMusicIcon.negateVisibility();
		muteMusicIcon.transform.position.x = 50;
		muteMusicIcon.transform.position.z = 205;
		muteMusicIcon.transform.position.y = 10;

		muteMusicText =  new GameObject((Shape2D) new Rectangle(10,20, 20, 20), new BTexture("muteSound.png", 190, 85));
		muteMusicText.negateVisibility();
		muteMusicText.transform.position.x = 140;
		muteMusicText.transform.position.z = 195;
		muteMusicText.transform.position.y = 10;

		menuControls = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("controls.png", 250, 350));
		menuControls.transform.position.y = 5;
		menuControls.transform.position.x = 500;
		menuControls.transform.position.z = 25;

		settings = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("gear.png", 65, 70));
		settings.transform.position.y = 5;
		settings.transform.position.x = 75;
		settings.transform.position.z = 125;

		highscores = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("trophy-for-sports.png", 300, 70));
		highscores.transform.position.y = 5;
		highscores.transform.position.x = 75;
		highscores.transform.position.z = 200;

		exit = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("exitIcon.png", 65, 70));
		exit.transform.position.y = 5;
		exit.transform.position.x = 75;
		exit.transform.position.z = 50;

		start = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("play-button-arrowhead.png", 300, 70));
		start.transform.position.y = 5;
		start.transform.position.x = 75;
		start.transform.position.z = 275;


		unmuteMusicIcon =  new GameObject((Shape2D) new Rectangle(10,20, 20, 20), new BTexture("mute-speaker.png", 64, 64));
		unmuteMusicIcon.negateVisibility();
		unmuteMusicIcon.transform.position.x = 50;
		unmuteMusicIcon.transform.position.z = 205;
		unmuteMusicIcon.transform.position.y = 10;

		unmuteMusicText =  new GameObject((Shape2D) new Rectangle(10,20, 20, 20), new BTexture("unMuteSound.png", 230, 85));
		unmuteMusicText.negateVisibility();
		unmuteMusicText.transform.position.x = 140;
		unmuteMusicText.transform.position.z = 195;
		unmuteMusicText.transform.position.y = 10;

		closeGameText = new GameObject((Shape2D) new Rectangle(10,20, 20, 20), new BTexture("closeGame.png", 190, 85));
		closeGameText.negateVisibility();
		closeGameText.transform.position.x = 140;
		closeGameText.transform.position.z = 85;
		closeGameText.transform.position.y = 10;

		closeGameIcon = new GameObject((Shape2D) new Rectangle(10,20, 20, 20), new BTexture("cancel-button.png", 64, 64));
		closeGameIcon.negateVisibility();
		closeGameIcon.transform.position.x = 50;
		closeGameIcon.transform.position.z = 85;
		closeGameIcon.transform.position.y = 10;

		controlsText = new GameObject((Shape2D) new Rectangle(10,20, 20, 20), new BTexture("controls.png", 252, 350));
		controlsText.negateVisibility();
		controlsText.transform.position.x = 450;
		controlsText.transform.position.z = 65;
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
		customerManager.EndGameCommand = this::FinishGame;
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
		closePauseMenuText.negateVisibility();
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

	/**
	 * Function to change menu visibility when an action is taken from it
	 */
	public void changeMenuVisbility() {
		settings.negateVisibility();
		start.negateVisibility();
		highscores.negateVisibility();
		exit.negateVisibility();
		menuControls.negateVisibility();
		menu.negateVisibility();

	}


	public void Restart(){

		//Place chefs in area
		//Destroy chefs items
		masterChef.ResetSequence(GameWorld.SpawnPointChef1,GameWorld.SpawnPointChef2);
		//Remove items from machines
		GameWorld.Reset();
		//Reset customers, seen customers, wave number
		CustomerManager.customermanager.Reset();

		//Go to menu or what ever it is suppose to
		changeMenuVisbility();
		isGameRunning = false;
	}

	@Override
	public void render () {

		if(menu.getVisibility() && ((Gdx.input.isKeyJustPressed(InputsDefaults.start)) || (start.isObjectTouched()))){ // If ENTER is pressed or the test is clicked, the game will be unpaused and the menu will disappear
			// Music changes from main menu music to game music
			// Main menu disappears and becomes invisible
			this.changeMenuVisbility();
			isGameRunning = !isGameRunning;
			Pause = !Pause;

		}

		// if(Gdx.input.isKeyJustPressed(InputsDefaults.exit)){ // If the escape key is pressed in the main menu the game will shut down
		if(menu.getVisibility() && ((Gdx.input.isKeyJustPressed(InputsDefaults.exit)) || (exit.isObjectTouched()))){ // If the X key is pressed or the text is clicked in the main menu the game will shut down
			Gdx.app.exit();
			System.exit(0);
		}

		if(menu.getVisibility() && ((Gdx.input.isKeyJustPressed(InputsDefaults.highscores)) || (highscores.isObjectTouched()))){ // If the H button is pressed or the text is clicked in the main menu the game will display the high scores
			this.changeMenuVisbility();
			// code to display the highscores


		}

		if(menu.getVisibility() && ((Gdx.input.isKeyJustPressed(InputsDefaults.settings)) || (settings.isObjectTouched()))){ // If the S button is pressed or the text is clicked in the main menu, the game will display settings over the menu
			// The main menu is at position y = 3 so that the settings menu can be rendered over it if needed in position y = 4
			negatePauseMenu();
			this.changeMenuVisbility();
			// code to display the settings menu

		}


		// If P is pressed while the game is running, the pause variable is negated and the menu will appear
		if (!Pause && (Gdx.input.isKeyJustPressed(InputsDefaults.pause) || pauseButton.isObjectTouched())){
			if (!isGameRunning){
				negatePauseMenu(); // the pause menu is closed
				this.changeMenuVisbility(); // the main menu is opened again
			}else {
				Pause = !Pause;
				negatePauseMenu();
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
			ScriptManager.RunUpdate(); // this is run when the game is not paused
		}else{
			if (!menu.getVisibility()){
				if (!isGameRunning && (Gdx.input.isKeyJustPressed(InputsDefaults.pause) || playIcon.isObjectTouched() || closePauseMenuText.isObjectTouched())){
					negatePauseMenu(); // the pause menu is closed
					changeMenuVisbility(); // the start menu is displayed again
				}
				else if (isGameRunning && (Gdx.input.isKeyJustPressed(InputsDefaults.pause) || playIcon.isObjectTouched() || closePauseMenuText.isObjectTouched())){
					Pause = !Pause;
					negatePauseMenu();
				}
				// If the text is clicked or the X key is pressed the game will exit
				if (Gdx.input.isKeyJustPressed(InputsDefaults.exit) || closeGameText.isObjectTouched() || closeGameIcon.isObjectTouched()){
//					this.dispose();
//					Gdx.app.exit();
//					System.exit(0);
					negatePauseMenu();
					Restart();
				}
				// If the unmute/ mute icon or text is clicked, the music will be muted or unmuted
				if (!menu.getVisibility() && (Gdx.input.isKeyJustPressed(InputsDefaults.mute) || muteMusicIcon.isObjectTouched() || unmuteMusicIcon.isObjectTouched()
						|| muteMusicText.isObjectTouched() || unmuteMusicText.isObjectTouched())) {
					if (muteState) {
						soundFrame.unMuteSound();
						unmuteMusicIcon.negateVisibility();
						muteMusicIcon.negateVisibility();
						unmuteMusicText.negateVisibility();
						muteMusicText.negateVisibility();
						muteState = !muteState;
					} else {
						soundFrame.muteSound();
						unmuteMusicIcon.negateVisibility();
						muteMusicIcon.negateVisibility();
						unmuteMusicText.negateVisibility();
						muteMusicText.negateVisibility();
						muteState = !muteState;
					}
					System.out.print(soundFrame.volume);
				}
				}


			}
		camera.update();
		ScreenUtils.clear(1, 0, 0, 1);
		batch.RenderTextures(camera.combined);
	}




	public void FinishGame(float score){
		System.out.println("Ended the game with score: " + score);
	}

	@Override
	public void dispose () {
		gameObjectHandler.dispose();
		batch.dispose();
	}
}
