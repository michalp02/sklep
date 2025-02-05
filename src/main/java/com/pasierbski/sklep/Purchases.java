/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pasierbski.sklep;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Michał
 */
@Entity
@Table(name = "purchases")
@NamedQueries({
    @NamedQuery(name = "Purchases.findAll", query = "SELECT p FROM Purchases p"),
    @NamedQuery(name = "Purchases.findByPurchaseId", query = "SELECT p FROM Purchases p WHERE p.purchaseId = :purchaseId"),
    @NamedQuery(name = "Purchases.findByUserId", query = "SELECT p FROM Purchases p WHERE p.userId = :userId"),
    @NamedQuery(name = "Purchases.findByCreateTime", query = "SELECT p FROM Purchases p WHERE p.createTime = :createTime"),
    @NamedQuery(name = "Purchases.findByPaymentTime", query = "SELECT p FROM Purchases p WHERE p.paymentTime = :paymentTime"),
    @NamedQuery(name = "Purchases.findByPaymentMethod", query = "SELECT p FROM Purchases p WHERE p.paymentMethod = :paymentMethod")})
public class Purchases implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "purchase_id")
    private Integer purchaseId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "payment_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentTime;
    @Column(name = "payment_method")
    private Integer paymentMethod;

    public Purchases() {
    }

    public Purchases(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Purchases(Integer purchaseId, int userId, Date createTime) {
        this.purchaseId = purchaseId;
        this.userId = userId;
        this.createTime = createTime;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchaseId != null ? purchaseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchases)) {
            return false;
        }
        Purchases other = (Purchases) object;
        if ((this.purchaseId == null && other.purchaseId != null) || (this.purchaseId != null && !this.purchaseId.equals(other.purchaseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pasierbski.sklep.Purchases[ purchaseId=" + purchaseId + " ]";
    }
    
}
