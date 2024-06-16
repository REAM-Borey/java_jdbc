package model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    private Integer id;
    private String orderName;
    private String orderDescription;
    private Customers customer;
    private List<Product> productList;
    private Date orderDate;
}
