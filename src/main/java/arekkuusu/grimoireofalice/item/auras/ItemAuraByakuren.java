/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.item.auras;

import java.util.List;

import javax.annotation.Nullable;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import arekkuusu.grimoireofalice.client.model.ModelByakurenAura;
import arekkuusu.grimoireofalice.lib.LibItemName;
import arekkuusu.grimoireofalice.lib.LibMod;

public class ItemAuraByakuren extends ItemModAura {
	
	@SideOnly(Side.CLIENT)
	private ModelBiped model;

	public ItemAuraByakuren(ArmorMaterial material, int dmg) {
		super(material, dmg, LibItemName.AURABYAKUREN, EntityEquipmentSlot.HEAD);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean p_77624_4_) {
		list.add(TextFormatting.DARK_RED + "Demonic in nature");
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 0, 8));
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 0, 8));
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		return new ArmorProperties(0, damageReduceAmount / 25D, armor.getMaxDamage() + 1 - armor.getItemDamage());
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return damageReduceAmount;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {}

	@SuppressWarnings("NullableProblems") //Liar
	@Override
	@SideOnly(Side.CLIENT)
	@Nullable
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot Ui, ModelBiped imodel) {
		if(entityLiving.isSneaking()) {
			if (model == null) model = new ModelByakurenAura();
			model.setModelAttributes(imodel);
			return model;
		}
		return imodel;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if(entity.isSneaking()) {
			return LibMod.MODID + ":textures/models/armor/byakuren_aura.png";
		}
		return LibMod.MODID + ":textures/models/armor/byakuren.png";
    }

}
