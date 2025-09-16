package displayPanel;

import borrowAndLoan.LoanHistory;

public class LoanDetailsDisplay implements Display<LoanHistory> {
    @Override
    public void display(LoanHistory loanHistory) {
        System.out.println("Current Dues paid: ".concat(String.valueOf(loanHistory.totalFeesPaid())));
    }
}
