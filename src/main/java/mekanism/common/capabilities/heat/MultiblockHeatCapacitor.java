package mekanism.common.capabilities.heat;

import java.util.Objects;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import mekanism.api.annotations.FieldsAreNonnullByDefault;
import mekanism.api.heat.HeatAPI;
import mekanism.api.math.FloatingLongSupplier;
import mekanism.common.tile.TileEntityMultiblock;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultiblockHeatCapacitor<MULTIBLOCK extends TileEntityMultiblock<?>> extends VariableHeatCapacitor {

    private MULTIBLOCK multiblock;

    public static <MULTIBLOCK extends TileEntityMultiblock<?>> MultiblockHeatCapacitor<MULTIBLOCK> create(MULTIBLOCK multiblock, FloatingLongSupplier heatCapacity) {
        return create(multiblock, heatCapacity, () -> HeatAPI.DEFAULT_INVERSE_CONDUCTION, () -> HeatAPI.DEFAULT_INVERSE_INSULATION, true, true);
    }

    public static <MULTIBLOCK extends TileEntityMultiblock<?>> MultiblockHeatCapacitor<MULTIBLOCK> create(MULTIBLOCK multiblock, FloatingLongSupplier heatCapacity,
          FloatingLongSupplier conductionCoefficient, FloatingLongSupplier insulationCoefficient, boolean absorbHeat, boolean emitHeat) {
        Objects.requireNonNull(heatCapacity, "Heat capacity supplier cannot be null");
        Objects.requireNonNull(conductionCoefficient, "Conduction coefficient supplier cannot be null");
        Objects.requireNonNull(insulationCoefficient, "Insulation coefficient supplier cannot be null");
        return new MultiblockHeatCapacitor<>(multiblock, heatCapacity, conductionCoefficient, insulationCoefficient, absorbHeat, emitHeat);
    }

    protected MultiblockHeatCapacitor(MULTIBLOCK multiblock, FloatingLongSupplier heatCapacity, FloatingLongSupplier conductionCoefficient,
          FloatingLongSupplier insulationCoefficient, boolean absorbHeat, boolean emitHeat) {
        super(heatCapacity, conductionCoefficient, insulationCoefficient, absorbHeat, emitHeat, null);
        this.multiblock = multiblock;
    }

    @Override
    public void onContentsChanged() {
        super.onContentsChanged();
        if (multiblock.hasWorld() && !multiblock.isRemote() && multiblock.isRendering) {
            multiblock.markDirty(false);
        }
    }
}
