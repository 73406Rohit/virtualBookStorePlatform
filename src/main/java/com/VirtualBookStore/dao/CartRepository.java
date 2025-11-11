package com.VirtualBookStore.dao;
import com.VirtualBookStore.dto.Book;
import com.VirtualBookStore.dto.Cart;
import com.VirtualBookStore.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository  extends JpaRepository<Cart,Long> {

    List<Cart> findByUser(User user);
    Cart findByUserAndBook(User user, Book book);

}
