package de.heinerkuecker.constraint.util.coll;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Heiner K&uuml;cker
 */
public final class ReadOnlyMapAdaptor<K, V>
implements ReadOnlyMap<K, V>
{
    /**
     * Gegen Veränderung zu schützende Map.
     */
    private final HashMap<K, V> innerMap;

    /**
     * Konstruktor.
     * @param innerMap Map to wrap
     */
    public ReadOnlyMapAdaptor( 
            final HashMap<K, V> innerMap )
    {
        this.innerMap = innerMap;
    }

    /**
     * @see ReadOnlyMap#get
     */
    @Override
    public V get( K key )
    {
        return this.innerMap.get( key );
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "ReadOnlyMapAdaptor[innerMap=" + this.innerMap + "]";
    }

}
