package com.qihang.librarymanage.pojo;

public class BookType {
    private Integer id; // 图书类型ID
    private String typeName; // 图书类型名
    private String typeRemark; // 图书类型描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeRemark() {
        return typeRemark;
    }

    public void setTypeRemark(String typeRemark) {
        this.typeRemark = typeRemark;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
