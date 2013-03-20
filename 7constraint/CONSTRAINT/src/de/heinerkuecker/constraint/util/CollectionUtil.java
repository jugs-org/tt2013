package de.heinerkuecker.constraint.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.heinerkuecker.constraint.util.coll.NoPreviousValueHashSet;
import de.heinerkuecker.constraint.util.coll.NoPreviousValueLinkedHashSet;

/**
 * Hilfsmethoden für Collections.
 * 
 * @author Heiner K&uuml;cker
 */
public final class CollectionUtil
{
    /**
     * Konstruktor private,
     * Instantiieren verboten.
     */
    private CollectionUtil()
    {
        super();
    }
    
    /**
     * TODO
     * 
     * @param <K>
     * @param <V>
     */
    public static class Entry<K, V>
    {
        public final K key;
        public final V value;
        
        /**
         * Konstruktor.
         *
         * @param key
         * @param value
         */
        public Entry( 
                final K key , 
                final V value )
        {
            this.key = key;
            this.value = value;
        }
    }

    public static <K, V> HashMap<K, V> hashmap(
            final Entry<? extends K, ? extends V>... entrieArr )
    {
        final HashMap<K, V> hashMap =
            new HashMap<K, V>();

        for ( Entry<? extends K, ? extends V> entry : entrieArr )
        {
            hashMap.put( 
                    entry.key , 
                    entry.value );
        }

        return hashMap;
    }
    
    public static <K, V> Entry<? extends K, ? extends V> entry(
            final K key , 
            final V value )
    {
        return new Entry<K, V>(
                key ,
                value );
    }

    public static <E> HashSet<? extends E> hashset(
            final E... elementArr )
    {
        final HashSet<E> hashSet =
            new HashSet<E>();

        for ( E element : elementArr )
        {
            hashSet.add( 
                    element );
        }

        return hashSet;
    }
    
    public static <E> HashSet<E> noPrevHashset(
            final E... elementArr )
    {
        final NoPreviousValueHashSet<E> hashSet =
            new NoPreviousValueHashSet<E>();

        for ( E element : elementArr )
        {
            hashSet.add( 
                    element );
        }

        return hashSet;
    }
    
    public static <E> HashSet<E> noPrevLinkedHashset(
            final E... elementArr )
    {
        // Erhaltung der Einfüge-Reihenfolge
        final NoPreviousValueLinkedHashSet<E> hashSet =
            new NoPreviousValueLinkedHashSet<E>();

        for ( final E element : elementArr )
        {
            hashSet.add( 
                    element );
        }

        return hashSet;
    }
    
    /**
     * @param <E>
     * @param objToCheck
     * @param elementArr
     * @return
     * @deprecated use {@link #isInTypesafe}
     */
    public static <E> boolean isIn(
            final E objToCheck ,
            final E... elementArr )
    {
        for ( E element : elementArr )
        {
            if ( element.equals( objToCheck ) )
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param <E>
     * @param objToCheck
     * @param elementArr
     * @return
     * @deprecated use {@link #isNotInTypesafe}
     */
    public static <E> boolean isNotIn(
            final E objToCheck ,
            final E... elementArr )
    {
        return
            false == isIn( 
                objToCheck , 
                elementArr );
    }

    /**
     * Prüfen, ob der übergebene Wert
     * im übergebenen Array enthalten ist.
     * 
     * @param <E> Element-Typ
     * @param arr zu prüfendes Array
     * @param valueToCheck zu suchender Wert
     * @return ob der übergebene Wert
     *         im übergebenen Array enthalten ist
     */
    public static <E> boolean contains( 
            final E[] arr , 
            final E valueToCheck )
    {
        if ( arr == null || arr.length == 0 )
        {
            return false;
        }
        return Arrays.asList( arr ).contains( valueToCheck );
    }

    /**
     * @param <E> Element-Typ
     * @param arr in List umzuwandelndes Array
     * @return {@link Arrays#asList} oder <code>null</code>,
     *         das übergebene Array <code>null</code> ist
     */
    public static <E> List<E> asListNullSafe( 
            E[] arr )
    {
        if ( arr == null )
        {
            return null;
        }
        return Arrays.asList( arr );
    }
    
    /**
     * @param iterableToCount abzuzählendes {@link Iterable}
     * @return Anzahl der Elemente im übergebenen {@link Iterable}
     */
    public static int countElements(
            final Iterable<?> iterableToCount )
    {
        int count = 0;
        for ( @SuppressWarnings("unused") Object obj : iterableToCount )
        {
            count++;
        }
        return count;
    }

    /**
     * Umwandeln der übergebenen
     * Collection in eine
     * String-Collection bestehend aus
     * den in Strings umgewandelten
     * Elementen der übergebenen
     * Collection.
     * 
     * @param noStrColl not-String-Collection
     * @return String-Collection
     */
    public static Collection<String> toStrColl( 
            final /*Collection*/Iterable<?> noStrColl )
    {
        if ( noStrColl == null )
        {
            return null;
        }

        final Collection<String> coll;
        
        //if ( noStrColl instanceof TreeSet )
        //{
        //    TODO das kann nicht funktionieren, der Comparator ist höchstwahrscheinlich für den falschen Typ ausgelegt
        //    coll = new TreeSet<String>( ( (TreeSet) noStrColl ).comparator() );
        //}
        //else 
        if ( noStrColl instanceof Set )
        {
            coll = new HashSet<String>();
        }
        else if ( noStrColl instanceof List )
        {
            coll = 
                new ArrayList<String>( 
                        ( (List<?>) noStrColl ).size() );
        }
        else
        {
            coll = 
                new ArrayList<String>();
        }
        
        for ( final Object obj : noStrColl )
        {
            if ( obj == null )
            {
                coll.add( null );
            }
            else
            {
                coll.add( String.valueOf( obj ) );
            }
        }
        
        return coll;
    }

    /**
     * Methode zum prüfen, ob in übergebenen
     * Collection mindestens ein Element des 
     * anderen Iterable enthalten sind.
     * 
     * @param thisColl in dieser Collection sollen alle Elemente des anderen Iterable enthalten sein
     * @param otherIterable anderes Iterable
     * @return ob mindestens ein Element enthalten
     */
    public static <E> boolean containsMinOne(
            final Collection<E> thisColl ,
            final Iterable<E> otherIterable )
    {
        for ( final E otherElem : otherIterable )
        {
            if ( thisColl.contains( otherElem ) )
            {
                return true;
            }
        }
        
        return false;
    }
}
