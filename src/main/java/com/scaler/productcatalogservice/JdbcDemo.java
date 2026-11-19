package com.scaler.productcatalogservice;

import com.scaler.productcatalogservice.models.Product;


import java.sql.*;

// JDBC -> Java SDK (JDK)
// try(...) - try-with-resource
public class JdbcDemo {
    public static void main(String[] args) {
//        System.out.println(0.1 + 0.1 + 0.1);
//        System.out.println(0.1 + 0.2);
//        System.out.println(new BigDecimal("0.1").add(new BigDecimal("0.2")));
          String url ="jdbc:mysql://localhost:3306/productcatalogtestdb";
          Long id=2l;
            try (Connection con = DriverManager.getConnection(url, "root", System.getenv("DB_PASSWORD"));
                 PreparedStatement ps = con.prepareStatement("SELECT id, name, price FROM demo_product WHERE id = ?")) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("id"));
                    product.setName(rs.getString("name"));
//                    product.setPrice(rs.getDouble("price"));
                    System.out.println(product.getName() + " costs " + product.getPrice());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
// 1/2 -> 0.5, 1/3 -> 3.33333334
// 0.1 -> can't be represented ->
// SELECT id, name, price FROM demo_product WHERE id = "2l;DROP TABLE;"

// try (resources ) {
//      your code goes here (scope)
// } // resources are auto closed