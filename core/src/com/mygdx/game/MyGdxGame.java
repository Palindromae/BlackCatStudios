package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackCore.Pathfinding.*;
import com.mygdx.game.BlackScripts.*;

import com.mygdx.game.BlackScripts.CoreData.Inputs.InputsDefaults;


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
	GameObject menu;
	LoadSounds soundLoader;
	SoundFrame soundFrame;
	GameObject settings;
	GameObject highscoresButton;
	GameObject start;
	GameObject exit;
	BatchDrawer batch;

	GameOver gameOver;

	public static PathfindingConfig pathfindingConfig;
	private static Integer fixedTimeCount = 0;

	CustomerManager customerManager;
	public Boolean gameRestart = false;
	public static Boolean gameEnded = false;
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
	GameObject gameTitle;
	GameObject pauseButton;
	GameObject orderPageButton;
	GameObject orderPageCloseButton;
	GameObject closeHighscoresIcon;
	public static GameObject orderAlert;
	public static GameObject orderPage;
	boolean orderPageShown = false;
	public static Boolean isGameRunning = false;

	boolean isHighscores = false;

	public static GameObject menuHighscores;



	HighScore highScores;

	public static Boolean getGameRunning(){
		return isGameRunning;
	}

	/**
	 * Creates the game and sets everything up
	 */
	@Override
	public void create() {

		soundFrame = new SoundFrame();
		soundLoader = new LoadSounds();
		soundLoader.loadAllSounds(soundFrame);

		Save.doFileCheck();

		long id = soundFrame.playSound("Main Screen");
		soundFrame.setLooping(id, "Main Screen");

		Runnable runnable = () -> Restart();

		highScores = new HighScore();
		gameOver = new GameOver(runnable);
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

		gameTitle = new GameObject( new Rectangle(10, 20, 20, 20), new BTexture("PiazzaPanic.png", 280, 113));
		gameTitle.transform.position.x = 60;
		gameTitle.transform.position.y = 5;
		gameTitle.transform.position.z = 355;


		// Makes a menu game object using the menu.png file as a texture and sets it to position y = 3, which brings it to the front
		menu = new GameObject( new Rectangle(10, 20, 20, 20), new BTexture("menu.png", 800, 480));
		menu.transform.position.y = 4;

		// Makes a pauseMenu game object using the pauseMenu.png file as a texture
		pauseMenu = new GameObject( new Rectangle(10,20,20,20),
				new BTexture("pauseMenu.png", 800, 480));
		pauseMenu.negateVisibility(); // makes it invisible initially, so it does not block the screen
		pauseMenu.transform.position.y = 6;

		pauseButton = new GameObject( new Rectangle(10,20,20,20),
				new BTexture("pause.png", 64, 46));
		pauseButton.transform.position.x = 735;
		pauseButton.transform.position.z = 415;
		pauseButton.transform.position.y = 3;

		// All the following game objects are used to display the pause menu options
		playIcon =  new GameObject( new Rectangle(10,20, 20, 20), new BTexture("play-button-arrowhead.png", 64, 64));
		playIcon.negateVisibility();
		playIcon.transform.position.x = 50;
		playIcon.transform.position.z = 325;
		playIcon.transform.position.y = 10;


		closePauseMenuText = new GameObject( new Rectangle(10,20, 20, 20), new BTexture("Resume.png", 180, 85));
		closePauseMenuText.negateVisibility();
		closePauseMenuText.transform.position.x = 140;
		closePauseMenuText.transform.position.z = 315;
		closePauseMenuText.transform.position.y = 10;

		muteMusicIcon =  new GameObject( new Rectangle(10,20, 20, 20), new BTexture("volume.png", 64, 64));
		muteMusicIcon.negateVisibility();
		muteMusicIcon.transform.position.x = 50;
		muteMusicIcon.transform.position.z = 205;
		muteMusicIcon.transform.position.y = 10;

		muteMusicText =  new GameObject( new Rectangle(10,20, 20, 20), new BTexture("muteSound.png", 190, 85));
		muteMusicText.negateVisibility();
		muteMusicText.transform.position.x = 140;
		muteMusicText.transform.position.z = 195;
		muteMusicText.transform.position.y = 10;

		settings = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("gear.png", 300, 64));
		settings.transform.position.y = 5;
		settings.transform.position.x = 75;
		settings.transform.position.z = 120;

		highscoresButton = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("trophy-for-sports.png", 300, 64));
		highscoresButton.transform.position.y = 5;
		highscoresButton.transform.position.x = 75;
		highscoresButton.transform.position.z = 205;

		exit = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("exitIcon.png", 300, 64));
		exit.transform.position.y = 5;
		exit.transform.position.x = 75;
		exit.transform.position.z = 40;

		start = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("StartGame.png", 300, 64));
		start.transform.position.y = 5;
		start.transform.position.x = 75;
		start.transform.position.z = 285;


		menuHighscores = new GameObject(new Rectangle(10, 20, 20, 20), new BTexture("White.png", 800, 480));
		menuHighscores.negateVisibility();
		menuHighscores.transform.position.y = 6;


		unmuteMusicIcon =  new GameObject( new Rectangle(10,20, 20, 20), new BTexture("mute-speaker.png", 64, 64));
		unmuteMusicIcon.negateVisibility();
		unmuteMusicIcon.transform.position.x = 50;
		unmuteMusicIcon.transform.position.z = 205;
		unmuteMusicIcon.transform.position.y = 10;

		unmuteMusicText =  new GameObject( new Rectangle(10,20, 20, 20), new BTexture("unMuteSound.png", 230, 85));
		unmuteMusicText.negateVisibility();
		unmuteMusicText.transform.position.x = 140;
		unmuteMusicText.transform.position.z = 195;
		unmuteMusicText.transform.position.y = 10;

		closeGameText = new GameObject( new Rectangle(10,20, 20, 20), new BTexture("closeGame.png", 190, 85));
		closeGameText.negateVisibility();
		closeGameText.transform.position.x = 140;
		closeGameText.transform.position.z = 85;
		closeGameText.transform.position.y = 10;

		closeGameIcon = new GameObject( new Rectangle(10,20, 20, 20), new BTexture("cancel-button.png", 64, 64));
		closeGameIcon.negateVisibility();
		closeGameIcon.transform.position.x = 50;
		closeGameIcon.transform.position.z = 85;
		closeGameIcon.transform.position.y = 10;

		closeHighscoresIcon = new GameObject( new Rectangle(10, 20, 20, 20), new BTexture("cancel-button.png", 64, 64));
		closeHighscoresIcon.negateVisibility();
		closeHighscoresIcon.transform.position.x = 700;
		closeHighscoresIcon.transform.position.z = 415;
		closeHighscoresIcon.transform.position.y = 7;

		controlsText = new GameObject( new Rectangle(10,20, 20, 20), new BTexture("controls.png", 400, 390));
