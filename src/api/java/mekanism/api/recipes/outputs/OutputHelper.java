package mekanism.api.recipes.outputs;

import java.util.function.Supplier;
import javax.annotation.Nonnull;
import mekanism.api.annotations.NonNull;
import mekanism.api.Action;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.GasTank;
import mekanism.api.recipes.SawmillRecipe.ChanceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.commons.lang3.tuple.Pair;

public class OutputHelper {

    public static IOutputHandler<@NonNull GasStack> getOutputHandler(@Nonnull GasTank gasTank) {
        return new IOutputHandler<@NonNull GasStack>() {

            @Override
            public void handleOutput(@NonNull GasStack toOutput, int operations) {
                OutputHelper.handleOutput(gasTank, toOutput, operations);
            }

            @Override
            public int operationsRoomFor(@NonNull GasStack toOutput, int currentMax) {
                return OutputHelper.operationsRoomFor(gasTank, toOutput, currentMax);
            }
        };
    }

    public static IOutputHandler<@NonNull FluidStack> getOutputHandler(@Nonnull IFluidHandler fluidHandler) {
        return new IOutputHandler<@NonNull FluidStack>() {

            @Override
            public void handleOutput(@NonNull FluidStack toOutput, int operations) {
                OutputHelper.handleOutput(fluidHandler, toOutput, operations);
            }

            @Override
            public int operationsRoomFor(@NonNull FluidStack toOutput, int currentMax) {
                return OutputHelper.operationsRoomFor(fluidHandler, toOutput, currentMax);
            }
        };
    }

    //TODO: 1.14, evaluate making Supplier<NonNullList<ItemStack>> be an IItemHandler instead
    public static IOutputHandler<@NonNull ItemStack> getOutputHandler(@Nonnull Supplier<NonNullList<ItemStack>> inventorySupplier, int slot) {
        return new IOutputHandler<@NonNull ItemStack>() {

            @Nonnull
            private NonNullList<ItemStack> getInventory() {
                return inventorySupplier.get();
            }

            @Override
            public void handleOutput(@NonNull ItemStack toOutput, int operations) {
                OutputHelper.handleOutput(getInventory(), slot, toOutput, operations);
            }

            @Override
            public int operationsRoomFor(@NonNull ItemStack toOutput, int currentMax) {
                return OutputHelper.operationsRoomFor(getInventory(), slot, toOutput, currentMax);
            }
        };
    }

    //TODO: 1.14, evaluate making Supplier<NonNullList<ItemStack>> be an IItemHandler instead
    public static IOutputHandler<@NonNull ChanceOutput> getOutputHandler(@Nonnull Supplier<NonNullList<ItemStack>> inventorySupplier, int slot, int secondarySlot) {
        return new IOutputHandler<@NonNull ChanceOutput>() {

            @Nonnull
            private NonNullList<ItemStack> getInventory() {
                return inventorySupplier.get();
            }

            @Override
            public void handleOutput(@NonNull ChanceOutput toOutput, int operations) {
                NonNullList<ItemStack> inventory = getInventory();
                OutputHelper.handleOutput(inventory, slot, toOutput.getMainOutput(), operations);
                //TODO: Batch this into a single addition call, by looping over and calculating things?
                ItemStack secondaryOutput = toOutput.getSecondaryOutput();
                for (int i = 0; i < operations; i++) {
                    OutputHelper.handleOutput(inventory, secondarySlot, secondaryOutput, operations);
                    if (i + 1 < operations) {
                        secondaryOutput = toOutput.nextSecondaryOutput();
                    }
                }
            }

            @Override
            public int operationsRoomFor(@NonNull ChanceOutput toOutput, int currentMax) {
                NonNullList<ItemStack> inventory = getInventory();
                currentMax = OutputHelper.operationsRoomFor(inventory, slot, toOutput.getMainOutput(), currentMax);
                return OutputHelper.operationsRoomFor(inventory, secondarySlot, toOutput.getMaxSecondaryOutput(), currentMax);
            }
        };
    }

    //TODO: 1.14, evaluate making Supplier<NonNullList<ItemStack>> be an IItemHandler instead
    //TODO: IGasHandler??
    public static IOutputHandler<@NonNull Pair<@NonNull ItemStack, @NonNull GasStack>> getOutputHandler(@Nonnull GasTank gasTank,
          @Nonnull Supplier<NonNullList<ItemStack>> inventorySupplier, int slot) {
        return new IOutputHandler<@NonNull Pair<@NonNull ItemStack, @NonNull GasStack>>() {

            @Nonnull
            private NonNullList<ItemStack> getInventory() {
                return inventorySupplier.get();
            }

            @Override
            public void handleOutput(@NonNull Pair<@NonNull ItemStack, @NonNull GasStack> toOutput, int operations) {
                OutputHelper.handleOutput(getInventory(), slot, toOutput.getLeft(), operations);
                OutputHelper.handleOutput(gasTank, toOutput.getRight(), operations);
            }

            @Override
            public int operationsRoomFor(@NonNull Pair<@NonNull ItemStack, @NonNull GasStack> toOutput, int currentMax) {
                currentMax = OutputHelper.operationsRoomFor(getInventory(), slot, toOutput.getLeft(), currentMax);
                return OutputHelper.operationsRoomFor(gasTank, toOutput.getRight(), currentMax);
            }
        };
    }

