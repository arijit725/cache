package org.arijit.cache.test;

import org.arijit.cache.lru.LRU;
import org.arijit.cache.lru.LRULinkedNode;

/**
 * Test LRU Cache. Here for testing we are invoking insert method seperately.
 * But in actual case when we will try to retrieve some element from LRU cache
 * and if it returns null it means that element is not present in cache. So, we
 * need to update that element in cache. At that time insert method will be
 * invoked.
 * 
 * @author ARIJIT
 *
 */
public class TestLRUCache {

	public static void main(String args[]) {
		LRU lru = LRU.create(5);

		lru.insert(1, "Microsoft");
		lru.insert("test2", "Google");
		lru.insert("test3", "Apple");
		lru.insert("test1", "Microsoft");
		lru.detailedLru();

		lru.insert("test3", "Apple");
		lru.insert("test4", "Oracle");

		lru.detailedLru();
		LRULinkedNode<Object, Object> node = lru.retrieve("test2");
		System.out.println("Retrieved:" + node);
		lru.detailedLru();
		node = lru.retrieve("test4");
		System.out.println("Retrieved:" + node);
		lru.detailedLru();
		node = lru.retrieve("test4");
		System.out.println("Retrieved:" + node);
		lru.detailedLru();
		lru.insert("test5", "Symantec");
		lru.insert("test5", "Qualcomm");
		lru.detailedLru();
	}
}
