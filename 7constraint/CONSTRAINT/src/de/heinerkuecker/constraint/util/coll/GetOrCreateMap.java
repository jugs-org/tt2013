package de.heinerkuecker.constraint.util.coll;

import java.util.Map;

/**
 * 
 * @author Heiner K&uuml;cker
 */
public interface GetOrCreateMap<K, V>
extends Map<K, V>
{
    public V create( final K key );
}
