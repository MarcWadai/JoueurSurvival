package com.mygdx.game.model;

/**
 * Created by rxcai on 11/10/15.
 */
public class ScoreLabel extends com.badlogic.gdx.scenes.scene2d.ui.Label {
        private String text;

        LabelStyle style;

        public ScoreLabel( CharSequence text, LabelStyle style) {
            super(text, style);
            this.text = text.toString();

        }

        @Override
        public void act(final float delta) {
            this.setText(text);
            super.act(delta);
        }

        public void updateText(final String text) {
            this.text = text;
        }
}
