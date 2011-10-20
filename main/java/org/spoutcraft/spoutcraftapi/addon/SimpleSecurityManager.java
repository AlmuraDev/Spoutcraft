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
package org.spoutcraft.spoutcraftapi.addon;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

class SimpleSecurityManager extends SecurityManager {
	private final double key;
	private boolean locked = false;

	protected SimpleSecurityManager(double key) {
		this.key = key;
	}

	public void lock(double key) {
		if (key == this.key) {
			locked = true;
		} else {
			throw new SecurityException("Incorrect key!");
		}
	}

	public void unlock(double key) {
		if (key == this.key) {
			locked = false;
		} else {
			throw new SecurityException("Incorrect key!");
		}
	}

	private void checkAccess() {
		if (locked) {
			throw new SecurityException("Access is restricted!");
		}
	}

	public void checkAccept(String host, int port) {
		checkAccess();
	}

	@Override
	public void checkAccess(Thread t) {
		checkAccess();
	}

	@Override
	public void checkAccess(ThreadGroup g) {
		checkAccess();
	}

	@Override
	public void checkAwtEventQueueAccess() {
		checkAccess();
	}

	@Override
	public void checkConnect(String host, int port) {
		checkAccess();
	}

	@Override
	public void checkConnect(String host, int port, Object context) {
		checkAccess();
	}

	@Override
	public void checkCreateClassLoader() {
		checkAccess();
	}

	@Override
	public void checkDelete(String file) {
		checkAccess();
	}

	@Override
	public void checkExec(String cmd) {
		checkAccess();
	}

	@Override
	public void checkExit(int status) {
		checkAccess();
	}

	@Override
	public void checkLink(String lib) {
		checkAccess();
	}

	@Override
	public void checkListen(int port) {
		checkAccess();
	}

	@Override
	public void checkMemberAccess(Class<?> clazz, int which) {
		checkAccess();
	}

	@Override
	public void checkMulticast(InetAddress maddr) {
		checkAccess();
	}

	@Override
	public void checkPackageAccess(String pckg) {
		//TODO doesn't the classloader handle this already?
	}

	@Override
	public void checkPackageDefinition(String pckg) {
		//TODO doesn't the classloader handle this already?
	}

	@Override
	public void checkPermission(Permission perm) {
		checkAccess();
	}

	@Override
	public void checkPermission(Permission per, Object context) {
		checkAccess();
	}

	@Override
	public void checkPrintJobAccess() {
		checkAccess();
	}

	@Override
	public void checkPropertiesAccess() {
		checkAccess();
	}

	@Override
	public void checkPropertyAccess(String property) {
		checkAccess();
	}

	@Override
	public void checkRead(String file) {
		checkAccess();
	}

	@Override
	public void checkRead(String file, Object context) {
		checkAccess();
	}

	@Override
	public void checkSecurityAccess(String target) {
		checkAccess();
	}

	@Override
	public void checkSetFactory() {
		checkAccess();
	}

	@Override
	public void checkSystemClipboardAccess() {
		checkAccess();
	}

	@Override
	public boolean checkTopLevelWindow(Object window) {
		return !locked;
	}

	@Override
	public void checkWrite(FileDescriptor fd) {
		checkAccess();
	}

	@Override
	public void checkWrite(String file) {
		checkAccess();
	}
}
