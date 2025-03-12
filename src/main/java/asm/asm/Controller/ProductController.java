package asm.asm.Controller;

import asm.asm.Model.Product;
import asm.asm.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // Hiển thị danh sách sản phẩm
    @GetMapping("/products")
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list"; // Trả về trang danh sách sản phẩm
    }

    // Xem chi tiết sản phẩm
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại");
        }

        model.addAttribute("product", product);
        return "product-detail"; // Trả về trang chi tiết sản phẩm
    }

    // Thêm sản phẩm vào giỏ hàng
    @GetMapping("/cart/add/{id}")
    public String addToCart(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id); // Tìm sản phẩm trong cơ sở dữ liệu
        if (product == null) {
            // Trả về trang lỗi 404 nếu sản phẩm không tìm thấy
            return "error/404";
        }
        // Thêm sản phẩm vào giỏ hàng (logic giỏ hàng có thể thêm vào sau)
        model.addAttribute("product", product);
        return "cart"; // Trả về trang giỏ hàng
    }
}
