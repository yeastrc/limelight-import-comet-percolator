package org.yeastrc.limelight.xml.comet_percolator.utils;

import java.math.BigDecimal;

import org.yeastrc.limelight.xml.comet_percolator.objects.CometReportedPeptide;

public class CometPercolatorUtils {

	/**
	 * Number of decimal places comet reports for modifications for percolator peptides
	 */
	public static int MOD_DECIMAL_PLACES = 4;
	
	public static String getPercolatorReportedPeptideForCometPeptide( CometReportedPeptide cometPeptide ) {
		
		String nakedPeptide = cometPeptide.getNakedPeptide();
		StringBuilder percReportedPeptide = new StringBuilder();
		
		for (int i = 0; i < nakedPeptide.length(); i++){
		
			int position = i + 1;
			char c = nakedPeptide.charAt(i);        

			percReportedPeptide.append( c );
			
			if( cometPeptide.getMods() != null && cometPeptide.getMods().containsKey( position ) ) {
				BigDecimal mod = cometPeptide.getMods().get( position );
				mod = mod.setScale( MOD_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
				percReportedPeptide.append( "[" + mod + "]" );
			}
		}
		
		return percReportedPeptide.toString();
	}
	
}
