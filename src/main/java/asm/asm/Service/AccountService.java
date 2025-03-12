package asm.asm.Service;

import asm.asm.Model.Account;
import java.util.List;

public interface AccountService {
    Account findByUsername(String username);
    void save(Account account);
    List<Account> findAll();
    void deleteByUsername(String username);
    
    // Thêm phương thức để lấy thông tin tài khoản hiện tại
    Account getCurrentAccount();
    

    // Thêm phương thức cập nhật tài khoản
    void updateAccount(Account account);
}
