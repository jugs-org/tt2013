package de.heinerkuecker.constraint.util.coll;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Heiner K&uuml;cker
 */
abstract public class GetOrCreateHashmap<K, V>
extends HashMap<K, V>
implements GetOrCreateMap<K, V>
{

    /**
     * Konstruktor.
     */
    public GetOrCreateHashmap()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * Konstruktor.
     * @param initialCapacity
     */
    public GetOrCreateHashmap( int initialCapacity )
    {
        super( initialCapacity );
        // TODO Auto-generated constructor stub
    }

    /**
     * Konstruktor.
     * @param m
     */
    public GetOrCreateHashmap( Map m )
    {
        super( m );
        // TODO Auto-generated constructor stub
    }

    /**
     * Konstruktor.
     * @param initialCapacity
     * @param loadFactor
     */
    public GetOrCreateHashmap( int initialCapacity , float loadFactor )
    {
        super( initialCapacity , loadFactor );
        // TODO Auto-generated constructor stub
    }

    /**
     * @see java.util.HashMap#get(java.lang.Object)
     */
    @Override
    public final V get( 
            final Object key )
    {
        V tmpValue = super.get( key );
        
        if ( tmpValue == null )
        {
            tmpValue =
                    create(
                            (K) key );

            this.put(
                    (K) key ,
                    tmpValue );
        }

        return tmpValue;
    }

}
