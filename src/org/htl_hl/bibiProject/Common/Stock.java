package org.htl_hl.bibiProject.Common;

/**
 * <p>Title: Stock</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse Stock.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */

public class Stock {
    /** item - Private Eigenschaft von Stock vom Typ Item.<br>
     * Jeder Spieler hat f&uuml;r jede Ware ein "Lager" - item stellt diese Ware dar.
     */
    private final Item item;
    /** quantity - Private Eigenschaft von Stock vom Typ long.<br>
     * Jeder Spieler hat eine bestimmte St&uuml;ckzahl von der Ware - quantity stellt diese St&uuml;ckzahl dar.
     */
    private long quantity;

    public Stock() {
        this(new Item(), 0);
    }

    public Stock(Item item, long quantity) {
        this.item = item;
        this.quantity = quantity;
    }
//
    /** Methode zum Abfragen der Ware im "Lager".
     * @return item Ware in diesem "Lager"
     */
    public Item getItem() {
        return item;
    }

    /** Methode zum Abfragen der St&uuml;ckzahl der Ware im "Lager".
     * @return quantity St&uuml;ckzahl der Ware in diesem "Lager"
     */
    public long getQuantity() {
        return quantity;
    }

    /** Methode zum Setzen der St&uuml;ckzahl der Ware im "Lager".
     * Die Methode setzt die gespeicherte St&uuml;ckzahl auf die St&uuml;ckzahl die sie in der Parameterliste bekommt.
     * @param quantity long - Neue St&uuml;ckzahl der Ware
     */
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
