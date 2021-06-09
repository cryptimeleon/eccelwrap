package org.cryptimeleon.eccelwrap.pairings;

import org.cryptimeleon.eccelwrap.pairings.atepairingoverbn.EccelBilinearGroup;
import org.cryptimeleon.math.pairings.PairingTests;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PairingTest extends PairingTests {
    public PairingTest(BilinearMap pairing) {
        super(pairing);
    }

    @Parameterized.Parameters(name = "Test: {0}")
    public static Collection<BilinearMap[]> data() {
        BilinearMap params[][] = new BilinearMap[][]{
                {new EccelBilinearGroup(100, BilinearGroup.Type.TYPE_3).getBilinearMap()},
                {new EccelBilinearGroup(100, BilinearGroup.Type.TYPE_2).getBilinearMap()}
        };
        return Arrays.asList(params);
    }
}
