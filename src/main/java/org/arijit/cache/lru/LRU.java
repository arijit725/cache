package org.arijit.cache.lru;

import java.util.HashMap;
import java.util.Map;

public class LRU {

	private Map<Object, LRULinkedNode<Object, Object>> lruMap;
	/* latest node should appear in the begining. */
	private LRULinkedNode<Object, Object> start;
	private LRULinkedNode<Object, Object> end;

	/* maximum number of entries present in cache */
	private int cacheLimit;

	private LRU(int cacheLimit) {
		this.cacheLimit = cacheLimit;
		lruMap = new HashMap<Object, LRULinkedNode<Object, Object>>(this.cacheLimit);
	}

	public static LRU create(int cacheLimit) {
		LRU lru = new LRU(cacheLimit);
		return lru;
	}

	public void insert(Object key, Object value) {
		/*
		 * there could be chance that multiple threads are accessing this part. If we do
		 * not synchronize there could be chance that we end up creating multiple
		 * LinkedNode for same object
		 */
		synchronized (this) {
			LRULinkedNode<Object, Object> node = null;
			evict();
			if (!lruMap.containsKey(key)) {
				node = new LRULinkedNode<Object, Object>(key, value);
				lruMap.put(key, node);
				addNode(node);
			} else {
				node = lruMap.get(key);
				deleteNode(node);
				addNode(node);
			}
		}
	}

	private void addNode(LRULinkedNode<Object, Object> node) {
		if (start == null) {
			start = node;
			end = node;
		} else {
			end.setNextNode(node);
			node.setPrevNode(end);
			end = node;
		}
	}

	private void deleteNode(LRULinkedNode<Object, Object> node) {
		if (node.getPrevNode() != null) {
			node.getPrevNode().setNextNode(node.getNextNode());
		}
		else {
			// only start node has prev = null. so move start to next node before delete it.
			start = node.getNextNode();
		}
		if (node.getNextNode() != null) {
			node.getNextNode().setPrevNode(node.getPrevNode());
		} else {
			// only end node can have nextNode = null. setting end to prev node before
			// delete it
			end = node.getPrevNode();
		}
		// now detaching the node from prev and next
		node.setPrevNode(null);
		node.setNextNode(null);
	}

	/**
	 * Evicting the least recently used node. We are inserting most recently node in
	 * start. So end will always containg least recently used node. now we are
	 * removing the end node whence cacheLimit is reached.
	 */
	private void evict() {
		if (lruMap.size() < this.cacheLimit)
			return;

		/*
		 * removing the end node as it is the last node so far accessed
		 */
		LRULinkedNode<Object, Object> tmpStart = start;
		LRULinkedNode<Object, Object> tmp = start.getNextNode();
		start.setNextNode(null);
		start.setPrevNode(null);
		start = tmp;
		System.out.println("Evicted Old node: " + tmpStart);

	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public synchronized LRULinkedNode<Object, Object> retrieve(Object key) {
		LRULinkedNode<Object, Object> node = lruMap.get(key);
		deleteNode(node);
		addNode(node);
		return node;
	}

	public void detailedLru() {
		System.out.println("LRU:: first accessed node comes first: ");
		LRULinkedNode<Object, Object> tmpStart = start;
		while (tmpStart != null) {
			System.out.println(tmpStart);
			tmpStart = tmpStart.getNextNode();
		}

	}
}
