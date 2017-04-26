package casaneeds.entity;

/**
 * Created by Sunita on 20-May-2016.
 *
 * { "itemDetails":[{"ItemId":"16","ItemName":"MANGO ( SAFEDA)","ItemQty":"1"},
 * {"ItemId":"17","ItemName":"LOOSE SUGAR - 1KG","ItemQty":"5"},
 * {"ItemId":"18","ItemName":"KACHI GHAANI MUSTARD OIL - 1 KG","ItemQty":"5"},
 * {"ItemId":"19","ItemName":"FORTUNE SOYA OIL 1 ltr","ItemQty":"2"},
 * {"ItemId":"20","ItemName":"CHANNA DAL","ItemQty":"1"},
 * {"ItemId":"21","ItemName":"DAAWAT BASMATI RICE - 1 KG","ItemQty":"2"}]};
 *
 */
public class Items {
   int ordNo;


   int itemNo;
   String itemName ;
   int itemQty ;
   int fqty;
   float Price;

   public float getPrice() {
      return Price;
   }

   public void setPrice(float price) {
      Price = price;
   }

   public Items(int ordNum,int itemNum, String itemname, int itemqty, int itemfqty) {
      this.ordNo = ordNum ;
      this.itemNo = itemNum;
      this.itemName = itemname;
      this.itemQty = itemqty;
      this.fqty = itemfqty;
   }

   public Items() {

   }

   public int getOrdNo() {
      return ordNo;
   }

   public int getFqty() {
      return fqty;
   }

   public void setOrdNo(int ordNo) {
      this.ordNo = ordNo;
   }


   public void setFqty(int fqty) {
      this.fqty = fqty;
   }

   public int getItemNo() {
      return itemNo;
   }

   public void setItemNo(int itemNo) {
      this.itemNo = itemNo;
   }

   public String getItemName() {
      return itemName;
   }

   public void setItemName(String itemName) {
      this.itemName = itemName;
   }

   public int getItemQty() {
      return itemQty;
   }

   public void setItemQty(int itemQty) {
      this.itemQty = itemQty;
   }


}