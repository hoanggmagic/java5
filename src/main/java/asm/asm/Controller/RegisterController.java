package asm.asm.Controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import asm.asm.DAO.AccountDAO;
import asm.asm.Model.Account;


@Controller
public class RegisterController {

    @Autowired
    private AccountDAO accountDAO;

    private static final String UPLOAD_DIR = "D:\\HocKy5\\JAVA5\\Lab_Java5\\asm\\src\\main\\resources\\static\\uploads"; // Thư
                                                                                                                                    // mục
                                                                                                                                    // lưu
                                                                                                                                    // ảnh

    @GetMapping("/auth/register")
    public String showRegisterForm() {
        return "auth/register";
    }

    @PostMapping("/auth/register")
    public String register(@RequestParam String username, @RequestParam String password,
            @RequestParam String fullname, @RequestParam String email,
            @RequestParam("photo") MultipartFile photoFile,
            RedirectAttributes redirectAttributes) {
        // Kiểm tra nếu tài khoản đã tồn tại
        if (accountDAO.findById(username).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "redirect:/auth/register";
        }

        // Xử lý lưu ảnh
        String photoPath = "default.jpg"; // Nếu người dùng không chọn ảnh
        if (!photoFile.isEmpty()) {
            try {
                // Tạo thư mục nếu chưa có
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Đổi tên file tránh trùng lặp
                String originalFileName = photoFile.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); // Lấy phần mở// rộng
                String uniqueFileName = UUID.randomUUID().toString() + fileExtension; // Đặt tên mới

                // Lưu file vào thư mục uploads/
                File destFile = new File(UPLOAD_DIR + uniqueFileName);
photoFile.transferTo(destFile);

                // Cập nhật đường dẫn ảnh
                photoPath = uniqueFileName;

            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Lỗi khi tải ảnh lên! Vui lòng thử lại.");
                return "redirect:/auth/register";
            }
        }

        // Tạo tài khoản mới
        Account newAccount = new Account();
        newAccount.setUsername(username);
        newAccount.setPassword(password);
        newAccount.setFullname(fullname);
        newAccount.setEmail(email);
        newAccount.setPhoto(photoPath);
        newAccount.setActivated(true);
        newAccount.setAdmin(false);

        // Lưu vào database
        accountDAO.save(newAccount);

        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Hãy đăng nhập.");
        return "redirect:/auth/login";
    }
}