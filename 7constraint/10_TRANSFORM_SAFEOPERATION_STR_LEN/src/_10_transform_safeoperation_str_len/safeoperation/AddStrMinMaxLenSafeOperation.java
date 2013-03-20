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
 * Beispiel {@link TransformDestinationConstraintSafeOperation} f�r
 * die sichere Add-String-Operation {@link String#concat(String)}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class AddStrMinMaxLenSafeOperation
extends TransformDestinationConstraintSafeOperation<String>
{
    /**
     * Minimale L�nge des zu addierenden Strings.
     */
    public final int strToAddMinLen;

    /**
     * Maximale L�nge des zu addierenden Strings.
     */
    public final int strToAddMaxLen;

    /**
     * Konstruktor.
     * 
     * @param strToAddMinLen minimale L�nge des zu addierenden Strings
     * @param strToAddMaxLen maximale L�nge des zu addierenden Strings
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
                "\t * @param strConstraintToAdd auf minimale L�nge constraint-gesicherter zu addierender String\n" +
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
    //        // vorherige minimale L�nge
    //    {
    //        final int preMinLen = ( (StrMinLen) predicateToTransform ).minLen;
    //
    //        // neue minimale L�nge = vorherige minimale L�nge + hinzukommende minimale L�nge
    //        return
    //                new StrMinLen( 
    //                        preMinLen +
    //                        strToAddMinLen );
    //    }
    //    
    //    if ( predicateToTransform instanceof StrMaxLen )
    //        // vorherige maximale L�nge
    //    {
    //        final int preMaxLen = ( (StrMaxLen) predicateToTransform ).maxLen;
    //
    //        // neue maximale L�nge = vorherige maximale L�nge + hinzukommende maximale L�nge
    //        return
    //                new StrMaxLen( 
    //                        preMaxLen +
    //                        strToAddMaxLen );
    //    }
    //    
    //    if ( predicateToTransform instanceof AbstractStrLastCharacter )
    //        // durch das Hinzuf�gen eines Strings mit einer L�nge von mindestens einem Zeichen 
    //        // (Leerstring wird wahrscheinlich niemand anh�ngen, w�re sinnlos) 
    //        // ist ein vorher vorhandenes Cosntraint f�r das letzte Zeichen nicht mehr g�ltig
    //    {
    //        return null;
    //    }
    //
    //    // alle anderen Pr�dikate werden unver�ndert �bernommen
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