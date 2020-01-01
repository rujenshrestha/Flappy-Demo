package com.sresta.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {

    private Array<TextureRegion> frames;
    private float maxFrameTime; //duration of a frame in view
    private float currentFrameTime; //time of the frame has been view
    private int frameCount; // no. of frame in animation
    private int frame;

    public Animation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();

        //Spitting the TextureRegion into multiple frames and inserting into frames Array
        int frameWidth = region.getRegionWidth() / frameCount;
        for(int i=0; i< frameCount; i++){
            frames.add(new TextureRegion(region, i * frameWidth, 0,
                        frameWidth, region.getRegionHeight()));
        }

        this.frameCount =  frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            //changing the frame i.e. changing the texture after sometime
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount){
            //if the frame has reached the end of the original texture region, go back to start
            frame = 0;
        }
    }

    public TextureRegion getFrame(){
        //returns the current frame
        return frames.get(frame);
    }
}
