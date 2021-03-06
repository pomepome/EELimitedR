package eelimitedr.net;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;

public class PacketHandler
{
	private final static int MAX_PACKET = 256;
	private static int next_ID;
	private static final SimpleNetworkWrapper HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel("eelimited");
	public static void register()
	{
		HANDLER.registerMessage(PacketKeyInput.class,PacketKeyInput.class,getNextID(),Side.SERVER);
		HANDLER.registerMessage(PacketSpawnParticle.class,PacketSpawnParticle.class,getNextID(),Side.CLIENT);
	}
	public static int getNextID()
	{
		return next_ID++;
	}
	/**
	 * Sends a packet to the server.<br>
	 * Must be called Client side.
	 */
	public static void sendToServer(IMessage msg)
	{
		HANDLER.sendToServer(msg);
	}

	/**
	 * Sends a packet to all the clients.<br>
	 * Must be called Server side.
	 */
	public static void sendToAll(IMessage msg)
	{
		HANDLER.sendToAll(msg);
	}

	/**
	 * Send a packet to all players around a specific point.<br>
	 * Must be called Server side.
	 */
	public static void sendToAllAround(IMessage msg, TargetPoint point)
	{
		HANDLER.sendToAllAround(msg, point);
	}

	/**
	 * Send a packet to a specific player.<br>
	 * Must be called Server side.
	 */
	public static void sendTo(IMessage msg, EntityPlayerMP player)
	{
		HANDLER.sendTo(msg, player);
	}

	/**
	 * Send a packet to all the players in the specified dimension.<br>
	 *  Must be called Server side.
	 */
	public static void sendToDimension(IMessage msg, int dimension)
	{
		HANDLER.sendToDimension(msg, dimension);
	}
	public static Packet getMCPacket(IMessage message)
	{
		return HANDLER.getPacketFrom(message);
	}
}
