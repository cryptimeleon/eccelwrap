package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.Pairing;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.annotations.Represented;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.GroupHomomorphismImpl;

import java.util.Objects;

class EccelIsomorphism implements GroupHomomorphismImpl {

    Pairing pairing;

    public EccelIsomorphism(Pairing pairing) {
        this.pairing = pairing;
    }
    @Override
    public GroupElementImpl apply(GroupElementImpl groupElement) {
        if (!(groupElement instanceof EccelGroup2ElementImpl)) {
            throw new IllegalArgumentException("Given element" + groupElement + " is not element of G2");
        }
        EccelGroup2ElementImpl elementG2 = (EccelGroup2ElementImpl) groupElement;
        return new EccelGroup1ElementImpl(
                new EccelGroup1Impl(pairing.getGroup1()),
                pairing.applyIsomorphism(elementG2.point)
        );
    }

    @Override
    public Representation getRepresentation() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EccelIsomorphism that = (EccelIsomorphism) o;
        return Objects.equals(pairing, that.pairing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pairing);
    }
}
