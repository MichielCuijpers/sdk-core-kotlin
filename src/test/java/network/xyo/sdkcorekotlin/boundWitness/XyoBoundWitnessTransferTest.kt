package network.xyo.sdkcorekotlin.boundWitness

import network.xyo.sdkcorekotlin.XyoTestBase
import network.xyo.sdkcorekotlin.data.XyoObject
import network.xyo.sdkcorekotlin.data.XyoPayload
import network.xyo.sdkcorekotlin.data.array.multi.XyoKeySet
import network.xyo.sdkcorekotlin.data.array.multi.XyoMultiTypeArrayInt
import network.xyo.sdkcorekotlin.data.array.single.XyoSingleTypeArrayShort
import network.xyo.sdkcorekotlin.data.heuristics.number.signed.XyoRssi
import network.xyo.sdkcorekotlin.signing.XyoSignatureSet
import network.xyo.sdkcorekotlin.signing.XyoSigningObject
import network.xyo.sdkcorekotlin.signing.algorithms.ecc.secp256k.XyoSha1WithSecp256K
import network.xyo.sdkcorekotlin.signing.algorithms.ecc.secp256k.XyoSha256WithSecp256K
import network.xyo.sdkcorekotlin.signing.algorithms.ecc.secp256k.keys.XyoSecp256K1CompressedPublicKey
import network.xyo.sdkcorekotlin.signing.algorithms.ecc.secp256k.signatures.XyoSha256WithEcdsaSignature
import network.xyo.sdkcorekotlin.signing.algorithms.rsa.XyoRsaPublicKey
import network.xyo.sdkcorekotlin.signing.algorithms.rsa.signatures.XyoRsaWithSha256Singature
import java.math.BigInteger

class XyoBoundWitnessTransferTest : XyoTestBase() {
    private val expectedPayloads = arrayOf<XyoObject>(
            XyoPayload(XyoMultiTypeArrayInt(arrayOf(XyoRssi(-34))), XyoMultiTypeArrayInt(arrayOf(XyoRssi(-34))))
    )

    private val expectedKeys : Array<XyoObject>
            get() {
                XyoSha256WithSecp256K.enable()
                XyoSha1WithSecp256K.enable()
                return arrayOf(
                XyoKeySet(arrayOf(
                            XyoSigningObject.getCreator(0x02)!!.newInstance().value!!.publicKey.value!!,
                            XyoSigningObject.getCreator(0x02)!!.newInstance().value!!.publicKey.value!!
                    )),
                            XyoKeySet(arrayOf(
                            XyoSigningObject.getCreator(0x01)!!.newInstance().value!!.publicKey.value!!,
                            XyoSigningObject.getCreator(0x02)!!.newInstance().value!!.publicKey.value!!
                    ))
                )
            }

    private val expectedSignatures = arrayOf<XyoObject>(
            XyoSignatureSet(arrayOf(
                    XyoSha256WithEcdsaSignature(byteArrayOf(0x00)),
                    XyoRsaWithSha256Singature(byteArrayOf(0x00))
            )),
            XyoSignatureSet(arrayOf(
                    XyoSha256WithEcdsaSignature(byteArrayOf(0x00))
            ))
    )

    @kotlin.test.Test
    fun testTransferPackingAll () {
        XyoRssi.enable()
        XyoKeySet.enable()
        XyoPayload.enable()
        XyoSignatureSet.enable()
        XyoSha256WithEcdsaSignature.enable()
        XyoRsaWithSha256Singature.enable()
        XyoSecp256K1CompressedPublicKey.enable()
        XyoRsaPublicKey.enable()
        XyoSha256WithSecp256K.enable()
        XyoSha1WithSecp256K.enable()

        val expectedTransfer = XyoBoundWitnessTransfer(expectedKeys, expectedPayloads, expectedSignatures)
        val expectedTransferPacked = expectedTransfer.untyped
        val recreated = XyoBoundWitnessTransfer.createFromPacked(expectedTransferPacked.value!!)

        assertXyoObject(expectedTransfer, recreated.value!!)
    }
}