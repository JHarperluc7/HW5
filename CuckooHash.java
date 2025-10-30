/******************************************************************
 *
 *   Julia Harper / Assignment 5- 272 Data Structures
 *
 *   Note, additional comments provided throughout this source code
 *   is for educational purposes
 *
 ********************************************************************/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.Math;
import java.util.Random; 

@SuppressWarnings("unchecked")
public class CuckooHash<K, V> {
  
	private int CAPACITY;  					// Hashmap capacity
	private Bucket<K, V>[] table;			// Hashmap table
	private int a = 37, b = 17;				// Constants used in h2(key)
	private Random rand = new Random();     

	/**
	 * Class Bucket
	 *
	 * Inner bucket class which represents a <key,value> pair 
     * within the hash map.
	 */
	private class Bucket<K, V> {
		private K bucKey = null;
		private V value = null;
		
		public Bucket(K k, V v) {
			bucKey = k; 
			value = v;
		}
		private K getBucKey() { return bucKey; }
		private V getValue()  { return value;  }
	}

	/*
	 * Hash functions
	 */
	private int hash1(K key) { return Math.abs(key.hashCode()) % CAPACITY; }
	private int hash2(K key) { return (a * b + Math.abs(key.hashCode())) % CAPACITY; }

	/**
	 * Constructor initializes the hashmap
	 */
	public CuckooHash(int size) {
		CAPACITY = size;
		table = new Bucket[CAPACITY];
	}						  

	public int size() {
		int count = 0;
		for (int i=0; i<CAPACITY; ++i)
			if (table[i] != null) count++; 	
		return count;
	}

	public void clear() {
		table = new Bucket[CAPACITY]; 
	}

	public int mapSize() { return CAPACITY; }

	public List<V> values() {
		List<V> allValues = new ArrayList<>();
		for (int i=0; i<CAPACITY; ++i)
			if (table[i] != null)
				allValues.add(table[i].getValue());
		return allValues;
	}

	public Set<K> keys() {
		Set<K> allKeys = new HashSet<>();
		for (int i=0; i<CAPACITY; ++i)
			if (table[i] != null)
				allKeys.add(table[i].getBucKey());
		return allKeys;
	}

	/**
	 * Method put
	 * Implements Cuckoo Hashing with displacement and rehash handling.
	 */
 	public void put(K key, V value) { //method start
		int count = 0; //track displacements
		K curKey = key; 
		V curValue = value; 
		int pos = hash1(curKey); //start at h1

		while (count < CAPACITY) { //limit moves
			if (table[pos] == null) { //found empty slot
				table[pos] = new Bucket<>(curKey, curValue); //ADDED place here
				return; 
			}

			//skip if same <key,value> exists
			if (table[pos].getBucKey().equals(curKey) && table[pos].getValue().equals(curValue))
				return; //no duplicate insert

			//kick out existing entry
			Bucket<K, V> temp = table[pos];
			table[pos] = new Bucket<>(curKey, curValue);

			curKey = temp.getBucKey(); //swap
			curValue = temp.getValue(); //swap

			//alternate between hash1 and hash2
			if (pos == hash1(curKey))
				pos = hash2(curKey);
			else
				pos = hash1(curKey);

			count++; //increment displacement count
		}

		//if we reach here, assume cycle — grow and rehash
		rehash(); 
		put(curKey, curValue); //retry insertion
	} 

	public V get(K key) {
		int pos1 = hash1(key);
		int pos2 = hash2(key);
		if (table[pos1] != null && table[pos1].getBucKey().equals(key))
			return table[pos1].getValue();
		else if (table[pos2] != null && table[pos2].getBucKey().equals(key))
			return table[pos2].getValue();
		return null;
	}

	public boolean remove(K key, V value) {
		int pos1 = hash1(key);
		int pos2 = hash2(key);
		if (table[pos1] != null && table[pos1].getValue().equals(value)) {
			table[pos1] = null;
			return true;
		}
		else if (table[pos2] != null && table[pos2].getValue().equals(value)) {
			table[pos2] = null;
			return true;
		}
		return false;
	}

	public String printTable() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int i=0; i<CAPACITY; ++i) {
			if (table[i] != null) {
				sb.append("<");
				sb.append(table[i].getBucKey());
				sb.append(", ");
				sb.append(table[i].getValue());
				sb.append("> ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	private void rehash() {
		Bucket<K, V>[] tableCopy = table.clone();
		int OLD_CAPACITY = CAPACITY;
		CAPACITY = (CAPACITY * 2) + 1;
		table = new Bucket[CAPACITY];

		for (int i=0; i<OLD_CAPACITY; ++i) {
			if (tableCopy[i] != null) {
				put(tableCopy[i].getBucKey(), tableCopy[i].getValue());
			}
		}
	}
}