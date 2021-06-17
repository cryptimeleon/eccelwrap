package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.PairingTypes;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.annotations.ReprUtil;
import org.cryptimeleon.math.serialization.annotations.Represented;
import org.cryptimeleon.math.structures.groups.GroupImpl;

import java.util.Optional;

abstract class EccelGroupImpl implements GroupImpl {

    @Represented
    PairingTypes pairingType;
    @Represented
    Integer groupBitSize;

    public EccelGroupImpl(PairingTypes pairingType, int groupBitSize) {
        this.pairingType = pairingType;
        this.groupBitSize = groupBitSize;
        init(pairingType, groupBitSize);
    }

    public EccelGroupImpl(Representation repr) {
        new ReprUtil(this).deserialize(repr);
        // workaround for not being able to represent Eccelerate structures directly
        init(pairingType, groupBitSize);
    }

    /**
     * Initializes this group based on the given parameters.
     * <p>
     * The group objects from Eccelerate themselves cannot be represented, so we need to recreate them using
     * the pairing factory using parameters that CAN be represented.
     */
    abstract void init(PairingTypes pairingType, int groupBitSize);

    @Override
    public boolean hasPrimeSize() {
        return true;
    }

    @Override
    public Optional<Integer> getUniqueByteLength() {
        return Optional.empty();
    }

    @Override
    public boolean isCommutative() {
        return true;
    }

    @Override
    public Representation getRepresentation() {
        return ReprUtil.serialize(this);
    }
}
