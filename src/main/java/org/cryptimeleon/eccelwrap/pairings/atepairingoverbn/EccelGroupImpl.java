package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupImpl;

import java.util.Optional;

abstract class EccelGroupImpl implements GroupImpl {

    public EccelGroupImpl() {

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
        throw new UnsupportedOperationException("ECCelerate structures cannot be represented");
    }
}
