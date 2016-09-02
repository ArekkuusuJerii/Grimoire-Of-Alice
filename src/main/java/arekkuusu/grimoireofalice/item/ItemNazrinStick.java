/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.item;

import java.util.List;

import arekkuusu.grimoireofalice.lib.LibItemName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNazrinStick extends ItemModSword {

	ItemNazrinStick(ToolMaterial material) {
		super(material);
		setUnlocalizedName(LibItemName.NAZRINSTICK);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		list.add(TextFormatting.GOLD + "Rare treasure from an old era");
		list.add(TextFormatting.ITALIC + "By holding it you become the");
		list.add(TextFormatting.ITALIC + "the Little Dowser General");
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float x, float y, float z) {
		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItemStack(new ItemStack(Items.COAL))) {
			playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(15), 25, 0));
			//playerIn.setItemInUse(p_77659_1_, getMaxItemUseDuration(p_77659_1_));
		}
		return new ActionResult(EnumActionResult.PASS, itemStackIn);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if(entityLiving instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entityLiving;
			if(player.experienceLevel > 30) {
				//Vec3 look = player.getLookVec();
				worldIn.createExplosion(player, player.posX - 5.0, player.posY, player.posZ - 5.0, 2.5F, false);
				worldIn.createExplosion(player, player.posX - 5.0, player.posY, player.posZ, 2.5F, false);
				worldIn.createExplosion(player, player.posX - 5.0, player.posY, player.posZ + 5.0, 2.5F, false);
				worldIn.createExplosion(player, player.posX, player.posY, player.posZ + 5.0, 2.5F, false);
				worldIn.createExplosion(player, player.posX, player.posY, player.posZ - 5.0, 2.5F, false);
				worldIn.createExplosion(player, player.posX + 5.0, player.posY, player.posZ - 5.0, 2.5F, false);
				worldIn.createExplosion(player, player.posX + 5.0, player.posY, player.posZ, 2.5F, false);
				worldIn.createExplosion(player, player.posX + 5.0, player.posY, player.posZ + 5.0, 2.5F, false);
			}
			else {
				player.setVelocity(0, 10, 0);
				worldIn.createExplosion(player, player.posX, player.posY+1, player.posZ, 2.0F, false);
				stack.damageItem(1, player);
			}
			player.inventory.deleteStack(new ItemStack(Items.COAL));
		}
		return stack;
	}
}
