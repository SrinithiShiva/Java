package borrowAndLoan;

import displayPanel.LoanDetailsDisplay;
import members.Member;

/** This class contains data related to the load history
 * Each member has a loan history
 */
public class LoanHistory
{
    private Double totalFeesPaid=0.0;
    LoanDetailsDisplay loanDetailsDisplay=new LoanDetailsDisplay();

    public Double totalFeesPaid()
    {
        return this.totalFeesPaid;
    }
    public void calculate(Member member)
    {
        this.totalFeesPaid+=member.loanFeesPerDay();
    }

    public LoanDetailsDisplay loanDetailsDisplay()
    {
        return this.loanDetailsDisplay;
    }
}
