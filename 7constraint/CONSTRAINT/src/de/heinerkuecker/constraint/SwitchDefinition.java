package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.Arrays;

import de.heinerkuecker.constraint.util.StringUtil;


/**
 * Definition zum Erzeugen
 * einer Switch-Klasse zum
 * Spezialisieren einer
 * Prädikat-Expression.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Type
 * @author Heiner K&uuml;cker
 */
public final class SwitchDefinition<CT>
{
    /**
     * Da eine Prädikat-Expression
     * mehrere Spezialisierungs-Switche
     * haben kann, muss jeder von diesen
     * einen uniken Namen haben.
     */
    public final String name;
    
    /**
     * Return-Typ der zu generierenden Switch-Case-Methoden
     */
    private final String caseMethodReturnTypeName;
    
    /**
     * @return Return-Typ der zu generierenden Switch-Case-Methoden
     * 
     */
    public final String caseMethodReturnTypeName()
    {
        if ( StringUtil.isEmpty( this.caseMethodReturnTypeName ) )
        {
            // aus null oder Leerstring wird void
            return "void";
        }
        return this.caseMethodReturnTypeName;
    }
    
    /**
     * Exceptions der zu generierenden Methoden
     */
    public final String caseMethodExceptionsStr;
    
    /**
     * Array mit spezialisierenden
     * Sub-Prädikaten.
     */
    public final CasePredicateSpecializedPredicateTuple<CT>[] casePredicateTupleArr;
    
    /**
     * Flag, ob die Prädikate des Switch alle
     * Belegungen (Model) des zu switchenden
     * Prädikates überdecken oder nicht.
     * 
     * Wenn nicht alle Belegungen überdeckt
     * werden, muss eine caseDefault-Methode
     * für die nicht überdeckten Belegungen
     * erzeugt werden.
     */
    public /*final*/ boolean isCovering;
    
    /**
     * Klassen-Name des Constraint-Parameters der casDefault-Methode,
     * welche bei
     * <code>{@link #isCovering}==true</code>
     * generiert wird.
     */
    //klappt nicht, weil ich nicht ausschliessen kann, dass in den switch-Prädikaten das zu switchende Prädikat wiederholt wird und damit beim Negieren aller switch-Prädikate für das default-Prädikat eine Kontradiktion entsteht: public /*final*/ String defaultParamPredicateClassName;

    /**
     * Konstruktor.
     *
     * @param name
     * @param caseMethodReturnType 
     * @param casePredicateArr
     */
    SwitchDefinition( 
            final String name , 
            final String caseMethodReturnTypeName ,
            final String caseMethodExceptionsStr ,
            final Exclude<CT> exclude ,
            final Predicate<CT>... casePredicateArr )
    {
        this.name = name;
        this.caseMethodReturnTypeName = caseMethodReturnTypeName;
        this.caseMethodExceptionsStr = caseMethodExceptionsStr;
        
        final ArrayList<CasePredicateSpecializedPredicateTuple<CT>> casePredicateTupleList = new ArrayList<CasePredicateSpecializedPredicateTuple<CT>>();

        //checkDisjointness( 
        //        casePredicateArr ,
        //        exclude );
        
        for ( final Predicate<CT> casePredicate : casePredicateArr )
        {
            casePredicateTupleList.add(
                    new CasePredicateSpecializedPredicateTuple<CT>( 
                            casePredicate ) );
        }
        this.casePredicateTupleArr =
                (CasePredicateSpecializedPredicateTuple<CT>[]) casePredicateTupleList.toArray( 
                        new CasePredicateSpecializedPredicateTuple<?>[ casePredicateTupleList.size() ] );
    }

    /**
     * Prüfen, dass sich die Erfüllungsmengen verschiedener
     * Switch-Case-Prädikate nicht überlappen.
     * Dies würde zu Fehlern durch zufällig zuerst
     * angewendeten Switch-Case-Zweig führen.
     * 
     * @param casePredicateArr
     * @param exclude Exclude
     */
//    private void checkDisjointness( 
//            final Predicate<CT>[] casePredicateArr ,
//            final Exclude<CT> exclude )
//    {
//        for ( int i = 0 ; i < casePredicateArr.length ; i++ )
//        {
//            final Predicate<CT> casePredicate = casePredicateArr[ i ];
//            
//            for ( int j = 0 ; j < casePredicateArr.length ; j++ )
//            {
//                if ( i != j )
//                    // nicht mit sich selbst vergleichen
//                {
//                    final Predicate<CT> otherCasePredicate = casePredicateArr[ j ];
//                    
//                    if ( false == BruteForceSolver/*ConstraintCodeGenerator*/.isDisjoint( 
//                            casePredicate , 
//                            otherCasePredicate
//                            //exclude
//                            ) )
//                    {
//                        throw new IllegalArgumentException(
//                                "predicates not disjoint: " +
//                                 casePredicate.getClass().getSimpleName() + " " +
//                                 otherCasePredicate.getClass().getSimpleName() );
//                    }
//                }
//            }
//        }
//    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ( ( this.caseMethodExceptionsStr == null ) ? 0 : this.caseMethodExceptionsStr
                        .hashCode() );
        result = prime
                * result
                + ( ( this.caseMethodReturnTypeName == null ) ? 0 : this.caseMethodReturnTypeName
                        .hashCode() );
        result = prime * result + Arrays.hashCode( this.casePredicateTupleArr );
        result = prime * result + ( ( this.name == null ) ? 0 : this.name.hashCode() );
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        SwitchDefinition other = (SwitchDefinition) obj;
        if ( this.caseMethodExceptionsStr == null )
        {
            if ( other.caseMethodExceptionsStr != null )
            {
                return false;
            }
        }
        else if ( !this.caseMethodExceptionsStr.equals( other.caseMethodExceptionsStr ) )
        {
            return false;
        }
        if ( this.caseMethodReturnTypeName == null )
        {
            if ( other.caseMethodReturnTypeName != null )
            {
                return false;
            }
        }
        else if ( !this.caseMethodReturnTypeName.equals( other.caseMethodReturnTypeName ) )
        {
            return false;
        }
        if ( !Arrays.equals( this.casePredicateTupleArr , other.casePredicateTupleArr ) )
        {
            return false;
        }
        if ( this.name == null )
        {
            if ( other.name != null )
            {
                return false;
            }
        }
        else if ( !this.name.equals( other.name ) )
        {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                "SwitchDefinition [" +
                "name=" + name + ", " +
                "caseMethodReturnTypeName=" + caseMethodReturnTypeName + ", " +
                "caseMethodExceptionsStr=" + caseMethodExceptionsStr + ", " +
                "casePredicateTupleArr=" + Arrays.toString( casePredicateTupleArr ) + ", " +
                "isCovering=" + isCovering + 
                "]";
    }

}
