package external.client;

public class NbpRate {
    private String currency;
    private String code;
    private Double bid;
    private Double ask;


    public NbpRate(String currency, String code, Double bid, Double ask) {
        this.currency = currency;
        this.code = code;
        this.bid = bid;
        this.ask = ask;
    }

    public NbpRate() {
    }

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public Double getBid() {
        return bid;
    }

    public Double getAsk() {
        return ask;
    }
}
