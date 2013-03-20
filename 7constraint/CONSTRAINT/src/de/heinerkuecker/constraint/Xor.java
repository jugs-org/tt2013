package de.heinerkuecker.constraint;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.coll.HashsetWithReadonly;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;
import de.heinerkuecker.constraint.util.lang.SeparatedBuffer;

/**
 * ENTWEDER-ODER-Verknüpfung von Prädikaten
 * {@link Predicate}, wobei beliebig viele
 * Prädikate xor-verknüpft werden können.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Type
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache Änderungen an der Klasse erkennt
public final class Xor<CT>
extends Predicate<CT>
implements Iterable<Predicate<CT>>
{
//    /**
//     * Zeilen-Nummer zum besseren Finden der Codestelle,
//     * an welcher das Constraint definiert wurde.
//     */
//    /*default*/ final int lineNr;

    /**
     * FirstInsertOrderHashset zum Absichern, dass ein identisches
     * Prädikat nur einmal aufgeführt wird und Absichern der
     * Erhaltung der Reihenfolge der Prädikate.
     */
    private final FirstInsertOrderHashset<Predicate<CT>> xorPredicateSet;
    
    /**
     * Konstruktor.
     *
     * @param lineNr Zeilen-Nummer
     * @param xorPredicateArr Array mit Prädikaten, welche 
     *                        ENTWEDER-ODER-verknüpft 
     *                        werden sollen
     */
    public Xor( 
            final int lineNr ,
            final Predicate<CT>... xorPredicateArr )
    {
        super( 
                lineNr );
        //this.lineNr = lineNr;

        if ( xorPredicateArr.length < 1 )
        {
            throw new IllegalArgumentException(
                    "xorPredicateArr is empty" );
        }

        if ( xorPredicateArr.length < 2 )
        {
            throw new IllegalArgumentException();
        }

        this.xorPredicateSet = 
            new FirstInsertOrderHashset<Predicate<CT>>();

        //this.xorPredicateSet.addAll(
        //        Arrays.asList(
        //                xorPredicateArr ) );
        for ( final Predicate<CT> xorSubPredicate : xorPredicateArr )
        {
            // avoid null elements
            if ( xorSubPredicate == null )
            {
                throw new IllegalArgumentException(
                        "predicate is null" );
            }

            if ( this.xorPredicateSet.contains( xorSubPredicate ) )
            {
                throw new IllegalArgumentException(
                        "predicate is already in XOR defined, XOR is contradiction(not satisfiable) " +
                        xorSubPredicate );
            }

            xorPredicateSet.add(
                    xorSubPredicate );
        }
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( 
            final Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        final Xor<?> other = (Xor<?>) obj;
        return this.xorPredicateSet.equals( other.xorPredicateSet );
    }

    /**
     * hash code cache.
     */
    private int hashCode;
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        if ( this.hashCode == 0 )
        {
            this.hashCode = this.xorPredicateSet.hashCode();
        }
        return this.hashCode;
    }

    /**
     * @see java.lang.Object#toString()
     */
    private String toStrCache;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        if ( this.toStrCache == null )
        {
            //return "Or:" + this.orPredicateSet.toString();
            final SeparatedBuffer sepBuff = new SeparatedBuffer( " xor " );
            for ( final Predicate<CT> predicate : this.xorPredicateSet )
            {
                sepBuff.add( predicate.toString() );
            }

            this.toStrCache = "(" + sepBuff + ")";
        }
        return this.toStrCache;
    }

    /**
     * @see Predicate#test(java.lang.Object)
     */
    @Override
    public boolean test( 
            final CT contextObj )
    //throws Exception
    {
        int count = 0;
        for ( final Predicate<CT> predicate : this.xorPredicateSet )
        {
            if ( predicate.test( contextObj ) )
                // mindestens ein Prädikats-Test ist erfolgreich
            {
                count++;
            }
        }

        return count == 1;
    }

    /**
     * @see Predicate#toFileName(java.lang.String)
     */
    @Override
    public String toFileName( 
            final String separatorStr )
    {
        final SeparatedBuffer sepBuff = new SeparatedBuffer( separatorStr );
        
        // XOR-Begin
        sepBuff.add( "XORB" );
        
        for ( final Predicate<CT> predicate : this.xorPredicateSet )
        {
            sepBuff.add( 
                    predicate.toFileName( 
                            separatorStr ) );
        }

        // XOR-End
        sepBuff.add( "XORE" );
        
        return sepBuff.toString();
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Predicate<CT>> iterator()
    {
        return this.xorPredicateSet.iterator();
    }

    /**
     * @return Anzahl Unter-Prädikate
     */
    public int size()
    {
        return this.xorPredicateSet.size();
    }
    
    /**
     * Zurückgeben eines bestimmten Unter-Prädikats.
     * 
     * @param index Position des gewünschten Unter-Prädikates (Basis 0)
     * @return gewünschtes Unter-Prädikate
     */
    public Predicate<CT> get(
            final int index )
    {
        return this.xorPredicateSet.get( 
                index );
    }

    /**
     * @param otherPredicate zu prüfendes Prädikat
     * @return ob enthalten
     * @see java.util.Collection#contains(Object)
     */
    public boolean contains(
            final Predicate<CT> otherPredicate )
    {
        return this.xorPredicateSet.contains( 
                otherPredicate );
    }

    /**
     * @see Predicate#isPrimitive()
     */
    @Override
    public boolean isPrimitive()
    {
        return false;
    }

    /**
     * @see Predicate#replacePrimitivePredicatesWithFalse
     */
    @Override
    public Predicate<CT> replacePrimitivePredicatesWithFalse(
            final HashSet<PrimitivPredicate<CT>> predicateSetToRemove , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        throw new UnsupportedOperationException();
    }

    ///**
    // * @see Predicate#replacePrimitivePredicatesWithTrue
    // */
    //@Override
    //public Predicate<CT> replacePrimitivePredicatesWithTrue(
    //        final HashSet<PrimitivPredicate<CT>> predicateSetToRemove , 
    //        final Exclude<CT> exclude )
    //{
    //    throw new UnsupportedOperationException();
    //}

    /**
     * @see Predicate#replacePredicatesWithTrue
     */
    @Override
    public Predicate<CT> replacePredicatesWithTrue(
            final HashSet<Predicate<CT>> predicateSetToRemove , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see Predicate#getPredicateListFromAndNotNegated(java.lang.Class)
     */
    @Override
    public final List<PrimitivPredicate<CT>> getPredicateListFromAndNotNegated(
            final Class<? extends PrimitivPredicate<CT>> predicateClass )
    {
        return null;
    }

    /**
     * @see Predicate#getIncludePredicateSetRecursiv
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getIncludePredicateSetRecursiv()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see Predicate#getIncludePredicateSetRecursivInclusiveSelf
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getIncludePredicateSetRecursivInclusiveSelf()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Cache für {@link #getPrimitivPredicateSet}
     */
    private ReadOnlySet<PrimitivPredicate<CT>> primitivPredicateSet;

    /**
     * @see Predicate#getPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getPrimitivPredicateSet()
    {
        if ( this.primitivPredicateSet == null )
        {
            final HashsetWithReadonly<PrimitivPredicate<CT>> result =
                    new HashsetWithReadonly<PrimitivPredicate<CT>>();

            for ( final Predicate<CT> subPredicate : this )
            {
                result.addAll(
                        subPredicate.getPrimitivPredicateSet() );
            }

            this.primitivPredicateSet =
                    new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                            result );

        }
        return this.primitivPredicateSet;
    }

    /**
     * @see Predicate#hasNotSubPred
     */
    @Override
    public final boolean hasNotSubPred()
    {
        // Xor wird immer auf And-Not abgebildet
        return true;
    }

    /**
     * @see Predicate#getMandantoryPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getMandantoryPrimitivPredicateSet()
    {
        // im Solver soll Xor nicht mehr auftauchen
        throw new UnsupportedOperationException();
    }

    /**
     * @see Predicate#getNegatedMandantoryPrimitivPredicateSet
     */
    @Override
    public ReadOnlySet<PrimitivPredicate<CT>> getNegatedMandantoryPrimitivPredicateSet()
    {
        // im Solver soll Xor nicht mehr auftauchen
        throw new UnsupportedOperationException();
    }

    /**
     * @see Predicate#getNonNegatedPrimitivPredicateSet
     */
    @Override
    public ReadOnlySet<PrimitivPredicate<CT>> getNonNegatedPrimitivPredicateSet()
    {
        // im Solver soll Xor nicht mehr auftauchen
        throw new UnsupportedOperationException();
    }

    /**
     * @see Predicate#hasOr
     */
    @Override
    public boolean hasOr()
    {
        return true;
    }

    /**
     * @see Predicate#hasNot
     */
    @Override
    public boolean hasNot()
    {
        return true;
    }

}
