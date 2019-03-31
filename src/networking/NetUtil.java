package networking;


import java.util.ArrayList;
import java.util.List;

public class NetUtil {
	public static List<String> getParts(String string, int partitionSize) {
        List<String> parts = new ArrayList<String>();
        int len = string.length();
        for (int i=0; i<len; i+=partitionSize)
        {
        	String partString = string.substring(i, Math.min(len, i + partitionSize));
        	while(partString.length() != partitionSize){
        		partString=partString+"0";
        	}
            parts.add(partString);
        }
        return parts;
    }
	public static int getLastNewLine(String input){
		for(int i = input.length()-1; i >= 0; i--){
			if(input.charAt(i)=='\n'){
				return i;
			}
		}
		return -1;
	}
}
