package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.basic.BasicBilinearGroup;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;

public class EccelBasicBilinearGroup extends BasicBilinearGroup {
    public EccelBasicBilinearGroup(int securityParameter, BilinearGroup.Type pairingType) {
        super(new EccelBilinearGroupImpl(securityParameter, pairingType));
    }

    public EccelBasicBilinearGroup(Representation repr) {
        super(repr);
    }
}