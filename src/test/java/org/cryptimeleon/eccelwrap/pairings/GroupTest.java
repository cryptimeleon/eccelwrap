package org.cryptimeleon.eccelwrap.pairings;

import org.cryptimeleon.eccelwrap.pairings.atepairingoverbn.EccelBilinearGroup;
import org.cryptimeleon.math.structures.GroupTests;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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
        BilinearMap bilinearMap = new EccelBilinearGroup(100, BilinearGroup.Type.TYPE_3)
                .getBilinearMap();

        TestParams params[][] = new TestParams[][]{
                {new TestParams(bilinearMap.getG1())},
                {new TestParams(bilinearMap.getG2())},
                {new TestParams(bilinearMap.getGT())}
        };
        return Arrays.asList(params);
    }
}
