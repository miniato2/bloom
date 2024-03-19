package com.cov.bloom.order.model.dto;

public class ApprovedCancelAmount {
    private int total;
    private int tax_free;

    public ApprovedCancelAmount() {
    }

    public ApprovedCancelAmount(int total, int tax_free) {
        this.total = total;
        this.tax_free = tax_free;
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

    @Override
    public String toString() {
        return "ApprovedCancelAmount{" +
                "total=" + total +
                ", tax_free=" + tax_free +
                '}';
    }
}
