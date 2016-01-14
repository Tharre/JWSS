package org.htl_hl.bibiProject.Common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * <p>Title: Item</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse Item.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */

public class Item {
	/** id - Private Eigenschaft der Klasse Item vom Typ int.<br>
	 * Jedes Item hat eine eindeutige ID.
	 */
	private final int id;
	/** name - Private Eigenschaft der Klasse Item vom Typ String.<br>
	 * Jedes Item hat einen eindeutigen Namen.
	 */
	private final String name;
	/** maxAmount - Private Eigenschaft der Klasse vom Typ int.<br>
	 * Jedes Item hat eine maximal verf&uuml;gbare St&uuml;ckzahl.
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

	/** Methode zum Abrufen der maximal verf&uuml;gbaren St&uuml;ckzahl der Ware.
	 * @return maxAmount Maximal verf&uuml;gbare St&uuml;ckzahl der Ware
     */
	public int getMaxAmount() {
		return maxAmount;
	}

	/** Methode zum Abrufen aller privaten Eigenschaften (id,name,maxAmount), welche als String zur&uuml;ckgegeben werden.
	 * @return String
     */
	public String toString() {
		return "Item: " + id + ", name: " + name + ", maxAmount: " + maxAmount;
	}

	/** Methode zum Auslesen und Laden der Waren aus einer Datei.
	 * @param file File - Datei aus der die Waren geladen werden
	 * @return m Eine Map die die Ware mit der zugeh&ouml;rigen ID verbindet
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
