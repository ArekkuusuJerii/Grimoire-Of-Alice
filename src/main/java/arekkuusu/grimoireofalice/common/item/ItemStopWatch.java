package arekkuusu.grimoireofalice.common.item;

import arekkuusu.grimoireofalice.common.entity.EntityStopWatch;
import arekkuusu.grimoireofalice.common.lib.LibItemName;
import net.katsstuff.danmakucore.entity.living.TouhouCharacter;
import net.katsstuff.danmakucore.item.IOwnedBy;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemStopWatch extends ItemBase implements IOwnedBy {

	public ItemStopWatch() {
		super(LibItemName.STOPWATCH);
		setMaxStackSize(1);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.EPIC;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
		list.add(TextFormatting.WHITE + "" + TextFormatting.ITALIC + I18n.format("grimoire.tooltip.stop_watch_header.name"));
		list.add("");
		list.add(TextFormatting.ITALIC + I18n.format("grimoire.tooltip.stop_watch_description_top.name"));
		list.add(TextFormatting.ITALIC + I18n.format("grimoire.tooltip.stop_watch_description_bottom.name"));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		player.setActiveHand(hand);
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
		if(entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			EntityStopWatch watch = new EntityStopWatch(world, player);
			if(!world.isRemote) {
				Vec3d look = player.getLookVec();
				float distance = 1F;
				double dx = player.posX + look.x;
				double dy = player.posY + player.getEyeHeight() - 1;
				double dz = player.posZ + look.z * distance;
				watch.setPosition(dx, dy, dz);
				world.spawnEntity(watch);
			}
			if(!player.capabilities.isCreativeMode) {
				stack.shrink(1);
			}
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.NONE;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 7000;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public TouhouCharacter character(ItemStack stack) {
		return TouhouCharacter.SAKUYA_IZAYOI;
	}
}