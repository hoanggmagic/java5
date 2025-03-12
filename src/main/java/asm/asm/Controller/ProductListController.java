package asm.asm.Controller;

import asm.asm.Model.Category;
import asm.asm.Model.Product;
import asm.asm.Repository.CategoryRepository;
import asm.asm.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
public class ProductListController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("product", new Product());
        return "admin/products";
    }

    // Lưu sản phẩm (Thêm mới hoặc cập nhật)
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // Xử lý upload ảnh
            if (!imageFile.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.createDirectories(filePath.getParent()); // Tạo thư mục nếu chưa có
                Files.write(filePath, imageFile.getBytes());
                product.setImageUrl("/uploads/" + fileName); // Lưu đường dẫn
            }

            // Kiểm tra danh mục có tồn tại không
            Optional<Category> categoryOpt = categoryRepository.findById(product.getCategory().getId());
            if (!categoryOpt.isPresent()) {
                return "redirect:/admin/products?error=invalid_category";
            }

            product.setCategory(categoryOpt.get());
            productRepository.save(product);
            return "redirect:/admin/products?success=product_saved";
        } catch (IOException e) {
            return "redirect:/admin/products?error=image_upload_failed";
        }
    }

    // Chỉnh sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (!productOpt.isPresent()) {
            return "redirect:/admin/products?error=product_not_found";
        }

        model.addAttribute("product", productOpt.get());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/products";
    }

    // Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (!productOpt.isPresent()) {
            return "redirect:/admin/products?error=product_not_found";
        }

        try {
            productRepository.deleteById(id);
            return "redirect:/admin/products?success=product_deleted";
        } catch (Exception e) {
            return "redirect:/admin/products?error=delete_failed";
        }
    }
}
