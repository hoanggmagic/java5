package asm.asm.Repository;

import asm.asm.Model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // ✅ Lấy danh sách chi tiết đơn hàng theo ID của Order
    List<OrderDetail> findByOrder_Id(Long orderId);
}
