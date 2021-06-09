package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import iaik.security.ec.math.curve.AtePairingOverBarretoNaehrigCurveFactory;
import iaik.security.ec.math.curve.ECPoint;
import iaik.security.ec.math.curve.Pairing;
import iaik.security.ec.math.curve.PairingTypes;
import iaik.security.ec.math.field.ExtensionFieldElement;
import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.serialization.annotations.ReprUtil;
import org.cryptimeleon.math.serialization.annotations.Represented;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroup;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearGroupImpl;
import org.cryptimeleon.math.structures.groups.elliptic.BilinearMapImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.GroupHomomorphismImpl;
import org.cryptimeleon.math.structures.groups.mappings.impl.HashIntoGroupImpl;

import java.util.Arrays;
import java.util.Objects;

class EccelBilinearGroupImpl implements BilinearGroupImpl {

    /**
     * The security level guaranteed by this bilinear group.
     */
    @Represented
    protected Integer securityParameter;
    @Represented
    protected PairingTypes pairingType;
    @Represented
    protected Integer groupBitSize;
    // (ordered ascending)
    protected final int[] securityLimits = {100, 128};
    // semantics: to achieve security securityLimits[i], you need a group of bit size minimumGroupBitSize[i]
    // predefined group sizes defined at http://javadoc.iaik.tugraz.at/ECCelerate/current/index.html
    protected final int[] minimumGroupBitSize = {256, 464};

    protected Pairing pairing;

    protected EccelGroup1Impl g1;
    protected EccelGroup2Impl g2;
    protected EccelTargetGroupImpl gT;

    protected HashIntoGroupImpl hashIntoG1, hashIntoG2;

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
    public EccelBilinearGroupImpl(int securityParameter, BilinearGroup.Type pairingType) {
        if (securityParameter > securityLimits[securityLimits.length -1]) {
            throw new IllegalArgumentException("Cannot accommodate a security parameter of " + securityParameter
                    + ", please choose one of at most " + securityLimits[securityLimits.length - 1]);
        }
        if (pairingType != BilinearGroup.Type.TYPE_2 && pairingType != BilinearGroup.Type.TYPE_3) {
            throw new IllegalArgumentException("Cannot accomodate pairing type " + BilinearGroup.Type.TYPE_1
                    + ", please choose either " + BilinearGroup.Type.TYPE_2 + " or " + BilinearGroup.Type.TYPE_3);
        }
        this.securityParameter = securityParameter;
        groupBitSize = 0;
        for (int i = 0; i < securityLimits.length; i++) {
            if (securityParameter <= securityLimits[i]) {
                groupBitSize = minimumGroupBitSize[i];
                break;
            }
        }
        this.pairingType = getECCeleratePairingType(pairingType);
        init(this.pairingType, groupBitSize);
    }

    public EccelBilinearGroupImpl(Representation repr) {
        new ReprUtil(this).deserialize(repr);
        // This only works if the pairing is always the same based on these two parameters
        // So the groupBitSize must correspond to a pre-defined parametrization
        init(pairingType, groupBitSize);
    }

    protected void init(PairingTypes pairingType, int groupBitSize) {
        this.pairing = AtePairingOverBarretoNaehrigCurveFactory.getPairing(
                this.pairingType,
                groupBitSize
        );
        this.g1 = new EccelGroup1Impl(pairing.getGroup1());
        ECPoint generatorG2 = pairing.getGroup2().getGenerator().multiplyPoint(
                pairing.getGroup2().getOrder().divide(pairing.getGroup1().getOrder())
        );
        this.hashIntoG1 = new EccelHashIntoG1Impl(g1);
        this.g2 = new EccelGroup2Impl(pairing.getGroup2(), generatorG2 , pairing.getGroup1().getOrder());
        ExtensionFieldElement generatorGT = pairing.pair(
                pairing.getGroup1().getGenerator(),
                pairing.getGroup2().getGenerator()
        );
        this.hashIntoG2 = new EccelHashIntoG2Impl(g2);
        this.gT = new EccelTargetGroupImpl(pairing.getTargetGroup(), generatorGT, pairing.getGroup1().getOrder());
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
    public EccelGroup1Impl getG1() {
        return g1;
    }

    @Override
    public EccelGroup2Impl getG2() {
        return g2;
    }

    @Override
    public EccelTargetGroupImpl getGT() {
        return gT;
    }

    @Override
    public BilinearMapImpl getBilinearMap() {
        return new EccelBilinearMapImpl(this);
    }

    @Override
    public GroupHomomorphismImpl getHomomorphismG2toG1() throws UnsupportedOperationException {
        if (pairing.getType() == PairingTypes.TYPE_2) {
            return new EccelIsomorphism(pairing);
        } else {
            throw new UnsupportedOperationException("Type 3 does not support a homomorphism from G2 to G1");
        }
    }

    @Override
    public HashIntoGroupImpl getHashIntoG1() throws UnsupportedOperationException {
        return hashIntoG1;
    }

    @Override
    public HashIntoGroupImpl getHashIntoG2() throws UnsupportedOperationException {
        return hashIntoG2;
    }

    @Override
    public HashIntoGroupImpl getHashIntoGT() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Bilinear group does not support hashing to GT");
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
        EccelBilinearGroupImpl that = (EccelBilinearGroupImpl) o;
        return Objects.equals(securityParameter, that.securityParameter) &&
                pairingType == that.pairingType &&
                Objects.equals(groupBitSize, that.groupBitSize) &&
                Arrays.equals(securityLimits, that.securityLimits) &&
                Arrays.equals(minimumGroupBitSize, that.minimumGroupBitSize) &&
                Objects.equals(pairing, that.pairing) &&
                Objects.equals(g1, that.g1) &&
                Objects.equals(g2, that.g2) &&
                Objects.equals(gT, that.gT) &&
                Objects.equals(hashIntoG1, that.hashIntoG1);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(securityParameter, pairingType, groupBitSize, pairing, g1, g2, gT, hashIntoG1);
        result = 31 * result + Arrays.hashCode(securityLimits);
        result = 31 * result + Arrays.hashCode(minimumGroupBitSize);
        return result;
    }

    @Override
    public Representation getRepresentation() {
        return ReprUtil.serialize(this);
    }
}
