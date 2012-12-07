// Compute all relationships in a multithreaded fashion
/**
 * Copyright (c) 2010 - 2011 European Molecular Biology Laboratory and University of Groningen
 *
 * Contact: ontocat-users@lists.sourceforge.net
 * 
 * This file is part of OntoCAT
 * 
 * OntoCAT is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * 
 * OntoCAT is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along
 * with OntoCAT. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ebi.ontocat.examples;

import jargs.gnu.CmdLineParser;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;

/**
 * MultithreadedReasoner
 * 
 * Shows how to compute all relationships in a multithreaded fashion.
 * Unfortunately this doesn't work because OWL API stalls if used by multiple
 * threads. A working alternative is Example15 which employs a multi process
 * approach.
 */
public class MultithreadedReasoner {
	private static OntologyService os;
	private static URI uOntology;
	private static String dot = "";
	private static int maxTasks = 5;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {

		CmdLineParser parser = new CmdLineParser();
		CmdLineParser.Option oUri = parser.addStringOption('o', "ontology");
		CmdLineParser.Option oStartNode = parser.addStringOption('s',
		"startnode");
		CmdLineParser.Option oThreads = parser.addIntegerOption('t', "threads");

		String startNode;
		try {
			parser.parse(args);

			maxTasks = (Integer) parser.getOptionValue(oThreads);
			uOntology = new URI((String) parser.getOptionValue(oUri));
			startNode = (String) parser.getOptionValue(oStartNode);

		} catch (CmdLineParser.OptionException e) {
			throw new Exception("Wrong arguments");
		} catch (NullPointerException e) {
			throw new Exception("Wrong arguments");
		}

		// uOntology = new File(
		// "C:\\work\\EFO\\EFO on bar\\ExperimentalFactorOntology\\ExFactorInOWL\\efoarchivereleaseversions\\efo_v2_11.owl")
		// .toURI();
		// System.out.println(uOntology);
		// uOntology = new URI("http://www.ebi.ac.uk/efo/efo.owl");

		// Build a tree of relations (including partonomy)
		// To make it quicker we'll focus
		// on the skeleton structure branch (EFO_0000806)
		// to compute a larger tree try organism part (EFO_0000635)
		// heart EFO_0000815

		// instantiate the threads
		ExecutorService executor = Executors.newFixedThreadPool(maxTasks);
		for (int i = 1; i <= maxTasks; i++) {
			executor.execute(new RelationsWalker(uOntology, startNode));
		}

		// Call shutdown and wait for the processes to finish
		executor.shutdown();
		executor.awaitTermination(24, TimeUnit.HOURS);

		System.out.println(dot);
		downloadFile(dot, "dot");
		downloadFile(dot, "neato");
		downloadFile(dot, "twopi");
		// downloadFile(graphviz_dot, "circo");
		// downloadFile(dot, "fdp");
	}

	public static void downloadFile(String dot, String format)
	throws IOException {
		String postData = "cht=gv:" + format
		+ "&chl=digraph{"
		+ dot
		+ "}";

		URL url = new URL("https://chart.googleapis.com/chart");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(postData);
		wr.flush();
		wr.close();

		java.io.BufferedInputStream in = new java.io.BufferedInputStream(
				conn.getInputStream());
		java.io.FileOutputStream fos = new java.io.FileOutputStream(
				"tree_" + format
				+ ".png");
		java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
		byte[] data = new byte[1024];
		int x = 0;
		while ((x = in.read(data, 0, 1024)) >= 0) {
			bout.write(data, 0, x);
		}
		bout.close();
		in.close();
	}

	public static class RelationsWalker implements Runnable {
		private static final Logger log = Logger
		.getLogger(RelationsWalker.class);
		private OntologyService os;
		private static Stack<OntologyTerm> processQueue = null;
		private static int progressCounter = 0;
		private static final CyclicBarrier barrier = new CyclicBarrier(
				maxTasks, new Runnable() {
					@Override
					public void run() {
						log.info("All finished. Closing up.");
					}
				});

		public RelationsWalker(URI uOntology, String startNode)
		throws OntologyServiceException {
			// instantiate a new ontologyservice to get a separate
			// reasoner instance
			this.os = new ReasonedFileOntologyService(uOntology, "efo");

			initialiseQueue(os.getTerm(startNode));
		}

