/*
 * Copyright 2009 Denys Pavlov, Igor Azarnyi
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.yes.cart.domain.dto.impl;





import com.inspiresoftware.lib.dto.geda.annotations.Dto;
import com.inspiresoftware.lib.dto.geda.annotations.DtoField;
import org.yes.cart.constants.Constants;
import org.yes.cart.domain.dto.CustomerOrderDeliveryDetailDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Customer order detail DTO interface implementation.
 * <p/>
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 8/12/12
 * Time: 7:21 AM
 */
@Dto
public class CustomerOrderDeliveryDetailDTOImpl implements CustomerOrderDeliveryDetailDTO {

    private static final long serialVersionUID = 20120812L;

    @DtoField(value = "customerOrderDeliveryDetId", readOnly = true)
    private long customerOrderDeliveryDetId;

    @DtoField(value = "productSkuCode", readOnly = true)
    private String skuCode;

    @DtoField(value = "productName", readOnly = true)
    private String skuName;


    @DtoField(value = "qty", readOnly = true)
    private BigDecimal qty;

    @DtoField(value = "price", readOnly = true)
    private BigDecimal price;

    @DtoField(value = "listPrice", readOnly = true)
    private BigDecimal listPrice;
    @DtoField(value = "salePrice", readOnly = true)
    private BigDecimal salePrice;
    @DtoField(value = "gift", readOnly = true)
    private boolean gift;
    @DtoField(value = "promoApplied", readOnly = true)
    private boolean promoApplied;
    @DtoField(value = "appliedPromo", readOnly = true)
    private String appliedPromo;

    @DtoField(value = "netPrice", readOnly = true)
    private BigDecimal netPrice;
    @DtoField(value = "grossPrice", readOnly = true)
    private BigDecimal grossPrice;
    @DtoField(value = "taxRate", readOnly = true)
    private BigDecimal taxRate;
    @DtoField(value = "taxCode", readOnly = true)
    private String taxCode;
    @DtoField(value = "taxExclusiveOfPrice", readOnly = true)
    private boolean taxExclusiveOfPrice;

    private BigDecimal lineTotal;

    private BigDecimal lineTax;


    @DtoField(value = "delivery.deliveryNum", readOnly = true)
    private String deliveryNum;

    @DtoField(value = "delivery.deliveryStatus", readOnly = true)
    private String deliveryStatusLabel;


    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(final BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public BigDecimal getLineTax() {
        return lineTax;
    }

    public void setLineTax(final BigDecimal lineTax) {
        this.lineTax = lineTax;
    }

    private void calculateLineTotal() {

        if (qty != null && netPrice != null && grossPrice != null) {
            lineTotal = qty.multiply(grossPrice).setScale(Constants.DEFAULT_SCALE, RoundingMode.HALF_UP);
            lineTax = qty.multiply(grossPrice.subtract(netPrice)).setScale(Constants.DEFAULT_SCALE, RoundingMode.HALF_UP);
        }

    }

    /** {@inheritDoc} */
    public long getId() {
        return customerOrderDeliveryDetId;
    }

    /** {@inheritDoc} */
    public long getCustomerOrderDeliveryDetId() {
        return customerOrderDeliveryDetId;
    }

    /** {@inheritDoc} */
    public void setCustomerOrderDeliveryDetId(final long customerOrderDeliveryDetId) {
        this.customerOrderDeliveryDetId = customerOrderDeliveryDetId;
    }

    /** {@inheritDoc} */
    public String getSkuCode() {
        return skuCode;
    }

    /** {@inheritDoc} */
    public void setSkuCode(final String skuCode) {
        this.skuCode = skuCode;
    }

    /** {@inheritDoc} */
    public String getSkuName() {
        return skuName;
    }

    /** {@inheritDoc} */
    public void setSkuName(final String skuName) {
        this.skuName = skuName;
    }

    /** {@inheritDoc} */
    public BigDecimal getQty() {
        return qty;
    }

    /** {@inheritDoc} */
    public void setQty(final BigDecimal qty) {
        this.qty = qty;
        calculateLineTotal();
    }

    /** {@inheritDoc} */
    public BigDecimal getPrice() {
        return price;
    }

    /** {@inheritDoc} */
    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    /** {@inheritDoc} */
    public BigDecimal getNetPrice() {
        return netPrice;
    }

    /** {@inheritDoc} */
    public void setNetPrice(final BigDecimal netPrice) {
        this.netPrice = netPrice;
        calculateLineTotal();
    }

    /** {@inheritDoc} */
    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    /** {@inheritDoc} */
    public void setGrossPrice(final BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
        calculateLineTotal();
    }

    /** {@inheritDoc} */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /** {@inheritDoc} */
    public void setTaxRate(final BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /** {@inheritDoc} */
    public String getTaxCode() {
        return taxCode;
    }

    /** {@inheritDoc} */
    public void setTaxCode(final String taxCode) {
        this.taxCode = taxCode;
    }

    /** {@inheritDoc} */
    public boolean isTaxExclusiveOfPrice() {
        return taxExclusiveOfPrice;
    }

    /** {@inheritDoc} */
    public void setTaxExclusiveOfPrice(final boolean taxExclusiveOfPrice) {
        this.taxExclusiveOfPrice = taxExclusiveOfPrice;
    }

    /** {@inheritDoc} */
    public BigDecimal getListPrice() {
        return listPrice;
    }

    /** {@inheritDoc} */
    public void setListPrice(final BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    /** {@inheritDoc} */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /** {@inheritDoc} */
    public void setSalePrice(final BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /** {@inheritDoc} */
    public boolean isGift() {
        return gift;
    }

    /** {@inheritDoc} */
    public void setGift(final boolean gift) {
        this.gift = gift;
    }

    /** {@inheritDoc} */
    public boolean isPromoApplied() {
        return promoApplied;
    }

    /** {@inheritDoc} */
    public void setPromoApplied(final boolean promoApplied) {
        this.promoApplied = promoApplied;
    }

    /** {@inheritDoc} */
    public String getAppliedPromo() {
        return appliedPromo;
    }

    /** {@inheritDoc} */
    public void setAppliedPromo(final String appliedPromo) {
        this.appliedPromo = appliedPromo;
    }

    /** {@inheritDoc} */
    public String getDeliveryNum() {
        return deliveryNum;
    }

    /** {@inheritDoc} */
    public void setDeliveryNum(final String deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    /** {@inheritDoc} */
    public String getDeliveryStatusLabel() {
        return deliveryStatusLabel;
    }

    /** {@inheritDoc} */
    public void setDeliveryStatusLabel(final String deliveryStatusLabel) {
        this.deliveryStatusLabel = deliveryStatusLabel;
    }

    /** {@inheritDoc} */
    public String toString() {
        return "CustomerOrderDeliveryDetailDTOImpl{" +
                "customerorderdeliveryId=" + customerOrderDeliveryDetId +
                ", skuCode='" + skuCode + '\'' +
                ", skuName='" + skuName + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", netPrice=" + netPrice +
                ", grossPrice=" + grossPrice +
                ", listPrice=" + listPrice +
                ", deliveryNum='" + deliveryNum + '\'' +
                ", deliveryStatusLabel='" + deliveryStatusLabel + '\'' +
                '}';
    }
}
