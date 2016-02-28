package com.tigeroakes.pacmanmaps;

import android.graphics.RectF;

import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

/**
 * Logic for ghosts that follow pac-man around
 */

public class Ghost {
    private LatLng pos;
    private int speed;
    private Random random;
    double ghostSizeX;
    double ghostSizeY;

    public Ghost(double minY, double maxY, double minX, double maxX, double playerRadius, LatLng playerPos) {
        // Constructor
        double ghostX;
        double ghostY;
        double playerDist;
        ghostSizeX = 0.5;
        ghostSizeY = 0.5;

        int cyclesRan = 0;
        //playerRadius is the minimum distance the ghost can be from the player in order for the ghost to spawn

        do {
            // makes the ghost a uniformly distributed random position in between minX, maxX for x and minY, maxY for y
            ghostX = (random.nextDouble() * (maxX - minX)) + minX;
            ghostY = (random.nextDouble() * (maxY - minY)) + minY;
            pos = new LatLng(ghostX, ghostY);
            double distX = Math.abs(ghostX - playerPos.latitude);
            double distY = Math.abs(ghostY - playerPos.longitude);
            playerDist = Math.sqrt((distX * distX) + (distY * distY));
            cyclesRan++;
        } while (playerDist < playerRadius && cyclesRan < 200);

        if (cyclesRan > 200) {
            //TODO:throw error here because this means that the box radius was probably smaller than the minimum range you can spawn a ghost
        }
        // above code should make it so the ghost won't spawn within player radius
    }

    public void update(LatLng playerPos) {
        //TODO: figure out how to find time passed since last pos update and test this code

        //calc dist between ghost and player
        double distX = playerPos.latitude - pos.latitude;
        double distY = playerPos.longitude - pos.longitude;

        //this code should make the ghost move exactly towards the player
        double speedX = (distX/(Math.abs(distX) + Math.abs(distY))) * speed;
        double speedY = (distY/(Math.abs(distX)+ Math.abs(distY)))* speed;

        //this code is placeholder and does not work yet because i dont know how to find the time passed since last update
        //PLACEHOLDER CODE
        pos = new LatLng(pos.latitude + speedX, pos.longitude + speedY);

        //REAL CODE SHOULD USE TIMEPASSED LIKE THIS BUT IDK HOW TO DO THAT
        /*
        pos.latitude += speedX * timePassedSinceLastUpdate;
        pos.longitude += speedY * timePassedSinceLastUpdate;
        */

        //TODO: add collision detection between player and ghost here
        double playerSizeX = 0.5;
        double playerSizeY = 0.5;


        RectF playerRect = new RectF((float)(playerPos.latitude - playerSizeX), (float)(playerPos.longitude - playerSizeY),
                (float)(playerPos.latitude + playerSizeX), (float)(playerPos.longitude + playerSizeY));

        RectF ghostRect = new RectF((float)(pos.latitude - ghostSizeX), (float)(pos.longitude - ghostSizeY),
                (float)(pos.latitude + ghostSizeX), (float)(pos.longitude + ghostSizeY));

        if (playerRect.contains(ghostRect)) {
            //TODO: add the code that does what should be done when the ghost hits the player here
            //i.e probably remove a life from the player and reset the gamestate
        }


    }



}