		private synchronized void initialiseQueue(OntologyTerm startNode) {
			// Initialise cache, classes will be popped from the stack
			// asynchronously
			if (processQueue == null) {
				processQueue = new Stack<OntologyTerm>();
				processQueue.push(startNode);
			}
		}

		private synchronized OntologyTerm getNextAvailableTerm() {
			if (!processQueue.empty() && processQueue.size() % 100 == 0) {
				log.info(processQueue.size() + " classes left to process (touched "
						+ progressCounter
						+ " annotations).");
			}
			// only a single thread will report it (0 would be reported by all)
			// if (processQueue.size() == 1) {
			// log.info("Completed with " + (progressCounter + 1)
			// + " annotations");
			// }
			if (!processQueue.empty()) {
				return processQueue.pop();
			} else {
				return null;
			}
		}



		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// we're going to sync all threads on a CyclicBarrier
			// in case the processQueue goes down to 0 and needs
			// to be re-populated by a single thread
			// this will happen at least once on start
			do {
				OntologyTerm currentNode;
				while (barrier.getNumberWaiting() == 0 && (currentNode = getNextAvailableTerm()) != null) {
					log.debug(this.hashCode() + " processing "
							+ currentNode.getLabel()
							+ " (queue size: "
							+ processQueue.size()
							+ ")");

					Set<OntologyTerm> isaChildren = null;
					Map<String, Set<OntologyTerm>> mOtherChildren = null;

					try {
						isaChildren = new HashSet<OntologyTerm>(
								this.os.getChildren(currentNode));

						mOtherChildren = this.os.getRelations(
								currentNode.getOntologyAccession(),
								currentNode.getAccession());
					} catch (OntologyServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// remove isa children inferred by the reasoner
					// under a structure defined specifically to capture a
					// relationship
					// example skeleton structure, or skeleton disease
					// these will be shown with the original relationship in the
					// next step
					for (Set<OntologyTerm> sOtherChildren : mOtherChildren
							.values()) {
						isaChildren.removeAll(sOtherChildren);
					}

					// print isa children
					for (OntologyTerm term : isaChildren) {
						String dotPart = "\"" + term.getLabel()
						+ "\"->\""
						+ currentNode.getLabel()
						+ "\""
						+ "[label=\"isa\",len=\"2.5\"];";

						dot += dotPart;
						System.out.println(dotPart);
						synchronized (processQueue) {
							processQueue.push(term);
						}
					}
					// print other children
					for (Entry<String, Set<OntologyTerm>> e : mOtherChildren
							.entrySet()) {
						for (OntologyTerm term : e.getValue()) {
							String dotPart = "\"" + currentNode.getLabel()
							+ "\"->\""
							+ term.getLabel()
							+ "\""
							+ "[label=\""
							+ e.getKey()
							+ "\",style=\"dotted\",len=\"2.5\"];";
							dot += dotPart;
							System.out.println(dotPart);
							synchronized (processQueue) {
								processQueue.push(term);
							}
						}
					}

					log.debug(this.hashCode() + " finished "
							+ currentNode.getLabel()
							+ " (queue size: "
							+ processQueue.size()
							+ ")");
				}
				// if new data available break the barrier and
				// release other tasks
				if (!processQueue.empty()) {
					log.debug(this.hashCode() + " New data available. Releasing "
							+ barrier
							.getNumberWaiting()
							+ " task(s) waiting");
					barrier.reset();
					// wait at the barrier for new data to arrive
				} else {
					int taskNo = barrier.getNumberWaiting() + 1;
					log.debug(String.format(
							"%d %d/%d task(s) waiting for new data",
							this.hashCode(),
							barrier.getNumberWaiting() + 1,
							barrier.getParties()));
					try {
						barrier.await();
						log.debug(String.format(
								"%d Task %d finished. Process queue size %d",
								this.hashCode(), taskNo, processQueue.size()
						));
						break;
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						log.debug(String.format(
								"%d Task %d released for new data",
								this.hashCode(), taskNo));
					}
				}

			} while (true);
		}
	}
}