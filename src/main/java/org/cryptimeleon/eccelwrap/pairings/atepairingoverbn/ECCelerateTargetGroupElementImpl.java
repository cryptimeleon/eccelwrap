package main.java.org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.field.ExtensionFieldElement;
import org.cryptimeleon.math.hash.ByteAccumulator;
import org.cryptimeleon.math.serialization.ByteArrayRepresentation;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;

import java.math.BigInteger;
import java.util.Objects;

class ECCelerateTargetGroupElementImpl extends ECCelerateGroupElementImpl {
    protected ExtensionFieldElement element;

    public ECCelerateTargetGroupElementImpl(ECCelerateTargetGroupImpl group, ExtensionFieldElement element) {
        super(group);
        this.element = element;
    }

    public ECCelerateTargetGroupElementImpl(ECCelerateTargetGroupImpl group, Representation repr) {
        super(group);
        this.element = group.targetGroup.newElement(repr.bytes().get());
    }

    @Override
    public ECCelerateTargetGroupImpl getStructure() {
        return (ECCelerateTargetGroupImpl) super.getStructure();
    }

    @Override
    public GroupElementImpl inv() {
        return getStructure().createElement(element.clone().invert());
    }

    @Override
    public GroupElementImpl op(GroupElementImpl e) throws IllegalArgumentException {
        ECCelerateTargetGroupElementImpl elementOp = (ECCelerateTargetGroupElementImpl) e;
        ExtensionFieldElement element1 = elementOp.element;
        return getStructure().createElement(this.element.clone().multiply(element1));
    }

    @Override
    public boolean isNeutralElement() {
        return element.isOne();
    }

    @Override
    public GroupElementImpl pow(BigInteger k) {
        if(k.signum() == -1) {
            k = k.mod(getStructure().size());
        }
        return getStructure().createElement(this.element.clone().exponentiate(k));
    }

    @Override
    public ByteAccumulator updateAccumulator(ByteAccumulator byteAccumulator) {
        byteAccumulator.escapeAndAppend(element.toByteArray());
        return byteAccumulator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ECCelerateTargetGroupElementImpl that = (ECCelerateTargetGroupElementImpl) o;
        return Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element);
    }

    @Override
    public Representation getRepresentation() {
        return new ByteArrayRepresentation(element.toByteArray());
    }
}
