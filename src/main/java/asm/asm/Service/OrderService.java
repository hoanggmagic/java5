package asm.asm.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asm.asm.Model.Order;
import asm.asm.Repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrdersByUser(String username) {
        // Sử dụng phương thức đã thay đổi trong OrderRepository
        return orderRepository.findByAccount_Username(username);
    }

    public void saveOrder(Order order) {    
        orderRepository.save(order);
    }
}
