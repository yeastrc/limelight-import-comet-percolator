package org.yeastrc.limelight.xml.comet_percolator.reader;

import org.yeastrc.limelight.xml.comet_percolator.objects.CometReportedPeptide;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometResults;
import org.yeastrc.limelight.xml.comet_percolator.objects.PercolatorPSM;
import org.yeastrc.limelight.xml.comet_percolator.objects.PercolatorResults;
import org.yeastrc.limelight.xml.comet_percolator.utils.CometParsingUtils;
import org.yeastrc.limelight.xml.comet_percolator.utils.ConversionUtils;

import java.util.Map;

public class CometPercolatorValidator {

	/**
	 *
	 * @param cometResultsByFile
	 * @param percolatorResults
	 * @throws Exception
	 */
	public static void validateData(Map<String, CometResults> cometResultsByFile, PercolatorResults percolatorResults ) throws Exception {
		
		for( String percolatorReportedPeptide : percolatorResults.getReportedPeptideResults().keySet() ) {

			for (int scanNumber : percolatorResults.getReportedPeptideResults().get(percolatorReportedPeptide).getPercolatorPSMs().keySet()) {
				PercolatorPSM percPSM = percolatorResults.getReportedPeptideResults().get(percolatorReportedPeptide).getPercolatorPSMs().get( scanNumber );

				String pepXMLFileName = ConversionUtils.getPepXMLPrefixFromPsmId( percPSM.getPsmId() ) + ".pep.xml";

				if( !cometResultsByFile.containsKey( pepXMLFileName ) ) {
					throw new Exception( "Did not have any comet results for pep XML file " + pepXMLFileName + " for a psm with id " + percPSM.getPsmId() );
				}

				CometResults cometResults = cometResultsByFile.get( pepXMLFileName );
				CometReportedPeptide cometReportedPeptide = CometParsingUtils.getCometReportedPeptideForString(percolatorReportedPeptide, cometResults);

				if (!cometResults.getPeptidePSMMap().get(cometReportedPeptide).containsKey(scanNumber)) {
					throw new Exception("Error: Could not find PSM data for scan number " + scanNumber + " in percolator results for peptide: " + percolatorReportedPeptide);
				}

			}
		}
	}
	
}
