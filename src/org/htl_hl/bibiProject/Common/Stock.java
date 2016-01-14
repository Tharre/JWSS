import org.htl_hl.bibiProject.Common.Item;
package org.htl_hl.bibiProject.Common;

/** Objekt vom Typ Stock.
 * In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften von Stock.
 */

public class Stock {
    /** item - Private Eigenschaft von Stock.
     * Jeder Spieler hat für jede Ware ein "Lager" - item stellt diese Ware dar.
     */
    private final Item item;
    /** quantity - Private Eigenschaft von Stock.
     * Jeder Spieler hat eine bestimmte Stückzahl von der Ware - quantity stellt diese Stückzahl dar.
     */
    private long quantity;

    public Stock() {
        this(new Item(), 0);
    }

    public Stock(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    /** Methode zum Abfragen der Ware im "Lager".
     * @return item Ware in diesem "Lager"
     */
    public Item getItem() {
        return item;
    }

    /** Methode zum Abfragen der Stückzahl der Ware im "Lager".
     * @return quantity Stückzahl der Ware in diesem "Lager"
     */
    public long getQuantity() {
        return quantity;
    }

    /** Methode zum Setzen der Stückzahl der Ware im "Lager".
     * Die Methode setzt die gespeicherte Stückzahl auf die Stückzahl die sie in der Parameterliste bekommt.
     * @param quantity long Neue Stückzahl der Ware
     */
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}

