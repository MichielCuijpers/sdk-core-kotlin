package network.xyo.sdkcorekotlin.network

/**
 * A network provider that allows components to talk to other parties.
 */
interface XyoNetworkProviderInterface {
    /**
     * Fins a peer to open a pipe to according to a procedureCatalogue.
     *
     * @param procedureCatalogue the catalogue to comply to when finding peers.
     * @return A XyoNetwork pipe to talk to the peer.
     */
    suspend fun find (procedureCatalogue: XyoNetworkProcedureCatalogueInterface) : XyoNetworkPipe
}