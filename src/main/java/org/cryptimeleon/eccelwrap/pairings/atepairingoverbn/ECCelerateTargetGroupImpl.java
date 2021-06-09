package main.java.org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.field.ExtensionField;
import iaik.security.ec.math.field.ExtensionFieldElement;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElement;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.rings.zn.Zp;

import java.math.BigInteger;

class ECCelerateTargetGroupImpl extends ECCelerateGroupImpl {
    protected ExtensionField targetGroup;
    protected ExtensionFieldElement generator;
    protected BigInteger primeP;

    public ECCelerateTargetGroupImpl(ExtensionField targetGroup, ExtensionFieldElement generator, BigInteger primeP) {
        this.targetGroup = targetGroup;
        this.generator = generator;
        this.primeP = primeP;
    }

    @Override
    public GroupElementImpl restoreElement(Representation repr) {
        return new ECCelerateTargetGroupElementImpl(this, repr);
    }

    public ECCelerateTargetGroupElementImpl createElement(ExtensionFieldElement element) {
        return new ECCelerateTargetGroupElementImpl(this, element);
    }

    @Override
    public GroupElementImpl getNeutralElement() {
        return createElement(targetGroup.getOne());
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
}
