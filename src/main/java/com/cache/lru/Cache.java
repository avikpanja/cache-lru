package com.cache.lru;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Cache {
	private static final long DEFAULT_SIZE_CONSTRAINT = 4;
	private List<Entry> cache;
	private long capacity;
	private long size = 0;
	
	private static class Entry implements Comparable<Entry> {
		String key;
		String value;
		
		public Entry(String key, String value) {
			this.key = key; this.value = value;
		}
		
		public int compareTo(Entry another) {
			System.out.println("compareTo called");
			return this.key.compareTo(another.key);
		}
		
		public boolean equals(Entry another) {
			System.out.println("equals called");
			return this.key.equals(another.key);
		}
		@Override
		public String toString() {
			return key;
		}
	}
		
	public Cache() {
		this.cache = new LinkedList<>();
		this.capacity = DEFAULT_SIZE_CONSTRAINT;
	}
	
	public long size() { return size; }
	
	public Optional<String> getData(String key) {
		final int[] index = new int[] {-1};
		Optional<String> optData = Optional.empty();
		Optional<Entry> optEntry = this.cache
				.stream()
				.filter(entry -> {
					++index[0];
					return entry.key.equals(key);
				})
				.findAny();
		if(optEntry.isPresent()) {
			if(index[0]!=this.size-1) {
				this.cache.remove(optEntry.get());
				this.cache.add(optEntry.get());
			}
			optData = Optional.of(optEntry.get().value);
		} 
		return optData;
	}
	
	public void storeData(String key, String value) {
		final int[] index = new int[] {-1};
		
		Optional<Entry> optEntry = this.cache
				.stream()
				.filter(entry -> {
					++index[0];
					return entry.key.equals(key);
				})
				.findAny();
		if(optEntry.isEmpty()) {
			if(this.size==this.capacity) {
				this.cache.remove(0);
			} else {
				++size;
			}
			this.cache.add(new Entry(key, value));
		}
	}

	@Override
	public String toString() {
		return this.cache.toString();
	}
}
