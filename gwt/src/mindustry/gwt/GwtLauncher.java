package mindustry.gwt;

import arc.Files.*;
import arc.backend.gwt.GwtApplication;
import arc.backend.gwt.GwtApplicationConfiguration;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.mod.Mods.*;
import mindustry.net.*;

import static mindustry.Vars.*;

public class GwtLauncher extends ClientLauncher{
    public final String[] args;

    public static void main(String[] arg){
        try {
            Vars.loadLogger();
            new GwtLoader(new GwtLauncher(arg));
        } catch (Throwable e){
            //handleCrash(e);
        }
    }

    public GwtLauncher(String[] args){
        this.args = args;
        
        Version.init();
        testMobile = Seq.with(args).contains("-testMobile");
    }
}