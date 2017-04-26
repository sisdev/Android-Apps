package casaneeds.entity;

/**
 * Created by admin on 28-05-2016.
 */
public class Order {

    int taskNo ;
    int orderNo ;
    String custName ;
    String custAddress ;
    String custArea ;
    float totalAmount ;
    String status ;
    String pmode;
    String voucher;

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    int payment;

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }



    public Order(int orderno, String name, String address, String area, float amt, String status) {
    this.orderNo = orderno;
    this.custName = name;
    this.custAddress = address;
    this.custArea = area;
    this.totalAmount = amt;
    this.status = status;
    }

public  Order()
{

}


    public String getPmode() {
        return pmode;
    }

    public void setPmode(String pmode) {
        this.pmode = pmode;
    }

    public int getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(int taskNo) {
        this.taskNo = taskNo;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustArea() {
        return custArea;
    }

    public void setCustArea(String custArea) {
        this.custArea = custArea;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
