package de.heinerkuecker.constraint;

import java.util.Collection;
import java.util.Set;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Eine Gruppe sich ausschliessender Prädikate.
 * 
 * @author Heiner K&uuml;cker
 */
public /*aua, aua, aua wieder eine nicht finale konkrete Klasse TODO evtl mal besser lösen: final*/ class ExcludeGroup<CT>
extends AbstractExcludeGroup<CT>
{
    /**
     * Set mit den sich ausschliessenden Prädikaten
     * 
     * TODO unveränderlich
     */
    /*default*/ final /*HashSet*/ReadOnlySet<PrimitivPredicate<CT>> predicateSet;

    /**
     * Bei closed world assumption wird davon ausgegangen,
     * dass beim Vorliegen aller Prädikate einer Exclude-Group
     * und bei keinem erfüllten Prädikat das gesamte Prädikat
     * nicht erfüllt ist.
     * <br/>
     * Also ein Prädikat der Exclude-Gruppe muss erfüllt sein.
     * <br/>
     * <pre>
     * Testfall:
     *   {@link _03_excludes.ExcludesConstraintCodeGenerator}
     *  
     *   Constraints:
     *     {@link _03_excludes.NOT_RolleFiliale}
     *     {@link _03_excludes.ORB_RolleZentrale_RolleNiederlassung_ORE
     *  
     *  Die Methode
     *  
     *  {@link _03_excludes.NOT_RolleFiliale#convertToORB_RolleZentrale_RolleNiederlassung_ORE}
     *  
     *  wird nut bei vorliegender closed world assumption generiert.
     * </pre>
     * 
     * Falls eine Exclude-Group mit closed world assumption und alternatic
     * mit open world assumption benötigt wird, muss dies jeweils über ein
     * anderes Kontext-Objekt geregelt werden:
     * <pre>
     * Beispiel:
     *   Kontext-Objekt-Type Rolle
     *      die Ebene des Benutzers muss immer vorhanden sein: closed world assumption
     *      
     *   Kontext-Objekt-Type fachliche Entität optional
     *      die fachliche Entität kann fehlen,
     *      der Status der fachlichen Entität unterliegt der open world assumption
     * </pre>
     */
    /*default*/ final boolean isClosedWorldAssumption;

    /**
     * Konstruktor.
     *
     * @param name Name der Gruppe sich ausschliessender Prädikaten
     * @param predicateArr Array mit den sich ausschliessenden Prädikaten
     */
    public ExcludeGroup( 
            final String name , 
            final boolean isClosedWorldAssumption ,
            final PrimitivPredicate<CT>... predicateArr )
    {
        super( name );

        this.isClosedWorldAssumption = isClosedWorldAssumption;

        // das verbieten von Prädikaten mit includierten Prädikaten in closed-world-assumption-ExcludeGroup's war mal zum Lösen des closed-world-assumption-Problems für Kompatibilitäts-Feststellung gedacht, nicht mehr notwendig, anders gelöst
        //if ( isClosedWorldAssumption )
        //{
        //    for ( final PrimitivPredicate<CT> subPredicate : predicateArr )
        //    {
        //        if ( subPredicate.hasIncludes() )
        //        {
        //            throw new IllegalArgumentException(
        //                    "closed world assumption is only usefuel for predicates without inluded predicates " +
        //                    subPredicate );
        //        }
        //    }
        //}

        this.predicateSet =
                new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                //new HashSet<PrimitivPredicate<CT>>(
                        //Arrays.asList(
                                predicateArr );
    }
    
    ///**
    // * Erzeugen der Xor-Klausel für die sich ausschliessenden Prädikate,
    // * 
    // * @param includePredicateColl nur Prädikate, die in dieser Collection enthalten sind, sollen in die XOR-Klausel aufgenommen werden
    // * @return Xor-Klausel
    // */
    //public Xor<CT> getXor(
    //        //final Collection<Predicate<CT>> includePredicateColl
    //        )
    //{
    //    final HashSet<Predicate<CT>> resultSet =
    //            new HashSet<Predicate<CT>>(
    //                    this.predicateSet );
    //    
    //    //resultSet.retainAll( 
    //    //        includePredicateColl );
    //
    //    return new Xor<CT>(
    //            // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
    //            0 ,
    //            (Predicate<CT>[]) resultSet.toArray( new Predicate<?>[ resultSet.size() ] ) );
    //}
    
    ///**
    // * Erzeugen der Or-Klausel für die sich ausschliessenden Prädikate,
    // * 
    // * @param includePredicateColl nur Prädikate, die in dieser Collection enthalten sind, sollen in die XOR-Klausel aufgenommen werden
    // * @return Xor-Klausel
    // */
    //public /*Or*/Predicate<CT> getOr(
    //        //final Collection<Predicate<CT>> includePredicateColl
    //        final Exclude<CT> exclude )
    //{
    //    final HashSet<Predicate<CT>> resultSet =
    //            new HashSet<Predicate<CT>>(
    //                    this.predicateSet );
    //
    //    //resultSet.retainAll( includePredicateColl );
    //
    //    return 
    //            //new Or<CT>(
    //            Or.instanceOf(
    //                    // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
    //                    0 ,
    //                    exclude ,
    //                    (Predicate<CT>[]) resultSet.toArray( new Predicate<?>[ resultSet.size() ] ) );
    //}

    /**
     * Erzeugen der Or-Klausel für die sich ausschliessenden Prädikate,
     * 
     * @param includePredicateColl nur Prädikate, die in dieser Collection enthalten sind, sollen in die XOR-Klausel aufgenommen werden
     * @return Xor-Klausel
     */
    public /*Or*/Predicate<CT> getOr(
            final Collection<PrimitivPredicate<CT>> includePredicateColl ,
            //final Exclude<CT> exclude 
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final Set<PrimitivPredicate<CT>> resultSet =
                //new HashSet<Predicate<CT>>(
                //        this.predicateSet )
                this.predicateSet.toJavaSet();
        
        resultSet.removeAll( 
                includePredicateColl );

        return 
                //new Or<CT>(
                Or.instanceOf(
                        // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
                        0 ,
                        cache ,
                        (Predicate<CT>[]) resultSet.toArray( new Predicate<?>[ resultSet.size() ] ) );
    }

