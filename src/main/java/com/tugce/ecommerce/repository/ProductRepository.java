package com.tugce.ecommerce.repository;
/*ProductRepository arayüzü, Product entity'si üzerinde veritabanı işlemlerini gerçekleştirmek için kullanılır.
JpaRepository'den kalıtım aldığı için temel CRUD (Create, Read, Update, Delete)işlemleri Spring Data JPA tarafından
otomatik olarak sağlanır.*/

import com.tugce.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//ProductRepository adında bir interface (arayüz) oluşturulur.
//interface görevi:Product tablosuyla ilgili veritabanı işlemlerini yönetmek.
public interface ProductRepository extends JpaRepository<Product, Long>{

}


