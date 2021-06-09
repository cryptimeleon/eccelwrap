import iaik.security.ec.math.curve.*;
import iaik.security.ec.provider.ECCelerate;
import iaik.security.ec.provider.OptimizationLevel;

import java.time.Duration;
import java.time.Instant;

public class TimingTest {
    public static void main(String[] args) {
        Pairing pairing = AtePairingOverBarretoNaehrigCurveFactory.getPairing(
                PairingTypes.TYPE_3,
                256
        );
        EllipticCurve g1 = pairing.getGroup1();
        EllipticCurve g2 = pairing.getGroup2();
        ECPoint genG1 = g1.getGenerator();
        ECPoint genG2 = g2.getGenerator();
        ECCelerate.setOptimizationLevel(OptimizationLevel.SPEED);
        Instant start, finish;
        for (int i = 0; i < 10; ++i) {
            start = Instant.now();
            pairing.pair(genG1, genG2);
            finish = Instant.now();
            System.out.println(Duration.between(start, finish).toMillis());
        }
    }
}
