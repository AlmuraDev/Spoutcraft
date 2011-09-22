/*
 * This file is part of Spoutcraft API (http://wiki.getspout.org/).
 * 
 * Spoutcraft API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Spoutcraft API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spoutcraft.spoutcraftapi;

import org.spoutcraft.spoutcraftapi.block.Block;
import org.spoutcraft.spoutcraftapi.block.Chunk;
import org.spoutcraft.spoutcraftapi.util.FixedLocation;

public interface World {

	public Block getBlockAt(int x, int y, int z);

	@NotImplemented
	public Block getHighestBlockAt(int x, int z);

	@NotImplemented
	public int getHighestBlockYAt(int x, int z);

	@NotImplemented
	public void setTime(long time);

	@NotImplemented
	public long getTime();

	@NotImplemented
	public void setFullTime(long time);

	@NotImplemented
	public long getFullTime();

	@NotImplemented
	public void save();

	@NotImplemented
	public int getMaxHeight();

	@NotImplemented
	public boolean getAllowAnimals();

	@NotImplemented
	public boolean getAllowMonsters();

	public Chunk getChunkAt(Block block);

	public Chunk getChunkAt(FixedLocation location);

	public Chunk getChunkAt(int x, int z);

	@NotImplemented
	public Chunk[] getLoadedChunks();

	@NotImplemented
	public long getSeed();

	@NotImplemented
	public boolean isChunkLoaded(Chunk chunk);

	@NotImplemented
	public boolean isChunkLoaded(int x, int z);

	@NotImplemented
	public void loadChunk(Chunk chunk);

	@NotImplemented
	public void loadChunk(int x, int z);

	@NotImplemented
	public boolean loadChunk(int x, int z, boolean generate);

	@NotImplemented
	public boolean refreshChunk(int x, int z);

	@NotImplemented
	public boolean regenerateChunk(int x, int z);

}
