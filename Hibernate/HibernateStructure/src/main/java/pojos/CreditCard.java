package pojos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CreditCard {
    private long cardNumber;
    // VISA  or MasterCard
    private String cardCompany;
    private String firstName;
    private String lastName;
    private Date expDate;
    private int cvv;
    private int pin;
    private int cashBack;
    private String status;
    private String bankAccount;
    private long clientID;

    public CreditCard(){}

    public long getCardNumber() {
        return cardNumber;
    }

    public CreditCard(long cardNumber, String cardCompany, String firstName, String lastName, Date expDate, int cvv, int pin, int cashBack, String status, String bankAccount, long clientID) {
        this.cardNumber = cardNumber;
        this.cardCompany = cardCompany;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expDate = expDate;
        this.cvv = cvv;
        this.pin = pin;
        this.cashBack = cashBack;
        this.status = status;
        this.bankAccount = bankAccount;
        this.clientID = clientID;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCompany() {
        return cardCompany;
    }

    public void setCardCompany(String cardCompany) {
        this.cardCompany = cardCompany;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getCashBack() {
        return cashBack;
    }

    public void setCashBack(int cashBack) {
        this.cashBack = cashBack;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return cardNumber == that.cardNumber &&
                cvv == that.cvv &&
                pin == that.pin &&
                cashBack == that.cashBack &&
                clientID == that.clientID &&
                Objects.equals(cardCompany, that.cardCompany) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(expDate, that.expDate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(bankAccount, that.bankAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, cardCompany, firstName, lastName, expDate, cvv, pin, cashBack, status, bankAccount, clientID);
    }

    @Override
    public String toString() {
        String pattern = "MM/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return "CreditCard{" +
                "cardNumber=" + cardNumber +
                ", cardCompany='" + cardCompany + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", expDate=" + simpleDateFormat.format(expDate) +
                ", cvv=" + cvv +
                ", pin=" + pin +
                ", cashBack=" + cashBack +
                ", status='" + status + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", clientID=" + clientID +
                '}';
    }
}