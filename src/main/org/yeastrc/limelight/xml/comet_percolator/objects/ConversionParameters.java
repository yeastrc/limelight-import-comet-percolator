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

package org.yeastrc.limelight.xml.comet_percolator.objects;

import java.io.File;
import java.util.Map;

public class ConversionParameters {
		
	/**
	 * @return the fastaFile
	 */
	public File getFastaFile() {
		return fastaFile;
	}
	/**
	 * @param fastaFile the fastaFile to set
	 */
	public void setFastaFile(File fastaFile) {
		this.fastaFile = fastaFile;
	}
	/**
	 * @return the cometParametersFile
	 */
	public File getCometParametersFile() {
		return cometParametersFile;
	}
	/**
	 * @param cometParametersFile the cometParametersFile to set
	 */
	public void setCometParametersFile(File cometParametersFile) {
		this.cometParametersFile = cometParametersFile;
	}
	/**
	 * @return the limelightXMLOutputFile
	 */
	public File getLimelightXMLOutputFile() {
		return limelightXMLOutputFile;
	}
	/**
	 * @param limelightXMLOutputFile the limelightXMLOutputFile to set
	 */
	public void setLimelightXMLOutputFile(File limelightXMLOutputFile) {
		this.limelightXMLOutputFile = limelightXMLOutputFile;
	}
	/**
	 * @return the conversionProgramInfo
	 */
	public ConversionProgramInfo getConversionProgramInfo() {
		return conversionProgramInfo;
	}
	/**
	 * @param conversionProgramInfo the conversionProgramInfo to set
	 */
	public void setConversionProgramInfo(ConversionProgramInfo conversionProgramInfo) {
		this.conversionProgramInfo = conversionProgramInfo;
	}
	
	/**
	 * @return the percolatorXMLFile
	 */
	public File getPercolatorXMLFile() {
		return percolatorXMLFile;
	}
	/**
	 * @param percolatorXMLFile the percolatorXMLFile to set
	 */
	public void setPercolatorXMLFile(File percolatorXMLFile) {
		this.percolatorXMLFile = percolatorXMLFile;
	}

	public File getPepXMLDataDirectory() {
		return pepXMLDataDirectory;
	}

	public void setPepXMLDataDirectory(File pepXMLDataDirectory) {
		this.pepXMLDataDirectory = pepXMLDataDirectory;
	}

	private File fastaFile;
	private File cometParametersFile;
	private File limelightXMLOutputFile;
	private File pepXMLDataDirectory;
	private File percolatorXMLFile;
	private ConversionProgramInfo conversionProgramInfo;
	
}
