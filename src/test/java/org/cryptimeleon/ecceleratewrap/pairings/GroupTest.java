package org.cryptimeleon.ecceleratewrap.pairings;

import iaik.security.ec.math.curve.ECPoint;
import iaik.security.ec.math.curve.EllipticCurve;
import iaik.security.ec.math.field.ExtensionFieldElement;
import org.cryptimeleon.ecceleratewrap.pairings.atepairingoverbn.ECCelerateBilinearGroup;
import org.cryptimeleon.math.structures.GroupTests;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GroupTest extends GroupTests {

    public GroupTest(GroupTests.TestParams params) {
        super(params);
    }

    @Parameterized.Parameters(name = "Test: {0}")
    // add (name="Test: {0}") for jUnit 4.12+ to print group's name to test
    public static Collection<TestParams[]> data() {
        // Collect parameters
        BilinearMap bilinearMap = new ECCelerateBilinearGroup(100, BilinearGroup.Type.TYPE_3)
                .getBilinearMap();

        TestParams params[][] = new TestParams[][]{
                {new TestParams(bilinearMap.getG1())},
                {new TestParams(bilinearMap.getG2())},
                {new TestParams(bilinearMap.getGT())}
        };
        return Arrays.asList(params);
    }
}
