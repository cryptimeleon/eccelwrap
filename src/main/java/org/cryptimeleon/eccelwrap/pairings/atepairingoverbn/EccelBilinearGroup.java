package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.math.structures.groups.lazy.LazyBilinearGroup;

public class EccelBilinearGroup extends LazyBilinearGroup {
    public EccelBilinearGroup(int securityParameter, BilinearGroup.Type pairingType) {
        super(new EccelBilinearGroupImpl(securityParameter, pairingType));
    }

    public EccelBilinearGroup(Representation repr) {
        super(repr);
    }
}
