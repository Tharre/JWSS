package org.htl_hl.bibiProject.Common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Item {

	private int id;
	private String name;
	private final int maxAmount;

	public Item() {
		this(0, "", 0);
	};

	public Item(int id, String name, int maxAmount) {
		this.id = id;
		this.name = name;
		this.maxAmount = maxAmount;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public String toString() {
		return "Item: " + id + ", name: " + name + ", maxAmount: " + maxAmount;
	}

	public static Map<Integer, Item> loadItems(File file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<Integer, Item> m = new HashMap<>();

		for (Item item : mapper.readValue(file, Item[].class))
			m.put(item.getId(), item);

		return m;
	}

	public static void main(String[] args) throws IOException {
		Map<Integer, Item> m = Item.loadItems(new File("res/Items.json"));

		System.out.println(m.get(23));
	}
}
