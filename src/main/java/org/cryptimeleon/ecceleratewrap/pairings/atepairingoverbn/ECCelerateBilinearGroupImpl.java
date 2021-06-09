package org.cryptimeleon.ecceleratewrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.AtePairingOverBarretoNaehrigCurveFactory;
import iaik.security.ec.math.curve.ECPoint;
import iaik.security.ec.math.curve.Pairing;
import iaik.security.ec.math.curve.PairingTypes;
import iaik.security.ec.math.field.ExtensionFieldElement;
import iaik.security.ec.provider.ECCelerate;
import iaik.security.ec.provider.OptimizationLevel;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.annotations.ReprUtil;
import org.cryptimeleon.math.serialization.annotations.Represented;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroupImpl;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMapImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.GroupHomomorphismImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.HashIntoGroupImpl;

import java.util.Objects;

class ECCelerateBilinearGroupImpl implements BilinearGroupImpl {

    @Represented
    private Integer securityParameter;
    // (ordered ascending)
    protected final int[] securityLimits = {100, 128};
    // semantics: to achieve security securityLimits[i], you need a group of bit size minimumGroupBitSize[i]
    // predefined group sizes defined at http://javadoc.iaik.tugraz.at/ECCelerate/current/index.html
    protected final int[] minimumGroupBitSize = {256, 464};
    @Represented
    protected Pairing pairing;
    @Represented
    protected ECCelerateGroup1Impl g1;
    @Represented
    protected ECCelerateGroup2Impl g2;
    @Represented
    protected ECCelerateTargetGroupImpl gT;

    /**
     * Tries to instantiate the bilinear group with the given security parameter and pairing type.
     * Currently only supports security parameters up to 128 and pairing types {@code BilinearGroup.Type.TYPE_2}
     * and {@code BilinearGroup.Type.TYPE_3}.
     *
     * @param securityParameter the desired security parameter
     * @param pairingType the desired pairing type
     *
     * @throws IllegalArgumentException if the desired security parameter and/or pairing type cannot be fulfilled
     */
    public ECCelerateBilinearGroupImpl(int securityParameter, BilinearGroup.Type pairingType) {
        if (securityParameter > securityLimits[securityLimits.length -1]) {
            throw new IllegalArgumentException("Cannot accommodate a security parameter of " + securityParameter
                    + ", please choose one of at most " + securityLimits[securityLimits.length - 1]);
        }
        if (pairingType != BilinearGroup.Type.TYPE_2 && pairingType != BilinearGroup.Type.TYPE_3) {
            throw new IllegalArgumentException("Cannot accomodate pairing type " + BilinearGroup.Type.TYPE_1
                    + ", please choose either " + BilinearGroup.Type.TYPE_2 + " or " + BilinearGroup.Type.TYPE_3);
        }
        this.securityParameter = securityParameter;
        int groupBitSize = 0;
        for (int i = 0; i < securityLimits.length; i++) {
            if (securityParameter <= securityLimits[i]) {
                groupBitSize = minimumGroupBitSize[i];
                break;
            }
        }
        this.pairing = AtePairingOverBarretoNaehrigCurveFactory.getPairing(
                getECCeleratePairingType(pairingType),
                groupBitSize
        );
        this.g1 = new ECCelerateGroup1Impl(pairing.getGroup1());
        ECPoint generatorG2 = pairing.getGroup2().getGenerator().multiplyPoint(
                pairing.getGroup2().getOrder().divide(pairing.getGroup1().getOrder())
        );
        this.g2 = new ECCelerateGroup2Impl(pairing.getGroup2(), generatorG2 , pairing.getGroup1().getOrder());
        ExtensionFieldElement generatorGT = pairing.pair(
                pairing.getGroup1().getGenerator(),
                pairing.getGroup2().getGenerator()
        );
        this.gT = new ECCelerateTargetGroupImpl(pairing.getTargetGroup(), generatorGT, pairing.getGroup1().getOrder());
    }

    /**
     * Maps our pairing types to Eccelerate's pairing types.
     * Does not work for {@code BilinearGroup.Type.TYPE_1} as that type is not offered by Eccelerate.
     */
    private PairingTypes getECCeleratePairingType(BilinearGroup.Type ourPairingType) {
        if (ourPairingType == BilinearGroup.Type.TYPE_2) {
            return PairingTypes.TYPE_2;
        } else if (ourPairingType == BilinearGroup.Type.TYPE_3) {
            return PairingTypes.TYPE_3;
        } else {
            throw new IllegalArgumentException("Pairing type " + ourPairingType + " is not supported by ECCelerate");
        }
    }

    @Override
    public ECCelerateGroup1Impl getG1() {
        return g1;
    }

    @Override
    public ECCelerateGroup2Impl getG2() {
        return g2;
    }

    @Override
    public ECCelerateTargetGroupImpl getGT() {
        return gT;
    }

    @Override
    public BilinearMapImpl getBilinearMap() {
        return new ECCelerateBilinearMapImpl(this);
    }

    @Override
    public GroupHomomorphismImpl getHomomorphismG2toG1() throws UnsupportedOperationException {
        if (pairing.getType() == PairingTypes.TYPE_2) {
            return new ECCelerateIsomorphism(pairing);
        } else {
            throw new UnsupportedOperationException("Type 3 does not support a homomorphism from G2 to G1");
        }
    }

    @Override
    public HashIntoGroupImpl getHashIntoG1() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public HashIntoGroupImpl getHashIntoG2() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public HashIntoGroupImpl getHashIntoGT() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public Integer getSecurityLevel() {
        return securityParameter;
    }

    @Override
    public BilinearGroup.Type getPairingType() {
        if (pairing.getType() == PairingTypes.TYPE_2) {
            return BilinearGroup.Type.TYPE_2;
        }
        return BilinearGroup.Type.TYPE_3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ECCelerateBilinearGroupImpl that = (ECCelerateBilinearGroupImpl) o;
        return Objects.equals(securityParameter, that.securityParameter) &&
                Objects.equals(pairing, that.pairing) &&
                Objects.equals(g1, that.g1) &&
                Objects.equals(g2, that.g2) &&
                Objects.equals(gT, that.gT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityParameter, pairing, g1, g2, gT);
    }

    @Override
    public Representation getRepresentation() {
        return ReprUtil.serialize(this);
    }
}
