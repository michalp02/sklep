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
@Table(name = "payment_methods")
@NamedQueries({
    @NamedQuery(name = "PaymentMethods.findAll", query = "SELECT p FROM PaymentMethods p"),
    @NamedQuery(name = "PaymentMethods.findByPmId", query = "SELECT p FROM PaymentMethods p WHERE p.pmId = :pmId"),
    @NamedQuery(name = "PaymentMethods.findByPmName", query = "SELECT p FROM PaymentMethods p WHERE p.pmName = :pmName"),
    @NamedQuery(name = "PaymentMethods.findByPmFee", query = "SELECT p FROM PaymentMethods p WHERE p.pmFee = :pmFee")})
public class PaymentMethods implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pm_id")
    private Integer pmId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pm_name")
    private String pmName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pm_fee")
    private float pmFee;

    public PaymentMethods() {
    }

    public PaymentMethods(Integer pmId) {
        this.pmId = pmId;
    }

    public PaymentMethods(Integer pmId, String pmName, float pmFee) {
        this.pmId = pmId;
        this.pmName = pmName;
        this.pmFee = pmFee;
    }

    public Integer getPmId() {
        return pmId;
    }

    public void setPmId(Integer pmId) {
        this.pmId = pmId;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public float getPmFee() {
        return pmFee;
    }

    public void setPmFee(float pmFee) {
        this.pmFee = pmFee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmId != null ? pmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentMethods)) {
            return false;
        }
        PaymentMethods other = (PaymentMethods) object;
        if ((this.pmId == null && other.pmId != null) || (this.pmId != null && !this.pmId.equals(other.pmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pasierbski.sklep.PaymentMethods[ pmId=" + pmId + " ]";
    }
    
}
