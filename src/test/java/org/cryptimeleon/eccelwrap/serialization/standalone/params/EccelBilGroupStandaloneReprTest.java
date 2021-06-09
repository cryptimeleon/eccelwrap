package org.cryptimeleon.eccelwrap.serialization.standalone.params;

import org.cryptimeleon.eccelwrap.pairings.atepairingoverbn.EccelBasicBilinearGroup;
import org.cryptimeleon.eccelwrap.pairings.atepairingoverbn.EccelBilinearGroup;
import org.cryptimeleon.math.serialization.standalone.StandaloneReprSubTest;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;

public class EccelBilGroupStandaloneReprTest extends StandaloneReprSubTest {
    public void testBilinearGroup(BilinearGroup bilGroup) {
        test(bilGroup);
        test(bilGroup.getG1());
        test(bilGroup.getG2());
        test(bilGroup.getGT());
        try {
            test(bilGroup.getHashIntoG1());
        } catch (UnsupportedOperationException ignored) {}
        try {
            test(bilGroup.getHashIntoG2());
        } catch (UnsupportedOperationException ignored) {}
        try {
            test(bilGroup.getHashIntoGT());
        } catch (UnsupportedOperationException ignored) {}
        try {
            test(bilGroup.getHomomorphismG2toG1());
        } catch (UnsupportedOperationException ignored) {}
    }

    public void testEccelBilinearGroup() {
        testBilinearGroup(new EccelBilinearGroup(100, BilinearGroup.Type.TYPE_2));
        testBilinearGroup(new EccelBilinearGroup(128, BilinearGroup.Type.TYPE_2));
        testBilinearGroup(new EccelBilinearGroup(100, BilinearGroup.Type.TYPE_3));
        testBilinearGroup(new EccelBilinearGroup(128, BilinearGroup.Type.TYPE_3));

        testBilinearGroup(new EccelBasicBilinearGroup(100, BilinearGroup.Type.TYPE_2));
        testBilinearGroup(new EccelBasicBilinearGroup(128, BilinearGroup.Type.TYPE_2));
        testBilinearGroup(new EccelBasicBilinearGroup(100, BilinearGroup.Type.TYPE_3));
        testBilinearGroup(new EccelBasicBilinearGroup(128, BilinearGroup.Type.TYPE_3));
    }
}
