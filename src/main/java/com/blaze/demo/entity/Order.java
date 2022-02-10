package com.blaze.demo.entity;
import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordernumber")
    private int ordernumber;
    @Column(name = "status")
    private String status = "Pending";
    @Column(name = "date")
    private String date;
    @Column(name = "customer")
    private String customer;
    @Column(name = "taxesamounts")
    private Double taxesamounts = 0.0;
    @Column(name = "totaltaxes")
    private Double totaltaxes = 0.0;
    @Column(name = "listorders")
    private String listorders ;
    @Column(name = "totalamount")
    private Double totalamount = 0.0;


    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


    public Double getTaxesamounts() {
        return taxesamounts;
    }

    public void setTaxesamounts(Double taxesamounts) {
        this.taxesamounts = taxesamounts;
    }

    public Double getTotaltaxes() {
        return totaltaxes;
    }

    public void setTotaltaxes(Double totaltaxes) {
        this.totaltaxes = totaltaxes;
    }

    public Double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Double totalamount) {
        this.totalamount = totalamount;
    }

    public String getListorders() {
        return listorders;
    }

    public void setListorders(String listorders) {
        this.listorders = listorders;
    }
}
