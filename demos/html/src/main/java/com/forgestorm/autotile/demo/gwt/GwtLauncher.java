package com.forgestorm.autotile.demo.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.forgestorm.autotile.demo.AutoTileDemo;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
        @Override
        public GwtApplicationConfiguration getConfig () {
            return new GwtApplicationConfiguration(AutoTileDemo.WINDOW_WIDTH, AutoTileDemo.WINDOW_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
            return new AutoTileDemo();
        }
}
