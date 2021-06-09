package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.ECPoint;
import iaik.security.ec.math.curve.EllipticCurve;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.rings.zn.Zp;

import java.math.BigInteger;

class EccelGroup2Impl extends EccelGroupImpl {
    protected EllipticCurve curve;
    protected ECPoint generator;
    protected BigInteger primeP;

    public EccelGroup2Impl(EllipticCurve curve, ECPoint generator, BigInteger primeP) {
        this.curve = curve;
        this.generator = generator;
        this.primeP = primeP;
    }

    @Override
    public GroupElementImpl getGenerator() throws UnsupportedOperationException {
        return createElement(curve.getGenerator());
    }

    protected EccelGroup2ElementImpl createElement(ECPoint point) {
        return new EccelGroup2ElementImpl(this, point);
    }

    @Override
    public GroupElementImpl restoreElement(Representation repr) {
        return new EccelGroup2ElementImpl(this, repr);
    }

    @Override
    public GroupElementImpl getNeutralElement() {
        return createElement(curve.getNeutralPoint());
    }

    @Override
    public BigInteger size() throws UnsupportedOperationException {
        return primeP;
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
        return createElement(generator.clone()).pow(new Zp(primeP).getUniformlyRandomUnit().asInteger());
    }
}
