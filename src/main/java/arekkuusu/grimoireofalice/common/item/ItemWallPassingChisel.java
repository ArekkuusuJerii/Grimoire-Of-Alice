/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.common.item;

import arekkuusu.grimoireofalice.api.sound.GrimoireSoundEvents;
import arekkuusu.grimoireofalice.common.lib.LibName;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemWallPassingChisel extends ItemBase  {

	public ItemWallPassingChisel() {
		super(LibName.WALL_PASSING_CHISEL);
		setMaxDamage(100);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float x, float y, float z) {
		BlockPos travel = travelBlockPos(world, player, pos, facing);
		if(travel != null && isSafePos(world, travel.up())) { //If BlockPos isn't null and the block above is safe -->
			if(player instanceof EntityPlayerMP) {
				player.setPositionAndUpdate(travel.getX() + 0.5, travel.getY(), travel.getZ() + 0.5);
				player.getHeldItem(hand).damageItem(1, player);
			}
			world.playSound(player, travel.getX(), travel.getY(), travel.getZ(), GrimoireSoundEvents.WARP, SoundCategory.PLAYERS, 0.2F, itemRand.nextFloat() * 0.4F + 0.8F);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	/**
	 * @param world  The world
	 * @param pos    Pos of the block right clicked
	 * @param facing Facing of the block right clicked
	 * @return Position for player
	 */
	@Nullable
	private static BlockPos travelBlockPos(World world, EntityPlayer player, BlockPos pos, EnumFacing facing) {
		facing = facing.getOpposite();
		BlockPos triedPos = pos;

		for(int i = 0; i < 50; i++) {
			IBlockState stateBase = world.getBlockState(triedPos);
			if(stateBase.getBlock() != Blocks.BEDROCK && stateBase.getBlock().canEntityDestroy(stateBase, world, pos, player)) {
				if(isSafePos(world, triedPos)) {
					//Logic to prevent player to suffocate or get in awkward positions
					if(facing == EnumFacing.DOWN) {
						if(!isSafePos(world, triedPos.offset(facing))) {
							return null;
						}
						triedPos = triedPos.offset(facing);
					}
					else if(facing != EnumFacing.UP && isSafePos(world, triedPos.down())) {
						triedPos = triedPos.down();
					}

					return triedPos;
				}
				else {
					triedPos = triedPos.offset(facing);
				}
			}
		}

		return null;
	}

	private static boolean isSafePos(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		return state.getBlock().isAir(state, world, pos);// || state.isSideSolid(world, pos, EnumFacing.UP);
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}
}
