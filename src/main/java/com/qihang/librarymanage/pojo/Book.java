package com.qihang.librarymanage.pojo;

public class Book {
    private Integer id; // 图书ID
    private String bookName; //图书名
    private String author; //图书作者
    private String publish; //图书出版社
    private Integer number; //图书数量
    private String bookRemark; //图书描述
    private Integer typeId; //图书类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getBookRemark() {
        return bookRemark;
    }

    public void setBookRemark(String bookRemark) {
        this.bookRemark = bookRemark;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
