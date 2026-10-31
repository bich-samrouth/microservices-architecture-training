package co.khmer.samrouth.ecommerce.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(unique = true,nullable = false)
    private String id;
    private String username;
    private String gender;
    private String birthday;
    @Column(unique = true)
    private String mobilePhone;
    private String address;
}
