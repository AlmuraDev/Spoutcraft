/*
 * This file is part of Bukkit (http://bukkit.org/).
 * 
 * Bukkit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bukkit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
package org.spoutcraft.spoutcraftapi.block;

import org.spoutcraft.spoutcraftapi.World;
import org.spoutcraft.spoutcraftapi.material.MaterialData;
/**
 * Represents a captured state of a block, which will not change automatically.
 *
 * Unlike Block, which only one object can exist per coordinate, BlockState can
 * exist multiple times for any given Block. Note that another plugin may change
 * the state of the block and you will not know, or they may change the block to
 * another type entirely, causing your BlockState to become invalid.
 */
public interface BlockState {

	/**
	 * Gets the block represented by this BlockState
	 *
	 * @return Block that this BlockState represents
	 */
	Block getBlock();

	/**
	 * Gets the type of this block
	 *
	 * @return block type
	 */
	MaterialData getType();

	/**
	 * Gets the type-id of this block
	 *
	 * @return block type-id
	 */
	int getTypeId();

	/**
	 * Gets the light level between 0-15
	 *
	 * @return light level
	 */
	byte getLightLevel();

	/**
	 * Gets the world which contains this Block
	 *
	 * @return World containing this block
	 */
	World getWorld();

	/**
	 * Gets the x-coordinate of this block
	 *
	 * @return x-coordinate
	 */
	int getX();

	/**
	 * Gets the y-coordinate of this block
	 *
	 * @return y-coordinate
	 */
	int getY();

	/**
	 * Gets the z-coordinate of this block
	 *
	 * @return z-coordinate
	 */
	int getZ();

	/**
	 * Gets the chunk which contains this block
	 *
	 * @return Containing Chunk
	 */
	Chunk getChunk();

	/**
	 * Sets the type of this block
	 *
	 * @param type Material to change this block to
	 */
	void setType(MaterialData type);

	/**
	 * Sets the type-id of this block
	 *
	 * @param type Type-Id to change this block to
	 */
	boolean setTypeId(int type);

	/**
	 * Attempts to update the block represented by this state, setting it to the
	 * new values as defined by this state. <br />
	 * <br />
	 * This has the same effect as calling update(false). That is to say,
	 * this will not modify the state of a block if it is no longer the same
	 * type as it was when this state was taken. It will return false in this
	 * eventuality.
	 *
	 * @return true if the update was successful, otherwise false
	 * @see BlockState.update(boolean force)
	 */
	boolean update();

	/**
	 * Attempts to update the block represented by this state, setting it to the
	 * new values as defined by this state. <br />
	 * <br />
	 * Unless force is true, this will not modify the state of a block if it is
	 * no longer the same type as it was when this state was taken. It will return
	 * false in this eventuality.<br />
	 * <br />
	 * If force is true, it will set the type of the block to match the new state,
	 * set the state data and then return true.
	 *
	 * @param force true to forcefully set the state
	 * @return true if the update was successful, otherwise false
	 */
	boolean update(boolean force);

	public byte getRawData();
}
