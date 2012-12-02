package edu.champlain.csi319.findstuff.content;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Categories
{

    /**
     * No-instantiate constructor
     */
    private Categories() {}
    
    public static final Map<String, String> CATEGORY;
    static
    {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("For Sale", "S");
        map.put("Real Estate", "R");
        CATEGORY = Collections.unmodifiableMap(map);
    }
    
    public static final Map<String, String> SUBCATEGORY_FORSALE;
    static
    {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("Electronics & Photo", "ELE");
        map.put("Furniture", "FUR");
        map.put("Motorcycles", "MCA|mcy");
        SUBCATEGORY_FORSALE = Collections.unmodifiableMap(map);
    }
    
    public static final Map<String, String> SUBCATEGORY_REALESTATE;
    static
    {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("Housing For Sale", "HFS");
        map.put("Housing For Rent", "HFR");
        SUBCATEGORY_REALESTATE = Collections.unmodifiableMap(map);
    }
}
