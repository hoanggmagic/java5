package asm.asm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import asm.asm.Model.Product;
import asm.asm.Service.ProductService;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/home")
    public String index(Model model) {
        List<Product> products = productService.getAllProducts();  // Lấy danh sách sản phẩm từ service
        model.addAttribute("products", products);  // Thêm danh sách sản phẩm vào model
        return "home";  // Trả về trang home
    }
    
}
