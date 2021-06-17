package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.math.structures.groups.lazy.LazyBilinearGroup;

/**
 * A wrapper (with lazy evaluation of operations) around the BN256 and BN464 pairing implementations from the
 * ECCelerate library.
 * <p>
 * BN256 is chosen for security parameters of 100 and below, and
 * BN464 is chosen for security parameters between 100 and 128.
 * These numbers are based on [BD19].
 * <p>
 * Operation evaluation is done lazily via {@link LazyBilinearGroup}.
 * <p>
 * [BD19] Barbulescu, R., Duquesne, S. Updating Key Size Estimations for Pairings. J Cryptol 32, 1298–1336 (2019).
 * https://doi.org/10.1007/s00145-018-9280-5
 */
public class EccelBilinearGroup extends LazyBilinearGroup {
    public EccelBilinearGroup(int securityParameter, BilinearGroup.Type pairingType) {
        super(new EccelBilinearGroupImpl(securityParameter, pairingType));
    }

    public EccelBilinearGroup(Representation repr) {
        super(repr);
    }
}
