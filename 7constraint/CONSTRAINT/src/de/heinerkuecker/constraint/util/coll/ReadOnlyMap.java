package de.heinerkuecker.constraint.util.coll;

/**
 * Interface f�r eine unver�nderliche Map.
 * 
 * @author Heiner K&uuml;cker
 */
public interface ReadOnlyMap<K, V>
{
    public V get(
            final K key );

}
