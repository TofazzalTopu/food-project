package com.bits.bdfp.inventory.sales

/**
 * Created by NZ on 1/25/2016.
 */
enum MarketReturnType {
    LEAK_PACK("Leak Pack", "LEAK_PACK"),
    SHORT_PACK("Short Pack", "SHORT_PACK"),
    MARKET_RETURN("Market Return", "MARKET_RETURN"),
    SHORT_SUPPLY_FROM_CHALLAN("Short Supply from Challan", "SHORT_SUPPLY_FROM_CHALLAN"),
    DAMAGE("Damage", "DAMAGE");

    private String displayName;
    private String code

    MarketReturnType(String displayName, String code) {
        this.displayName = displayName;
        this.code = code
    }

    public String displayName() { return displayName; }
    public String code() { return code; }

    // Optionally and/or additionally, toString.
    @Override public String toString() { return displayName; }

}