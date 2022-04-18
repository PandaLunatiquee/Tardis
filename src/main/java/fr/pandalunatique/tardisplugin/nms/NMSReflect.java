package fr.pandalunatique.tardisplugin.nms;

import fr.pandalunatique.tardisplugin.TardisPlugin;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.*;


public class NMSReflect {

    public static Class<?> getClass(String clazz) {

        try {
            Class<?> a = Class.forName("net.minecraft.server." + TardisPlugin.getNmsVersion() + "." + clazz);

            return a;

        } catch(ClassNotFoundException e) {
            //TODO: Handle exception
        }
        return null;

    }


}
