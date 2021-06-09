package org.cryptimeleon.ecceleratewrap.pairings.atepairingoverbn;

import org.cryptimeleon.math.serialization.Representation;
import org.cryptimeleon.math.structures.groups.GroupElementImpl;
import org.cryptimeleon.math.structures.groups.GroupImpl;

abstract class ECCelerateGroupElementImpl implements GroupElementImpl {
    protected ECCelerateGroupImpl groupImpl;

    public ECCelerateGroupElementImpl(ECCelerateGroupImpl group) {
        this.groupImpl = group;
    }

    public ECCelerateGroupElementImpl(ECCelerateGroupImpl group, Representation repr) {
        this(group);
    }

    @Override
    public GroupImpl getStructure() {
        return groupImpl;
    }
}
