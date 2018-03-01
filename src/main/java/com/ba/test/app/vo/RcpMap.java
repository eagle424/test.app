package com.ba.test.app.vo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class RcpMap<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = -959726942810852950L;

	@Override
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		return super.put((K) CamelCase.convert2CamelCase((String) key), value);
	}

	public HashMap<K, V> getHashMap() {
		HashMap<K, V> map = new HashMap<K, V>();
		Iterator<K> keys = super.keySet().iterator();
		K key = null;

		while(keys.hasNext()) {
			key = keys.next();
			map.put(key, this.get(key));
		}

		return map;
	}
}
