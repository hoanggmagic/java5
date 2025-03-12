package asm.asm.Controller;

import asm.asm.Model.Category;
import asm.asm.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Hiển thị danh sách danh mục
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/category";  // Đường dẫn tới view Thymeleaf hiển thị danh mục
    }

    // Hiển thị form thêm danh mục mới
    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category_form";  // Đường dẫn tới form thêm danh mục
    }

    // Lưu danh mục mới
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/category";  // Sau khi lưu, chuyển hướng về trang danh sách
    }

    // Hiển thị form chỉnh sửa danh mục
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") Integer id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            model.addAttribute("category", category);
            return "admin/category_form";  // Đảm bảo template là category_form.html
        } else {
            return "redirect:/admin/category";  // Nếu không tìm thấy danh mục, quay lại danh sách
        }
    }

    // Cập nhật danh mục (kiểm tra xem là chỉnh sửa hay thêm mới)
    @PostMapping("/saveOrUpdate")
    public String saveOrUpdateCategory(@ModelAttribute("category") Category category) {
        if (category.getId() == null) {
            // Nếu id là null, tức là thêm mới
            categoryRepository.save(category);
        } else {
            // Nếu id có giá trị, tức là chỉnh sửa
            categoryRepository.save(category);
        }
        return "redirect:/admin/category";  // Sau khi lưu hoặc cập nhật, chuyển hướng về trang danh sách
    }

    // Xóa danh mục
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";  // Sau khi xóa, chuyển hướng về trang danh sách
    }
}
