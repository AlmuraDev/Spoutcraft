/*
 * This file is part of Spout API (http://wiki.getspout.org/).
 * 
 * Spout API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Spout API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spoutcraft.api.event.client;

import org.spoutcraft.api.event.Cancellable;
import org.spoutcraft.api.event.Event;
import org.spoutcraft.api.player.RenderDistance;

public class RenderDistanceChangeEvent extends Event implements Cancellable {
	protected RenderDistance newView;
	protected boolean cancel = false;
	public RenderDistanceChangeEvent(RenderDistance newView) {
		super(Type.RENDER_DISTANCE_CHANGE);
		this.newView = newView;
	}
	
	public RenderDistance getCurrentRenderDistance() {
		return null; //TODO: get method in player
	}
	
	public RenderDistance getNewRenderDistance() {
		return newView;
	}
	
	public boolean isCancelled() {
		return cancel;
	}
	
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

}
