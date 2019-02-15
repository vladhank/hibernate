package entities;

import java.util.Objects;

public class Transaction {
    private long transactionID;
    private long cardNumber;
    private long accountID;
    private String transactionType;
    private double amountMoney;

    public Transaction() {
    }

    public Transaction(long cardNumber, long accountID, String transactionType, double amountMoney) {
        this.cardNumber = cardNumber;
        this.accountID = accountID;
        this.transactionType = transactionType;
        this.amountMoney = amountMoney;
    }

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(double amountMoney) {
        this.amountMoney = amountMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionID == that.transactionID &&
                cardNumber == that.cardNumber &&
                accountID == that.accountID &&
                amountMoney == that.amountMoney &&
                Objects.equals(transactionType, that.transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, cardNumber, accountID, transactionType, amountMoney);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", cardNumber=" + cardNumber +
                ", accountID=" + accountID +
                ", transactionType='" + transactionType + '\'' +
                ", amountMoney=" + amountMoney +
                '}';
    }
}
