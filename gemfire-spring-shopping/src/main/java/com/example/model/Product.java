package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    private Integer prod_id;
    private Integer category;
    private String title;
    private String actor;
    private BigDecimal price;
    private Short special;
    private Integer common_prod_id;

    // getters and setters
    public Integer getProd_id() { return prod_id; }
    public void setProd_id(Integer prod_id) { this.prod_id = prod_id; }
    public Integer getCategory() { return category; }
    public void setCategory(Integer category) { this.category = category; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Short getSpecial() { return special; }
    public void setSpecial(Short special) { this.special = special; }
    public Integer getCommon_prod_id() { return common_prod_id; }
    public void setCommon_prod_id(Integer common_prod_id) { this.common_prod_id = common_prod_id; }
}