// Compute all relationships in an ontology
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;

/**
 * Example 16
 * 
 * Shows how to compute all relationships in a multi process fashion. Note that
 * OWL API stalls when accessed by multiple threads, so process level separation
 * is required. This approach effectively implements a CyclicBarrier in a disk
 * store to sync up process while they wait for new data to become available. <br>
 * A typical scenario involves running the script with a start node set to
 * initalise the queue. Then running multiple process (note that number of
 * actually running processes has to be set, so that everything finishes once
 * all the process hit the waiting queue, i.e. no more new data is being
 * generated). <br>
 * This will output ontocat.dot and ontocat.sif fils when finished, which contain
 * the inferred triples in graphviz's dot and cytoscape's sif file formats.
 */
public class Example16 {
	private static OntologyService os;
	private static URI uOntology;
	private static Stack<OntologyTerm> processQueue;
	private static File qFile;
	private static int maxTasks;
	private static File bFile;
	// in lieu of an actual process id
	private static UUID myPID = UUID.randomUUID();
	private static File dFile;
	private static File sFile;
	private static File cFile;
	private static final Logger log = Logger.getLogger(Example16.class);

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

		String startNode = null;
		try {
			parser.parse(args);
			uOntology = new URI((String) parser.getOptionValue(oUri));
			startNode = (String) parser.getOptionValue(oStartNode);
			maxTasks = (Integer) parser.getOptionValue(oThreads);
		} catch (CmdLineParser.OptionException e) {
		} catch (NullPointerException e) {
		}

		// set up
		do {
			try {
				os = new ReasonedFileOntologyService(uOntology, "efo");
			} catch (Exception e) {
				// this is bound to fail if >10 processes will try
				// to access the same resource, catch the exception
				// and retry
				log.debug("Service creation failed. Retrying.");
				Thread.sleep(500);
			}
		} while (os == null);
		System.out.println(getPID());

		// Get a file channel for the file
		qFile = new File("queue.txt");
		bFile = new File("barrier.txt");
		dFile = new File("ontocat.dot");
		sFile = new File("ontocat.sif");
		cFile = new File("seen_cache.txt");

