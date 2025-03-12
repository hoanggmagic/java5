package asm.asm.Repository;

import asm.asm.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Tìm danh sách đơn hàng theo username của tài khoản
    List<Order> findByAccount_Username(String username);
}
