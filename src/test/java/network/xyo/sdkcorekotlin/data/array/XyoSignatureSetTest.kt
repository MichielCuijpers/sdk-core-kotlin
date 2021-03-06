package network.xyo.sdkcorekotlin.data.array

import network.xyo.sdkcorekotlin.XyoTestBase
import network.xyo.sdkcorekotlin.signing.XyoSignatureSet
import network.xyo.sdkcorekotlin.signing.algorithms.ecc.secp256k.signatures.XyoSecp256kSha256WithEcdsaSignature
import network.xyo.sdkcorekotlin.signing.algorithms.rsa.signatures.XyoRsaWithSha256Signature
import org.junit.Test

class XyoSignatureSetTest : XyoTestBase() {

    @Test
    fun testXyoSignatureSetTest() {
        XyoRsaWithSha256Signature.enable()
        XyoSecp256kSha256WithEcdsaSignature.enable()

        val expected = XyoSignatureSet(
                arrayOf(
                        XyoSecp256kSha256WithEcdsaSignature(byteArrayOf(0x00, 0x01, 0x02))

                )
        )

        val unpacked = XyoSignatureSet.createFromPacked(expected.untyped)
        assertXyoObject(expected, unpacked)
    }
}