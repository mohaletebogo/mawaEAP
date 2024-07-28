/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.raretag.mawa.generic;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author tebogom
 */
public class Item {
    
    private String productCode;

    /**
     * Get the value of productCode
     *
     * @return the value of productCode
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Set the value of productCode
     *
     * @param productCode new value of productCode
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    private String description;

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

       private BigDecimal price;

    /**
     * Get the value of price
     *
     * @return the value of price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Set the value of price
     *
     * @param price new value of price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Item() {     
        
    }
 
    private String productCategory;

    /**
     * Get the value of productCategory
     *
     * @return the value of productCategory
     */
    public String getProductCategory() {
        return productCategory;
    }

    /**
     * Set the value of productCategory
     *
     * @param productCategory new value of productCategory
     */
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
    
       private String validFrom;

    /**
     * Get the value of validFrom
     *
     * @return the value of validFrom
     */
    public String getValidFrom() {
        return validFrom;
    }

    /**
     * Set the value of validFrom
     *
     * @param validFrom new value of validFrom
     */
    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

        private String validTo;

    /**
     * Get the value of validTo
     *
     * @return the value of validTo
     */
    public String getValidTo() {
        return validTo;
    }

    /**
     * Set the value of validTo
     *
     * @param validTo new value of validTo
     */
    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }


}
