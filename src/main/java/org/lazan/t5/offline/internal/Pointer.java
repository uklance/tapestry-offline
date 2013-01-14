package org.lazan.t5.offline.internal;

/**
 * Pointer to a value. Handy for when you want to mutate a final variable in an inner class
 * @author Lance
 *
 * @param <V>
 */
public class Pointer<V> {
	private V value;
	
	public Pointer() {
		
	}
	
	public Pointer(V t) {
		super();
		this.value = t;
	}

	public V get() {
		return value;
	}
	
	public void set(V value) {
		this.value = value;
	}
}