		// initialise phase
		if (startNode != null) {
			OntologyTerm ot = os.getTerm(startNode);
			processQueue = new Stack<OntologyTerm>();
			processQueue.push(ot);
			initialiseQueue();
			resetBarrier();
		} else {
			processNodes();
		}

	}

	private static void initialiseQueue() throws IOException {
		RandomAccessFile ras = new RandomAccessFile(qFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		ObjectOutputStream oos = new ObjectOutputStream(
				Channels.newOutputStream(channel));
		oos.writeObject(processQueue);
		lock.release();
		ras.close();

		ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(
				cFile));
		fos.writeObject(new HashSet<OntologyTerm>());
		fos.close();

		log.info("Serialized the queue successfully " + processQueue.size());
	}

	@SuppressWarnings("unchecked")
	private static OntologyTerm getNextAvailableTerm() throws IOException,
	ClassNotFoundException {
		// deserialize queue
		OntologyTerm result = null;

		// lock
		RandomAccessFile ras = new RandomAccessFile(qFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		processQueue = (Stack<OntologyTerm>) ois.readObject();
		ras.seek(0);
		// pop
		if (!processQueue.isEmpty()) {
			result = processQueue.pop();
		}

		// serialise back
		// might want to do an else
		ObjectOutputStream oos = new ObjectOutputStream(
				Channels.newOutputStream(channel));
		oos.writeObject(processQueue);
		oos.flush();
		// log.info("Serialized the queue successfully " + processQueue.size());

		lock.release();
		ras.close();
		return result;
	}

	private static void writeDOT(String s) throws IOException {
		// lock
		RandomAccessFile ras = new RandomAccessFile(dFile, "rw");
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();
		ras.seek(ras.length());

		// deserialise
		Writer writer = Channels.newWriter(channel, "UTF-8");
		PrintWriter pw = new PrintWriter(writer, true);

		pw.println(s);

		lock.release();
		ras.close();
	}

	private static void writeSIF(String s) throws IOException {
		// lock
		RandomAccessFile ras = new RandomAccessFile(sFile, "rw");
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();
		ras.seek(ras.length());

		// deserialise
		Writer writer = Channels.newWriter(channel, "UTF-8");
		PrintWriter pw = new PrintWriter(writer, true);

		pw.println(s);

		lock.release();
		ras.close();
	}

	private static void addTermsToQueue(Set<OntologyTerm> terms)
	throws IOException, ClassNotFoundException, SecurityException {
		if (terms.isEmpty()) {
			return;
		}
		// deserialize queue
		OntologyTerm result = null;

		// lock
		RandomAccessFile ras = new RandomAccessFile(qFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise queue
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		processQueue = (Stack<OntologyTerm>) ois.readObject();

		// deserialise seen
		ObjectInputStream cis = new ObjectInputStream(
				new FileInputStream(cFile));
		Set<OntologyTerm> seenCache = (Set<OntologyTerm>) cis.readObject();
		cis.close();

		log.info(String.format("Seen %d terms so far", seenCache.size()));

		ras.seek(0);
		// add new unseen to queue
		for (OntologyTerm ot : terms) {
			if (!seenCache.contains(ot)) {
				processQueue.add(ot);
				seenCache.add(ot);
			}
		}

		// serialise back
		// might want to do an else
		ObjectOutputStream oos = new ObjectOutputStream(
				Channels.newOutputStream(channel));
		oos.writeObject(processQueue);
		oos.flush();

		ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(
				cFile));
		fos.writeObject(seenCache);
		fos.close();
		// log.info("Serialized the queue successfully " + processQueue.size());

		lock.release();
		ras.close();
	}

	static UUID getPID() {
		return myPID;
	}

	private static int getNumberWaiting() throws IOException,
	ClassNotFoundException {
		// deserialize queue
		Set<UUID> result = null;

		// lock
		RandomAccessFile ras = new RandomAccessFile(bFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		result = (Set<UUID>) ois.readObject();

		lock.release();
		ras.close();
		return result.size();
	}

	private static boolean isAtBarrier(UUID uuid) throws IOException,
	ClassNotFoundException {
		// deserialize queue
		Set<UUID> result = null;

		// lock
		RandomAccessFile ras = new RandomAccessFile(bFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		result = (Set<UUID>) ois.readObject();

		lock.release();
		ras.close();

		return result.contains(uuid);
	}

	private static void addToBarrier(UUID uuid) throws IOException,
	ClassNotFoundException {
		// deserialize queue
		// lock
		RandomAccessFile ras = new RandomAccessFile(bFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		Set<UUID> set = (Set<UUID>) ois.readObject();
		// log.info("Deserialized the queue successfully " +
		// processQueue.size());

		ras.seek(0);
		// add new
		set.add(uuid);

		// serialise back
		// might want to do an else
		ObjectOutputStream oos = new ObjectOutputStream(
				Channels.newOutputStream(channel));
		oos.writeObject(set);
		oos.flush();
		// log.info("Serialized the queue successfully " + processQueue.size());

		lock.release();
		ras.close();
	}

	private static void resetBarrier() throws IOException,
	ClassNotFoundException {

		// lock
		RandomAccessFile ras = new RandomAccessFile(bFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		ObjectOutputStream oos = new ObjectOutputStream(
				Channels.newOutputStream(channel));
		oos.writeObject(new HashSet<UUID>());
		oos.flush();

		lock.release();
		ras.close();
	}

	private static Integer getQueueSize() throws IOException,
	ClassNotFoundException {
		// deserialize queue
		Integer result = null;

		// lock
		RandomAccessFile ras = new RandomAccessFile(qFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		processQueue = (Stack<OntologyTerm>) ois.readObject();
		// log.info("Deserialized the queue successfully " +
		// processQueue.size());

		lock.release();
		ras.close();
		return processQueue.size();

	}

	private static void processNodes() throws IOException,
	ClassNotFoundException, InterruptedException {

		do {
			OntologyTerm currentNode;
			while (getNumberWaiting() == 0 && (currentNode = getNextAvailableTerm()) != null) {
				log.debug(" processing " + currentNode.getLabel()
						+ " (queue size: "
						+ processQueue.size()
						+ ")");

				Set<OntologyTerm> isaChildren = null;
				Map<String, Set<OntologyTerm>> mOtherChildren = null;

				try {
					isaChildren = new HashSet<OntologyTerm>(
							os.getChildren(currentNode));

					mOtherChildren = os.getRelations(
							currentNode.getOntologyAccession(),
							currentNode.getAccession());
				} catch (OntologyServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// remove isa children inferred by the reasoner
				for (Set<OntologyTerm> sOtherChildren : mOtherChildren.values()) {
					isaChildren.removeAll(sOtherChildren);
				}

				// print isa children
				for (OntologyTerm term : isaChildren) {
					String dotPart = "\"" + term.getLabel()
					+ "\"->\""
					+ currentNode.getLabel()
					+ "\""
					+ "[label=\"isa\",len=\"2.5\"];";

					// dot += dotPart;
					System.out.println(dotPart);
					writeDOT(dotPart);
					writeSIF(term.getLabel() + "\tisa\t"
							+ currentNode.getLabel());
				}
				addTermsToQueue(isaChildren);

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
						// dot += dotPart;
						System.out.println(dotPart);
						writeDOT(dotPart);
						writeSIF(currentNode.getLabel() + "\t"
								+ e.getKey()
								+ "\t"
								+ term.getLabel());
					}
					addTermsToQueue(e.getValue());
				}

				log.debug(" finished " + currentNode.getLabel()
						+ " (queue size: "
						+ processQueue.size()
						+ ")");
			}

			// if new data available break the barrier and
			// release other tasks
			if (getQueueSize() > 0) {
				log.debug(getPID() + " New data available. Releasing "
						+ getNumberWaiting()
						+ " task(s) waiting");
				resetBarrier();
				// wait at the barrier for new data to arrive
			} else {
				int taskNo = getNumberWaiting() + 1;
				log.debug(String.format(
						getPID() + " %d task(s) waiting for new data", taskNo));

				addToBarrier(getPID());
				while (isAtBarrier(getPID()) && (getNumberWaiting() != maxTasks)) {
					Thread.sleep(500);
				}

				log.debug(String.format(
						getPID() + " Task %d released for new data", taskNo));

			}

		} while (getNumberWaiting() != maxTasks);
		log.debug(String.format(
				getPID() + " Task finished. Process queue size %d",
				processQueue.size()));
	}
}