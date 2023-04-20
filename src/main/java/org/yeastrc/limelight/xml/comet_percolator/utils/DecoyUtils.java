package org.yeastrc.limelight.xml.comet_percolator.utils;

import net.systemsbiology.regis_web.pepxml.AltProteinDataType;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis;

public class DecoyUtils {

    /**
     * Return true if this searchHit is a decoy. This means that it only matches
     * decoy proteins.
     *
     * @param searchHit
     * @return
     */
    public static boolean searchHitIsDecoy(MsmsPipelineAnalysis.MsmsRunSummary.SpectrumQuery.SearchResult.SearchHit searchHit, String decoyPrefix ) {

        if(decoyPrefix == null) { return false; }

        String protein = searchHit.getProtein();
        if( protein.startsWith(decoyPrefix ) ) {

            if( searchHit.getAlternativeProtein() != null ) {
                for( AltProteinDataType ap : searchHit.getAlternativeProtein() ) {
                    if( !ap.getProtein().startsWith(decoyPrefix ) ) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Return true if this searchHit is an independent decoy. This means that it only matches
     * independent decoy proteins.
     *
     * @param searchHit
     * @return
     */
    public static boolean searchHitIsIndependentDecoy(MsmsPipelineAnalysis.MsmsRunSummary.SpectrumQuery.SearchResult.SearchHit searchHit, String independentDecoyPrefix ) {

        if(independentDecoyPrefix == null) { return false; }

        String protein = searchHit.getProtein();
        if( protein.startsWith(independentDecoyPrefix ) ) {

            if( searchHit.getAlternativeProtein() != null ) {
                for( AltProteinDataType ap : searchHit.getAlternativeProtein() ) {
                    if( !ap.getProtein().startsWith(independentDecoyPrefix ) ) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }
}
