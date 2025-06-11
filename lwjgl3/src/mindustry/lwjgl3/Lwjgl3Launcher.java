package mindustry.lwjgl3;

import arc.Files.*;
import arc.backend.lwjgl3.Lwjgl3Application;
import arc.backend.lwjgl3.Lwjgl3ApplicationConfiguration;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.mod.Mods.*;
import mindustry.net.*;

import javax.swing.*;

import static mindustry.Vars.*;

public class Lwjgl3Launcher extends ClientLauncher{
    public final String[] args;

    public static void main(String[] arg){
        try{
            Vars.loadLogger();
            new Lwjgl3Application(new Lwjgl3Launcher(arg), new Lwjgl3ApplicationConfiguration(){{
                setTitle("Mindustry");
                setWindowWidth(900);
                setWindowHeight(600);
                setMaximized(true);
                //setGlEmulation(GLEmulation.GL32);
                setOpenGLEmulation(GLEmulation.ANGLE_GLES30, 0, 0);
                setWindowIcon(FileType.internal, "icons/icon_64.png");
            }});
        }catch(Throwable e){
            handleCrash(e);
        }
    }

    public Lwjgl3Launcher(String[] args){
        this.args = args;
        
        Version.init();
        testMobile = Seq.with(args).contains("-testMobile");
    }

    static void handleCrash(Throwable e){
        boolean badGPU = false;
        String finalMessage = Strings.getFinalMessage(e);
        String total = Strings.getCauses(e).toString();

        if(total.contains("Couldn't create window") || total.contains("OpenGL 2.0 or higher") || total.toLowerCase().contains("pixel format") || total.contains("GLEW")|| total.contains("unsupported combination of formats")){

            message(
                total.contains("Couldn't create window") ? "A graphics initialization error has occured! Try to update your graphics drivers:\n" + finalMessage :
                            "Your graphics card does not support the right OpenGL features.\n" +
                                    "Try to update your graphics drivers. If this doesn't work, your computer may not support Mindustry.\n\n" +
                                    "Full message: " + finalMessage);
            badGPU = true;
        }

        boolean fbgp = badGPU;

        LoadedMod cause = CrashHandler.getModCause(e);
        String causeString = cause == null ? (Structs.contains(e.getStackTrace(), st -> st.getClassName().contains("rhino.gen.")) ? "A mod or script has caused Mindustry to crash.\nConsider disabling your mods if the issue persists.\n" : "Mindustry has crashed.") :
            "'" + cause.meta.displayName + "' (" + cause.name + ") has caused Mindustry to crash.\nConsider disabling this mod if issues persist.\n";

        CrashHandler.handle(e, file -> {
            Throwable fc = Strings.getFinalCause(e);
            if(!fbgp){
                message(causeString + "\nThe logs have been saved in:\n" + file.getAbsolutePath() + "\n" + fc.getClass().getSimpleName().replace("Exception", "") + (fc.getMessage() == null ? "" : ":\n" + fc.getMessage()));
            }
        });
    }

    private static void message(String message){
        JOptionPane.showMessageDialog(null, message, "oh no", JOptionPane.ERROR_MESSAGE);
    }
}
