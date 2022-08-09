package me.bounser.skyblockagui.tools;

import me.bounser.skyblockagui.listeners.InteractionListener;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RegisterManager {

    private static RegisterManager instance;

    public static RegisterManager getInstance(){
        if(instance != null) return instance;

        RegisterManager.instance = new RegisterManager();
        return instance;
    }

    List firstR = new ArrayList<Player>();
    List secondR = new ArrayList<Player>();

    public void setFirstRegister(Player p){ firstR.add(p); }
    public void setSecondRegister(Player p){ secondR.add(p); }

    public void removeRegister(Player p, String phase) {
        switch (phase) {
            case "first": firstR.remove(p);
            case "second": secondR.remove(p);
        }
    }

    public boolean isRegistering(Player p, String phase){
        switch(phase){
            case "first": if(firstR.contains(p)) return true;
            case "second": if(secondR.contains(p)) return true;
            case "both": if(firstR.contains(p) || secondR.contains(p)) return true;
        }
        return false;
    }

}
