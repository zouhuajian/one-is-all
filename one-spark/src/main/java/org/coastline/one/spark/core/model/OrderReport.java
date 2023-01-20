package org.coastline.one.spark.core.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2023/1/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReport {
    @SerializedName("order_id")
    private String orderId;

    @SerializedName("total_amount")
    private double totalAmount;

    @SerializedName("actual_amount")
    private double actualAmount;

    @SerializedName("address")
    private String address;

    @SerializedName("creation_time")
    private Timestamp creationTime;

    @SerializedName("payment_time")
    private Timestamp paymentTime;

    @SerializedName("refund_amount")
    private double refundAmount;
}
