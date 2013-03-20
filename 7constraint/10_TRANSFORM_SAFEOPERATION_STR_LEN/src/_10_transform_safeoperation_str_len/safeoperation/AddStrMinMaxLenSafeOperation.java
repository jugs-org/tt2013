package _10_transform_safeoperation_str_len.safeoperation;

import java.util.List;

import _10_transform_safeoperation_str_len.predicates.StrIsNotNull;
import _10_transform_safeoperation_str_len.predicates.StrMaxLen;
import _10_transform_safeoperation_str_len.predicates.StrMinLen;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.And;
import de.heinerkuecker.constraint.ConstraintCodeGeneratorCache;
import de.heinerkuecker.constraint.DestinationConstraintSafeOperation;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.TransformDestinationConstraintSafeOperation;

/**
 * Beispiel {@link TransformDestinationConstraintSafeOperation} für
 * die sichere Add-String-Operation {@link String#concat(String)}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class AddStrMinMaxLenSafeOperation
extends TransformDestinationConstraintSafeOperation<String>
{
    /**
     * Minimale Länge des zu addierenden Strings.
     */
    public final int strToAddMinLen;

    /**
     * Maximale Länge des zu addierenden Strings.
     */
    public final int strToAddMaxLen;

    /**
     * Konstruktor.
     * 
     * @param strToAddMinLen minimale Länge des zu addierenden Strings
     * @param strToAddMaxLen maximale Länge des zu addierenden Strings
     */
    public AddStrMinMaxLenSafeOperation( 
            final int strToAddMinLen ,
            final int strToAddMaxLen )
    {
        super( AbstractConstraintCodeGenerator.callerLineNr() );

        if ( strToAddMinLen < 0 )
        {
            throw new IllegalArgumentException(
                    "strToAddMinLen is lesser 0: " +
                            strToAddMinLen );
        }
        
        if ( strToAddMaxLen < strToAddMinLen )
        {
            throw new IllegalArgumentException(
                    "strToAddMaxLen is lesser than strToAddMinLen: " +
                    strToAddMinLen + " " +
                    strToAddMaxLen );
        }

        this.strToAddMinLen = strToAddMinLen;
        this.strToAddMaxLen = strToAddMaxLen;
    }

    /**
     * @see DestinationConstraintSafeOperation#getPremise
     */
    @Override
    public Predicate<String> getPremise(
            final ConstraintCodeGeneratorCache<String> cache )
    {
        return
                new StrIsNotNull();        
    }

    /**
     * @see DestinationConstraintSafeOperation#toJavaCode
     */
    @Override
    public String toJavaCode( 
            final String destinationConstraintClassName ,
            final String otherConstraintClassName ,
            final String contextObjName )
    {
        return
                "\t/**\n" +
                "\t * Sichere String-Add-Operation.\n" +
                "\t *\n" +
                "\t * @param strConstraintToAdd auf minimale Länge constraint-gesicherter zu addierender String\n" +
                "\t * @return constraint-gesicherter String\n" +
                "\t */\n" +
                "\tpublic " + destinationConstraintClassName + " addStrMin" + this.strToAddMinLen + "Max" + this.strToAddMaxLen + "Len(\n" +
                "\t\t\tfinal " + otherConstraintClassName + " strConstraintToAdd )\n" +
                "\t{\n" +
                "\t\treturn new " + destinationConstraintClassName + "( " + contextObjName+ ".concat( strConstraintToAdd.str ) );\n" +
                "\t}\n\n";
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                this.getClass().getSimpleName() + " " +
                "[" +
                "strToAddMinLen=" + this.strToAddMinLen + 
                "]";
    }

    ///**
    // * @see TransformDestinationConstraintSafeOperation#transformPredicate
    // */
    ////@Override TODO Methode entfernen
    //public Predicate<String> transformPredicate( 
    //        final Predicate<String> srcConstraintPredicate ,
    //        final PrimitivPredicate<String> predicateToTransform )
    //{
    //    if ( predicateToTransform instanceof StrMinLen )
    //        // vorherige minimale Länge
    //    {
    //        final int preMinLen = ( (StrMinLen) predicateToTransform ).minLen;
    //
    //        // neue minimale Länge = vorherige minimale Länge + hinzukommende minimale Länge
    //        return
    //                new StrMinLen( 
    //                        preMinLen +
    //                        strToAddMinLen );
    //    }
    //    
    //    if ( predicateToTransform instanceof StrMaxLen )
    //        // vorherige maximale Länge
    //    {
    //        final int preMaxLen = ( (StrMaxLen) predicateToTransform ).maxLen;
    //
    //        // neue maximale Länge = vorherige maximale Länge + hinzukommende maximale Länge
    //        return
    //                new StrMaxLen( 
    //                        preMaxLen +
    //                        strToAddMaxLen );
    //    }
    //    
    //    if ( predicateToTransform instanceof AbstractStrLastCharacter )
    //        // durch das Hinzufügen eines Strings mit einer Länge von mindestens einem Zeichen 
    //        // (Leerstring wird wahrscheinlich niemand anhängen, wäre sinnlos) 
    //        // ist ein vorher vorhandenes Cosntraint für das letzte Zeichen nicht mehr gültig
    //    {
    //        return null;
    //    }
    //
    //    // alle anderen Prädikate werden unverändert übernommen
    //    return predicateToTransform;
    //}

    /**
     * @see TransformDestinationConstraintSafeOperation#getPredicatesToAdd
     */
    @Override
    public List<? extends Predicate<String>> getPredicatesToAdd(
            final int lineNr ,
            final ConstraintCodeGeneratorCache<String> cache )
    {
        return null;
    }

    /**
     * @see AbstractSafeOperation#getOtherParameterPredicate
     */
    @Override
    public Predicate<String> getOtherParameterPredicate(
            final int lineNr ,
            final ConstraintCodeGeneratorCache<String> cache )
    {
        return
                And.instanceOf(
                        lineNr , 
                        cache , 
                        //cleanForImplicits
                        //true ,
                        new StrMinLen(
                                strToAddMinLen ) ,
                        new StrMaxLen( 
                                strToAddMaxLen ) );
    }

}