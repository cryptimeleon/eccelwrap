package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

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
    protected BigInteger primeP;

    public EccelTargetGroupImpl(ExtensionField targetField, ExtensionFieldElement generator, BigInteger primeP) {
        this.targetField = targetField;
        this.generator = generator;
        this.primeP = primeP;
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
    public GroupElementImpl getGenerator() throws UnsupportedOperationException {
        return createElement(generator.clone());
    }

    @Override
    public GroupElementImpl getUniformlyRandomElement() throws UnsupportedOperationException {
        return createElement(generator.clone()).pow(new Zp(primeP).getUniformlyRandomUnit().asInteger());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EccelTargetGroupImpl that = (EccelTargetGroupImpl) o;
        return Objects.equals(targetField, that.targetField) &&
                Objects.equals(generator, that.generator) &&
                Objects.equals(primeP, that.primeP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetField, generator, primeP);
    }
}
