package entities.factory.impl;

import com.mercadopago.resources.Preference;
import entities.factory.PreferenceFactory;

public class PreferenceImpl implements PreferenceFactory {
    @Override
    public Preference newPreference() {
        return new Preference();
    }
}
