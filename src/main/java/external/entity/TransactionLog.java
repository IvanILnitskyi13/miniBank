package external.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction_logs")
@Getter
@Setter
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(name = "foreign_currency_account")
    private Integer foreignCurrencyAccount;

    @Column(name = "transfer_amount")
    private Double transferAmount;

    @Column(name = "balance_after_transaction")
    private Double balanceAfterTransaction;

    @ManyToOne
    @JoinColumn(name ="currency_account_id")
    private CurrencyAccount currencyAccount;

    public TransactionLog(LocalDate date, Integer foreignCurrencyAccount, Double transferAmount, Double balanceAfterTransaction, CurrencyAccount currencyAccount) {
        this.date = date;
        this.foreignCurrencyAccount = foreignCurrencyAccount;
        this.transferAmount = transferAmount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.currencyAccount = currencyAccount;
    }
}
