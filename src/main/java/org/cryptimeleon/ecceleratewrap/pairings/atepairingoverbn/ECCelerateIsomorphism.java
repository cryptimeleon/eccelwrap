package org.cryptimeleon.ecceleratewrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.Pairing;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.annotations.Represented;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.GroupHomomorphismImpl;

public class ECCelerateIsomorphism implements GroupHomomorphismImpl {

    @Represented
    Pairing pairing;

    public ECCelerateIsomorphism(Pairing pairing) {
        this.pairing = pairing;
    }
    @Override
    public GroupElementImpl apply(GroupElementImpl groupElement) {
        if (!(groupElement instanceof ECCelerateGroup2ElementImpl)) {
            throw new IllegalArgumentException("Given element" + groupElement + " is not element of G2");
        }
        ECCelerateGroup2ElementImpl elementG2 = (ECCelerateGroup2ElementImpl) groupElement;
        return new ECCelerateGroup1ElementImpl(
                new ECCelerateGroup1Impl(pairing.getGroup1()),
                pairing.applyIsomorphism(elementG2.point)
        );
    }

    @Override
    public Representation getRepresentation() {
        return null;
    }
}
