package com.example.androidmusicbrainz.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CountryCode {
    public Map<String, String> countries = new HashMap<>();

    public CountryCode() {
        for(String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }
    }

    public String getCountryCode(String countryName){
        return this.countries.get(countryName);
    }
}
