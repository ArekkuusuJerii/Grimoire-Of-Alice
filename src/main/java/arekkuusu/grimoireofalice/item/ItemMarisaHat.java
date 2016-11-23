package arekkuusu.grimoireofalice.item;

import java.util.List;

import arekkuusu.grimoireofalice.client.model.ModelMarisaHat;
import arekkuusu.grimoireofalice.lib.LibItemName;
import arekkuusu.grimoireofalice.lib.LibMod;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMarisaHat extends ItemModArmor {

	@SideOnly(Side.CLIENT)
	private ModelBiped model;

	public ItemMarisaHat(ArmorMaterial materialIn, int dmg) {
		super(materialIn, dmg, LibItemName.MARISA_HAT, EntityEquipmentSlot.HEAD);
		setMaxDamage(200);
		setNoRepair();
		setMaxStackSize(1);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean p_77624_4_) {
		list.add(TextFormatting.GOLD + I18n.format("grimoire.tooltip.marisa_hat_header.name"));
		list.add(TextFormatting.ITALIC + I18n.format("grimoire.tooltip.marisa_hat_description.name"));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if(!player.isSneaking()) {
			world.getEntitiesWithinAABB(EntityItem.class, player.getEntityBoundingBox().expandXyz(5)).forEach(i -> i.onCollideWithPlayer(player));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot Ui, ModelBiped imodel) {
		if(model == null) model = new ModelMarisaHat();
		model.setModelAttributes(imodel);
		return model;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return LibMod.MODID + ":textures/models/armor/marisahat.png";
	}
}