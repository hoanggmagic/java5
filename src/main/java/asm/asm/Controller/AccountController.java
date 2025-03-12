package asm.asm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import asm.asm.Model.Account;
import asm.asm.Repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    // Hiển thị danh sách tài khoản
    @GetMapping
    public String listAccounts(Model model) {
        // Lấy tất cả tài khoản từ database
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "admin/accounts"; // Trả về trang danh sách tài khoản
    }

    // Hiển thị form chỉnh sửa tài khoản
    @GetMapping("/edit/{username}")
public String editAccount(@PathVariable String username, Model model) {
    Optional<Account> accountOptional = accountRepository.findById(username);
    if (accountOptional.isPresent()) {
        Account account = accountOptional.get();
        model.addAttribute("account", account);
        return "admin/account-form"; // Trả về trang chỉnh sửa
    }
    return "redirect:/admin/accounts"; // Quay lại danh sách nếu không tìm thấy tài khoản
}


@PostMapping("/edit/{username}")
public String updateAccount(@PathVariable String username, @ModelAttribute Account account) {
    account.setUsername(username); // Đảm bảo rằng tên đăng nhập không bị thay đổi
    accountRepository.save(account);
    return "redirect:/admin/accounts"; // Sau khi cập nhật, quay lại danh sách
}



    // Xóa tài khoản
    @GetMapping("/delete/{username}")
    public String deleteAccount(@PathVariable String username) {
        // Xóa tài khoản theo username
        accountRepository.deleteById(username);
        return "redirect:/admin/accounts"; // Sau khi xóa, quay lại danh sách
    }

    // Hiển thị form thêm tài khoản
    @GetMapping("/add")
    public String showAddAccountForm(Model model) {
        // Tạo đối tượng Account mới để điền vào form thêm tài khoản
        model.addAttribute("account", new Account());
        return "admin/account-form"; // Form dùng chung để thêm/sửa
    }

    // Xử lý thêm tài khoản
    @PostMapping("/add")
    public String addAccount(@ModelAttribute Account account) {
        // Lưu tài khoản mới vào database
        accountRepository.save(account);
        return "redirect:/admin/accounts"; // Sau khi thêm tài khoản, quay lại danh sách
    }
}
