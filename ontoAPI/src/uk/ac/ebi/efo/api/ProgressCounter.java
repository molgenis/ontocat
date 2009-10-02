/**
 * 
 */
package uk.ac.ebi.efo.api;

/**
 * @author Tomasz
 * @version $Id:$
 * 
 */
public class ProgressCounter {
	private int runningTotalAdded;
	private int runningTotalUpdated;
	private int currentAdded;
	private int currentUpdated;

	final void incrementAdded() {
		this.runningTotalAdded++;
		this.currentAdded++;
	}

	final void incrementUpdated() {
		this.currentUpdated++;
		this.runningTotalUpdated++;
	}

	final int getTotalAdded() {
		return runningTotalAdded;
	}

	final int getTotalNew() {
		return runningTotalAdded - runningTotalUpdated;
	}

	final int getCurrentAdded() {
		return currentAdded;
	}

	final int getCurrentNew() {
		return currentAdded - currentUpdated;
	}

	final void resetCurrent() {
		this.currentAdded = 0;
		this.currentUpdated = 0;
	}

	public ProgressCounter() {
		this.runningTotalAdded = 0;
		this.runningTotalUpdated = 0;
		this.currentAdded = 0;
		this.currentUpdated = 0;
	}
}
