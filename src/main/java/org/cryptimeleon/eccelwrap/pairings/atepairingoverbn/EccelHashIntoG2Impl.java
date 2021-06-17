package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.PairingTypes;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.annotations.ReprUtil;
import org.cryptimeleon.math.serialization.annotations.Represented;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.HashIntoGroupImpl;

import java.util.Objects;

class EccelHashIntoG2Impl implements HashIntoGroupImpl {

    @Represented
    protected PairingTypes pairingType;
    @Represented
    protected Integer groupBitSize;

    protected EccelGroup2Impl group;

    public EccelHashIntoG2Impl(PairingTypes pairingType, Integer groupBitSize) {
        this.pairingType = pairingType;
        this.groupBitSize = groupBitSize;
        init(pairingType, groupBitSize);
    }

    public EccelHashIntoG2Impl(Representation repr) {
        new ReprUtil(this).deserialize(repr);
        init(pairingType, groupBitSize);
    }

    void init(PairingTypes pairingType, Integer groupBitSize) {
        group = new EccelGroup2Impl(pairingType, groupBitSize);
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
        return pairingType == that.pairingType &&
                Objects.equals(groupBitSize, that.groupBitSize) &&
                Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pairingType, groupBitSize, group);
    }

    @Override
    public Representation getRepresentation() {
        return ReprUtil.serialize(this);
    }
}
