package com.reljicd.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2018-07-07.
 */
@Entity
@Table(name = "WEBINFO")
public class WebInfo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "OPTION_KEY", nullable = false)
    @Length(max = 100, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your optionKey")
    private String optionKey;

    @Column(name = "DESCRIPTION", nullable = false)
    @Length(min = 1, message = "*Your password must have at least 1 characters")
    @NotEmpty(message = "*Please provide your description")
    private String description;

    @Column(name = "OPTION_VALUE", nullable = false)
    @Length(max = 500, message = "*Your password must have at least 5 characters")
    private String optionValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionKey() {
        return optionKey;
    }

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

  }
