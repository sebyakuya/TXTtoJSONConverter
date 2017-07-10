package whatsapp.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class TXTtoJSONConverter {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
	
	private List<String> errors;
	private JSONArray array;
	
	public TXTtoJSONConverter(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		errors = new ArrayList<String>();
		array = new JSONArray();
	}

	public void convert(String[] args) throws IOException{
		BufferedReader whatsappReader = null;
		BufferedWriter toJson = null;

		try {
			whatsappReader = new BufferedReader(new FileReader(args[0]));
			toJson = new BufferedWriter(new FileWriter(args[1]));
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		String bufferedLine = whatsappReader.readLine();
		while (bufferedLine != null) {
			String line = bufferedLine;
			JSONObject json = new JSONObject();

			try {

				String date = StringUtils.substringBefore(bufferedLine, "-");
				line = StringUtils.substringAfter(line, "-");
				String name = StringUtils.substringBefore(line, ":").substring(1);
				line = StringUtils.substringAfter(line, ":");
				String message = line.substring(1);

				json.put("date", dateFormat.parse(date));
				json.put("name", name);
				json.put("message", message);
				
			} catch (Exception e) {
				errors.add(bufferedLine);
			}

			array.put(json);
			bufferedLine = whatsappReader.readLine();
		}
		System.out.println(errors.size() + " errors detected:");
		System.out.println(errors);

		whatsappReader.close();
		toJson.write(array.toString());
		toJson.close();
	}

}
