package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.field.ExtensionFieldElement;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMapImpl;

import java.math.BigInteger;

/**
 * This is in ECCelerate the class Pairing
 */
class EccelBilinearMapImpl implements BilinearMapImpl {
    protected EccelBilinearGroupImpl bilinearGroup;

    public EccelBilinearMapImpl(EccelBilinearGroupImpl bilinearGroup){
        this.bilinearGroup = bilinearGroup;
    }

    @Override
    public EccelTargetGroupElementImpl apply(GroupElementImpl groupElement1, GroupElementImpl groupElement2,
                                             BigInteger k) {
        ExtensionFieldElement extensionFieldElement = bilinearGroup.pairing.pair(
                ((EccelGroup1ElementImpl) groupElement1.pow(k)).point,
                ((EccelGroup2ElementImpl) groupElement2).point
        );
        return bilinearGroup.getGT().createElement(extensionFieldElement);
    }

    @Override
    public boolean isSymmetric() {
        return false;
    }
}
