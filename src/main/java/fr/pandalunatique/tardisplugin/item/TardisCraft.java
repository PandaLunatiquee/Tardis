package fr.pandalunatique.tardisplugin.item;

import fr.pandalunatique.tardisplugin.TardisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum TardisCraft {

    TARDIS_KEY("BBBBKBBBB", "tardis_key", TardisItem.TARDIS_KEY.getItemStack(), new HashMap<Character, ItemStack>(){
        {
            put('B', TardisItem.ARTRON_BOTTLE.getItemStack());
            put('K', TardisItem.BASIC_KEY.getItemStack());
        }
    }),
    TEST("test", TardisItem.TARDIS_KEY.getItemStack(), new ItemStack[]{new ItemStack(Material.STONE), new ItemStack(Material.STONE)});

    //TODO: ShapelessRecipe

    private boolean isShapeless;
    private final String shape;
    private final String namespaceKey;
    private final ItemStack result;
    private final Map<Character, ItemStack> ingredients;
    private final ItemStack[] ingredientsShapeless;

    private TardisCraft(String shape, String namespaceKey, ItemStack result, Map<Character, ItemStack> ingredients) {

        this.isShapeless = false;
        this.shape = shape;
        this.namespaceKey = namespaceKey;
        this.result = result;
        this.ingredients = ingredients;
        this.ingredientsShapeless = null;

    }

    private TardisCraft(String namespaceKey, ItemStack result, ItemStack[] ingredients) {

        this.isShapeless = true;
        this.shape = null;
        this.namespaceKey = namespaceKey;
        this.result = result;
        this.ingredients = null;
        this.ingredientsShapeless = ingredients;

    }

    public boolean isRegistered() {
        NamespacedKey key = new NamespacedKey(TardisPlugin.getInstance(), this.namespaceKey);
        return Bukkit.getRecipe(key) != null;
    }

    public void registerRecipe() {

        if(this.isRegistered()) return;

        NamespacedKey key = new NamespacedKey(TardisPlugin.getInstance(), this.namespaceKey);

        if(this.isShapeless) {

            ShapelessRecipe recipe = new ShapelessRecipe(key, this.result);

            for(ItemStack i : this.ingredientsShapeless) {

                RecipeChoice choice = new RecipeChoice.ExactChoice(i);

                recipe.addIngredient(choice);

            }

            Bukkit.addRecipe(recipe);

        } else {

            ShapedRecipe recipe = new ShapedRecipe(key, this.result);

            recipe.shape(this.shape.substring(0, 3), this.shape.substring(3, 6), this.shape.substring(6, 9));

            this.ingredients.forEach((s, i) -> {

                RecipeChoice choice = new RecipeChoice.ExactChoice(i);

                recipe.setIngredient(s, choice);

            });

            Bukkit.addRecipe(recipe);

        }

    }

    public void unregisterRecipe() {
        NamespacedKey key = new NamespacedKey(TardisPlugin.getInstance(), this.namespaceKey);
        if(this.isRegistered()) {
            Bukkit.getServer().removeRecipe(key);
        }

    }

}
