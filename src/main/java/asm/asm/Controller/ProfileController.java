package asm.asm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import asm.asm.Model.Account;
import asm.asm.Service.AccountService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("")
    public String viewAccount(HttpSession session, Model model) {
        // Lấy thông tin tài khoản từ session
        Account loggedInAccount = (Account) session.getAttribute("user");

        // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
        if (loggedInAccount == null) {
            return "redirect:/auth/login";
        }

        // Đưa thông tin tài khoản vào Model để hiển thị trên giao diện
        model.addAttribute("account", loggedInAccount);
        return "profile"; // Trả về trang profile
    }
    
}
