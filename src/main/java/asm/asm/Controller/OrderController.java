package asm.asm.Controller;

import asm.asm.Model.Order;
import asm.asm.Model.Account;
import asm.asm.Repository.OrderRepository;
import asm.asm.Repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public String showOrderPage() {
        return "cart"; // Trang giỏ hàng
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("username") String username, Model model) {
        Optional<Account> accountOpt = accountRepository.findById(username);
        if (accountOpt.isEmpty()) {
            model.addAttribute("error", "Tài khoản không tồn tại!");
            return "cart";
        }

        // Tạo đơn hàng mới
        Order order = new Order();
        order.setAccount(accountOpt.get());
        order.setOrderDate(LocalDate.now().toString());

        orderRepository.save(order); // Lưu đơn hàng

        model.addAttribute("message", "Đơn hàng đã được đặt thành công!");
        return "cart"; 
    }

    @GetMapping("/details")
    public String orderDetails(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "order-details"; // Trả về trang order-details.html
    }
}