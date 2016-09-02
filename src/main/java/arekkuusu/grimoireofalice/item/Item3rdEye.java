package arekkuusu.grimoireofalice.item;

import java.util.List;

import arekkuusu.grimoireofalice.lib.LibItemName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Item3rdEye extends ItemMod{
	
	public Item3rdEye() {
		super();
		setUnlocalizedName(LibItemName.EYE);
		setMaxStackSize(1);
		setMaxDamage(300);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		list.add(TextFormatting.GOLD + "Satori's 3rdEye");
		list.add(TextFormatting.ITALIC + "Shift right click to activate");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(playerIn.isSneaking() && itemStackIn.getItemDamage() == 0) {
			itemStackIn.setItemDamage(300);
		}
		return new ActionResult(EnumActionResult.PASS, itemStackIn);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			if(player.inventory.hasItemStack(stack) && !(stack.getItemDamage() == 0)) {
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(14), 10, 0));
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(15), 10, 0));
			}
		}
		if(stack.isItemDamaged()){
			stack.setItemDamage(stack.getItemDamage() - 1);
		}
	}
	
}
