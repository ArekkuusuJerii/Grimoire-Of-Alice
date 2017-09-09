/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimore Of Alice is Open Source and distributed under the
 * Grimore Of Alice license: https://github.com/ArekkuusuJerii/Grimore-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.common.item;

import arekkuusu.grimoireofalice.common.Alice;
import net.minecraft.item.ItemSword;

public class ItemBaseSword extends ItemSword {

	public ItemBaseSword(ToolMaterial material, String id) {
		super(material);
		setRegistryName(id);
		setUnlocalizedName(id);
		setCreativeTab(Alice.CREATIVE_TAB);
	}
}