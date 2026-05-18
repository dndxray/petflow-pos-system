package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String transactionId;
    private List<CartItem> items;
    private double totalAmount;
    private double amountPaid;
    private double change;
    private String cashierId;
    private String cashierName;
    private LocalDateTime timestamp;

    public Transaction(String transactionId, List<CartItem> items,
                       double amountPaid, String cashierId, String cashierName) {
        assert amountPaid > 0;
        this.transactionId = transactionId;
        this.items = new ArrayList<>(items);
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.timestamp = LocalDateTime.now();

        this.totalAmount = 0;
        for (CartItem item : items){
            this.totalAmount += item.getSubtotal();
        }

        if (amountPaid < totalAmount) {
            throw new IllegalArgumentException("Uang tidak cukup!");
        }

        this.amountPaid = amountPaid;
        this.change = amountPaid - totalAmount;
    }

    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("       PET SHOP POS SYSTEM      \n");
        sb.append("================================\n");
        sb.append("No: ").append(transactionId).append("\n");
        sb.append("Kasir: ").append(cashierName).append("\n");
        sb.append("Waktu: ").append(timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        sb.append("================================\n");
        for (CartItem item : items) {
            sb.append(String.format("%-16s x%d\n", item.getProduct().getName(), item.getQuantity()));
            sb.append(String.format("  Rp%.0f\n", item.getSubtotal()));
        }
        sb.append("================================\n");
        sb.append(String.format("TOTAL   : Rp%.0f\n", totalAmount));
        sb.append(String.format("BAYAR   : Rp%.0f\n", amountPaid));
        sb.append(String.format("KEMBALI : Rp%.0f\n", change));
        sb.append("================================\n");
        return sb.toString();
    }

    // Getters
    public String getTransactionId() { 
        return transactionId; 
    }

    public double getTotalAmount() { 
        return totalAmount; 
    }

    public double getAmountPaid() { 
        return amountPaid; 
    }

    public double getChange() { 
        return change; 
    }

    public String getCashierId() { 
        return cashierId; 
    }

    public String getCashierName() { 
        return cashierName; 
    }
    public LocalDateTime getTimestamp() { 
        return timestamp; 
    }

    public List<CartItem> getItems() { 
        return items; 
    }
}