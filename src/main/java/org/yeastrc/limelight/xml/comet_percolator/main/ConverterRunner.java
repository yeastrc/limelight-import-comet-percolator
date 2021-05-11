/*
 * Original author: Michael Riffle <mriffle .at. uw.edu>
 *                  
 * Copyright 2018 University of Washington - Seattle, WA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yeastrc.limelight.xml.comet_percolator.main;

import org.yeastrc.limelight.xml.comet_percolator.builder.XMLBuilder;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometParameters;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometResults;
import org.yeastrc.limelight.xml.comet_percolator.objects.ConversionParameters;
import org.yeastrc.limelight.xml.comet_percolator.objects.PercolatorResults;
import org.yeastrc.limelight.xml.comet_percolator.reader.CometParamsReader;
import org.yeastrc.limelight.xml.comet_percolator.reader.CometPepXMLResultsParser;
import org.yeastrc.limelight.xml.comet_percolator.reader.CometPercolatorValidator;
import org.yeastrc.limelight.xml.comet_percolator.reader.PercolatorResultsReader;
import org.yeastrc.limelight.xml.comet_percolator.utils.ConversionUtils;

import java.io.File;
import java.util.Collection;

public class ConverterRunner {

	// conveniently get a new instance of this class
	public static ConverterRunner createInstance() { return new ConverterRunner(); }
	
	
	public void convertCometPercolatorToLimelightXML(ConversionParameters conversionParameters ) throws Throwable {

		{
			System.err.print("Reading comet params into memory...");
			CometParameters cometParams = CometParamsReader.getCometParameters(conversionParameters.getCometParametersFile());
			ConversionUtils.conditionallyAddFastaToConversionParameters(conversionParameters, cometParams);
			System.err.println(" Done.");

			System.err.print("Reading Percolator XML data into memory...");
			PercolatorResults percResults = PercolatorResultsReader.getPercolatorResults(conversionParameters.getPercolatorXMLFile());
			System.err.println(" Done.");

			System.err.print("Locating pepXML files using Percolator results...");
			Collection<File> pepXMLFiles = ConversionUtils.findPepXMLFilesUsingPercolatorResults(conversionParameters, percResults);
			System.err.println(" Done.");

			System.err.print("Reading Comet pepXML data into memory...");
			CometResults cometResults = CometPepXMLResultsParser.getCometResults(pepXMLFiles, cometParams, percResults.getReportedPeptideResults().keySet());
			System.err.println(" Done.");

			System.err.print("Verifying all percolator results have comet results...");
			CometPercolatorValidator.validateData(cometResults, percResults);
			System.err.println(" Done.");

			System.err.print("Writing out XML...");
			(new XMLBuilder()).buildAndSaveXML(conversionParameters, cometResults, percResults, cometParams);
			System.err.println(" Done.");
		}

		// validate the limelight xml
		System.err.print( "Validating Limelight XML..." );
		LimelightXMLValidator.validateLimelightXML(conversionParameters.getLimelightXMLOutputFile());
		System.err.println( " Done." );

	}
}
