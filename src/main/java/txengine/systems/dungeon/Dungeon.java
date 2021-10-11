package txengine.systems.dungeon;

import txengine.structures.Canvas;
import txengine.structures.CanvasNode;
import txengine.structures.Coordinate;
import txengine.structures.Graph;
import txengine.systems.room.Room;
import txengine.systems.room.action.Action;
import txengine.systems.room.action.actions.*;
import txengine.ui.LogUtils;
import txengine.ui.component.Components;
import txengine.util.Utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dungeon {

    Canvas roomCanvas;
    String seed;
    Integer[] enemyPool;
    List<AbstractMap.SimpleEntry<Integer, Integer>> clearRewards;
    int maximumLength;
    Random rand;

    public Dungeon() {
        maximumLength = 10;
        roomCanvas = new Canvas(maximumLength+ Utils.randomInt(-1,maximumLength/2), maximumLength + Utils.randomInt(-1, maximumLength/2));
        rand = new Random();
        clearRewards = new ArrayList<>();
    }

    public boolean enter() {


        return true;
    }

    DungeonRoom getRoot() {
        // Generate a coordinate pair at (n,0) where n is between 0 and the width of the canvas
        Coordinate rootCoordinate = new Coordinate(Utils.randomInt(0,roomCanvas.getWidth()),0);
        DungeonRoom dr = new DungeonRoom(this, rootCoordinate);

        for (int i = 0; i < 2; i++) {
            dr.addDoor(Utils.selectRandom(CanvasNode.Direction.values(), rand));
        }

        return dr;
    }

    private void generateCoreRoute() {

    }

    public static List<Action> getDefaultActions() {
        List<Action> actions = new ArrayList<>();
        actions.add(new SummaryAction());
        actions.add(new InventoryAction());
        actions.add(new AbilitySummaryAction());
        actions.add(new SkillSummaryAction());
        actions.add(new EquipmentAction());

        return actions;
    }

    /*** Accessors ***/
    public Canvas getRoomCanvas() {
        return roomCanvas;
    }

    public void setRoomCanvas(Canvas roomCanvas) {
        this.roomCanvas = roomCanvas;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public Integer[] getEnemyPool() {
        return enemyPool;
    }

    public void setEnemyPool(Integer[] enemyPool) {
        this.enemyPool = enemyPool;
    }

    public List<AbstractMap.SimpleEntry<Integer, Integer>> getClearRewards() {
        return clearRewards;
    }

    public void setClearRewards(List<AbstractMap.SimpleEntry<Integer, Integer>> clearRewards) {
        this.clearRewards = clearRewards;
    }

    public int getMaximumLength() {
        return maximumLength;
    }

    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }
}
