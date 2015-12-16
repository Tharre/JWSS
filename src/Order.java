import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Order {

	private int id;
	private Item item;
	private Player player;
	private boolean isBuy;
	private double limit;
	private int quantity;

	public Order() {
		this(0, new Item(), new Player(), false, 0.0, 0);
	}

	public Order(int id, Item item, Player player, boolean isBuy, double limit, int quantity) {
		this.id = id;
		this.item = item;
		this.player = player;
		this.isBuy = isBuy;
		this.limit = limit;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public Item getItem() {
		return item;
	}

	public int getItemID() {
		return item.getId();
	}

	public boolean getIsBuy() {
		return isBuy;
	}

	public double getLimit() {
		return limit;
	}

	public int getQuantity() {
		return quantity;
	}

	public String toString() {
		return "Order: " + id + ", itemID: " + item.getId() + ", isBuy: " + isBuy + ", limit: " + limit + ", quantity: " + quantity;
	}

	public static Map<Integer, Order> loadOrders(File file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<Integer, Order> m = new HashMap<>();
		
		for (Order order : mapper.readValue(file, Order[].class))
			m.put(order.getId(), order);
		
		return m;
	}

	public static void main(String[] args) throws IOException {
		Map<Integer, Order> m = Order.loadOrders(new File("res/Orders.json"));
		
		System.out.println(m.get(23));
	}
}
