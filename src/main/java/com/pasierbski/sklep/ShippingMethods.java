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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author Michał
 */
@Entity
@Table(name = "shipping_methods")
@NamedQueries({
    @NamedQuery(name = "ShippingMethods.findAll", query = "SELECT s FROM ShippingMethods s"),
    @NamedQuery(name = "ShippingMethods.findBySmId", query = "SELECT s FROM ShippingMethods s WHERE s.smId = :smId"),
    @NamedQuery(name = "ShippingMethods.findBySmName", query = "SELECT s FROM ShippingMethods s WHERE s.smName = :smName"),
    @NamedQuery(name = "ShippingMethods.findBySmPrice", query = "SELECT s FROM ShippingMethods s WHERE s.smPrice = :smPrice")})
public class ShippingMethods implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sm_id")
    private Integer smId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "sm_name")
    private String smName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sm_price")
    private float smPrice;

    public ShippingMethods() {
    }

    public ShippingMethods(Integer smId) {
        this.smId = smId;
    }

    public ShippingMethods(Integer smId, String smName, float smPrice) {
        this.smId = smId;
        this.smName = smName;
        this.smPrice = smPrice;
    }

    public Integer getSmId() {
        return smId;
    }

    public void setSmId(Integer smId) {
        this.smId = smId;
    }

    public String getSmName() {
        return smName;
    }

    public void setSmName(String smName) {
        this.smName = smName;
    }

    public float getSmPrice() {
        return smPrice;
    }

    public void setSmPrice(float smPrice) {
        this.smPrice = smPrice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (smId != null ? smId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShippingMethods)) {
            return false;
        }
        ShippingMethods other = (ShippingMethods) object;
        if ((this.smId == null && other.smId != null) || (this.smId != null && !this.smId.equals(other.smId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pasierbski.sklep.ShippingMethods[ smId=" + smId + " ]";
    }
    
}
