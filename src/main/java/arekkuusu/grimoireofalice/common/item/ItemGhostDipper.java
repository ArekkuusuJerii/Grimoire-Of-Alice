/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 * <p>
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.common.item;

import arekkuusu.grimoireofalice.common.lib.LibName;
import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Queue;

public class ItemGhostDipper extends ItemBase {

	public ItemGhostDipper() {
		super(LibName.GHOST_DIPPER);
		setMaxStackSize(1);
		setMaxDamage(100);
		setNoRepair();
		addPropertyOverride(new ResourceLocation("using"),
				(stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1F : 0F);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.EPIC;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		RayTraceResult raytraceresult = rayTrace(world, player, true);
		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(player, world, stack, raytraceresult);
		if(ret != null) {
			return ret;
		}

		//noinspection ConstantConditions
		if(raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
			return new ActionResult<>(EnumActionResult.PASS, stack);
		} else {
			if(!player.isSneaking()) {
				BlockPos blockpos = raytraceresult.getBlockPos();

				if(absorb(world, blockpos)) {
					world.playSound(null, blockpos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if(stack.isItemDamaged()) {
						stack.setItemDamage(stack.getItemDamage() - 1);
					}
					player.setActiveHand(hand);
					return new ActionResult<>(EnumActionResult.SUCCESS, stack);
				}
			} else {
				BlockPos pos = raytraceresult.getBlockPos();
				boolean replaceable = world.getBlockState(pos).getBlock().isReplaceable(world, pos);
				BlockPos posUp = replaceable && raytraceresult.sideHit == EnumFacing.UP ? pos : pos.offset(raytraceresult.sideHit);

				if(player.canPlayerEdit(posUp, raytraceresult.sideHit, stack)) {

					IBlockState iblockstate = world.getBlockState(posUp);
					Material material = iblockstate.getMaterial();
					boolean isSolid = material.isSolid();
					boolean canReplace = iblockstate.getBlock().isReplaceable(world, posUp);

					if(world.isAirBlock(posUp) || !isSolid || canReplace) {
						if(world.provider.doesWaterVaporize()) {
							world.playSound(null, posUp, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F,
									2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

							for(int k = 0; k < 8; ++k) {
								world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posUp.getX() + itemRand.nextDouble(),
										posUp.getY() + itemRand.nextDouble(), posUp.getZ() + itemRand.nextDouble(), 0.0D, 0.0D, 0.0D);
							}
						} else {
							if(!world.isRemote && (isSolid || canReplace) && !material.isLiquid()) {
								world.destroyBlock(posUp, true);
							}

							world.playSound(null, posUp, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
							world.setBlockState(posUp, Blocks.FLOWING_WATER.getDefaultState(), 11);
							stack.damageItem(1, player);
							player.setActiveHand(hand);
							return new ActionResult<>(EnumActionResult.SUCCESS, stack);
						}
					}
				}
			}
		}

		return new ActionResult<>(EnumActionResult.FAIL, stack);
	}

	private static boolean absorb(World world, BlockPos pos) {
		Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
		queue.add(new Tuple<>(pos, 0));
		int blocksChanged = 0;

		while(!queue.isEmpty() && blocksChanged <= 64) {
			Tuple<BlockPos, Integer> tuple = queue.poll();
			BlockPos blockpos = tuple.getFirst();
			int depth = tuple.getSecond();

			for(EnumFacing enumfacing : EnumFacing.values()) {
				BlockPos offset = blockpos.offset(enumfacing);

				if(world.getBlockState(offset).getMaterial() == Material.WATER) {
					++blocksChanged;

					//The client is for the most part concerned with if any blocks were changed. If we reach this far that is true. No need to do more.
					if(!world.isRemote) {
						world.setBlockState(offset, Blocks.AIR.getDefaultState());

						if(depth < 6) {
							queue.add(new Tuple<>(offset, depth + 1));
						}
					}
				}
			}
		}

		return blocksChanged > 0;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.NONE;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 2000;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}
}
