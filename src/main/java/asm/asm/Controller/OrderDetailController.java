package asm.asm.Controller;

import asm.asm.Model.Order;
import asm.asm.Model.OrderDetail;
import asm.asm.Model.Product;
import asm.asm.Repository.OrderDetailRepository;
import asm.asm.Repository.OrderRepository;
import asm.asm.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // ✅ Lấy danh sách chi tiết đơn hàng theo Order ID
    @GetMapping("/{orderId}")
    public String getOrderDetails(@PathVariable Long orderId, Model model) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            model.addAttribute("error", "❌ Đơn hàng không tồn tại!");
            return "redirect:/cart";
        }
        
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_Id(orderId);
        if (orderDetails.isEmpty()) {
            model.addAttribute("message", "🚫 Đơn hàng chưa có sản phẩm nào.");
        }
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orderId", orderId);
        return "order-details";
    }

    // ✅ Thêm sản phẩm vào đơn hàng
    @PostMapping("/add")
    public String addOrderDetail(
            @RequestParam Long orderId,
            @RequestParam int productId,
            @RequestParam int quantity,
            @RequestParam double price,
            Model model) {

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        Optional<Product> productOpt = productRepository.findById(productId);

        if (orderOpt.isEmpty() || productOpt.isEmpty()) {
            model.addAttribute("error", "❌ Đơn hàng hoặc sản phẩm không tồn tại!");
            return "redirect:/cart";
        }

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(orderOpt.get());
        orderDetail.setProduct(productOpt.get());
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(price);

        orderDetailRepository.save(orderDetail);
        return "redirect:/order-details/" + orderId;
    }

    // ✅ Xóa một sản phẩm khỏi đơn hàng
    @PostMapping("/delete/{id}")
    public String deleteOrderDetail(@PathVariable Long id, Model model) {
        Optional<OrderDetail> orderDetailOpt = orderDetailRepository.findById(id);

        if (orderDetailOpt.isPresent()) {
            Long orderId = orderDetailOpt.get().getOrder().getId();
            orderDetailRepository.deleteById(id);
            return "redirect:/order-details/" + orderId;
        } else {
            model.addAttribute("error", "❌ Không tìm thấy sản phẩm!");
            return "redirect:/cart";
        }
    }
}