//		controlsText.negateVisibility();
		controlsText.transform.position.x = 390;
		controlsText.transform.position.z = 65;
		controlsText.transform.position.y = 10;

		orderPageButton = new GameObject(new Rectangle(0, 500, 30, 50), new BTexture("PullOut.png", 25, 50));
		orderPageButton.transform.position.z = Gdx.graphics.getHeight()/2;
		orderPageButton.transform.position.y = 1;

		orderPageCloseButton = new GameObject( new Rectangle(200,500,30,50), new BTexture("PushIn.png", 25,50));
		orderPageCloseButton.transform.position.z = Gdx.graphics.getHeight()/2;
		orderPageCloseButton.transform.position.y = 4;
		orderPageCloseButton.transform.position.x = -100;
		orderPageButton.InvisPressAllowed = true;



		orderPage = new GameObject( new Rectangle(0, 0, 200, 400), new BTexture("OrderPage.png", 200, 400));
		orderPage.transform.position.x = -200;
		orderPage.transform.position.y = 100;

		orderAlert = new GameObject( new Rectangle(0,0,10,10), new BTexture("NewOrderBig.png", 25, 25));
		orderAlert.transform.position.x = 5;
		orderAlert.transform.position.z = Gdx.graphics.getHeight()/2 + 40;
		orderAlert.transform.position.y = 3;
		orderAlert.negateVisibility();

		ShowOrderText showText = new ShowOrderText();
		DisplayOrders x = new DisplayOrders();
		GameWorld.Instantiate(gPart);





		interact = new RunInteract(GameWorld);




		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);


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

		masterChef = new MasterChef(GameWorld.SpawnPointChef1,GameWorld.SpawnPointChef2);
		masterChef.camera = camera;
		masterChef.chefTex = texture;
		masterChef.KitchenPartition = gPart;

		ScriptManager.tryAppendLooseScript(masterChef);

		customerManager = new CustomerManager(GameWorld.Tables, gPart, GameWorld.TableRadius);
		//CustomerManager.customermanager.setCustomerTexture(texture);
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
	public void negatePauseMenu() {
		pauseMenu.negateVisibility();
		closePauseMenuText.negateVisibility();
		playIcon.negateVisibility();
		if (isGameRunning){
			closeGameText.negateVisibility();
			closeGameIcon.negateVisibility();
		}
		controlsText.negateVisibility();
		if (!soundFrame.muteState) {
			muteMusicIcon.negateVisibility();
			muteMusicText.negateVisibility();
		} else {
			unmuteMusicIcon.negateVisibility();
			unmuteMusicText.negateVisibility();
		}
	}

	/**
	 * Function to change start menu visibility when an action is taken from it
	 */
	public void changeMenuVisbility() {
		gameTitle.negateVisibility();
		settings.negateVisibility();
		start.negateVisibility();
		highscoresButton.negateVisibility();
		exit.negateVisibility();
		controlsText.negateVisibility();
		menu.negateVisibility();

	}


	boolean ShouldPause(){
		return pauseButton.isObjectTouched()  || Gdx.input.isKeyJustPressed(InputsDefaults.pause);
	}

	boolean CanStartGameFromMainMenu(){
		return start.isObjectTouched();
	}
	boolean CanPlay(boolean RecentlyPaused){
		return (Gdx.input.isKeyJustPressed(InputsDefaults.pause) && !RecentlyPaused) ||playIcon.isObjectTouched() || closePauseMenuText.isObjectTouched();
	}

	void CloseOrderMenu(){
		orderPageButton.transform.position.x = 0;
		orderPage.transform.position.x = -200;
		orderPageCloseButton.transform.position.x = -100;
		orderPageShown = false;
		orderPageButton.setVisibility(true);
		DisplayOrders.allSeen = true;
	}
	void OpenOrderMenu(){
		orderPageButton.transform.position.x = 200;
		orderPage.transform.position.x = 0;
		orderPageCloseButton.transform.position.x = 200;
		orderPageButton.setVisibility(false);
		orderPageShown = true;
		DisplayOrders.allSeen = true;
	}

	boolean shouldOpenSettings(){
		return settings.isObjectTouched();
	}
	@Override
	public void render () {
		if(!orderPageShown && DisplayOrders.allSeen){
			orderAlert.setVisibility(false);
		}else if(!orderPageShown && !DisplayOrders.allSeen){
			orderAlert.setVisibility(true);
		}else{
			orderAlert.setVisibility(false);
		}

		boolean noOrders = false;
		for(Integer key: DisplayOrders.displayOrders.orderDict.keySet()){
			if(!DisplayOrders.displayOrders.orderDict.get(key).isEmpty()){
				noOrders = true;
				break;
			}
		}
		if(!noOrders){
			orderAlert.setVisibility(false);
		}

		boolean recentlyPaused = false;
		if(orderPageButton.isObjectTouched() && !Pause){
			if(!orderPageShown){
				masterChef.AllowTouch = false;
				OpenOrderMenu();
			}else{
				masterChef.AllowTouch =false;
				CloseOrderMenu();
			}
		}

		if(menu.getVisibility() && !menuHighscores.getVisibility() && CanStartGameFromMainMenu()){ // If ENTER is pressed or the test is clicked, the game will be unpaused and the menu will disappear
			// Music changes from main menu music to game music
			// Main menu disappears and becomes invisible
			customerManager.setStartTime();
			this.changeMenuVisbility();
			Pause = !Pause;
			isGameRunning = true;
			masterChef.AllowTouch = false;


		}

		// if(Gdx.input.isKeyJustPressed(InputsDefaults.exit)){ // If the escape key is pressed in the main menu the game will shut down
		if(menu.getVisibility() && !menuHighscores.getVisibility() && ShouldCloseGame()){ // If the X key is pressed or the text is clicked in the main menu the game will shut down
			Gdx.app.exit();
			System.exit(0);
		}

		if(menu.getVisibility() && (highscoresButton.isObjectTouched())){ // If the H button is pressed or the text is clicked in the main menu the game will display the high scores
			changeMenuVisbility();
			this.menuHighscores.negateVisibility();
			closeHighscoresIcon.IsActiveAndVisible = true;
			isHighscores = !isHighscores;
			Save.gd.sortHighScoreMap();
			highScores.drawText();
			ShowOrderText.displayText();


//			if (isHighscores){
//				changeMenuVisbility();
//				menuHighscores.negateVisibility();
//			}
//			else{
//				changeMenuVisbility();
//				menuHighscores.negateVisibility();
//			}

			// code to display the highscores
		}




		if(menu.getVisibility()  && !menuHighscores.getVisibility() && shouldOpenSettings() ){ // If the S button is pressed or the text is clicked in the main menu, the game will display settings over the menu
			// The main menu is at position y = 3 so that the settings menu can be rendered over it if needed in position y = 4
			negatePauseMenu();
			this.changeMenuVisbility();
		}


		if (menuHighscores.getVisibility() && (closeHighscoresIcon.isObjectTouched())){
			menuHighscores.negateVisibility();
			closeHighscoresIcon.negateVisibility();
			isHighscores = false;
			Restart();
		}


		// If P is pressed while the game is running, the pause variable is negated and the menu will appear
		if (!Pause && ShouldPause()){

			if (orderPageShown) {
				CloseOrderMenu();
			}
			recentlyPaused = true;

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
				} else{
					if (isGameRunning) {
						CustomerManager.customermanager.accumPauseTime += fixedTime.dt;
					}
				}
				fixedTime.accumulator-= fixedTime.dt;
			}
		}
		if (!Pause){
			ScriptManager.RunUpdate();
		}else{
			if (!menu.getVisibility() && pauseMenu.getVisibility()){
				Boolean a = CanPlay(recentlyPaused);
				if (!isGameRunning && !menuHighscores.getVisibility() && a){
					negatePauseMenu(); // the pause menu is closed
					changeMenuVisbility(); // the start menu is displayed again
					masterChef.AllowTouch = false;

				}
				else if (!menuHighscores.getVisibility() && a){
					Pause = !Pause;
					negatePauseMenu();
					masterChef.AllowTouch = false;

				}
				// If the text is clicked or the X key is pressed the game will exit
				if (ShouldCloseGame() && closeGameText.getVisibility()){

					negatePauseMenu();
					Restart();
				}
				// If the unmute/ mute icon or text is clicked, the music will be muted or unmuted
				if (!menuHighscores.getVisibility() && ShouldMute()) {
					if (soundFrame.muteState) {

						soundFrame.unMuteSound();
						negateSoundVisibility();
						soundFrame.muteState = !soundFrame.muteState;
					} else {
						soundFrame.muteSound();

						negateSoundVisibility();
						soundFrame.muteState = !soundFrame.muteState;
					}
				}

			}
		}

		if(isHighscores){
			//if (menuHighscores.getVisibility() == false)
			//	menuHighscores.negateVisibility();
			if(menuHighscores.getVisibility() && (Gdx.input.isKeyJustPressed(InputsDefaults.highscores))){
				menuHighscores.IsActiveAndVisible = false;
				closeHighscoresIcon.IsActiveAndVisible = false;
				isHighscores = false;
				Restart();
			}
		}
		camera.update();
		ScreenUtils.clear(1, 1, 1, 1);
		batch.RenderTextures(camera.combined);


		if(orderPageShown && !Pause){
			ShowOrderText.displayText();
		}
		if(isHighscores)
			highScores.drawText();

		if(gameEnded){

			if(!menuHighscores.getVisibility()){
				menuHighscores.negateVisibility();

			}
			orderAlert.setVisibility(false);
			gameOver.drawText();
			gameOver.update(Gdx.graphics.getDeltaTime());

		}





	}

	boolean ShouldCloseGame(){
		return closeGameText.isObjectTouched() || closeGameIcon.isObjectTouched() || exit.isObjectTouched();
	}
	boolean ShouldMute(){
		return pauseMenu.getVisibility() && Gdx.input.isKeyJustPressed(InputsDefaults.mute) || muteMusicIcon.isObjectTouched() || unmuteMusicIcon.isObjectTouched()
				|| muteMusicText.isObjectTouched() || unmuteMusicText.isObjectTouched();
	}

	/**
	 * Flips the sound icons visibility
	 */
	void negateSoundVisibility(){
		unmuteMusicIcon.negateVisibility();
		muteMusicIcon.negateVisibility();
		unmuteMusicText.negateVisibility();
		muteMusicText.negateVisibility();
	}

	/**
	 * This reset everything in the game to a new game state
	 */
	public void Restart(){

		//Place chefs in area
		//Destroy chefs items
		masterChef.ResetSequence(GameWorld.SpawnPointChef1,GameWorld.SpawnPointChef2);
		//Remove items from machines
		GameWorld.Reset();
		//Reset customers, seen customers, wave number
		CustomerManager.customermanager.Reset();
		isGameRunning = false;
		//Go to menu or what ever it is suppose to
		if (!menu.getVisibility()){
			changeMenuVisbility();
		}

		isGameRunning = false;
	}

	/**
	 * This method is called when the game is paused
	 * @param p_s The score of the game
	 */
	public void FinishGame(Score p_s){
		if (orderPage.getVisibility()){
			this.CloseOrderMenu();
		}
		Save.gd.setTentativeScore((long) p_s.score);
		Save.gd.setTiming(p_s.timing);
		gameEnded = true;
		Pause = true;
	}

	/*
	Dispose of contained info
	 */
	@Override
	public void dispose () {
		gameObjectHandler.dispose();
		batch.dispose();
		ShowOrderText.disposeOf();
	}
}
