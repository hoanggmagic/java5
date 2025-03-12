package asm.asm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    // Hiển thị trang liên hệ
    @GetMapping("/contact")
    public String showContactForm() {
        return "contact"; // Trả về tên view "contact" (trang HTML liên hệ)
    }

    // Xử lý form khi người dùng gửi yêu cầu liên hệ
    @PostMapping("/contact")
    public String handleContactForm(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("message") String message,
        Model model) {

        // Xử lý thông tin nhận được từ form
        // Ví dụ: In thông tin ra console (hoặc gửi email, lưu vào DB, v.v.)

        System.out.println("Tên: " + name);
        System.out.println("Email: " + email);
        System.out.println("Tin nhắn: " + message);

        // Thêm thông báo cho người dùng
        model.addAttribute("successMessage", "Cảm ơn bạn đã liên hệ! Chúng tôi sẽ trả lời bạn sớm.");

        // Trả về view "contact" và hiển thị thông báo thành công
        return "contact";
    }
}
