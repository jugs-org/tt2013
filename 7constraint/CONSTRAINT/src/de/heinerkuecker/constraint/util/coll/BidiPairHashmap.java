package de.heinerkuecker.constraint.util.coll;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Diese Klasse realisiert eine
 * Map, welche Paare unabhängig
 * von der Richtung der Verknüpfung
 * speichert.
 * 
 * @author Heiner K&uuml;cker
 */
public final class BidiPairHashmap<E>
implements Serializable
{
    /**
     * innere Map.
     */
    private final HashsetHashmap<E, E> innerMap =
            new HashsetHashmap<E, E>();

    /**
     * Add.
     * @param key
     * @param value
     */
    public void add(
            final E key ,
            final E value )
    {
        innerMap.put( key , value );
        //// auch in der umgekehrten Richtung vermerken
        //innerMap.put( value , key );
    }

    public boolean contains(
            final E key ,
            final E value )
    {
        final HashSet<E> valueSet =
                this.innerMap.get( 
                        key );
        
        if ( valueSet != null &&
                valueSet.contains( value ) )
        {
            return true;
        }

        // key und value vertauscht versuchen
        final HashSet<E> reverseValueSet =
                this.innerMap.get( 
                        value );
        
        return reverseValueSet != null &&
                reverseValueSet.contains( 
                        key );
    }
    
}
