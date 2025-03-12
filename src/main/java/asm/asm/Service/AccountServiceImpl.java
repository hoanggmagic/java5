package asm.asm.Service;

import asm.asm.Model.Account;
import asm.asm.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteByUsername(String username) {
        accountRepository.deleteByUsername(username); // Đảm bảo phương thức này có trong repository
    }

    @Override
    public Account getCurrentAccount() {
        // Giả sử bạn sử dụng Spring Security để lấy thông tin người dùng hiện tại
        // Lấy tên đăng nhập của người dùng hiện tại và tìm tài khoản
        String username = "currentUsername"; // Đổi lại với tên người dùng thực tế từ Spring Security
        return accountRepository.findByUsername(username);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }
}
