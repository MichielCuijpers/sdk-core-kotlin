package network.xyo.sdkcorekotlin.node

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.selects.selectUnbiased
import network.xyo.sdkcorekotlin.boundWitness.XyoBoundWitness
import network.xyo.sdkcorekotlin.data.XyoObject
import network.xyo.sdkcorekotlin.data.array.multi.XyoBridgeHashSet
import network.xyo.sdkcorekotlin.data.array.single.XyoBridgeBlockSet
import network.xyo.sdkcorekotlin.data.array.single.XyoSingleTypeArrayInt
import network.xyo.sdkcorekotlin.hashing.XyoHash
import network.xyo.sdkcorekotlin.network.XyoProcedureCatalogue
import network.xyo.sdkcorekotlin.storage.XyoStorageProviderInterface
import java.lang.ref.WeakReference

class XyoBridgingOption (private val originBlocks: XyoStorageProviderInterface): XyoBoundWitnessOption() {
    private var hashOfOriginBlocks : XyoBridgeHashSet? = null
    private var originBlocksToSend : WeakReference<XyoObject?> = WeakReference(null)

    override val flag: Int = XyoProcedureCatalogue.GIVE_ORIGIN_CHAIN

    override suspend fun getSignedPayload(): XyoObject? {
        return hashOfOriginBlocks
    }

    override suspend fun getUnsignedPayload(): XyoObject? {
        val originBlocks = originBlocksToSend.get()
        if (originBlocks != null) {
            return originBlocks
        } else if (hashOfOriginBlocks != null) {
            updateOriginChain(hashOfOriginBlocks!!).await()
        }
        return originBlocksToSend.get()
    }

    fun addHashSet (bridgeHashSet : XyoBridgeHashSet) {
        updateOriginChain(bridgeHashSet)
    }


    private fun updateOriginChain(bridgeHashSet : XyoBridgeHashSet) = async {
        hashOfOriginBlocks = bridgeHashSet
        val blocks = ArrayList<XyoObject>()
        if (hashOfOriginBlocks != null) {
            for (hash in bridgeHashSet.array) {
                val blockEncoded = originBlocks.read(hash.typed).await()
                if (blockEncoded != null) {
                    try {
                        blocks.add(XyoBoundWitness.createFromPacked(blockEncoded))
                    } catch (exception : Exception) {
                        originBlocks.delete(hash.typed).await()
                    }
                }
            }
        }
        originBlocksToSend = WeakReference(XyoBridgeBlockSet(blocks.toTypedArray()))
    }
}