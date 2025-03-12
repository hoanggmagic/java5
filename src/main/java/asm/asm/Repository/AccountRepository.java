package asm.asm.Repository;

import asm.asm.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    // Phương thức tìm tài khoản theo username
    Account findByUsername(String username);

    // Phương thức xóa tài khoản theo username
    void deleteByUsername(String username);
}
