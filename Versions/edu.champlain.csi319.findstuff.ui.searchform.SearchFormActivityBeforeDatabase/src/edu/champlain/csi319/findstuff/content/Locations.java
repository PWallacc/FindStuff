package edu.champlain.csi319.findstuff.content;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Locations
{

    /**
     * No-instantiate constructor
     */
    private Locations() {}
    
    public static final Map<String, String> COUNTRY;
    static
    {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("USA", "USA");
        map.put("Canada", "CAN");
        COUNTRY = Collections.unmodifiableMap(map);
    }
    
    public static final Map<String, String> STATES_USA;
    static
    {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("Alabama", "AL");
        map.put("California", "CA");
        map.put("New York", "NY");
        map.put("Vermont", "VT");
        STATES_USA = Collections.unmodifiableMap(map);
    }
    
    public static final Map<String, String> PROVINCES_CAN;
    static
    {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("British Columbia", "BC");
        map.put("Ontario", "ON");
        map.put("Quebec", "QC");
        PROVINCES_CAN = Collections.unmodifiableMap(map);
    }    
}
