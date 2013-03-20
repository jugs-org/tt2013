package de.heinerkuecker.constraint.util.coll;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Zweidimensionale HashMap.
 * Diese Map hat zwei Schlüssel.
 * 
 * @author Heiner K&uuml;cker
 */
public final class TwoDimHashMap<K1, K2, V>
implements Serializable
{
    /**
     * primäre (1) innere HashMap
     */
    private final HashMap<K1, HashMap<K2, V>> map1 = 
        new HashMap<K1, HashMap<K2, V>>();

    /**
     * @see java.util.HashMap#get(java.lang.Object)
     */
    public V get( 
            final K1 key1 , 
            final K2 key2 )
    {
        // sekundäre Map holen
        final HashMap<K2, V> map2 =
            map1.get( 
                    key1 );
        
        if ( map2 == null )
        {
            return null;
        }
        
        return map2.get( key2 );
    }

    /**
     * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
     */
    public V put( 
            final K1 key1 , 
            final K2 key2 , 
            final V value )
    {
        // sekundäre Map holen
        HashMap<K2, V> map2 =
            map1.get( 
                    key1 );
        
        if ( map2 == null )
        {
            // sekundäre Map erzeugen
            map2 = new HashMap<K2, V>();
            
            map1.put( 
                    key1 , 
                    map2 );
        }

        return map2.put( 
                key2 , 
                value );
    }

    /**
     * @return debug output
     * @see java.util.AbstractMap#toString()
     */
    @Override
    public String toString()
    {
        return this.map1.toString();
    }

}
