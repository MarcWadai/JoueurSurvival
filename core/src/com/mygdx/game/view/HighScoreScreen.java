package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.MyGdxGame;

/**
 * Created by Marco on 25/12/15.
 */
public class HighScoreScreen extends ScreenAdapter implements Net.HttpResponseListener {


    Net.HttpRequest httpRequest;
    MyGdxGame myGdxGame;

    public  HighScoreScreen(MyGdxGame myGdxGame) {

    }


    @Override
    public void show() {
        String url = "http://localhost:8080/getKPI";
        String requestContent = null;
        String httpMethod = Net.HttpMethods.GET;
        httpRequest = new Net.HttpRequest(httpMethod);
        httpRequest.setUrl(url);
        httpRequest.setContent(requestContent);
        Gdx.net.sendHttpRequest(httpRequest, HighScoreScreen.this);
        this.myGdxGame = myGdxGame;
        System.out.println("in highScoree");

    }


    public void draw(){



    }

    public void render(float delta) {

        super.render(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


        @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        /*final int statusCode = httpResponse.getStatus().getStatusCode();
        // We are not in main thread right now so we need to post to main thread for ui updates
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run () {
                System.out.println("the status code is "+ statusCode);
            }
        });*/

        System.out.println(httpResponse.getResultAsString());
    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {
        System.out.println("request cancelled");
    }
}











