package external.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "currency_accounts")
@NoArgsConstructor
@Getter
@Setter
public class CurrencyAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_number")
    private Integer accountNumber;

    private String currency;

    private String code;

    private Double balance;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    public CurrencyAccount(Integer accountNumber, String currency, String code, Double balance, UserAccount userAccount) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.code = code;
        this.balance = balance;
        this.userAccount = userAccount;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "currencyAccount", cascade = CascadeType.REMOVE)
    private List<TransactionLog> transactionLogs = new ArrayList<>();
}
