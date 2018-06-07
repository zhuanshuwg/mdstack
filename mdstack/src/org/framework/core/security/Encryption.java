package org.framework.core.security;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class Encryption {
	
	private static final Logger log = Logger.getLogger(Encryption.class);
	
	public String getGUID(){
		String server_info = this.getServerInfo();
		String md5_str = DigestUtils.md5Hex(server_info);
		return md5_str.substring(0, 8) 
				+ "-" + md5_str.substring(8, 16) 
				+ "-" + md5_str.substring(16, 24) 
				+ "-" + md5_str.substring(24, 32);
	}
	
	public String getServerInfo(){
		
		String mac_str = "",tmp_mac_str = "";
		String cpu_str = "",tmp_cpu_str = "";
		String[] cpus ;
		String mobo_sn_str = "",tmp_mobo_sn_str = "";
		
		try {
			tmp_mac_str = runCmd("sudo salt-call --local network.hwaddr eth0 --output=json".split(" ")," ");
		} catch (Exception e) {
			tmp_mac_str = "{\"local\":\"0A:0B:0C:0D:0E:0F\"}";
		}
		
		try {
			tmp_cpu_str = runCmd("sudo dmidecode -t processor | grep 'ID'".split(" "),",");
		} catch (Exception e) {
			tmp_cpu_str = "ID:F0 F1 F2 F3 F4 F5 F6 F7";
		}
		
		try {
			tmp_mobo_sn_str = runCmd("sudo dmidecode -s baseboard-serial-number".split(" ")," ");
		} catch (Exception e) {
			tmp_mobo_sn_str = "888888888888666";
		}
		mac_str = tmp_mac_str.replaceAll("local", "MAC").replaceAll("\\{", "").replaceAll("\\}", "");
		cpus = tmp_cpu_str.split(",");
		int index = 0;
		for(String cpu : cpus){
			cpu_str += "\"CPU ID\":\"" + cpu.split(":")[1] + "\"";
			if(++index != cpus.length){
				cpu_str += "\r";
			}
		}
		mobo_sn_str = "\"Mobo SN\":\"" + tmp_mobo_sn_str + "\"";
		
		return mac_str + "\r" + cpu_str + "\r" + mobo_sn_str;
	}
	
	public String runCmd(String[] cmd,String split) {
		String rtnString = "";
		Process process;
		try {
			process = Runtime.getRuntime().exec(cmd);
			InputStreamReader isr = new InputStreamReader(
					process.getInputStream());
			LineNumberReader  lnr = new LineNumberReader(isr);

			String line;
			while ((line = lnr.readLine()) != null) {
				rtnString += line + split;
			}
			process.waitFor();
			lnr.close();
			lnr = null;
			isr.close();
			isr = null;
			process.destroy();
			process = null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return rtnString;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String md5_str = DigestUtils.md5Hex("admin");
		System.out.println(md5_str);
	}

}
