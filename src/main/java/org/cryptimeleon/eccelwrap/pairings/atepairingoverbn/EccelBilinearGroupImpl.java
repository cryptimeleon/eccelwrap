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
    // numbers based on [BD19] Barbulescu, R., Duquesne, S. Updating Key Size Estimations for Pairings.
    // !!! If you want to support a group bit size that is not predefined, you will have to rewrite the !!!
    // !!! representation support and group class constructors as they all assume pre-definedness       !!!
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
                pairingType,
                groupBitSize
        );
        this.g1 = new EccelGroup1Impl(pairingType, groupBitSize);
        this.hashIntoG1 = new EccelHashIntoG1Impl(pairingType, groupBitSize);
        this.g2 = new EccelGroup2Impl(pairingType, groupBitSize);
        this.hashIntoG2 = new EccelHashIntoG2Impl(pairingType, groupBitSize);
        this.gT = new EccelTargetGroupImpl(pairingType, groupBitSize);
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
            return new EccelIsomorphism(pairingType, groupBitSize);
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
        // we do not check the pairing field here since it does not properly implement equals()
        return Objects.equals(securityParameter, that.securityParameter) &&
                pairingType == that.pairingType &&
                Objects.equals(groupBitSize, that.groupBitSize) &&
                Objects.equals(g1, that.g1) &&
                Objects.equals(g2, that.g2) &&
                Objects.equals(gT, that.gT) &&
                Objects.equals(hashIntoG1, that.hashIntoG1) &&
                Objects.equals(hashIntoG2, that.hashIntoG2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityParameter, pairingType, groupBitSize, g1, g2, gT, hashIntoG1, hashIntoG2);
    }

    @Override
    public Representation getRepresentation() {
        return ReprUtil.serialize(this);
    }
}
