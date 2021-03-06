package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.errorhandling.DecodingException;
import iaik.security.ec.math.curve.ECPoint;
import org.cryptimeleon.math.hash.ByteAccumulator;
import org.cryptimeleon.math.serialization.ByteArrayRepresentation;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;

import java.math.BigInteger;
import java.util.Objects;

class EccelGroup2ElementImpl extends EccelGroupElementImpl {
    protected ECPoint point;

    public EccelGroup2ElementImpl(EccelGroup2Impl group, ECPoint point) {
        super(group);
        this.point = point;
    }

    public EccelGroup2ElementImpl(EccelGroup2Impl group, Representation repr) {
        super(group);
        try {
            point = group.curve.decodePoint(repr.bytes().get());
        } catch (DecodingException e) {
            throw new IllegalArgumentException("Representation " + repr + " of given point is not on the curve " + group);
        }
    }
    
    @Override
    public EccelGroup2Impl getStructure() {
        return (EccelGroup2Impl) super.getStructure();
    }

    @Override
    public EccelGroup2ElementImpl inv() {
        return getStructure().createElement(point.clone().negatePoint());
    }

    @Override
    public GroupElementImpl op(GroupElementImpl groupElement) throws IllegalArgumentException {
        EccelGroup2ElementImpl elementOp = (EccelGroup2ElementImpl) groupElement;
        if (elementOp.point.equals(point)) {
            return getStructure().createElement(point.clone().doublePoint());
        } else {
            return getStructure().createElement(point.clone().addPoint(elementOp.point));
        }
    }

    @Override
    public EccelGroup2ElementImpl pow(BigInteger k) {
        if(k.signum() == -1) {
            k = k.mod(getStructure().size());
        }
        return getStructure().createElement(point.clone().multiplyPoint(k));
    }

    @Override
    public boolean isNeutralElement() {
        return point.isNeutralPoint();
    }

    @Override
    public ByteAccumulator updateAccumulator(ByteAccumulator accumulator) {
        accumulator.escapeAndAppend(point.toString());
        return accumulator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EccelGroup2ElementImpl that = (EccelGroup2ElementImpl) o;
        return Objects.equals(point, that.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point.scalePoint());
    }

    @Override
    public Representation getRepresentation() {
        return new ByteArrayRepresentation(point.encodePoint());
    }
}
