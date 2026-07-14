package com.tugce.ecommerce.exception;
// İstenen hata veritabanında bulunmadığında gösterilen özel hata sınfı.

public class ProductNotFoundException extends RuntimeException  {
    public ProductNotFoundException(Long id){
        super("Ürün Bulunamadı. ID: " + id);
    }
}
