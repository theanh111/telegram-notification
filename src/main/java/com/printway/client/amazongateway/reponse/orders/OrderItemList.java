package com.printway.client.amazongateway.reponse.orders;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Objects;

@NoArgsConstructor
public class OrderItemList extends ArrayList<OrderItem> {
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else {
            return o != null && this.getClass() == o.getClass() ? super.equals(o) : false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{super.hashCode()});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OrderItemList {\n");
        sb.append("    ").append(this.toIndentedString(super.toString())).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}

