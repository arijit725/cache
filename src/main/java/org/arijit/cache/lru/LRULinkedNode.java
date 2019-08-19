package org.arijit.cache.lru;

public class LRULinkedNode<K extends Object, V extends Object> {

	private K key;
	private V value;
	private LRULinkedNode<K,V> prevNode;
	private LRULinkedNode<K,V> nextNode;
	
	public LRULinkedNode(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public void setNextNode(LRULinkedNode<K,V> nextNode) {
		this.nextNode = nextNode;
	}
	public LRULinkedNode<K,V> getNextNode() {
		return nextNode;
	}
	public void setPrevNode(LRULinkedNode<K,V> prevNode) {
		this.prevNode = prevNode;
	}
	public LRULinkedNode<K,V> getPrevNode() {
		return prevNode;
	}
	public K getKey() {
		return key;
	}
	public V getValue() {
		return value;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LRULinkedNode<?, ?> other = (LRULinkedNode<?, ?>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "LRULinkedNode [key=" + key + ", value=" + value +"]";
	}
	
	
}
