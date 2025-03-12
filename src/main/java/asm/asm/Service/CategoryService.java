package asm.asm.Service;

import asm.asm.Model.Category;
import asm.asm.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy danh sách tất cả các danh mục
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Lấy danh mục theo ID
    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    // Thêm một danh mục mới
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Cập nhật danh mục
    public Optional<Category> updateCategory(Integer id, Category category) {
        if (categoryRepository.existsById(id)) {
            category.setId(id);
            return Optional.of(categoryRepository.save(category));
        }
        return Optional.empty();
    }

    // Xóa danh mục
    public boolean deleteCategory(Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
