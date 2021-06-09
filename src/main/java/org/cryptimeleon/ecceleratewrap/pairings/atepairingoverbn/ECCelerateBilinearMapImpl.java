package org.cryptimeleon.ecceleratewrap.pairings.atepairingoverbn;

import iaik.security.ec.math.field.ExtensionFieldElement;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElement;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMapImpl;

import java.math.BigInteger;
import java.util.Map;

/**
 * This is in ECCelerate the class Pairing
 */
class ECCelerateBilinearMapImpl implements BilinearMapImpl {
    protected ECCelerateBilinearGroupImpl bilinearGroup;

    public ECCelerateBilinearMapImpl(ECCelerateBilinearGroupImpl bilinearGroup){
        this.bilinearGroup = bilinearGroup;
    }

    @Override
    public ECCelerateTargetGroupElementImpl apply(GroupElementImpl groupElement1, GroupElementImpl groupElement2,
                                                  BigInteger k) {
        ExtensionFieldElement extensionFieldElement = bilinearGroup.pairing.pair(
                ((ECCelerateGroup1ElementImpl) groupElement1.pow(k)).point,
                ((ECCelerateGroup2ElementImpl) groupElement2).point
        );
        return bilinearGroup.getGT().createElement(extensionFieldElement);
    }

    @Override
    public boolean isSymmetric() {
        return false;
    }
}
