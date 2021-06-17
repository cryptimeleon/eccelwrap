package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.*;
import org.cryptimeleon.math.random.RandomGenerator;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.annotations.ReprUtil;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.rings.zn.Zp;

import java.math.BigInteger;
import java.util.Objects;

class EccelGroup2Impl extends EccelGroupImpl {
    protected EllipticCurve curve;
    protected BigInteger size;

    public EccelGroup2Impl(PairingTypes pairingType, int groupBitSize) {
        super(pairingType, groupBitSize);
    }

    public EccelGroup2Impl(Representation repr) {
        super(repr);
    }

    @Override
    void init(PairingTypes pairingType, int groupBitSize) {
        Pairing pairing = AtePairingOverBarretoNaehrigCurveFactory.getPairing(
                pairingType,
                groupBitSize
        );
        this.curve = pairing.getGroup2();
        // above curve's getOrder method gives the curve size, not the subgroup size. So use G1 size here
        this.size = pairing.getGroup1().getOrder();
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
        return size;
    }

    @Override
    public double estimateCostInvPerOp() {
        return 0;
    }

    @Override
    public GroupElementImpl getUniformlyRandomElement() throws UnsupportedOperationException {
        return createElement(curve.getGenerator().clone()).pow(RandomGenerator.getRandomNumber(size()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EccelGroup2Impl that = (EccelGroup2Impl) o;
        return Objects.equals(curve, that.curve);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curve);
    }
}
