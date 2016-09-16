package arekkuusu.grimoireofalice.item.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ShapelessRecipe {
	private ItemStack out = null;
	private final List<Object> ingredients = new ArrayList<>();

	public ShapelessRecipe outputs(Block out) {
		return outputs(new ItemStack(out));
	}

	public ShapelessRecipe outputs(Item out) {
		return outputs(new ItemStack(out));
	}

	public ShapelessRecipe outputs(ItemStack out) {
		this.out = out;
		return this;
	}

	public ShapelessRecipe add(Block block) {
		ingredients.add(block);
		return this;
	}

	public ShapelessRecipe add(Item item) {
		ingredients.add(item);
		return this;
	}

	public ShapelessRecipe add(ItemStack stack) {
		ingredients.add(stack);
		return this;
	}

	public ShapelessRecipe add(String string) {
		ingredients.add(string);
		return this;
	}

	public void build() {

		if(ingredients.isEmpty()) throw new IllegalArgumentException("You have to specify ingredients for the recipe");
		if(out == null) throw new IllegalArgumentException("Output not specified");

		ShapelessOreRecipe recipe = new ShapelessOreRecipe(out, ingredients.toArray());
		CraftingManager.getInstance().getRecipeList().add(recipe);
	}
}