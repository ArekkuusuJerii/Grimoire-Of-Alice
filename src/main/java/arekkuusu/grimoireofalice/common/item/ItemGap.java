package arekkuusu.grimoireofalice.common.item;

import arekkuusu.grimoireofalice.common.entity.EntityGap;
import arekkuusu.grimoireofalice.common.lib.LibName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemGap extends ItemBase {

	public ItemGap() {
		super(LibName.GAP);
		setMaxStackSize(1);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(!world.isRemote) {
			EntityGap gap = new EntityGap(world);
			gap.setPositionAndAngles(player);
			world.spawnEntity(gap);
			stack = ItemStack.EMPTY;
		} else player.playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, 0.1F, 0.1F);
		player.setActiveHand(hand);
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.NONE;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 2000;
	}
}
