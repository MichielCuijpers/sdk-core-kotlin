package network.xyo.sdkcorekotlin.data.heuristics.number.unsigned

import network.xyo.sdkcorekotlin.data.XyoObject
import network.xyo.sdkcorekotlin.data.XyoObjectProvider
import network.xyo.sdkcorekotlin.data.XyoUnsignedHelper
import network.xyo.sdkcorekotlin.data.heuristics.number.XyoNumberTypes

/**
 * The Xyo Index heuristic.
 *
 * @major 0x02
 * @minor 0x05
 *
 * @param number The index.
 */
class XyoIndex(override val number: Int) : XyoNumberUnsigned() {
    override val size: XyoNumberTypes = XyoNumberTypes.INT
    override val id: ByteArray = byteArrayOf(major, minor)
    override val sizeIdentifierSize: Int? = null

    companion object : XyoObjectProvider() {
        override val major: Byte = 0x02
        override val minor: Byte = 0x05
        override val sizeOfBytesToGetSize: Int? = 0

        override fun readSize(byteArray: ByteArray): Int {
            return 4
        }

        override fun createFromPacked(byteArray: ByteArray): XyoObject {
            return XyoIndex(XyoUnsignedHelper.readUnsignedInt(byteArray))
        }
    }
}