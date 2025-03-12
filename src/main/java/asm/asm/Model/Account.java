package asm.asm.Model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String photo;
    private boolean activated;
    private boolean admin;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @JsonIgnore // Ngăn vòng lặp vô hạn khi chuyển sang JSON
    private List<Order> orders;

    @Transient
    @Getter @Setter
    private MultipartFile photoFile; 
}
