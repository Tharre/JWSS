import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Order {
	private int runde;
	private char kv;
	private String spieler;
	private double limit;
	private String ware;
	private int stk;

	public Order(int runde, char kv, String spieler, double limit, String ware, int stk) {
		this.runde = runde;
		this.kv = kv;
		this.spieler = spieler;
		this.limit = limit;
		this.ware = ware;
		this.stk = stk;
	}

	public String toString() {
		return runde + "." + kv + "." + spieler + "." + limit + "." + ware + "." + stk;
	}

	public void save() {
		try {
			FileWriter a = new FileWriter("filename.txt", true);
			BufferedWriter w = new BufferedWriter(a);
			w.newLine();
			w.write(this.toString());
			w.close();
			a.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Order[] load() {
		try {
			FileReader a = new FileReader("filename.txt");
			BufferedReader r = new BufferedReader(a);
			int l = 0;
			BufferedReader t = new BufferedReader(a);
			String f;
			while (t.readLine() != null) {
				l++;
				f = t.readLine();
			}
			Order[] orders = new Order[l];
			for (int i = 0; i < l; i++) {
				String b = r.readLine();
				System.out.println(b);
				String[] c = b.split(".");
				orders[i] = new Order(Integer.parseInt(c[0]), c[1].charAt(0), c[2], Double.parseDouble(c[3]), ware,
						Integer.parseInt(c[4]));
			}
			r.close();
			t.close();
			return orders;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
