package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.AtePairingOverBarretoNaehrigCurveFactory;
import iaik.security.ec.math.curve.Pairing;
import iaik.security.ec.math.curve.PairingTypes;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.annotations.ReprUtil;
import org.cryptimeleon.math.serialization.annotations.Represented;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.GroupHomomorphismImpl;

import java.util.Objects;

class EccelIsomorphism implements GroupHomomorphismImpl {
    @Represented
    protected PairingTypes pairingType;
    @Represented
    protected Integer groupBitSize;

    protected Pairing pairing;

    public EccelIsomorphism(PairingTypes pairingType, Integer groupBitSize) {
        this.pairingType = pairingType;
        this.groupBitSize = groupBitSize;
        init(pairingType, groupBitSize);
    }

    public EccelIsomorphism(Representation repr) {
        new ReprUtil(this).deserialize(repr);
        init(pairingType, groupBitSize);
    }

    void init(PairingTypes pairingType, Integer groupBitSize) {
        pairing = AtePairingOverBarretoNaehrigCurveFactory.getPairing(
                pairingType,
                groupBitSize
        );
    }

    @Override
    public GroupElementImpl apply(GroupElementImpl groupElement) {
        if (!(groupElement instanceof EccelGroup2ElementImpl)) {
            throw new IllegalArgumentException("Given element" + groupElement + " is not element of G2");
        }
        EccelGroup2ElementImpl elementG2 = (EccelGroup2ElementImpl) groupElement;
        return new EccelGroup1ElementImpl(
                new EccelGroup1Impl(pairingType, groupBitSize),
                pairing.applyIsomorphism(elementG2.point)
        );
    }

    @Override
    public Representation getRepresentation() {
        return ReprUtil.serialize(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EccelIsomorphism that = (EccelIsomorphism) o;
        return pairingType == that.pairingType &&
                Objects.equals(groupBitSize, that.groupBitSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pairingType, groupBitSize);
    }
}
