package org.cryptimeleon.ecceleratewrap.pairings.atepairingoverbn;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.math.structures.groups.lazy.LazyBilinearGroup;

public class ECCelerateBilinearGroup extends LazyBilinearGroup {
    public ECCelerateBilinearGroup(int securityParameter, BilinearGroup.Type pairingType) {
        super(new ECCelerateBilinearGroupImpl(securityParameter, pairingType));
    }

    public ECCelerateBilinearGroup(Representation repr) {
        super(repr);
    }
}
