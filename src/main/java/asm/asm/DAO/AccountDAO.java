package asm.asm.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import asm.asm.Model.Account;

public interface AccountDAO extends JpaRepository<Account, String> {
    Account findByUsername(String username);
}
