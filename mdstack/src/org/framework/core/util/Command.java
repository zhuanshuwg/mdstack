package org.framework.core.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.log4j.Logger;

public class Command {

	private static final Logger log = Logger.getLogger(Command.class);
	
	/**
	 * 执行linux shell命令
	 * @param cmd
	 * @return
	 */
	public static String runCmd(String[] cmd) {
		String statusInfo = "";
		Process process;
		try {
			process = Runtime.getRuntime().exec(cmd);
			InputStreamReader isr = new InputStreamReader(
					process.getInputStream());
			LineNumberReader  lnr = new LineNumberReader(isr);

			String line;
			while ((line = lnr.readLine()) != null) {
				statusInfo += line + "\n";
			}
			String command = "";
			for(String c : cmd){
				command += c + " ";
			}
			process.waitFor();
			lnr.close();lnr = null;
			isr.close();isr = null;
			process.destroy();process = null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return statusInfo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String cmd = "sudo salt aa bb cc --oupput=json";
		for(String a : cmd.split(" ")){
		}
	}

}
