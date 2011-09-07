package org.spoutcraft.spoutcraftapi.animation;

import org.spoutcraft.spoutcraftapi.property.PropertyInterface;
import org.spoutcraft.spoutcraftapi.property.Property;

public class PropertyDelegate implements ValueSetDelegate {
	private Property delegate;
	
	public PropertyDelegate(PropertyInterface object, String property){
		delegate = object.getPropertyDelegate(property);
	}
	
	public void set(Animatable value) {
		delegate.set(value);
	}
	
	public void set(Number value) {
		delegate.set(value);
	}
}
