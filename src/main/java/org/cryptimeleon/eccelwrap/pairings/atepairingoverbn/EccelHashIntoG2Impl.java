package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.HashIntoGroupImpl;

import java.util.Objects;

class EccelHashIntoG2Impl implements HashIntoGroupImpl {
    protected EccelGroup2Impl group;

    public EccelHashIntoG2Impl(EccelGroup2Impl group) {
        this.group = group;
    }

    @Override
    public GroupElementImpl hashIntoGroupImpl(byte[] x) {
        return group.createElement(group.curve.hashToPoint(x));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EccelHashIntoG2Impl that = (EccelHashIntoG2Impl) o;
        return Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group);
    }

    @Override
    public Representation getRepresentation() {
        throw new UnsupportedOperationException("ECCelerate structures cannot be represented");
    }
}