    //TODO: IGasHandler??
    public static IOutputHandler<@NonNull Pair<@NonNull GasStack, @NonNull GasStack>> getOutputHandler(@Nonnull GasTank leftTank, @Nonnull GasTank rightTank) {
        return new IOutputHandler<@NonNull Pair<@NonNull GasStack, @NonNull GasStack>>() {

            @Override
            public void handleOutput(@NonNull Pair<@NonNull GasStack, @NonNull GasStack> toOutput, int operations) {
                OutputHelper.handleOutput(leftTank, toOutput.getLeft(), operations);
                OutputHelper.handleOutput(rightTank, toOutput.getRight(), operations);
            }

            @Override
            public int operationsRoomFor(@NonNull Pair<@NonNull GasStack, @NonNull GasStack> toOutput, int currentMax) {
                currentMax = OutputHelper.operationsRoomFor(leftTank, toOutput.getLeft(), currentMax);
                return OutputHelper.operationsRoomFor(rightTank, toOutput.getRight(), currentMax);
            }
        };
    }

    //TODO: Should these be public
    private static void handleOutput(@Nonnull GasTank gasTank, @NonNull GasStack toOutput, int operations) {
        if (operations == 0) {
            //This should not happen
            return;
        }
        GasStack output = new GasStack(toOutput, toOutput.getAmount() * operations);
        gasTank.fill(output, Action.EXECUTE);
    }

    private static void handleOutput(@Nonnull IFluidHandler fluidHandler, @NonNull FluidStack toOutput, int operations) {
        if (operations == 0) {
            //This should not happen
            return;
        }
        fluidHandler.fill(new FluidStack(toOutput, toOutput.getAmount() * operations), FluidAction.EXECUTE);
    }

    //TODO: 1.14, evaluate making NonNullList<ItemStack> be an IItemHandler instead
    private static void handleOutput(@Nonnull NonNullList<ItemStack> inventory, int slot, @NonNull ItemStack toOutput, int operations) {
        if (operations == 0 || toOutput.isEmpty()) {
            return;
        }
        ItemStack output = toOutput.copy();
        if (operations > 1) {
            //If we are doing more than one operation we need to make a copy of our stack and change the amount
            // that we are using the fill the tank with
            output.setCount(output.getCount() * operations);
        }
        //TODO: Add some form of handling for if it spreads across multiple slots??

        //TODO: If we do replace inventory with an IItemHandler in 1.14,
        // then the below code can be replaced with: inventory.insertItem(slot, output, false);
        ItemStack stack = inventory.get(slot);
        if (stack.isEmpty()) {
            inventory.set(slot, output.copy());
        } else if (ItemHandlerHelper.canItemStacksStack(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize()) {
            stack.grow(output.getCount());
        }
    }

    private static int operationsRoomFor(@Nonnull GasTank gasTank, @NonNull GasStack toOutput, int currentMax) {
        if (currentMax == 0) {
            //Short circuit that if we already can't perform any outputs, just return
            return 0;
        }
        if (toOutput.isEmpty()) {
            //If the output we want to add is empty we return that we can fit whatever we were told the max is
            return currentMax;
        }
        //Copy the stack and make it be max size
        GasStack maxOutput = new GasStack(toOutput, Integer.MAX_VALUE);
        //Then simulate filling the fluid tank so we can see how much actually can fit
        int amountUsed = gasTank.fill(maxOutput, Action.SIMULATE);
        //Divide the amount we can actually use by the amount one output operation is equal to, capping it at the max we were told about
        return Math.min(amountUsed / toOutput.getAmount(), currentMax);
    }

    private static int operationsRoomFor(@Nonnull IFluidHandler fluidHandler, @NonNull FluidStack toOutput, int currentMax) {
        if (currentMax == 0) {
            //Short circuit that if we already can't perform any outputs, just return
            return 0;
        }
        if (toOutput.isEmpty()) {
            //If the output we want to add is empty we return that we can fit whatever we were told the max is
            return currentMax;
        }
        //Copy the stack and make it be max size
        FluidStack maxOutput = new FluidStack(toOutput, Integer.MAX_VALUE);
        //Then simulate filling the fluid tank so we can see how much actually can fit
        int amountUsed = fluidHandler.fill(maxOutput, FluidAction.SIMULATE);
        //Divide the amount we can actually use by the amount one output operation is equal to, capping it at the max we were told about
        return Math.min(amountUsed / toOutput.getAmount(), currentMax);
    }

    //TODO: 1.14, evaluate making NonNullList<ItemStack> be an IItemHandler instead
    private static int operationsRoomFor(@Nonnull NonNullList<ItemStack> inventory, int slot, @NonNull ItemStack toOutput, int currentMax) {
        if (currentMax == 0) {
            //Short circuit that if we already can't perform any outputs, just return
            return 0;
        }
        if (toOutput.isEmpty()) {
            //If the output we want to add is empty we return that we can fit whatever we were told the max is
            return currentMax;
        }

        ItemStack output = toOutput.copy();
        //Make a cope of the stack we are outputting with its maximum size
        output.setCount(output.getMaxStackSize());

        //TODO: If we do replace inventory with an IItemHandler in 1.14,
        // then the below code can be replaced with:
        // ItemStack remainder = inventory.insertItem(slot, output, true);
        // int amountUsed = toOutput.getCount() - remainder.getCount();
        int amountUsed = 0;
        ItemStack stack = inventory.get(slot);
        if (stack.isEmpty()) {
            amountUsed = output.getCount();
        } else if (ItemHandlerHelper.canItemStacksStack(stack, output)) {
            if (stack.getCount() + output.getCount() <= stack.getMaxStackSize()) {
                amountUsed = output.getCount();
            } else {
                amountUsed = output.getCount() - stack.getCount();
            }
        }
        // End code that can be replaced if using IItemHandler

        //Divide the amount we can actually use by the amount one output operation is equal to, capping it at the max we were told about
        return Math.min(amountUsed / toOutput.getCount(), currentMax);
    }
}