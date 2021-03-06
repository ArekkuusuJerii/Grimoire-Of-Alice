/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 * <p>
 * Grimore Of Alice is Open Source and distributed under the
 * Grimore Of Alice license: https://github.com/ArekkuusuJerii/Grimore-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.common.item;

import arekkuusu.grimoireofalice.client.render.model.ModelSatoriEye;
import arekkuusu.grimoireofalice.client.util.ResourceLibrary;
import arekkuusu.grimoireofalice.common.lib.LibName;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemThirdEye extends ItemBaseArmor {

	@SideOnly(Side.CLIENT)
	private ModelBiped model;

	public ItemThirdEye(ArmorMaterial material, int dmg) {
		super(material, dmg, LibName.THIRD_EYE, EntityEquipmentSlot.CHEST);
		setMaxStackSize(1);
		setMaxDamage(125);
		setNoRepair();
		addPropertyOverride(new ResourceLocation("status"),
				(stack, world, entity) -> isClosed(stack) ? 1F : 0F);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@SideOnly(Side.CLIENT)
	private static String getStatus(ItemStack stack) {
		return isClosed(stack) ? TextFormatting.DARK_RED + I18n.format("grimoire.tooltip.third_eye_closed.name")
				: TextFormatting.DARK_PURPLE + I18n.format("grimoire.tooltip.third_eye_open.name");
	}

	@Override
	public void onArmorTick(World world, final EntityPlayer player, ItemStack itemStack) {
		if(isClosed(itemStack)) {
			if(!player.isSneaking()) {
				applyEffectNearby(world, player, new PotionEffect(MobEffects.BLINDNESS, 40, 0));
				player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 10, 0));
				if(!world.isRemote) {
					world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(player.getPosition()).grow(20D),
							entity -> entity != null && player.equals(entity.getAttackTarget()))
							.forEach(livingBase -> livingBase.setLastAttackedEntity(null));
				}
			}
		} else {
			applyEffectNearby(world, player, new PotionEffect(MobEffects.GLOWING, 20, 0));
		}
	}

	private static void applyEffectNearby(World world, EntityPlayer player, PotionEffect effect) {
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(20D));
		list.stream()
				.filter(mob -> mob instanceof EntityMob).map(mob -> (EntityMob) mob)
				.forEach(mob -> mob.addPotionEffect(effect));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(!player.isSneaking()) {
			EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(stack);
			ItemStack invStack = player.getItemStackFromSlot(entityequipmentslot);

			if(invStack.isEmpty()) {
				player.setItemStackToSlot(entityequipmentslot, stack.copy());
				stack.setCount(0);
			}
		} else {
			player.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1F, 1F);
			setClosed(stack, !isClosed(stack));
			stack.damageItem(1, player);
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	private static void setClosed(ItemStack stack, boolean isOpen) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if(tagCompound == null) {
			tagCompound = new NBTTagCompound();
			stack.setTagCompound(tagCompound);
		}
		tagCompound.setBoolean("eye", isOpen);
	}

	private static boolean isClosed(ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		return tagCompound != null && tagCompound.getBoolean("eye");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped imodel) {
		if(isClosed(itemStack)) {
			if((model == null || model instanceof ModelSatoriEye)) {
				model = new ModelSatoriEye();
			}
		} else if(model == null || model instanceof ModelSatoriEye) {
			model = new ModelSatoriEye();
		}
		model.setModelAttributes(imodel);
		return model;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return isClosed(stack) ? ResourceLibrary.KOISHI_EYE.toString() : ResourceLibrary.SATORI_EYE.toString();
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}
}
