package main.java.org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.ECPoint;
import iaik.security.ec.math.curve.EllipticCurve;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElement;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.rings.zn.Zp;

import java.math.BigInteger;

class ECCelerateGroup2Impl extends ECCelerateGroupImpl {
    protected EllipticCurve group;
    protected ECPoint generator;
    protected BigInteger primeP;

    public ECCelerateGroup2Impl(EllipticCurve group, ECPoint generator, BigInteger primeP) {
        this.group = group;
        this.generator = generator;
        this.primeP = primeP;
    }

    @Override
    public GroupElementImpl getGenerator() throws UnsupportedOperationException {
        return createElement(group.getGenerator());
    }

    protected ECCelerateGroup2ElementImpl createElement(ECPoint point) {
        return new ECCelerateGroup2ElementImpl(this, point);
    }

    @Override
    public GroupElementImpl restoreElement(Representation repr) {
        return new ECCelerateGroup2ElementImpl(this, repr);
    }

    @Override
    public GroupElementImpl getNeutralElement() {
        return createElement(group.getNeutralPoint());
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
