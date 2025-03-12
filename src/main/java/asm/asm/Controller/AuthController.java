package asm.asm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import asm.asm.Model.Account;
import asm.asm.Service.AccountService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    AccountService accountService;

    @Autowired
    HttpSession session;

    // Trang đăng nhập
    @GetMapping("/auth/login")
    public String loginForm(Model model) {
        return "auth/login"; // Trả về trang đăng nhập
    }

    // Xử lý đăng nhập
    @PostMapping("/auth/login")
    public String loginProcess(Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        // Tìm tài khoản theo tên người dùng
        Account user = accountService.findByUsername(username);

        // Kiểm tra tài khoản hợp lệ
        if (user == null) {
            model.addAttribute("message", "Tên người dùng không hợp lệ!");
            return "auth/login";
        } else if (!user.getPassword().equals(password)) {
            model.addAttribute("message", "Mật khẩu không hợp lệ!");
            return "auth/login";
        } else if (!user.isActivated()) {
            model.addAttribute("message", "Tài khoản chưa được kích hoạt!");
            return "auth/login";
        } else {
            // Lưu thông tin tài khoản vào session
            session.setAttribute("user", user);
            session.setAttribute("isAdmin", user.isAdmin()); // Lưu quyền Admin

            return "redirect:/home"; // Điều hướng đến trang chủ
        }
    }

    // Đăng xuất
    @GetMapping("/auth/logout")
    public String logout() {
        // Xóa thông tin tài khoản khỏi session
        session.removeAttribute("user");
        session.removeAttribute("isAdmin");

        return "redirect:/auth/login"; // Chuyển về trang đăng nhập
    }

    // Kiểm tra quyền truy cập admin
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/auth/login"; // Chuyển về trang đăng nhập nếu không phải admin
        }
        return "admin/dashboard"; // Trả về trang admin nếu có quyền
    }
}
