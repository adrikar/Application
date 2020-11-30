package com.example.application.model;

public class BookingInformation {
    private String customerName, customerPhone, time, tableId, tableName,restId,restName,restAddress;
    private Long slot;

    public BookingInformation() {
    }

    public BookingInformation(String customerName, String customerPhone, String time, String tableId, String tableName, String restId, String restName, String restAddress, Long slot) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.time = time;
        this.tableId = tableId;
        this.tableName = tableName;
        this.restId = restId;
        this.restName = restName;
        this.restAddress = restAddress;
        this.slot = slot;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String barberId) {
        this.tableId = barberId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String barberName) {
        this.tableName = barberName;
    }

    public String getRestId() {
        return restId;
    }

    public void setRestId(String salonId) {
        this.restId = restId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestAddress() {
        return restAddress;
    }

    public void setRestAddress(String restAddress) {
        this.restAddress = restAddress;
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }
}
