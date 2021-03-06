package network.xyo.sdkcorekotlin.signing.algorithms.ecc

import network.xyo.sdkcorekotlin.signing.XyoSigner
import java.security.*

/**
 * A base class for all EC crypto operations.
 */
abstract class XyoGeneralEc : XyoSigner() {
    /**
     * A key generator for creating EC keys.
     */
    protected val keyGenerator : KeyPairGenerator = KeyPairGenerator.getInstance("EC")
}


