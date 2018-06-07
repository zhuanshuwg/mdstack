package org.framework.core.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * cfg文件解析工具
 * 
 * @author wangguan
 * 
 */
public class CfgParser {

	private static final Logger log = Logger.getLogger(CfgParser.class);

	/**
	 * 根据文件、组和项获取值
	 * 
	 * @param group
	 * @param item
	 * @return
	 */
	public static String getValue(String file, String group, String item) {
		if (group == null || item == null) {
			return null;
		}
		String item_str = null;
		BufferedReader br;
		try {
			FileInputStream in_file = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(in_file));
			StringBuffer sb = new StringBuffer();
			String str = null;
			while ((str = br.readLine()) != null) {
				if (str.trim().startsWith("#") || str.trim().equals("")) {
					continue;
				}
				sb.append(str + "\r\n");
			}
			String sb_str = sb.toString();
			int group_start_index = sb_str.indexOf("[" + group + "]");
			int group_end_index = sb_str.indexOf("[", group_start_index + 1);

			if (group_end_index < group_start_index) {
				group_end_index = sb_str.length();
			}
			String group_str = sb_str.substring(group_start_index,
					group_end_index);
			int item_start_index = group_str.indexOf(item + "=");
			int item_end_index = group_str
					.indexOf("\r\n", item_start_index + 1);

			item_str = group_str.substring(
					item_start_index + item.length() + 1, item_end_index);
			br.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return item_str;
	}

	/**
	 * 修改cfg项的值
	 * 
	 * @param file
	 * @param group
	 * @param item
	 */
	public static void replace(String file, String group, String item,
			String value) {
		BufferedReader br;
		try {
			FileInputStream in_file = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(in_file));
			StringBuffer sb = new StringBuffer();
			String str = null;
			while ((str = br.readLine()) != null) {
				if (str.startsWith(item + "=")) {
					if (sb.toString().indexOf("[" + group + "]") > 0) {
						str = item + "=" + value;
					}
				}
				sb.append(str + "\r\n");
			}

			FileOutputStream out_file = new FileOutputStream(file);
			out_file.write(sb.toString().getBytes());
			br.close();
			out_file.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		String file = "C:/Users/HouqiZhang/Desktop/network-speed02.cfg";
		replace(file, "traceroute", "cmd.w", "1");
	}

}