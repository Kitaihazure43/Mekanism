package mekanism.api.gas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mekanism.api.util.CapabilityUtils;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A handy class containing several utilities for efficient gas transfer.
 * @author AidanBrady
 *
 */
public final class GasTransmission
{
	public static IGasHandler[] getConnectedAcceptors(TileEntity tileEntity, Collection<EnumFacing> sides)
	{
		return getConnectedAcceptors(tileEntity.getPos(), tileEntity.getWorld(), sides);
	}

	public static IGasHandler[] getConnectedAcceptors(BlockPos pos, World world, Collection<EnumFacing> sides)
	{
		IGasHandler[] acceptors = new IGasHandler[] {null, null, null, null, null, null};

		for(EnumFacing orientation : sides)
		{
			TileEntity acceptor = world.getTileEntity(pos.offset(orientation));

			if(CapabilityUtils.hasCapability(acceptor, Capabilities.GAS_HANDLER_CAPABILITY, orientation.getOpposite()))
			{
				acceptors[orientation.ordinal()] = CapabilityUtils.getCapability(acceptor, Capabilities.GAS_HANDLER_CAPABILITY, orientation.getOpposite());
			}
		}

		return acceptors;
	}
	
	/**
	 * Gets all the acceptors around a tile entity.
	 * @param tileEntity - center tile entity
	 * @return array of IGasAcceptors
	 */
	public static IGasHandler[] getConnectedAcceptors(TileEntity tileEntity)
	{
		return getConnectedAcceptors(tileEntity.getPos(), tileEntity.getWorld(), Arrays.asList(EnumFacing.VALUES));
	}

	public static IGasHandler[] getConnectedAcceptors(BlockPos pos, World world)
	{
		return getConnectedAcceptors(pos, world, Arrays.asList(EnumFacing.VALUES));
	}

	public static boolean isValidAcceptorOnSide(TileEntity tile, EnumFacing side)
	{
		if(CapabilityUtils.hasCapability(tile, Capabilities.GRID_TRANSMITTER_CAPABILITY, side.getOpposite()))
		{
			return false;
		}
		
		if(CapabilityUtils.hasCapability(tile, Capabilities.TUBE_CONNECTION_CAPABILITY, side.getOpposite()))
		{
			ITubeConnection connection = CapabilityUtils.getCapability(tile, Capabilities.TUBE_CONNECTION_CAPABILITY, side.getOpposite());
			
			if(connection.canTubeConnect(side.getOpposite()))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Removes a specified amount of gas from an IGasItem.
	 * @param itemStack - ItemStack of the IGasItem
	 * @param type - type of gas to remove from the IGasItem, null if it doesn't matter
	 * @param amount - amount of gas to remove from the ItemStack
	 * @return the GasStack removed by the IGasItem
	 */
	public static GasStack removeGas(ItemStack itemStack, Gas type, int amount)
	{
		if(!itemStack.isEmpty() && itemStack.getItem() instanceof IGasItem)
		{
			IGasItem item = (IGasItem)itemStack.getItem();

			if(type != null && item.getGas(itemStack) != null && item.getGas(itemStack).getGas() != type || !item.canProvideGas(itemStack, type))
			{
				return null;
			}

			return item.removeGas(itemStack, amount);
		}

		return null;
	}

	/**
	 * Adds a specified amount of gas to an IGasItem.
	 * @param itemStack - ItemStack of the IGasItem
	 * @param stack - stack to add to the IGasItem
	 * @return amount of gas accepted by the IGasItem
	 */
	public static int addGas(ItemStack itemStack, GasStack stack)
	{
		if(!itemStack.isEmpty() && itemStack.getItem() instanceof IGasItem && ((IGasItem)itemStack.getItem()).canReceiveGas(itemStack, stack.getGas()))
		{
			return ((IGasItem)itemStack.getItem()).addGas(itemStack, stack.copy());
		}

		return 0;
	}
	
	/**
	 * Emits gas from a central block by splitting the received stack among the sides given.
	 * @param stack - the stack to output
	 * @param from - the TileEntity to output from
	 * @param sides - the list of sides to output from
	 * @return the amount of gas emitted
	 */
	public static int emit(GasStack stack, TileEntity from, Collection<EnumFacing> sides)
	{
		if(stack == null)
		{
			return 0;
		}
		
		List<IGasHandler> availableAcceptors = new ArrayList<IGasHandler>();
		IGasHandler[] possibleAcceptors = getConnectedAcceptors(from, sides);
		
		for(int i = 0; i < possibleAcceptors.length; i++)
		{
			IGasHandler handler = possibleAcceptors[i];
			
			if(handler != null && handler.canReceiveGas(EnumFacing.getFront(i).getOpposite(), stack.getGas()))
			{
				availableAcceptors.add(handler);
			}
		}

		Collections.shuffle(availableAcceptors);

		int toSend = stack.amount;
		int prevSending = toSend;

		if(!availableAcceptors.isEmpty())
		{
			int divider = availableAcceptors.size();
			int remaining = toSend % divider;
			int sending = (toSend-remaining)/divider;

			for(IGasHandler acceptor : availableAcceptors)
			{
				int currentSending = sending;

				if(remaining > 0)
				{
					currentSending++;
					remaining--;
				}
				
				EnumFacing dir = EnumFacing.getFront(Arrays.asList(possibleAcceptors).indexOf(acceptor)).getOpposite();
				toSend -= acceptor.receiveGas(dir, new GasStack(stack.getGas(), currentSending), true);
			}
		}

		return prevSending-toSend;
	}
}
