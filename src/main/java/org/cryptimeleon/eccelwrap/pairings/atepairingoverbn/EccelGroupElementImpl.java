package org.cryptimeleon.eccelwrap.pairings.atepairingoverbn;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.GroupImpl;

abstract class EccelGroupElementImpl implements GroupElementImpl {
    protected EccelGroupImpl groupImpl;

    public EccelGroupElementImpl(EccelGroupImpl group) {
        this.groupImpl = group;
    }

    public EccelGroupElementImpl(EccelGroupImpl group, Representation repr) {
        this(group);
    }

    @Override
    public GroupImpl getStructure() {
        return groupImpl;
    }
}
