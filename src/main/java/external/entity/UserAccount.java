package external.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_accounts")
@NoArgsConstructor
@Getter
@Setter
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userAccount", cascade = CascadeType.REMOVE)
    private List<CurrencyAccount> currencyAccounts = new ArrayList<>();

    public UserAccount(String firstName, String lastName, String phoneNumber, String email, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.user = user;
    }

}
