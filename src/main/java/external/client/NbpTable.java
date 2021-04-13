package external.client;

import java.time.LocalDate;
import java.util.List;

public class NbpTable {
    private LocalDate effectiveDate;
    private List<NbpRate> rates;

    public NbpTable(LocalDate effectiveDate, List<NbpRate> rates) {
        this.effectiveDate = effectiveDate;
        this.rates = rates;
    }

    public NbpTable() {
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public List<NbpRate> getRates() {
        return rates;
    }
}
