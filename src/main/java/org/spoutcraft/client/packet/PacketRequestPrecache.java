package org.spoutcraft.client.packet;

import java.io.IOException;

import org.spoutcraft.api.io.SpoutInputStream;
import org.spoutcraft.api.io.SpoutOutputStream;

public class PacketRequestPrecache implements SpoutPacket {

	public PacketRequestPrecache() {
	}
	
	@Override
	public void readData(SpoutInputStream input) throws IOException {
		
	}

	@Override
	public void writeData(SpoutOutputStream output) throws IOException {
		
	}

	@Override
	public void run(int playerId) {
		
	}

	@Override
	public void failure(int playerId) {
		
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PacketRequestPrecache;
	}

	@Override
	public int getVersion() {
		return 0;
	}

}
