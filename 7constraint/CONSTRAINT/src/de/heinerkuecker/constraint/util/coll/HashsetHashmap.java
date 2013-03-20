package de.heinerkuecker.constraint.util.coll;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

/**
 * HashMap mit HashsetWithReadonly-Values
 * 
 * @author Heiner K&uuml;cker
 */
public final class HashsetHashmap<K, V>
implements Serializable
{
    private final HashMap<K, HashsetWithReadonly<V>> innerMap = new HashMap<K, HashsetWithReadonly<V>>();

    /**
     * @param key
     * @return
     * @see java.util.HashMap#get(java.lang.Object)
     */
    public HashsetWithReadonly<V> get( 
            final K key )
    {
        return this.innerMap.get( key );
    }

    /**
     * @param key
     * @return
     * @see java.util.HashMap#get(java.lang.Object)
     */
    public int getValueCount( 
            final K key )
    {
        final HashsetWithReadonly<V> valueSet = get( key );
        
        if ( valueSet == null )
        {
            return 0;
        }
        
        return valueSet.size();
    }

    /**
     * @param key
     * @param value
     * @return
     * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
     */
    public HashsetWithReadonly<V> put( 
            final K key , 
            //final HashsetWithReadonly<V> value
            final V value )
    {
        //return this.innerMap.put( key , value );
        
        // Pre- und Post-List getrennt zur Einhaltung des Put-Vertrages (return old entry)
        HashsetWithReadonly<V> preList = this.innerMap.get( key );
        
        final HashsetWithReadonly<V> postList = new HashsetWithReadonly<V>();
        if ( preList != null )
        {
            postList.addAll(
                    preList );
        }
        
        postList.add( value );
        
        return this.innerMap.put( 
                key ,
                postList );
    }

    /**
     * @param key
     * @return
     * @see java.util.HashMap#containsKey(java.lang.Object)
     */
    public boolean containsKey( 
            final K key )
    {
        return this.innerMap.containsKey( key );
    }

    /**
     * 
     * @param key Schlüssel
     * @param value Wert
     * @return ob die übergebene Schlüssel-Wert-Paarung in dieser Collection enthalten ist
     */
    public boolean containsKeyValue(
            final K key ,
            final V value )
    {
        final HashsetWithReadonly<V> valueMap = this.get( key );

        if ( valueMap == null )
        {
            return false;
        }

        return valueMap.contains( value );
    }

    /**
     * Feststellen, ob es eine einfache oder rekursive
     * Verknüpfung zwischen dem übergebenen Key und
     * Value gibt.
     * 
     * @param key Key
     * @param value Value
     * @return Verknüpfung existiert ja/nein
     */
    public boolean isConnected(
            final K key ,
            final V value )
    {
        final Stack<K> stack = new Stack<K>();
        
        return innerIsConnected( 
                key , 
                value , 
                stack );
    }

    /**
     * Feststellen, ob es eine einfache oder rekursive
     * Verknüpfung zwischen dem übergebenen Key und
     * Value gibt.
     * 
     * @param key
     * @param value
     * @param stack
     * @return Verknüpfung existiert ja/nein
     */
    private boolean innerIsConnected(
            final K key ,
            final V value ,
            final Stack<K> stack )
    {
        if ( stack.contains( key ) )
        {
            // verhindere Stackoverflow bei zirkulärer Struktur
            return false;
        }
        final HashsetWithReadonly<V> valueSet = this.get( key );
        
        if ( valueSet == null || valueSet.isEmpty() )
        {
            return false;
        }
        
        if ( valueSet.contains( key ) )
        {
            return true;
        }

        stack.push( key );
        try
        {
            for ( final V subValue : valueSet )
            {
                //if ( value.equals( subValue ) )
                //{
                //    return true;
                //}
                if ( innerIsConnected( 
                        key , 
                        subValue , 
                        stack ) )
                {
                    return true;
                }
            }
            return false;
        }
        finally
        {
            stack.pop();
        }
    }

    /**
     * 
     * @param key
     * @return
     */
    public HashsetWithReadonly<V> getAllConnected(
            final K key )
    {
        final HashsetWithReadonly<V> result = new HashsetWithReadonly<V>();

        final Stack<K> stack = new Stack<K>();

        innerGetAllConnected( 
                key ,
                result ,
                stack );

        return result;
    }

    /**
     * TODO
     * @param key
     * @param result
     * @param stack
     */
    @SuppressWarnings( "unchecked" )
    private void innerGetAllConnected(
            final K key ,
            final HashsetWithReadonly<V> result ,
            final Stack<K> stack )
    {
        if ( stack.contains( key ) )
            // TODO bei tiefen Strukturen ist ein selbstgebauter Stack mit Map/Set performanter
        {
            // verhindere Stackoverflow bei zirkulärer Struktur
            return;
        }
        final HashsetWithReadonly<V> valueSet = this.get( key );

        if ( valueSet == null || valueSet.isEmpty() )
        {
            return;
        }

        result.addAll( valueSet );

        stack.push( key );
        try
        {
            for ( final V subValue : valueSet )
            {
                innerGetAllConnected( 
                        //key
                        (K) subValue ,
                        result ,
                        stack );
            }
        }
        finally
        {
            stack.pop();
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "HashsetHashmap[innerMap=" + this.innerMap + "]";
    }

    /**
     * @return
     * @see java.util.HashMap#keySet()
     */
    public Set<K> keySet()
    {
        return this.innerMap.keySet();
    }
    
    

}
