package _07_int_range.use;

import _07_int_range.IntRangeConstraintCodeGenerator;
import _07_int_range.constraints.ANDB_IntGreaterMinus10_IntLesser10_ANDE;
import _07_int_range.constraints.ANDB_IntGreaterMinus10_IntLesserZero_ANDE;
import _07_int_range.constraints.ANDB_IntLesser10_IntGreaterZero_ANDE;
import _07_int_range.constraints.IntEqualZero;

/**
 * Anwendungsbeispiele für die genrierten Constraints aus {@link IntRangeConstraintCodeGenerator}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class IntRangeConstraintUse
{
    /**
     * 
     * @param constraint
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    public static void doANDB_IntGreaterMinus10_IntLesser10_ANDE(
//            final ANDB_IntGreaterMinus10_IntLesser10_ANDE constraint )
//    {
//        constraint.new LesserZeroEqualZeroGreaterZeroSwitch()
//        {
//            @Override
//            protected void caseIntLesserZero(
//                    final ANDB_IntGreaterMinus10_IntLesserZero_ANDE constraint )
//            {
//                doANDB_IntGreaterMinus10_IntLesser0_ANDE(
//                        constraint );
//            }
//
//            @Override
//            protected void caseIntEqualZero(
//                    final IntEqualZero constraint )
//            {
//                doIntEqual0(
//                        constraint );
//            }
//
//            @Override
//            protected void caseIntGreaterZero(
//                    final ANDB_IntLesser10_IntGreaterZero_ANDE constraint )
//            {
//                doANDB_IntLesser10_IntGreater0_ANDE(
//                        constraint );
//            }
//
//            //@Override
//            //protected void caseDefault()
//            //{
//            //    // TODO dieser Zweig wird nie durchlaufen, die Generierung wird durch das Exclude unterbunden
//            //    fail();
//            //    
//            //}
//        }.doSwitch();
//    }

    /**
     * @param constraint
     */
    protected static void doANDB_IntGreaterMinus10_IntLesser0_ANDE(
            final ANDB_IntGreaterMinus10_IntLesserZero_ANDE constraint )
    {
        // TODO Auto-generated method stub
    }

    /**
     * @param constraint
     */
    protected static void doIntEqual0(
            final IntEqualZero constraint )
    {
        // TODO Auto-generated method stub
    }

    /**
     * @param constraint
     */
    protected static void doANDB_IntLesser10_IntGreater0_ANDE(
            final ANDB_IntLesser10_IntGreaterZero_ANDE constraint )
    {
        // TODO Auto-generated method stub
    }

}