//    /**
//     * Erzeugen der Or-Klausel für die sich ausschliessenden Prädikate,
//     * 
//     * @param excludePredicateColl Prädikate, die in dieser Collection enthalten sind, sollen nicht in die OR-Klausel aufgenommen werden
//     * @return Xor-Klausel
//     */
//    public /*Or*/Predicate<CT> getOneOr(
//            final Collection<PrimitivPredicate<CT>> excludePredicateColl ,
//            //final Exclude<CT> exclude
//            final ConstraintCodeGeneratorCache<CT> cache )
//    {
//        final Set<PrimitivPredicate<CT>> resultSet =
//                //new HashSet<Predicate<CT>>(
//                //        this.predicateSet )
//                this.predicateSet.toJavaSet();
//        
//        resultSet.removeAll(
//                excludePredicateColl );
//
//        if ( resultSet.size() > 0 )
//        {
//            return 
//                    //new Or<CT>(
//                    Or.instanceOf(
//                            // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
//                            0 ,
//                            cache ,
//                            //(Predicate<CT>[]) resultSet.toArray( new Predicate<?>[ resultSet.size() ] )
//                            // für bessere Performance nur ein Prädikat der Exclude-Gruppe verwenden
//                            (Predicate<CT>[]) new Predicate[ resultSet.size() ] );
//        }
//        else
//        {
//            //return 
//            //        //new Or<CT>(
//            //        Or.instanceOf(
//            //                // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
//            //                0 ,
//            //                cache );
//            throw new IllegalArgumentException();
//        }
//    }

	/**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                "ExcludeGroup[" +
                "name=" + name + ", " +
                "predicateSet=" + predicateSet + 
                "]";
    }

    @Override
    public final boolean contains( 
            final PrimitivPredicate<CT> predicateToCheck )
    {
        //if ( predicateToCheck instanceof PrimitivPredicate )
        {
            final boolean result =
                    this.predicateSet.contains( 
                            /*(PrimitivPredicate<CT>)*/ predicateToCheck );

            //return result;
            if ( result )
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        //return false;
    }

}
