/*
 * Copyright (c) 2018, SomeoneWithAnInternetConnection
 * Copyright (c) 2018, oplosthee <https://github.com/oplosthee>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.hRoguesDen;

import com.google.inject.Provides;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.api.queries.TileQuery;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.botutils.BotUtils;
import org.pf4j.Extension;
import net.runelite.api.ItemID;

import static net.runelite.api.MenuAction.GAME_OBJECT_FIRST_OPTION;
import static net.runelite.api.MenuAction.ITEM_USE_ON_NPC;


@Extension
@PluginDependency(BotUtils.class)

@PluginDescriptor(
	name = "hRoguesDen",
	enabledByDefault = false,
	description = "rogue dens",
	tags = {"rogue","den","hass","roguesden"}

)
@Slf4j
public class hRoguesDenPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private hRoguesDenConfiguration config;

	@Inject
	private BotUtils utils;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ItemManager itemManager;

	public NPC guard;
	boolean doingDoors;
	int whatDoor;
	WallObject targetWallObject;
	GameObject targetObject;
	Player player;
	boolean pluginStarted;
	boolean inRoguesDen;
	int timeout;
	long sleepLength;
	int tickLength;
	String state;
	MenuEntry targetMenu;
	boolean flashReady;
	boolean waitPlayerEnter;
	LocalPoint beforeLoc = new LocalPoint(0, 0);
	boolean goingToEnter;
	WorldPoint firstLocation = new WorldPoint(3056, 4992, 1);
	boolean doFirstObst;
	boolean waitFinish;
	boolean doSecObst;
	boolean thirdObst;
	boolean fourthObst;
	boolean pickedUpObject;
	boolean pickedUpFlash;
	boolean useOnGuard;
	boolean runFast;
	boolean stunned;
	WorldPoint secondLocation = new WorldPoint(3048, 4997, 1);
	WorldPoint thirdLocation = new WorldPoint(3039, 4999, 1);
	WorldPoint fourthLocation = new WorldPoint(3029, 5003, 1);
	WorldPoint fifthLocation = new WorldPoint(3023, 5001, 1);
	WorldPoint sixthLocation = new WorldPoint(3011, 5005, 1);
	WorldPoint seventhLocation = new WorldPoint(3004, 5003, 1);
	WorldPoint eigthLocation = new WorldPoint(2988, 5004, 1);
	WorldPoint ninthLocation = new WorldPoint(2976, 5007, 1);
	WorldPoint tenthLocation = new WorldPoint(2969, 5019, 1);
	WorldPoint tenthLocResult = new WorldPoint(2967, 5019, 1);
	WorldPoint eleventhLocation = new WorldPoint(2958, 5035, 1);
	WorldPoint eleventhPreLocation = new WorldPoint(2958, 5028, 1);
	WorldPoint twelfthLocation = new WorldPoint(2962, 5050, 1);
	WorldPoint thirtheenlocation = new WorldPoint(2963, 5056, 1);
	WorldPoint forteenLocation = new WorldPoint(2957, 5072, 1);
	WorldPoint fifteenLocation = new WorldPoint(2957, 5076, 1);
	WorldPoint sixteenLocation = new WorldPoint(2955, 5090, 1);
	WorldPoint seventeenLocation = new WorldPoint(2955, 5098, 1);
	WorldPoint ateenLocation = new WorldPoint(2963, 5105, 1);
	WorldPoint ninteenLocation = new WorldPoint(2972, 5094, 1);
	WorldPoint twentyLocation = new WorldPoint(2972, 5093, 1);
	WorldPoint xxi = new WorldPoint(2991, 5087, 1);
	WorldPoint xxii = new WorldPoint(2993, 5088, 1);
	WorldPoint xxiii = new WorldPoint(3006, 5088, 1);
	WorldPoint xxiv = new WorldPoint(3018, 5080, 1);
	WorldPoint xxiv2 = new WorldPoint(3023,5082,1);
	WorldPoint xxv = new WorldPoint(3024,5082,1);
	WorldPoint agOne = new WorldPoint(3038,5068,1);
	WorldPoint agTwo = new WorldPoint(3038,5047,1);
	WorldPoint agThree = new WorldPoint(3028,5034,1);
	WorldPoint agFour = new WorldPoint(3014,5033,1);
	WorldPoint agFive = new WorldPoint(3009,5033,1);
	WorldPoint agSix = new WorldPoint(3000,5034,1);
	WorldPoint agSeven = new WorldPoint(2992,5045,1);
	WorldPoint agEight = new WorldPoint(2992,5067,1);
	WorldPoint agNine = new WorldPoint(3009,5063,1);
	WorldPoint agTen = new WorldPoint(3028,5056,1);
	WorldPoint agEleven= new WorldPoint(3028,5051,1);
	WorldPoint agTwelve= new WorldPoint(3028,5047,1);
	WorldPoint agThirteen= new WorldPoint(3018,5048,1);
	WorldPoint waitLocation;
	WorldPoint doorLoc;
	boolean test;
	boolean test2;
	boolean hitGUard;
	boolean doRun;
	@Provides
	hRoguesDenConfiguration provideConfig(ConfigManager configManager) {
		return configManager.getConfig(hRoguesDenConfiguration.class);
	}

	@Override
	protected void startUp() {
		hitGUard = false;
		doRun = false;

		//log.info("plogin Started");

	}

	@Override
	protected void shutDown() {
		test2 = false;
		test = false;
	}

	@Subscribe
	public void onConfigButtonClicked(ConfigButtonClicked event) {

		if (!event.getGroup().equalsIgnoreCase("hRoguesDen")) {
			return;
		}

		if (event.getKey().equals("startButton")) {
			log.info("starting");
			pluginStarted = true;
			inRoguesDen = true;
			goingToEnter = false;
			pickedUpObject = false;
			pickedUpFlash  = false;
			doRun = false;
			flashReady = false;
			doingDoors = false;
			hitGUard = false;
			whatDoor = 0;
			test = false;
			test2 = false;
		} else if (event.getKey().equals("stopButton")) {
			log.info("stopping");
			pluginStarted = false;
			inRoguesDen = false;

		}
		if(event.getKey().equals("test")){
			log.info("test clicked");
			test = !test;
		}
		if(event.getKey().equals("testButton2")){
			log.info("test2 clicked");
			test2 = true;
		}
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		if(test){
			if(!hitGUard) {
				calculateDistance();
			}else{
				hitAndRun();
			}
			return;
		}
		if(test2){
			calculateDistance();
			return;
		}

		if (!pluginStarted) {
			return;
		}
		if (timeout > 0) {
			timeout--;


		} else {
			player = client.getLocalPlayer();
			if (client != null && player != null && client.getGameState() == GameState.LOGGED_IN) {
				state = getState();

				switch (state) {
					case "ENTERDEN":
						utils.sendGameMessage("Starting run of Rogues Den, if you don't have full run STOP!");
						log.info("GOing to go in");
						enterDoor(7256);
						inRoguesDen = false;
						goingToEnter = true;
						break;
					case "WAITENTER":
						log.info("waiting to enter");
						if (checkIfEntered()) {

							goingToEnter = false;
							doFirstObst = true;
						} else {
							timeout = tickDelay();
						}
						break;

					case "OB1":
						log.info("Doing first ob");
						doObstacle(7251);
						doFirstObst = false;
						waitFinish = true;
						waitLocation = secondLocation;
						timeout = tickDelay();
						break;
					case "WAIT":
						//log.info("waiting to finsih");
						if (checkIfFinished(waitLocation)) {
							waitFinish = false;
							timeout = tickDelay();
							//log.info("We are at loocation of second object");
							break;
						} else {
							//log.info("we are not at locaiotn of second");
							timeout = tickDelay();
							break;
						}
					case "OB2":
						log.info("Doing ob 2");
						utils.walk(thirdLocation, 0, sleepDelay());
						waitLocation = thirdLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;

					case "OB3":
						log.info("Doing ob3");
						utils.walk(fourthLocation, 0, sleepDelay());
						waitLocation = fourthLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;

					case "OB4":
						log.info("we are at fourth obst");
						enterDoor(7255);
						waitLocation = fifthLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB5":
						log.info("we are at 5th");
						utils.walk(sixthLocation, 0, sleepDelay());
						waitLocation = sixthLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB6":
						log.info("sixth spot");
						utils.walk(seventhLocation, 0, sleepDelay());
						waitLocation = seventhLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB7":
						log.info("we are on ob7 spot");
						doObstacle(7240);
						waitLocation = eigthLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;

					case "OB8":
						log.info("we are on OB8");
						utils.walk(ninthLocation, 0, sleepDelay());
						waitLocation = ninthLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB9":
						log.info("we are on OB9");
						utils.walk(tenthLocation, 0, sleepDelay());
						waitLocation = tenthLocResult;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB10":
						log.info("we are on OB10");
						doGround(7239);
						waitLocation = eleventhPreLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB10.5":
						log.info("we are on OB10.5");
						doGround(7239);
						waitLocation = eleventhLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB11":
						log.info("we are on OB11");
						utils.walk(twelfthLocation, 0, sleepDelay());
						waitLocation = twelfthLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB12":
						log.info("we are on OB12");
						utils.walk(thirtheenlocation,0,sleepDelay());
						waitLocation =thirtheenlocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB13":
						log.info("we are on OB13");
						//utils.walk(thirtheenlocation,0,sleepDelay());
						enterDoor(7219);
						waitLocation = forteenLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB14":
						WorldPoint loko = new WorldPoint(2957,5074,1);
						log.info("we are on OB14");
						utils.walk(loko,0,sleepDelay());
						//enterDoor(7219);
						waitLocation = fifteenLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB15":
						log.info("we are on OB15");
						utils.walk(sixteenLocation,0,sleepDelay());
						waitLocation =sixteenLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB16":
						log.info("we are on OB16");
						//utils.walk(sixteenLocation,0,sleepDelay());
						enterDoor(7219);
						waitLocation =seventeenLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB17":
						log.info("we are on OB17");
						utils.walk(ateenLocation,0,sleepDelay());
						//enterDoor(7219);
						waitLocation =ateenLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB18":
						WorldPoint puta = new WorldPoint(2972,5097,1);
						log.info("we are on OB18");
						//enterDoor(7219);
						enterSpecificDoor(7219,puta);
						waitLocation =ninteenLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB19":
						log.info("we are on OB19");
						enterDoor(7255);
						waitLocation =twentyLocation;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB20":
						WorldPoint chincialla = new WorldPoint(2983,5087,1);
						log.info("we are on OB20");
						doSpecificObstacle(7240,chincialla);
						waitLocation =xxi;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB21":

						log.info("we are on OB21");
						GameObject objObstacle = utils.findNearestGameObject(7249);
						if (objObstacle != null) {
							targetMenu = new MenuEntry("", "", objObstacle.getId(), 1001, objObstacle.getSceneMinLocation().getX(), objObstacle.getSceneMinLocation().getY(), false);
							utils.setMenuEntry(targetMenu);
							utils.delayMouseClick(objObstacle.getConvexHull().getBounds(), sleepDelay());

						}else{
							log.info("not found");
						}
						waitLocation =xxii;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB22":

						log.info("we are on OB22");
						utils.walk(xxiii,0,sleepDelay());
						waitLocation =xxiii;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB23":

						log.info("we are on OB23");
						utils.walk(xxiv,0,sleepDelay());
						waitLocation =xxiv;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB24":

						log.info("we are on OB24");
						pickUpObj(5568);
						pickedUpObject = true;
						waitLocation =xxiv;
						waitFinish = true;
						timeout = tickDelay();

						break;
					case "OB24clickdoor":

						log.info("we are on OB24 door");
						enterDoor(7234);
						waitLocation = xxiv2;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB24puz":

						log.info("we are on OB24 puzzle");
						solvePuzzle();
						waitLocation = xxv;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB25":

						log.info("we are on OB25");
						whatDoor++;
						doDoors(whatDoor);
						doingDoors = true;
						break;
					case "MOVING":
						utils.handleRun(10, 5);
						log.info("runningn");
						timeout = tickDelay();
						if(whatDoor == 9){
							doingDoors = false;
							waitFinish =  true;
							waitLocation = agOne;
						}

						break;
					case "OB26":

						log.info("we are on OB26");
						utils.walk(agTwo,0,sleepDelay());
						waitLocation =agTwo;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB27":

						log.info("we are on OB27");
						utils.walk(agThree,0,sleepDelay());
						waitLocation =agThree;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB28":

						log.info("we are on OB28");
						//utils.walk(agThree,0,sleepDelay());
						enterDoor(7255);
						waitLocation =agFour;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB29":

						log.info("we are on OB29");
						//utils.walk(agThree,0,sleepDelay());
						WorldPoint locs = new WorldPoint(3010,5033,1);
						enterSpecificDoor(7255,locs);
						waitLocation =agFive;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB30":

						log.info("we are on OB30");
						utils.walk(agSix,0,sleepDelay());
						waitLocation =agSix;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB31":

						log.info("we are on OB31");
						utils.walk(agSeven,0,sleepDelay());
						waitLocation = agSeven;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB32":

						log.info("we are on OB32");
						utils.walk(agEight,0,sleepDelay());
						waitLocation = agEight;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB33":

						log.info("we are on OB33");
						utils.walk(agNine,0,sleepDelay());
						waitLocation = agNine;
						waitFinish = true;
						timeout = tickDelay();
						break;
					case "OB33.5":

						log.info("we are on OB33.5");
						pickUpFlash(5559);
						pickedUpFlash = true;
						waitLocation = agNine;
						waitFinish = true;
						timeout = tickDelay()+5;
						break;
					case "OB34":
						//flashing the guard and running
						if(!hitGUard) {
							calculateDistance();
						}else{
							hitAndRun();
						}
						break;
					case "OB35":

						log.info("we are on OB35");
						utils.walk(agEleven,0,sleepDelay());
						waitLocation = agEleven;
						waitFinish = true;
						timeout = tickDelay();
						break;

					case "OB36":
						log.info("we are on OB36");
						utils.walk(agTwelve,0,sleepDelay());
						waitLocation = agTwelve;
						waitFinish = true;
						timeout = tickDelay();
						break;

					case "OB37":
						log.info("we are on OB37");
						utils.walk(agThirteen,0,sleepDelay());
						waitLocation = agThirteen;
						waitFinish = true;
						timeout = tickDelay();
						break;

					case "OB38":
						solveLock();
						log.info("we are done");
						utils.sendGameMessage("Plugin is done a run, refill run and click Start to go again");
						pluginStarted = false;
						break;

					case "CURRENTPROG":

						//log.info("u will have to do rest on ur own");

						calculateDistance();


						break;

				}
			}
		}
		beforeLoc = player.getLocalLocation();


	}

	private String getState() {
		if (inRoguesDen) {
			return "ENTERDEN";

		}
		if (utils.isMoving(beforeLoc) && doingDoors)
		{
			timeout = tickDelay();
			return "MOVING";
		}
		if(!utils.isMoving(beforeLoc) && doingDoors){
			return "OB25";
		}
		if(flashReady){
			return "FLASHED";
		}
		if (goingToEnter) {
			return "WAITENTER";
		}
		if(runFast){
			return "RUNNIN";
		}
		if (doFirstObst) {
			return "OB1";
		}
		if (waitFinish) {
			return "WAIT";
		}
		if (!waitFinish) {
			return determineWhichObst();
		} else {
			return "UNKNOWN";

		}
	}

	private String determineWhichObst() {
		if (client.getLocalPlayer().getWorldLocation().equals(secondLocation)) {
			return "OB2";
		} else if (client.getLocalPlayer().getWorldLocation().equals(thirdLocation)) {
			return "OB3";
		} else if (client.getLocalPlayer().getWorldLocation().equals(fourthLocation)) {
			return "OB4";
		} else if (client.getLocalPlayer().getWorldLocation().equals(fifthLocation)) {
			return "OB5";
		} else if (client.getLocalPlayer().getWorldLocation().equals(sixthLocation)) {
			return "OB6";
		} else if (client.getLocalPlayer().getWorldLocation().equals(seventhLocation)) {
			return "OB7";
		} else if (client.getLocalPlayer().getWorldLocation().equals(eigthLocation)) {
			return "OB8";
		} else if (client.getLocalPlayer().getWorldLocation().equals(ninthLocation)) {
			return "OB9";
		} else if (client.getLocalPlayer().getWorldLocation().equals(tenthLocResult)) {
			return "OB10";
		} else if (client.getLocalPlayer().getWorldLocation().equals(eleventhLocation)) {
			return "OB11";
		} else if (client.getLocalPlayer().getWorldLocation().equals(twelfthLocation)) {
			return "OB12";
		} else if (client.getLocalPlayer().getWorldLocation().equals(eleventhPreLocation)) {
			return "OB10.5";
		} else if (client.getLocalPlayer().getWorldLocation().equals(thirtheenlocation)) {
			return "OB13";
		}else if (client.getLocalPlayer().getWorldLocation().equals(forteenLocation)) {
			return "OB14";
		} else if (client.getLocalPlayer().getWorldLocation().equals(fifteenLocation)) {
			return "OB15";
		}else if (client.getLocalPlayer().getWorldLocation().equals(sixteenLocation)) {
			return "OB16";
		}else if (client.getLocalPlayer().getWorldLocation().equals(seventeenLocation)) {
			return "OB17";
		}else if (client.getLocalPlayer().getWorldLocation().equals(ateenLocation)) {
			return "OB18";
		}else if (client.getLocalPlayer().getWorldLocation().equals(ninteenLocation)) {
			return "OB19";
		}else if (client.getLocalPlayer().getWorldLocation().equals(twentyLocation)) {
			return "OB20";
		}else if (client.getLocalPlayer().getWorldLocation().equals(xxi)) {
			return "OB21";
		}else if (client.getLocalPlayer().getWorldLocation().equals(xxii)) {
			return "OB22";
		}else if (client.getLocalPlayer().getWorldLocation().equals(xxiii)) {
			return "OB23";
		}else if (client.getLocalPlayer().getWorldLocation().equals(xxiv) && !pickedUpObject) {
			return "OB24";
		}else if (client.getLocalPlayer().getWorldLocation().equals(xxiv) && pickedUpObject) {
			return "OB24clickdoor";
		}else if (client.getLocalPlayer().getWorldLocation().equals(xxiv2)) {
			return "OB24puz";
		}else if (client.getLocalPlayer().getWorldLocation().equals(xxv)) {
			return "OB25";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agOne)){
			return "OB26";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agTwo)){
			return "OB27";
		}
		else if(client.getLocalPlayer().getWorldLocation().equals(agThree)){
			return "OB28";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agFour)){
			return "OB29";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agFive)){
			return "OB30";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agSix)){
			return "OB31";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agSeven)){
			return "OB32";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agEight)){
			return "OB33";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agNine) && !pickedUpFlash){
			return "OB33.5";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agNine) && pickedUpFlash){
			return "OB34";
			//return "CURRENTPROG";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agTen) ){
			return "OB35";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agEleven) ){
			return "OB36";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agTwelve) ){
			return "OB37";
		}else if(client.getLocalPlayer().getWorldLocation().equals(agThirteen) ){
			return "OB38";
		}


		else {
			return "UNKNOWN";
		}
	}

	private void hitAndRun(){
		log.info("Starting hit and run");
		WidgetItem flash = utils.getInventoryWidgetItem(5559);

		log.info(String.valueOf(flash.getQuantity()) + " si the number of flashes");
		if(flash.getQuantity()<5){
			utils.walk(agTen,0,20);
			test = false;
			timeout = 1;
			return;
		}
		if(flash == null){
			log.info("Flash is null");
			return;
		}
		NPC guard = utils.findNearestNpc(3191);
		if (guard != null) {
			targetMenu = new MenuEntry("", "", 20487, ITEM_USE_ON_NPC.getId(), 0, 0, false);
			utils.setModifiedMenuEntry(targetMenu,flash.getId(),flash.getIndex(),MenuAction.ITEM_USE_ON_NPC.getId());
			utils.delayMouseClick(guard.getConvexHull().getBounds(), sleepDelay());
			return;

		}
	}

	private void solveLock(){

		DecorativeObject das;
		das = utils.findNearestDecorObject(7237);

		if(das == null){
			log.info("it is null");
		return;
		}
		targetMenu = new MenuEntry("","",7237,GAME_OBJECT_FIRST_OPTION.getId(),das.getLocalLocation().getSceneX(),das.getLocalLocation().getSceneY(),false);
		utils.setMenuEntry(targetMenu);
		utils.delayMouseClick(das.getConvexHull().getBounds(),sleepDelay());
		return;

	}


	private void calculateDistance(){
		player = client.getLocalPlayer();
		guard = utils.findNearestNpc(3191);
		if(guard == null){
			log.info("Guard is null");
			return;
		}

		WorldPoint guardPos = guard.getWorldLocation();
		System.out.println(guardPos);
		int dist = guardPos.distanceTo(player.getWorldLocation());
		log.info("The distance is " + dist);
		if(dist<7){
			log.info("We are good to hit the guard");
			hitGUard = true;
		}else{
			return;
		}
	}
	private void selectFlash(){
		WidgetItem flash = utils.getInventoryWidgetItem(5559);
		targetMenu = new MenuEntry("Use", "Use", 5559, MenuAction.ITEM_USE.getId(),
				flash.getIndex(), 9764864, false);
		utils.setMenuEntry(targetMenu);
		utils.delayMouseClick(flash.getCanvasBounds(), sleepDelay());
	}
	private void selectGuard(){
		log.info("Running test 2");
		guard = utils.findNearestNpc(3191);
		WidgetItem flash = utils.getInventoryWidgetItem(5559);
		targetMenu = new MenuEntry("Examine", "<col=ffff00>Rogue Guard", 19934, 1003,
				0, 0, false);
		utils.setModifiedMenuEntry(targetMenu, flash.getId(), flash.getIndex(), MenuAction.ITEM_USE_ON_NPC.getId());
		utils.delayMouseClick(guard.getConvexHull().getBounds(), 40);


/*
		guard = utils.findNearestNpc(3191);
		if(guard != null) {
			targetMenu = new MenuEntry("Use", "<col=ff9040>Flash powder<col=ffffff> -> <col=ffff00>Rogue Guard",
					19934, 7, 0,
					0, false);
			//utils.setMenuEntry(targetMenu);
			//utils.setModifiedMenuEntry(targetMenu,ItemID.FLASH_POWDER,utils.getInventoryWidgetItem(5559).getIndex(),7);
			utils.setModifiedMenuEntry(targetMenu,ItemID.FLASH_POWDER,utils.getInventoryWidgetItem(ItemID.FLASH_POWDER).getIndex(),7);
			utils.delayMouseClick(guard.getConvexHull().getBounds(), sleepDelay());
		}else{
			log.info("is null");
		}
*/
	}
	private void doDoors(int door){
		log.info(Integer.toString(door));

		switch(door){
			case 1:
				 doorLoc = new WorldPoint(3030, 5079,1);
				break;
			case 2:

				doorLoc = new WorldPoint(3032, 5078,1);
				break;
			case 3:
				doorLoc = new WorldPoint(3036, 5076,1);
				break;
			case 4:
				doorLoc = new WorldPoint(3039, 5079,1);
				break;
			case 5:
				doorLoc = new WorldPoint(3042, 5076,1);
				break;
			case 6:
				doorLoc = new WorldPoint(3044, 5069,1);
				break;
			case 7:
				doorLoc = new WorldPoint(3041, 5068,1);
				break;
			case 8:
				doorLoc = new WorldPoint(3040, 5070,1);
				break;
			case 9:
				doorLoc = new WorldPoint(3038, 5069,1);
				break;
		}
		enterSpecificDoor(7255,doorLoc);
	}

	private void solvePuzzle(){
		if(client.getWidget(161,16) != null){
			targetMenu = new MenuEntry("Ok", "", 0, 24, 0, 19202051, false);
			utils.setMenuEntry(targetMenu);
			utils.delayMouseClick(getRandomNullPoint(), sleepDelay());
			return;
		}

	}

	private void pickUpObj(int id){

			log.info("tile piece under player called.");
			for(Tile tile : new TileQuery().isWithinDistance(client.getLocalPlayer().getWorldLocation(),0).result(client)) {
				if(tile.getGroundItems()!=null){
					for(TileItem tileItem : tile.getGroundItems()){
						if(tileItem.getId()==id){
							targetMenu = new MenuEntry ("Take", "<col=ff9040>Tile",5568,20,client.getLocalPlayer().getLocalLocation().getSceneX(),client.getLocalPlayer().getLocalLocation().getSceneY(),false);
							utils.setMenuEntry(targetMenu);
							utils.delayMouseClick(getRandomNullPoint(), sleepDelay());
							return;
						}
					}
				}
			}

	}
	private void testFunction( ){
		log.info("running test 1");
		guard = utils.findNearestNpc(3191);
		WidgetItem flash = utils.getInventoryWidgetItem(5559);
		targetMenu = new MenuEntry("Use", "Use", ItemID.FLASH_POWDER, 38,
				flash.getIndex(), 9764864, false);
		utils.setModifiedMenuEntry(targetMenu,guard.getIndex(),0,MenuAction.ITEM_USE_ON_NPC.getId());
		utils.delayMouseClick(guard.getConvexHull().getBounds(), sleepDelay());


	}

	private void testfun( ){

		log.info("Running test 2");
		guard = utils.findNearestNpc(3191);

		if(guard != null) {
			log.info("no null");
			if(player.getWorldLocation().getRegionY()-guard.getWorldLocation().getRegionY()<4) {
				log.info("going to go flash");
				WidgetItem flash = utils.getInventoryWidgetItem(5559);
				targetMenu = new MenuEntry("Examine", "<col=ffff00>Rogue Guard", 19934, 1003,
						0, 0, false);
				utils.setModifiedMenuEntry(targetMenu, flash.getId(), flash.getIndex(), MenuAction.ITEM_USE_ON_NPC.getId());
				utils.delayMouseClick(guard.getConvexHull().getBounds(), sleepDelay());

				//runFast = true;
				//pickedUpFlash = false;
				//flashReady = false;
				return;
			}else{
				log.info("He is too far away");
				return;
			}
		}


	}
	private void pickUpFlash(int id){

		log.info("tile piece under player called.");
		for(Tile tile : new TileQuery().isWithinDistance(client.getLocalPlayer().getWorldLocation(),0).result(client)) {
			if(tile.getGroundItems()!=null){
				for(TileItem tileItem : tile.getGroundItems()){
					if(tileItem.getId()==id){
						targetMenu = new MenuEntry ("Take", "<col=ff9040>Flash powder (5)",5559,20,client.getLocalPlayer().getLocalLocation().getSceneX(),client.getLocalPlayer().getLocalLocation().getSceneY(),false);
						utils.setMenuEntry(targetMenu);
						utils.delayMouseClick(getRandomNullPoint(), sleepDelay());
						return;
					}
				}
			}
		}

	}
	private boolean checkIfEntered() {

		if (client.getLocalPlayer().getWorldLocation().equals(firstLocation)) {
			log.info("we are in the game");

			return true;
		} else {
			log.info("we are not in the game");
			return false;
		}
	}

	private boolean checkIfFinished(WorldPoint finishLoc) {
		if (client.getLocalPlayer().getWorldLocation().equals(finishLoc)) {

			return true;
		} else {

			return false;
		}

	}
	private Point getRandomNullPoint()
	{
		if(client.getWidget(161,34)!=null){
			Rectangle nullArea = client.getWidget(161,34).getBounds();
			return new Point ((int)nullArea.getX()+utils.getRandomIntBetweenRange(0,nullArea.width), (int)nullArea.getY()+utils.getRandomIntBetweenRange(0,nullArea.height));
		}

		return new Point(client.getCanvasWidth()-utils.getRandomIntBetweenRange(0,2),client.getCanvasHeight()-utils.getRandomIntBetweenRange(0,2));
	}
	private long sleepDelay() {
		sleepLength = utils.randomDelay(false, 60, 300, 20, 100);
		return sleepLength;
	}

	private void doObstacle(int id) {
		GameObject objObstacle = utils.findNearestGameObject(id);
		if (objObstacle != null) {
			targetMenu = new MenuEntry("", "", objObstacle.getId(), MenuAction.GAME_OBJECT_FIRST_OPTION.getId(), objObstacle.getSceneMinLocation().getX(), objObstacle.getSceneMinLocation().getY(), false);
			utils.setMenuEntry(targetMenu);
			utils.delayMouseClick(objObstacle.getConvexHull().getBounds(), sleepDelay());
			return;
		}
	}

	private void doSpecificObstacle(int id, WorldPoint loc) {

		GameObject objObstacle = utils.findNearestGameObjectWithin(loc,0,id);
		if (objObstacle != null) {
			targetMenu = new MenuEntry("", "", objObstacle.getId(), MenuAction.GAME_OBJECT_FIRST_OPTION.getId(), objObstacle.getSceneMinLocation().getX(), objObstacle.getSceneMinLocation().getY(), false);
			utils.setMenuEntry(targetMenu);
			utils.delayMouseClick(objObstacle.getConvexHull().getBounds(), sleepDelay());
			return;
		}
	}

	private void doGround(int id) {

		GroundObject groundObstacle = utils.findNearestGroundObject(id);
		if (groundObstacle != null) {
			targetMenu = new MenuEntry("", "", groundObstacle.getId(), MenuAction.GAME_OBJECT_FIRST_OPTION.getId(), groundObstacle.getLocalLocation().getSceneX(), groundObstacle.getLocalLocation().getSceneY(), false);
			utils.setMenuEntry(targetMenu);
			utils.delayMouseClick(groundObstacle.getConvexHull().getBounds(), sleepDelay());
			return;
		}

	}

	private int tickDelay() {
		tickLength = (int) utils.randomDelay(false, 1, 2, 1, 1);
		return tickLength;
	}

	private void enterDoor(int id) {
		targetWallObject = utils.findNearestWallObject(id);
		if (targetWallObject != null) {
			targetMenu = new MenuEntry("", "", targetWallObject.getId(), 3,
					targetWallObject.getLocalLocation().getSceneX(), targetWallObject.getLocalLocation().getSceneY(), false);
			utils.setMenuEntry(targetMenu);
			utils.delayMouseClick(targetWallObject.getConvexHull().getBounds(), sleepDelay());
		} else {
			log.info("is null");
		}
	}

	private void enterSpecificDoor(int id, WorldPoint loc) {

		targetWallObject = utils.findWallObjectWithin(loc,0,id);;

		if (targetWallObject != null) {
			targetMenu = new MenuEntry("", "", targetWallObject.getId(), 3,
					targetWallObject.getLocalLocation().getSceneX(), targetWallObject.getLocalLocation().getSceneY(), false);
			utils.setMenuEntry(targetMenu);
			utils.delayMouseClick(targetWallObject.getConvexHull().getBounds(), sleepDelay());
		} else {
			log.info("is null");
		}
	}

/*
	private void multipleDoors(WorldPoint loc){
		targetWallObject = utils.findWallObjectWithin(loc,0,7255);
			if (targetWallObject != null) {
				targetWallObject.getLocalLocation().getSceneX(), targetWallObject.getLocalLocation().getSceneY(), false)
				utils.setMenuEntry(targetMenu);
				utils.delayMouseClick(targetWallObject.getConvexHull().getBounds(), sleepDelay());
		}else{
				log.info("is null");
			}*/

}