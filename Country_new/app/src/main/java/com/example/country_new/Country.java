package com.example.country_new;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Country {
    private String continent;
    private String capital;
    private String languages;
    private int    geonameId;
    private float  south;
    private String isoAlpha3;
    private float north;
    private String fipsCode;
    private int population;
    private float  east;
    private String isoNumeric;
    private String areaInSqKm;
    private String countryCode;
    private float  west;
    private String countryName;
    private String continentName;
    private String currencyCode;

    public Country() {
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public int getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(int geonameId) {
        this.geonameId = geonameId;
    }

    public float getSouth() {
        return south;
    }

    public void setSouth(float south) {
        this.south = south;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public float getNorth() {
        return north;
    }

    public void setNorth(float north) {
        this.north = north;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public float getEast() {
        return east;
    }

    public void setEast(float east) {
        this.east = east;
    }

    public String getIsoNumeric() {
        return isoNumeric;
    }

    public void setIsoNumeric(String isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public float getWest() {
        return west;
    }

    public void setWest(float west) {
        this.west = west;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    // Decodes array of book json results into business model objects
    public static ArrayList<Country> fromJson(JSONArray jsonArray) {
        ArrayList<Country> countries = new ArrayList<Country>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject countriesJson = null;
            try {
                countriesJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Country country = Country.fromJson(countriesJson);
            if (country != null) {
                countries.add(country);
            }
        }
        return countries;
    }

    // Returns a Book given the expected JSON
    public static Country fromJson(JSONObject jsonObject) {
        Country country = new Country();
        try {
            // Deserialize json into object fields
            // Check if a cover edition is available
            country.setContinent(jsonObject.getString("continent"));
            country.setCapital(jsonObject.getString("capital"));
            country.setLanguages(jsonObject.getString("languages"));
            country.setGeonameId(jsonObject.getInt("geonameId"));
            country.setSouth(jsonObject.getInt("south"));
            country.setIsoAlpha3(jsonObject.getString("isoAlpha3"));
            country.setNorth(jsonObject.getInt("north"));
            country.setFipsCode(jsonObject.getString("fipsCode"));
            country.setPopulation(jsonObject.getInt("population"));
            country.setEast(jsonObject.getInt("east"));
            country.setIsoNumeric(jsonObject.getString("isoNumeric"));
            country.setAreaInSqKm(jsonObject.getString("areaInSqKm"));
            country.setCountryCode(jsonObject.getString("countryCode"));
            country.setWest(jsonObject.getInt("west"));
            country.setCountryName(jsonObject.getString("countryName"));
            country.setContinentName(jsonObject.getString("continentName"));
            country.setCurrencyCode(jsonObject.getString("currencyCode"));

            //Log.e("Kiem tra", country.getCountryName());

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return country;
    }

}
