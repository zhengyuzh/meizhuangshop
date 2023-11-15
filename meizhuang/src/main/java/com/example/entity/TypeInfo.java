package com.example.entity;

import javax.persistence.*;

@Table(name = "type_info")
public class TypeInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型描述
     */
    private String description;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取类型名称
     *
     * @return name - 类型名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置类型名称
     *
     * @param name 类型名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取类型描述
     *
     * @return description - 类型描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置类型描述
     *
     * @param description 类型描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}