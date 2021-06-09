package org.cryptimeleon.ecceleratewrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.ECPoint;
import iaik.security.ec.math.curve.EllipticCurve;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElement;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.rings.zn.Zp;

import java.math.BigInteger;

class ECCelerateGroup1Impl extends ECCelerateGroupImpl {
    protected EllipticCurve group;

    public ECCelerateGroup1Impl(EllipticCurve group) {
        this.group = group;
    }

    @Override
    public GroupElementImpl getGenerator() throws UnsupportedOperationException {
        return createElement(group.getGenerator());
    }

    protected ECCelerateGroup1ElementImpl createElement(ECPoint point) {
        return new ECCelerateGroup1ElementImpl(this, point.clone());
    }

    @Override
    public GroupElementImpl restoreElement(Representation repr) {
        return new ECCelerateGroup1ElementImpl(this, repr);
    }

    @Override
    public GroupElementImpl getNeutralElement() {
        return createElement(group.getNeutralPoint());
    }

    @Override
    public BigInteger size() throws UnsupportedOperationException {
        return group.getOrder();
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
        return createElement(group.getGenerator()).pow(new Zp(size()).getUniformlyRandomUnit().asInteger());
    }
}
