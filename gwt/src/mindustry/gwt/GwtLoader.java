package mindustry.gwt;

import arc.ApplicationListener;
import arc.backend.gwt.GwtApplicationConfiguration;
import arc.struct.Seq;

public class GwtLoader extends arc.backend.gwt.GwtApplication {
    ApplicationListener app;

    GwtLoader(ApplicationListener applicationListener) {
        this.app = applicationListener;
    }

    @Override
    public GwtApplicationConfiguration getConfig () {
        // Resizable application, uses available space in browser with no padding:
        GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(true);
        cfg.padVertical = 0;
        cfg.padHorizontal = 0;
        return cfg;
        // If you want a fixed size application, comment out the above resizable section,
        // and uncomment below:
        //return new GwtApplicationConfiguration(640, 480);
    }

    @Override
    public ApplicationListener createApplicationListener () {
         return app;
    }

    @Override
    public Seq<ApplicationListener> getListeners() {
        return super.lifecycleListeners;
    }

    @Override
    public void post(Runnable runnable) {
        super.postRunnable(runnable);
    }
}
