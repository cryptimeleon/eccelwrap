# Ecceleratewrap

Eccelwrap provides a wrapper around the type 2 and 3 bilinear groups based on BN-256 and BN-464 curves offered by the [Eccelerate™](https://jce.iaik.tugraz.at/products/core-crypto-toolkits/eccelerate/) library.
It is designed to be used with the Cryptimeleon Math library to allow for use of faster bilinear groups compared to the slow ones implemented in the Cryptimeleon Math library.

To use this library, you need to own a valid license for IAIK JCE and the Eccelerate™ add-on.
For information on the right license for you, please consult their [license page](https://jce.iaik.tugraz.at/sales/#Licences).

## Security Disclaimer
**WARNING: This library is meant to be used for prototyping and as a research tool *only*. It has not been sufficiently vetted for use in security-critical production environments. All implementations are to be considered experimental.**

## Quickstart

To use Eccelwrap, you need to be in posession of a IAIK JCE license as well as the IAIK ECCelerate™ add-on.
When you download them, you will receive a JAR file for each.
To enable Eccelwrap to access them (they are not available in any existing repositories), you will need to install these to your local Maven repository via the following commands:
```
mvn install:install-file -Dfile=<JCE_file_name.jar> \
                         -DgroupId=iaik \
                         -DartifactId=jce_full \
                         -Dversion=5.62.0 \
                         -Dpackaging=jar \
                         -DgeneratePom=true
```
and
```
mvn install:install-file -Dfile=<ECCelerate_file_name.jar> \
                         -DgroupId=iaik \
                         -DartifactId=eccelerate \
                         -Dversion=6.2.0 \
                         -Dpackaging=jar \
                         -DgeneratePom=true
```
Insert the location of your JAR files for the `-Dfile` parameter.
Eccelwrap is tested with JCE version 5.62 and ECCelerate™ version 6.02 and the above commands install them using the version strings that are required for Eccelwrap.
You can still install other versions of the dependencies using the above commands, but the ´-Dversion` arguments need to be exactly as specified above.

## Miscellaneous Information

- Official Documentation can be found [here](https://cryptimeleon.github.io/).
    - The *For Contributors* area includes information on how to contribute.
- Eccelwrap adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
- The changelog can be found [here](CHANGELOG.md).
- IAIK JCE, Eccelerate™ and its API are licensed under the license found [here](https://jce.iaik.tugraz.at/sales/#Licences).
- Eccelwrap is licensed under Apache License 2.0, see [LICENSE file](LICENSE).

## Authors
The library was implemented at Paderborn University in the research group ["Codes und Cryptography"](https://cs.uni-paderborn.de/en/cuk/).

All rights to the Eccelerate™ library and its API belong to their respective owners.