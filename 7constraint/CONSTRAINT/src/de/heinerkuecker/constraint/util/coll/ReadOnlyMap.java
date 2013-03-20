package de.heinerkuecker.constraint.util.coll;

/**
 * Interface für eine unveränderliche Map.
 * 
 * @author Heiner K&uuml;cker
 */
public interface ReadOnlyMap<K, V>
{
    public V get(
            final K key );

}
