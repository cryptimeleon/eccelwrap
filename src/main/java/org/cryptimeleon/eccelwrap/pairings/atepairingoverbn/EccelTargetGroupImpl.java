package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.AtePairingOverBarretoNaehrigCurveFactory;
import iaik.security.ec.math.curve.Pairing;
import iaik.security.ec.math.curve.PairingTypes;
import iaik.security.ec.math.field.ExtensionField;
import iaik.security.ec.math.field.ExtensionFieldElement;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.rings.zn.Zp;

import java.math.BigInteger;
import java.util.Objects;

class EccelTargetGroupImpl extends EccelGroupImpl {
    protected ExtensionField targetField;
    protected ExtensionFieldElement generator;
    protected BigInteger size;

    public EccelTargetGroupImpl(PairingTypes pairingType, int groupBitSize) {
        super(pairingType, groupBitSize);
    }

    public EccelTargetGroupImpl(Representation repr) {
        super(repr);
    }

    @Override
    void init(PairingTypes pairingType, int groupBitSize) {
        Pairing pairing = AtePairingOverBarretoNaehrigCurveFactory.getPairing(
                pairingType,
                groupBitSize
        );
        targetField = pairing.getTargetGroup();
        generator = pairing.pair(
                pairing.getGroup1().getGenerator(),
                pairing.getGroup2().getGenerator()
        );
        // all groups involved in pairing have same order
        size = pairing.getGroup1().getOrder();
    }

    @Override
    public GroupElementImpl restoreElement(Representation repr) {
        return new EccelTargetGroupElementImpl(this, repr);
    }

    public EccelTargetGroupElementImpl createElement(ExtensionFieldElement element) {
        return new EccelTargetGroupElementImpl(this, element);
    }

    @Override
    public GroupElementImpl getNeutralElement() {
        return createElement(targetField.getOne());
    }

    @Override
    public BigInteger size() throws UnsupportedOperationException {
        return size;
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
    public GroupElementImpl getGenerator() throws UnsupportedOperationException {
        return createElement(generator.clone());
    }

    @Override
    public GroupElementImpl getUniformlyRandomElement() throws UnsupportedOperationException {
        return createElement(generator.clone()).pow(new Zp(size).getUniformlyRandomUnit().asInteger());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EccelTargetGroupImpl that = (EccelTargetGroupImpl) o;
        return Objects.equals(targetField, that.targetField) &&
                Objects.equals(generator, that.generator) &&
                Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetField, generator, size);
    }
}
