package com.blaze.demo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "detailorder")
public class DetailOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="iddetail")
    private int iddetail;
    @ManyToOne
    @JoinColumn(name="idordernumber")
    private Order idordernumber;
    @ManyToOne
    @JoinColumn(name="idproduct")
    private Product idproduct;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "costototal")
    private double costototal;
    @Column(name = "unitprice")
    private int unitprice;



    public Order getIdordernumber() {
        return idordernumber;
    }

    public void setIdordernumber(Order idordernumber) {
        this.idordernumber = idordernumber;
    }

    public Product getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(Product idproduct) {
        this.idproduct = idproduct;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCostototal() {
        return costototal;
    }

    public void setCostototal(double costototal) {
        this.costototal = costototal;
    }

    public int getIddetail() {
        return iddetail;
    }

    public void setIddetail(int iddetail) {
        this.iddetail = iddetail;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(int unitprice) {
        this.unitprice = unitprice;
    }
}
