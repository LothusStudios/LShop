package studio.lothus.delivery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import studio.lothus.delivery.model.config.ProductConfig;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;
    private String name;

    private ProductConfig config;

    private double price;

}
