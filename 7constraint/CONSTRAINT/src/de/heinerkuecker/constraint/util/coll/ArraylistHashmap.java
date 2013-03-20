package de.heinerkuecker.constraint.util.coll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

/**
 * HashMap mit ArrayList-Values
 * 
 * @author Heiner K&uuml;cker
 */
public final class ArraylistHashmap<K, V>
implements Serializable
{
    private final HashMap<K, ArrayList<V>> innerMap = new HashMap<K, ArrayList<V>>();

    /**
     * @param key
     * @return
     * @see java.util.HashMap#get(java.lang.Object)
     */
    public ArrayList<V> get( 
            final K key )
    {
        return this.innerMap.get( key );
    }

    /**
     * @param key
     * @param value
     * @return
     * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
     */
    public ArrayList<V> put( 
            final K key , 
            //final ArrayList<V> value
            final V value )
    {
        //return this.innerMap.put( key , value );
        
        // Pre- und Post-List getrennt zur Einhaltung des Put-Vertrages (return old entry)
        ArrayList<V> preList = this.innerMap.get( key );
        
        final ArrayList<V> postList = new ArrayList<V>();
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

        final ArrayList<V> valueList = this.get( key );

        if ( valueList == null || valueList.isEmpty() )
        {
            return false;
        }

        stack.push( key );
        try
        {
            for ( final V subValue : valueList )
            {
                if ( value.equals( subValue ) )
                {
                    return true;
                }
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "ArraylistHashmap[innerMap=" + this.innerMap + "]";
    }

    /**
     * @return a {@link Collection} view of the values contained in this map
     * @sse {@link java.util.Map#values}
     */
    public Collection<ArrayList<V>> values()
    {
        return this.innerMap.values();
    }

    /**
     * @return <tt>true</tt> if this map contains no key-value mappings
     * @see java.util.HashMap#isEmpty()
     */
    public boolean isEmpty()
    {
        return this.innerMap.isEmpty();
    }

    /**
     * @return a {@link Set} view of the keys contained in this map
     * @see java.util.HashMap#keySet()
     */
    public Set<K> keySet()
    {
        return this.innerMap.keySet();
    }

}
