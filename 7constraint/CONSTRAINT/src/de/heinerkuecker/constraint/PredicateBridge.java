package de.heinerkuecker.constraint;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.heinerkuecker.constraint.util.CcgReflectionUtil;
import de.heinerkuecker.constraint.util.coll.TwoDimHashMap;
import de.heinerkuecker.constraint.util.exc.UnreachableCodeException;

/**
 * Diese Klasse vermerkt einander entsprechende Prädikate
 * mit verschiedenen Kontext-Objekt-Typen.
 * <br/>
 * Sie dient dazu, für {@link OtherContextTypeDestinationConstraintSafeOperation} die
 * jeweils passenden Prädikate mit den Ziel-Kontext-Objekt-Typen
 * zu finden.
 * 
 * @author Heiner K&uuml;cker
 */
public final class PredicateBridge
{
    private final TwoDimHashMap<Type, PrimitivPredicate<?>, PrimitivPredicate<?>> map =
            new TwoDimHashMap<Type, PrimitivPredicate<?>, PrimitivPredicate<?>>();

    public void add(
            final PrimitivPredicate<?> pred1 ,
            final PrimitivPredicate<?> pred2 )
    {
        // Verknüpfung ist bidirektional, beide Richtungen in Map vermerken
        final Type type1 = contextObjTypeOf( pred1 );

        final Type type2 = contextObjTypeOf( pred2 );

        this.map.put( 
                //key1
                type1 , 
                //key2
                pred2 , 
                //value
                pred1 );

        this.map.put( 
                //key1
                type2 , 
                //key2
                pred1 , 
                //value
                pred2 );
    }

    /**
     * Ermtittlung Typ-Parameter per Reflection
     * 
     * <a href="http://openbook.galileocomputing.de/javainsel/javainsel_09_006.html#dodtpd9bd7d67-99c3-4603-bc32-f30b8add83a9">Galileo Computing / &lt;openbook&gt; / Java ist auch eine Insel  9 Generics, 9.6 Konsequenzen der Typlöschung: Typ-Token, Arrays und Brücken, 9.6.2 Super-Type-Token</a>
     * 
     * @param pred Prädikat, dessen Typ-Parameter zurückgegeben werden sollen
     * @return Typ-Parameter des übergebenen Objekts
     */
    /*default*/ static Type contextObjTypeOf(
            final PrimitivPredicate<?> pred )
    {
        return CcgReflectionUtil.typeParamArrOfObj( pred )[ 0 ];
    }

    /**
     * Get entsprechendes Prädikat für Ziel-Kontext-Objekt-Typ
     * @param srcPred ursprüngliches Prädikat
     * @param dstCtxType Ziel-Kontext-Objekt-Typ
     * @return entsprechendes Prädikat für Ziel-Kontext-Objekt-Typ
     */
    private <T extends Type> PrimitivPredicate<T> get(
            final PrimitivPredicate<?> srcPred ,
            final T dstCtxType )
    {
        return 
                (PrimitivPredicate<T>) this.map.get(
                        dstCtxType ,
                        srcPred );
    }

    /*default*/ <T extends Type> Predicate<T> convert(
            final Predicate<?> predToConvert ,
            final T dstCtxType ,
            //final Exclude<?> dstExclude ,
            final ConstraintCodeGeneratorCache<?> dstCache )
    {
        // TODO extra Interface für Konvertierung ParametricalPredicate machen
        //if ( predToConvert instanceof ParametricalPredicate )
        //{
        //    throw new IllegalArgumentException(
        //            "convert method not implemented for ParametricalPredicate " +
        //            predToConvert );
        //}
        if ( predToConvert instanceof PrimitivPredicate )
        {
            return
                    (Predicate<T>) map.get(
                            dstCtxType ,
                            (PrimitivPredicate) predToConvert );
        }
        if ( predToConvert instanceof And )
        {
            return convertAnd(
                    (And<?>) predToConvert ,
                    dstCtxType ,
                    //(Exclude<T>) dstExclude ,
                    (ConstraintCodeGeneratorCache<T>) dstCache );
        }
        if ( predToConvert instanceof Or )
        {
            return convertOr(
                    (Or<?>) predToConvert ,
                    dstCtxType ,
                    //(Exclude<T>) dstExclude ,
                    (ConstraintCodeGeneratorCache<T>) dstCache );
        }
        if ( predToConvert instanceof Not )
        {
            return Not.instanceOf(
                    // lineNr
                    0 ,
                    convert(
                            ( (Not<?>) predToConvert ).predicateToNegate ,
                            dstCtxType ,
                            //dstExclude ,
                            dstCache ) );
        }
        if ( predToConvert instanceof Xor )
        {
            return convertXor(
                    (Xor<?>) predToConvert ,
                    dstCtxType ,
                    //(Exclude<T>) dstExclude ,
                    (ConstraintCodeGeneratorCache<T>) dstCache );
        }
        throw new UnreachableCodeException(
                String.valueOf(
                        predToConvert ) );
    }

    /**
     * 
     * @param predToConvert
     * @param dstCtxType
     * @param dstExclude
     * @return
     */
    private <T extends Type> Predicate<T> convertAnd(
            final And<?> predToConvert ,
            final T dstCtxType ,
            //final Exclude<T> dstExclude ,
            final ConstraintCodeGeneratorCache<T> dstCache )
    {
        final List<Predicate<T>> dstPredList = new ArrayList<Predicate<T>>();

        for ( final Predicate<?> subPredToConvert : predToConvert )
        {
            dstPredList.add(
                    convert(
                            subPredToConvert ,
                            dstCtxType ,
                            //dstExclude ,
                            dstCache ) );
        }

        return And.instanceOf(
                //lineNr
                0 ,
                //dstExclude ,
                dstCache ,
                //cleanForImplicits
                //true ,
                dstPredList.toArray( new Predicate[ dstPredList.size() ] ) );
    }

    /**
     * 
     * @param predToConvert
     * @param dstCtxType
     * @param dstExclude
     * @return
     */
    private <T extends Type> Predicate<T> convertOr(
            final Or<?> predToConvert ,
            final T dstCtxType ,
            //final Exclude<T> dstExclude ,
            final ConstraintCodeGeneratorCache<T> dstCache )
    {
        final List<Predicate<T>> dstPredList = new ArrayList<Predicate<T>>();

        for ( final Predicate<?> subPredToConvert : predToConvert )
        {
            dstPredList.add(
                    convert(
                            subPredToConvert ,
                            dstCtxType ,
                            //dstExclude ,
                            dstCache ) );
        }

        return Or.instanceOf(
                //lineNr
                0 ,
                dstCache ,
                dstPredList.toArray( new Predicate[ dstPredList.size() ] ) );
    }

    /**
     * 
     * @param predToConvert
     * @param dstCtxType
     * @param dstExclude
     * @return
     */
    private <T extends Type> Predicate<T> convertXor(
            final Xor<?> predToConvert ,
            final T dstCtxType ,
            //final Exclude<T> dstExclude ,
            final ConstraintCodeGeneratorCache<T> dstCache )
    {
        final List<Predicate<T>> dstPredList = new ArrayList<Predicate<T>>();

        for ( final Predicate<?> subPredToConvert : predToConvert )
        {
            dstPredList.add(
                    convert(
                            subPredToConvert ,
                            dstCtxType ,
                            //dstExclude ,
                            dstCache ) );
        }

        return new Xor(
                //lineNr
                0 ,
                dstPredList.toArray( new Predicate[ dstPredList.size() ] ) );
    }

}
