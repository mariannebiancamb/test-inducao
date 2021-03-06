package controller.response;

import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;

import java.util.List;

public class PreferenceResponse {
    private final List<Item> items;
    private final Payer payer;
    private final String id;
    private final String initPoint;
    private final String sandBoxInitPoint;

    public PreferenceResponse(List<Item> items, Payer payer, String id, String initPoint, String sandBoxInitPoint) {
        this.items = items;
        this.payer = payer;
        this.id = id;
        this.initPoint = initPoint;
        this.sandBoxInitPoint = sandBoxInitPoint;
    }

    public List<Item> getItem() {
        return items;
    }

    //Builder Pattern
    public static class Builder {
        List<Item> items;
        Payer payer;
        String id;
        String initPoint;
        String sandBoxInitPoint;

        public Builder withItems(List<Item> items) {
            this.items = items;
            return this;
        }
        public Builder withPayer(Payer payer) {
            this.payer = payer;
            return this;
        }
        public Builder withId(String id) {
            this.id = id;
            return this;
        }
        public Builder withInitPoint(String initPoint) {
            this.initPoint = initPoint;
            return this;
        }
        public Builder withSandBoxInitPoint(String sandBoxInitPoint) {
            this.sandBoxInitPoint = sandBoxInitPoint;
            return this;
        }
        public PreferenceResponse build() {
            return new PreferenceResponse(items, payer, id, initPoint, sandBoxInitPoint);
        }
    }
}
