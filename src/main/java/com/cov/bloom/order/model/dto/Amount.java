package com.cov.bloom.order.model.dto;

public class Amount {   //결제 금액정보
    private int total;
    private int tax_free;
    private int tax;

    public Amount() {
    }

    public Amount(int total, int tax_free, int tax) {
        this.total = total;
        this.tax_free = tax_free;
        this.tax = tax;

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTax_free() {
        return tax_free;
    }

    public void setTax_free(int tax_free) {
        this.tax_free = tax_free;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "total=" + total +
                ", tax_free=" + tax_free +
                ", tax=" + tax +
                '}';
    }
}
