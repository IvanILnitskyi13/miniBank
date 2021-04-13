package external.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private String code;
    private Double ask;
    private Double bid;
    private LocalDate rateDate;

    public ExchangeRate(String currency, String code, Double ask, Double bid, LocalDate rateDate) {
        this.currency = currency;
        this.code = code;
        this.ask = ask;
        this.bid = bid;
        this.rateDate = rateDate;
    }

    public ExchangeRate() {
    }

    public Long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCode() { return code; }

    public Double getAsk() {
        return ask;
    }

    public Double getBid() { return bid; }

    public LocalDate getRateDate() {
        return rateDate;
    }

}
