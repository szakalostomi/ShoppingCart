package com.example.shoppingcart;

public class CartItem {
    String Id, Name, Price, Description;

    public CartItem() {}

    public CartItem(String Id, String Name, String Price, String Description) {
        this.Id = Id;
        this.Name = Name;
        this.Price = Price;
        this.Description = Description;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getDesc() {
        return Description;
    }

    public void setDesc(String Description) {
        this.Description = Description;
    }
}
