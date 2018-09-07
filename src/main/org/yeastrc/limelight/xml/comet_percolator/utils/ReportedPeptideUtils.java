package org.yeastrc.limelight.xml.comet_percolator.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.yeastrc.limelight.xml.comet_percolator.objects.CometPSM;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometReportedPeptide;

public class ReportedPeptideUtils {

	public static CometReportedPeptide getTPPReportedPeptideForTPPPSM( CometPSM psm ) throws Exception {
		
		CometReportedPeptide rp = new CometReportedPeptide();
		
		rp.setNakedPeptide( psm.getPeptideSequence() );
		rp.setMods( psm.getModifications() );
		rp.setReportedPeptideString( getReportedPeptideStringForSequenceAndMods( psm.getPeptideSequence(), psm.getModifications() ));

		return rp;
	}
	
	public static String getReportedPeptideStringForSequenceAndMods( String sequence, Map<Integer, BigDecimal> mods ) throws Exception {
		
		for( int position : mods.keySet() ) {
			if( position < 0 || position > sequence.length() )
				throw new Exception( "Position " + position + " is not in the peptide sequence." );
		}
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < sequence.length(); i++){
			int position = i + 1;
		    char c = sequence.charAt(i);        

		    sb.append( c );
		    
		    if( mods.containsKey( position ) ) {
		    	
		    	BigDecimal v = mods.get( position ).setScale( 4, RoundingMode.HALF_UP );
		    	sb.append( "[" );
		    	sb.append( v.toString() );
		    	sb.append( "]" );
		    	
		    }
		}
		
		return sb.toString();	
	}	
}
