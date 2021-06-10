package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.ECPoint;
import iaik.security.ec.math.curve.EllipticCurve;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.rings.zn.Zp;

import java.math.BigInteger;
import java.util.Objects;

class EccelGroup1Impl extends EccelGroupImpl {
    protected EllipticCurve curve;

    public EccelGroup1Impl(EllipticCurve curve) {
        this.curve = curve;
    }

    @Override
    public GroupElementImpl getGenerator() throws UnsupportedOperationException {
        return createElement(curve.getGenerator());
    }

    protected EccelGroup1ElementImpl createElement(ECPoint point) {
        return new EccelGroup1ElementImpl(this, point.clone());
    }

    @Override
    public GroupElementImpl restoreElement(Representation repr) {
        return new EccelGroup1ElementImpl(this, repr);
    }

    @Override
    public GroupElementImpl getNeutralElement() {
        return createElement(curve.getNeutralPoint());
    }

    @Override
    public BigInteger size() throws UnsupportedOperationException {
        return curve.getOrder();
    }

    @Override
    public boolean hasPrimeSize() {
        return false;
    }

    @Override
    public double estimateCostInvPerOp() {
        return 0;
    }

    @Override
    public GroupElementImpl getUniformlyRandomElement() throws UnsupportedOperationException {
        return createElement(curve.getGenerator()).pow(new Zp(size()).getUniformlyRandomUnit().asInteger());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EccelGroup1Impl that = (EccelGroup1Impl) o;
        return Objects.equals(curve, that.curve);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curve);
    }
}
