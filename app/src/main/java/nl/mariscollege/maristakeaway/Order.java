package nl.mariscollege.maristakeaway;

import java.time.LocalDate;

public class Order {
    private String Email;
    private String Items;
    private Integer Break;
    private String DeliveryDate;
    private String price;
    private String location;

    public Order() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getItems() {
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }

    public Integer getBreak() {
        return Break;
    }

    public void setBreak(Integer aBreak) {
        Break = aBreak;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
