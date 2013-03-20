package de.heinerkuecker.constraint.util.coll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Diese Klasse ähnelt
 * {@link LinkedHashSet},
 * arbeitet aber im Gegensatz zu dieser
 * mit der first insertion order
 * (Reihenfolge des ersten Einfügens)
 * statt re-insertion-order
 * (Reihenfolge des letzten Einfügens).
 * 
 * @author Heiner K&uuml;cker
 */
public final class FirstInsertOrderHashset<E>
implements List<E>, Set<E>, ReadOnlySet<E>, Serializable
{
    private final ArrayList<E> list = new ArrayList<E>();
    
    private final HashMap<E, Integer> map = new HashMap<E, Integer>();
    
    /**
     * Konstruktor.
     */
    public FirstInsertOrderHashset()
    {
        super();
    }

    /**
     * Konstruktor.
     * 
     * @param iterToInitialize Iterator mit den initial zu vermerkenden Elementen
     */
    public FirstInsertOrderHashset(
            final Iterable<? extends E> iterToInitialize )
    {
        for ( final E e : iterToInitialize )
        {
            add( e );
        }
    }

    /**
     * @see java.util.List#add(java.lang.Object)
     */
    @Override
    public boolean add( 
            final E e )
    {
        if ( this.map.containsKey( e ) )
        {
            return false;
        }
        
        // die vorherige Listengrösse entspricht der neuen letzten Position (alte letzte Position + 1)
        final int preListSize = this.list.size();

        this.list.add( e );

        this.map.put(
                e ,
                preListSize );

        return true;
    }

    /**
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public void add( 
            final int index , 
            final E element )
    {
        //if ( this.map.containsKey( element ) )
        //{
        //    return;
        //}
        //this.list.add( index , element );
        //hier müssen alle Positionen ab der Einfüge-Position korrigiert werden: this.set.add( element );
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.List#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll( 
            final Collection<? extends E> c )
    {
        boolean changed = false;
        
        for ( E e : c )
        {
            changed |= add( e );
        }
        
        return changed;
    }

    /**
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    @Override
    public boolean addAll( 
            int index , 
            final Collection<? extends E> c )
    {
        // Code von java.util.AbstractList hierher kopiert
        boolean modified = false;
        Iterator<? extends E> e = c.iterator();

        while (e.hasNext())
        {
            add(index++, e.next());
            modified = true;
        }
        return modified;
    }

    /**
     * @see java.util.List#clear()
     */
    @Override
    public void clear()
    {
        this.list.clear();
        this.map.clear();
    }

    /**
     * @see java.util.List#contains(java.lang.Object)
     */
    @Override
    public boolean contains( 
            final Object o )
    {
        return this.map.containsKey( o );
    }

    /**
     * @see java.util.List#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll( 
            final Collection<?> c )
    {
        return this.map.keySet().containsAll( c );
    }

    /**
     * @see java.util.List#get(int)
     */
    @Override
    public E get( 
            final int index )
    {
        return this.list.get( index );
    }

    /**
     * @see java.util.List#indexOf(java.lang.Object)
     */
    @Override
    public int indexOf( 
            final Object o )
    {
        return
                //this.list.indexOf( o )
                this.map.get( o ).intValue();
    }

    /**
     * @see java.util.List#isEmpty()
     */
    @Override
    public boolean isEmpty()
    {
        return this.list.isEmpty();
    }

    /**
     * @see java.util.List#iterator()
     */
    @Override
    public Iterator<E> iterator()
    {
        return this.list.iterator();
    }

    /**
     * @see java.util.List#lastIndexOf(java.lang.Object)
     */
    @Override
    public int lastIndexOf( 
            final Object o )
    {
        return this.indexOf( o );
    }

    /**
     * @see java.util.List#listIterator()
     */
    @Override
    public ListIterator<E> listIterator()
    {
        return this.list.listIterator();
    }

    /**
     * @see java.util.List#listIterator(int)
     */
    @Override
    public ListIterator<E> listIterator( 
            final int index )
    {
        return this.list.listIterator( index );
    }

    /**
     * @see java.util.List#remove(java.lang.Object)
     */
    @Override
    public boolean remove( 
            final Object o )
    {
        //if ( false == this.map.containsKey( o ) )
        //{
        //    return false;
        //}
        //final int pos = this.map.remove( o ).intValue();
        //hier müssen alle Positionen ab der Einfüge-Position korrigiert werden: this.list.remove( pos );
        //return pos;
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.List#remove(int)
     */
    @Override
    public E remove( 
            final int index )
    {
        //final E elem = this.list.remove( index );
        //hier müssen alle Positionen ab der Einfüge-Position korrigiert werden: this.map.remove( elem );
        //return elem;
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.List#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll( 
            final Collection<?> c )
    {
        boolean changed = false;
        
        for ( Object o : c )
        {
            changed |= remove( o );
        }
        
        return changed;
    }

    /**
     * @see java.util.List#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll( 
            final Collection<?> c )
    {
        //hier müssen alle Positionen ab der Einfüge-Position korrigiert werden: return this.set.retainAll( c );
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public E set( 
            final int index , 
            final E element )
    {
        //final E preElem = this.list.set( index , element );
        //hier müssen alle Positionen ab der Einfüge-Position korrigiert werden:
        //this.set.remove( preElem );
        //this.set.add( element );
        //return preElem;
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.List#size()
     */
    @Override
    public int size()
    {
        return this.list.size();
    }

    /**
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<E> subList( 
            final int fromIndex , 
            final int toIndex )
    {
        return this.list.subList( fromIndex , toIndex );
    }

    /**
     * @see java.util.List#toArray()
     */
    @Override
    public Object[] toArray()
    {
        return this.list.toArray();
    }

    /**
     * @see java.util.List#toArray(T[])
     */
    @Override
    public <T> T[] toArray( T[] a )
    {
        return this.list.toArray( a );
    }
    
    /**
     * Cache für {@link #hashCode}
     */
    private int hashCodeCache;

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ( ( this.list == null ) ? 0 : this.list.hashCode() );
//        result = prime * result + ( ( this.set == null ) ? 0 : this.set.hashCode() );
//        return result;
        // die innere Liste kann nicht null sein und der Vergleich der Liste sichert den Vergleich der Reihenfolge und aller Elemente ab
        if ( this.hashCodeCache == 0 )
        {
            // Cache setzen, wenn jemals einmal hashCode aufgerufen wurde (Verwendung dieses Objekt in HasSet/-Map) darf sich der hashCode sowieso nicht mehr ändern
            this.hashCodeCache = this.list.hashCode();
        }
        
        return this.hashCodeCache;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        FirstInsertOrderHashset<?> other = (FirstInsertOrderHashset<?>) obj;
//        if ( this.list == null )
//        {
//            if ( other.list != null )
//                return false;
//        }
//        else 
        // die innere Liste kann nicht null sein und der Vergleich der Liste sichert den Vergleich der Reihnefolge und aller Elemente ab
        if ( !this.list.equals( other.list ) )
            return false;
//        if ( this.set == null )
//        {
//            if ( other.set != null )
//                return false;
//        }
//        else if ( !this.set.equals( other.set ) )
//            return false;
        return true;
    }

    /**
     * Vergleich ohne Beachtung Reihenfolge (Sequence).
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equalsSet(
            final FirstInsertOrderHashset<E> other )
    {
       return this.map.keySet().equals( other.map.keySet() ); 
    }

    /**
     * Vergleich mit Beachtung Reihenfolge (Sequence).
     * @param other zu vergleichendes anderes Objekt
     * @return ob Inhalt und Reihenfolge gleich
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equalsList(
            final FirstInsertOrderHashset<E> other )
    {
       return this.list.equals( other.list ); 
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                this.getClass().getSimpleName() + ":" +
                this.list;
    }

    /**
     * Achtung, diese Methode kann bei Verändern des Set ausserhalb dieser Klasse zum Fehler führen
     * @return set
     */
    public Set<E> getSet()
    {
        return this.map.keySet();
    }

    /**
     * @see ReadOnlySet#toJavaSet()
     */
    @Override
    public Set<E> toJavaSet()
    {
        return this.getSet();
    }

    /**
     * @see ReadOnlySet#containsOnly
     */
    @Override
    public boolean containsOnly(
            final ReadOnlySet<? extends E> setToCheck )
    {
        if ( this.size() > setToCheck.size() )
        {
            return false;
        }

        for ( final E eToCheck : setToCheck )
        {
            if ( false == this.contains( eToCheck ) )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * @see ReadOnlySet#containsMinOneOf
     */
    @Override
    public boolean containsMinOneOf(
            final Iterable<? extends E> iterableToCheck )
    {
        for ( final E eToCheck : iterableToCheck )
        {
            if ( this.contains( eToCheck ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @see ReadOnlySet#containsAllFromIterable
     */
    @Override
    public boolean containsAllFromIterable( 
            final Iterable<? extends E> c )
    {
        for ( final E eToCheck : c )
        {
            if ( false == this.contains( eToCheck ) )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * @see ReadOnlySet#equalsByElements
     */
    @Override
    public boolean equalsByElements( 
            final Set<? extends E> setToCheck )
    {
        if ( this.size() != setToCheck.size() )
        {
            return false;
        }
        return this.containsAll( setToCheck );
    }

}
