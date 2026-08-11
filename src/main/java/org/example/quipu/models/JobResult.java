package org.example.quipu.models;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.math.BigDecimal;
import java.util.List;

@DynamoDbBean
public class JobResult {
    private BigDecimal totalRevenue;
    private long totalRows;
    private List<ProductSales> topProducts;

    public JobResult() {}

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public List<ProductSales> getTopProducts() {
        return topProducts;
    }

    public void setTopProducts(List<ProductSales> topProducts) {
        this.topProducts = topProducts;
    }
}
