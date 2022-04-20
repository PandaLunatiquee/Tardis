package fr.pandalunatique.tardisplugin.schematic;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import fr.pandalunatique.tardisplugin.TardisPlugin;
import lombok.Getter;
import org.bukkit.Location;

import javax.annotation.Nullable;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class SchematicManager {

    public enum Schematic {

        TEST("schematic/test.schem");

        @Getter private String source;
        @Getter private String destination;

        Schematic(String source) {

            this.source = source;

        }

        public void saveSchematic() {

            TardisPlugin.getInstance().saveResource(this.source, true);

        }

        public void paste(Location location) {

            // TODO: Check for close()

            File f = new File(TardisPlugin.getInstance().getDataFolder(), this.source);

            World world = FaweAPI.getWorld(location.getWorld().getName());
            BlockVector3 v = BlockVector3.at(location.getX(), location.getY(), location.getZ());

            try {

                ClipboardFormat format = ClipboardFormats.findByFile(f);
                ClipboardReader reader = format.getReader(new FileInputStream(f));

                Clipboard clipboard = reader.read();
                AffineTransform transform = new AffineTransform().rotateY(0);
                clipboard.paste(world, v, false, false, false, transform);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void saveSchematics() {

        for(Schematic schematic : Schematic.values()) {

            schematic.saveSchematic();

        }

    }

}
