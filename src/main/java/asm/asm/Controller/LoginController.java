package asm.asm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import asm.asm.Model.Account;
import asm.asm.Service.AccountService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Trả về trang đăng nhập
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Account user = accountService.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            return "login"; // Quay lại trang login nếu sai thông tin
        }

        // Lưu thông tin đăng nhập vào session
        session.setAttribute("user", user);

        // Chuyển hướng đến trang admin nếu là admin, nếu không thì về trang home
        return user.isAdmin() ? "redirect:/admin" : "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user"); // Xóa session
        return "redirect:/login"; // Quay lại trang đăng nhập
    }

    @GetMapping("/admin")
    public String adminDashboard(HttpSession session) {
        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        if (!user.isAdmin()) {
            return "redirect:/home";
        }

        return "admin"; // Trả về trang quản trị nếu là admin
    }
}
