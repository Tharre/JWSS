package org.htl_hl.bibiProject.Common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/** Objekt vom Typ Item.
 * In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse Item.
 */
public class Item {
	/** id - Private Eigenschaft der Klasse Item.
	 * Jedes Item hat eine eindeutige ID.
	 */
	private final int id;
	/** name - Private Eigenschaft der Klasse Item.
	 * Jedes Item hat einen eindeutigen Namen.
	 */
	private final String name;
	/** maxAmount - Private Eigenschaft der Klasse.
	 * Jedes Item hat eine maximal verfügbare Stückzahl.
	 */
	private final int maxAmount;

	public Item() {
		this(0, "", 0);
	};

	public Item(int id, String name, int maxAmount) {
		this.id = id;
		this.name = name;
		this.maxAmount = maxAmount;
	}

	/** Methode zum Abrufen der ID der Ware.
	 * @return id Eindeutige ID der Ware
     */
	public int getId() {
		return id;
	}

	/** Methode zum Abrufen vom Namen der Ware.
	 * @return name Name der Ware
     */
	public String getName() {
		return name;
	}

	/** Methode zum Abrufen der maximal verfügbaren Stückzahl der Ware.
	 * @return maxAmount Maximal verfügbare Stückzahl der Ware
     */
	public int getMaxAmount() {
		return maxAmount;
	}

	/** Methode zum Abrufen aller privaten Eigenschaften (id,name,maxAmount), welche als String zurückgegeben werden.
	 * @return String
     */
	public String toString() {
		return "Item: " + id + ", name: " + name + ", maxAmount: " + maxAmount;
	}

	/** Methode zum Auslesen und Laden der Waren aus einer Datei.
	 * @param file File Datei aus der die Waren geladen werden
	 * @return m Eine Map die die Ware mit der zugehörigen ID verbindet
	 * @throws IOException
     */
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
